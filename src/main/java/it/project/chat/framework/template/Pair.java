package it.project.chat.framework.template;

public class Pair<T> implements Comparable<Pair<T>> {

	private T e1;

	private T e2;

	private Comparable<Pair<T>> comparable;


	public Pair(T e1, T e2) {
		// TODO Auto-generated constructor stub
		this.e1 = e1;
		this.e2 = e2;
	}

	public T getE1() {
		return e1;
	}

	public void setE1(T e1) {
		this.e1 = e1;
	}

	public T getE2() {
		return e2;
	}

	public void setE2(T e2) {
		this.e2 = e2;
	}

	
	public void setComparable(Comparable<Pair<T>> comparable) {
		this.comparable = comparable;
	}
	
	@Override
	public int compareTo(Pair<T> o) {
		// TODO Auto-generated method stub
		return comparable.compareTo(o);
	}

}
