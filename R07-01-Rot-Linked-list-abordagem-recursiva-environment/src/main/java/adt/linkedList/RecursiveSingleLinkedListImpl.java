package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		return (data == null);
	}

	@Override
	public int size() {
		if (isEmpty()) return 0;
		else {
			return 1 + getNext().size();
		}
	}

	@Override
	public T search(T element) {
		if (isEmpty()) return null;
		
		if (getData().equals(element)) return getData();
		
		else {
			return getNext().search(element);
		}
	}

	@Override
	public void insert(T element) {
		
		if (this.data == null) {
			setData(element);
			setNext(new RecursiveSingleLinkedListImpl<T>());
		}else {
			getNext().insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (isEmpty()) return;
		if (getData().equals(element)) {
			setData(getNext().getData());
			setNext(getNext().getNext());
		}else {
			getNext().remove(element);
		}
	}

	@Override
	public T[] toArray() {
		T[] elements = (T[]) new Object[size()];
		int index = 0;
		if (this.data != null) {
			elements[index] = this.data;
			toArray(this.next, elements, index+1);
		}
		return elements;
	}


	private void toArray(RecursiveSingleLinkedListImpl<T> node, T[] elements, int index) {
		
		if (node.getData() != null) {
			elements[index] = node.getData();
			toArray(node.next, elements, index+1);
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
