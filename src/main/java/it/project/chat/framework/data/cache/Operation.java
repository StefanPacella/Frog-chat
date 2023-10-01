package it.project.chat.framework.data.cache;

import java.util.List;
import java.util.Optional;

import it.project.chat.framework.data.BusinessException;

public abstract class Operation<T> {

	private Optional<T> element = Optional.empty();
	private List<T> list;

	public abstract void operation() throws BusinessException;

	public Optional<T> getElement() {
		return element;
	}

	public void setElement(Optional<T> element) {
		this.element = element;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> l) {
		this.list = l;
	}

}
