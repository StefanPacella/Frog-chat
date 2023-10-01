package it.project.chat.framework.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import it.project.chat.framework.data.cache.Cache;

public abstract class DaoAbstract<T extends DataItem> {

	private Cache<T> cache = new Cache<T>(100000, this);
	private DaoAbstractQueryDbms<T> daoAbstractQueryDbms;

	private String entita;

	private String[] attributoChiaveDellaSelect;

	public DaoAbstract(String entita, String[] attributoChiaveDellaSelect) {
		// TODO Auto-generated constructor stub
		this.entita = entita;
		this.attributoChiaveDellaSelect = attributoChiaveDellaSelect;
		daoAbstractQueryDbms = new DaoAbstractQueryDbms<T>(this);
	}

	public void init() throws DataException {
		initDaoAbstract();
		initImpl();
	}

	public void destroy() throws DataException {
		destroyDaoAbstract();
		destroyImpl();
	}

	private void initDaoAbstract() throws DataException {

	}

	private void destroyDaoAbstract() throws DataException {
		cache.destroy();
	}

	public abstract void initImpl() throws DataException;

	public abstract void destroyImpl() throws DataException;

	public Cache<T> getCache() {
		return cache;
	}

	public Optional<T> selectConId(Integer id) throws BusinessException {
		return cache.get(x -> x.getId().equals(id), makeQuerySelectWithIdDbms(id));
	}

	public QueryPreparedStatement<T> makeQuerySelectWithIdDbms(Integer id) throws BusinessException {
		return daoAbstractQueryDbms.querySelectConid(id);
	} 
	
	public abstract Optional<T> querySelectConCondizioneSullaChiave(final String[] valore) throws BusinessException;

	public QueryPreparedStatement<T> querySelectConCondizioneSullaChiaveNelDB(final String[] valore)
			throws BusinessException {
		return daoAbstractQueryDbms.querySelectConCondizioneSullaChiaveNelDB(valore);
	}

	public abstract T risultatiSelect(PreparedStatement preparedStatement) throws SQLException;

	public void eliminaConId(final Integer id) throws BusinessException {
		cache.remove(x -> x.getId().equals(id), daoAbstractQueryDbms.eliminaConId(id));
	}

	public List<T> getLista() throws BusinessException {
		return getCache().getList(entita, x -> x.equals(x), daoAbstractQueryDbms.getLista());
	}

	public abstract T make(ResultSet rs) throws SQLException;

	public String getEntita() {
		return entita;
	}

	public String[] getAttributoChiaveDellaSelect() {
		return attributoChiaveDellaSelect;
	}

	protected void checkIfElementIsAlreadyPresentInMemoryAndThenAdd(String[] valore, T t) throws BusinessException {
		Optional<T> o = querySelectConCondizioneSullaChiave(valore);
		try {
			o.get();
			throw new BusinessException("Elemento gia presente");
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			methodAddInCacheAndDbms(t);
		}
	}

	protected abstract void methodAddInCacheAndDbms(T o) throws BusinessException;
}
