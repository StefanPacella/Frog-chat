package it.project.chat.framework.data;

import java.util.Optional;

public class GetEntity <T extends DataItem> {
	
	private Optional<T> e = Optional.empty();;
	private DaoAbstract<T> daoAbstract;
	
	public GetEntity(DaoAbstract<T> daoAbstract ) {
		// TODO Auto-generated constructor stub
		this.daoAbstract = daoAbstract;
	}

	public Optional<T> get(Integer id) throws BusinessException {
		if (id == null) {
			return Optional.empty();
		}

		if (!e.isEmpty()) {
			return e;
		}

		e = daoAbstract.selectConId(id);
		return e;
	}
}
