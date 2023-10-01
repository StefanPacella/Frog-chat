package it.project.chat.framework.data.cache;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.DaoAbstract;
import it.project.chat.framework.data.DataItem;
import it.project.chat.framework.data.Dbms;
import it.project.chat.framework.data.Function;
import it.project.chat.framework.data.QueryInsertStatement;
import it.project.chat.framework.data.QueryPreparedStatement;

public class Cache<T extends DataItem> implements WriterReaderProblem<T> {

	//// per semplicità uso l'algoritmo lru
	private LinkedList<T> lru = new LinkedList<T>();
	private int maximumNumberOfItems;
	/// la lista tag memorizza quali viste sono presenti nella cache
	private List<Tag<T>> listTag = new ArrayList<>();
	private volatile int nWriter = 0;
	private volatile Semaphore semaphoreR = new Semaphore(1);
	private volatile Semaphore semaphoreWr = new Semaphore(1);

	private DaoAbstract<T> daoAbstract;

	public Cache(int maximumNumberOfItems, DaoAbstract<T> daoAbstract) {
		this.maximumNumberOfItems = maximumNumberOfItems;
		this.daoAbstract = daoAbstract;
	}

	public Optional<T> get(final Predicate<T> predicate, QueryPreparedStatement<T> queryDataBase)
			throws BusinessException {
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				this.setElement(lru.stream().filter(x -> predicate.test(x)).findFirst());
			}
		};
		Reader<T> reader = new Reader<T>(o, this);
		reader.read();
		Optional<T> r = o.getElement();
		if (r.isEmpty()) {
			getDbms().makeConnectionAndQuery(queryDataBase);
			if (queryDataBase.getData() != null)
				r = Optional.of(queryDataBase.getData());
			final Optional<T> ed = r;
			if (ed.isPresent()) {
				Operation<T> w = new Operation<T>() {
					@Override
					public void operation() throws BusinessException {
						// TODO Auto-generated method stub
						methodAddInCacheForGetMethod(ed.get());
					}
				};
				Writer<T> writer = new Writer<T>(this, w);
				writer.writeAsync();
			}
		}
		return r;
	}

	public List<T> getList(String label, final Predicate<T> predicate, QueryPreparedStatement<List<T>> queryDataBases)
			throws BusinessException {
		List<T> l = getList(label);
		if (!l.isEmpty())
			return l;
		else {
			getDbms().makeConnectionAndQuery(queryDataBases);
			List<T> ldbms = queryDataBases.getData();
			addTag(label, predicate);
			addAllSync(ldbms);
			return ldbms;
		}
	}

	private void methodAddInCacheForGetMethod(final T e) {
		Optional<T> element = lru.stream().filter(z -> z.getId().equals(e.getId())).findFirst();
		try {
			if (element.get().getVersione() < e.getVersione()) {
				lru.remove(element.get());
				lru.addFirst(e);
				if (lru.size() >= maximumNumberOfItems) {
					T t = lru.removeLast();
					checkListPredicate(t);
				}
			}
		} catch (NoSuchElementException ex) {
			// TODO: handle exception
			lru.addFirst(e);
			if (lru.size() >= maximumNumberOfItems) {
				T t = lru.removeLast();
				checkListPredicate(t);
			}
		}
	}

	public List<T> callDbms(QueryPreparedStatement<List<T>> queryDataBase) throws BusinessException {
		getDbms().makeConnectionAndQuery(queryDataBase);
		return queryDataBase.getData();
	}

	public T callDbmsElement(QueryPreparedStatement<T> queryDataBase) throws BusinessException {
		getDbms().makeConnectionAndQuery(queryDataBase);
		return queryDataBase.getData();
	}

	public void update(Predicate<T> p, T y, QueryInsertStatement<T> queryDataBase,
			QueryPreparedStatement<T> querySelect) {
		T element = y;
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				element.setVersione(optimisticLocking(p, element, querySelect));

				Optional<T> optional = lru.stream().filter(x -> p.test(x)).findFirst();
				try {
					lru.remove(optional.get());
					checkListPredicate(optional.get());
					element.setId(optional.get().getId());
				} catch (NoSuchElementException e) {
					// TODO: handle exception
				}
				getDbms().makeConnectionAndQuery(queryDataBase);
				lru.add(element);
			}
		};
		Writer<T> writer = new Writer<T>(this, o);
		writer.writeSync();
	}

	public void updateAll(Predicate<T> p,  Function<T> f, QueryInsertStatement<T> queryDataBase) {
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				List<T> l = lru.stream().filter(p).collect(Collectors.toList());
				for (T e : l) {
					e.setVersione(optimisticLocking(x -> x.getId().equals(e.getId()), e,
							daoAbstract.makeQuerySelectWithIdDbms(e.getId())));
					f.function(e);
					checkListPredicate(e);
				}
				getDbms().makeConnectionAndQuery(queryDataBase);
			}
		};
		Writer<T> writer = new Writer<T>(this, o);
		writer.writeSync();
	}

	private int optimisticLocking(Predicate<T> p, T y, QueryPreparedStatement<T> queryDataBase)
			throws BusinessException {
		Optional<T> optional = lru.stream().filter(x -> p.test(x)).findFirst();
		T t = null;
		try {
			t = optional.get();
		} catch (NoSuchElementException e) {
			try {
				getDbms().makeConnectionAndQuery(queryDataBase);
				t = queryDataBase.getData();
			} catch (BusinessException e1) {
				throw new BusinessException("Error query");
			}
		}
		if (t == null)
			throw new BusinessException("Entità non è presente nel data base");
		if (t.getVersione() > y.getVersione())
			throw new BusinessException("Esiste una nuova versione");
		return (t.getVersione() + 1);
	}

	public void remove(Predicate<T> predicate, QueryPreparedStatement<T> queryDataBase) {
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				try {
					Optional<T> optional = lru.stream().filter(x -> predicate.test(x)).findFirst();
					lru.remove(optional.get());
				} catch (NoSuchElementException e) {
					// TODO: handle exception
				}
				getDbms().makeConnectionAndQuery(queryDataBase);
			}
		};
		Writer<T> writer = new Writer<T>(this, o);
		writer.writeSync();
	}

	public void add(Predicate<T> predicate, T e, final QueryInsertStatement<T> queryDataBase) {
		T element = e;
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				getDbms().makeConnectionAndQuery(queryDataBase);
				element.setId(queryDataBase.getLastID());
				methodAddInCache(element, predicate);
			}
		};
		Writer<T> writer = new Writer<T>(this, o);
		writer.writeSync();
	}

	private void methodAddInCache(final T e, Predicate<T> predicate) {
		Optional<T> element = lru.stream().filter(z -> predicate.test(z)).findFirst();
		element.ifPresent(x -> {
			lru.remove(x);
		});
		lru.addFirst(e);
		if (lru.size() >= maximumNumberOfItems) {
			T t = lru.removeLast();
			checkListPredicate(t);
		}
	}

	private void addAllAsync(List<T> e) throws BusinessException {
		// TODO Auto-generated method stub
		Writer<T> writer = new Writer<T>(this, makeOperationAddList(e));
		writer.writeAsync();
	}

	private void addAllSync(List<T> e) throws BusinessException {
		// TODO Auto-generated method stub
		Writer<T> writer = new Writer<T>(this, makeOperationAddList(e));
		writer.writeSync();
	}

	private void addTag(String label, Predicate<T> predicate) {
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				try {
					Tag<T> t = listTag.stream().filter(x -> x.getLabel().equals(label)).findFirst().get();
					listTag.remove(t);
				} catch (NoSuchElementException o) {

				}
				Tag<T> t = new Tag<>();
				t.setLabel(label);
				t.setPredicate(predicate);
				listTag.add(t);
			}
		};

		Writer<T> writer = new Writer<T>(this, o);
		writer.writeAsync();
	}

	private Operation<T> makeOperationAddList(List<T> e) {
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				e.stream().forEach(x -> methodAddInCacheForGetMethod(x));
			}
		};
		return o;
	}

	private List<T> getList(String label) throws BusinessException {
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				try {
					Tag<T> p = listTag.stream().filter(x -> x.getLabel().equals(label)).findFirst().get();
					this.setList(lru.stream().filter(x -> p.getPredicate().test(x)).collect(Collectors.toList()));
				} catch (NoSuchElementException o) {
					this.setList(new ArrayList<T>());
				}
			}
		};
		Reader<T> reader = new Reader<T>(o, this);
		reader.read();
		return o.getList();
	}

	public void clear(QueryPreparedStatement<T> queryDataBase) {
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				lru.clear();
				listTag.clear();
				getDbms().makeConnectionAndQuery(queryDataBase);
			}
		};
		Writer<T> writer = new Writer<T>(this, o);
		writer.writeSync();
	}

	public void clear() {
		Operation<T> o = new Operation<T>() {
			@Override
			public void operation() throws BusinessException {
				lru.clear();
				listTag.clear();
			}
		};
		Writer<T> writer = new Writer<T>(this, o);
		writer.writeSync();
	}

	public void destroy() {
		// TODO Auto-generated method stub
		clear();
	}

	private void checkListPredicate(T t) {
		List<Tag<T>> r = listTag.stream().filter(x -> x.getPredicate().test(t)).collect(Collectors.toList());
		r.stream().forEach(x -> listTag.remove(x));
	}

	public int getnWriter() {
		return nWriter;
	}

	public void addOneToNWriter() {
		nWriter++;
	}

	public void minusOneToNWriter() {
		nWriter--;
	}

	public Semaphore getWriterSemaphore() {
		return this.semaphoreWr;
	}

	public Semaphore getReaderSemaphore() {
		return this.semaphoreR;
	}

	private Dbms getDbms() {
		return new Dbms();
	}

}
