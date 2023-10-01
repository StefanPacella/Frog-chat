package it.project.chat.framework.data.cache;

import java.util.function.Predicate;

class Tag <T> {
	
	private String label;
	
	private Predicate<T> predicate;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Predicate<T> getPredicate() {
		return predicate;
	}

	public void setPredicate(Predicate<T> predicate) {
		this.predicate = predicate;
	}

}
