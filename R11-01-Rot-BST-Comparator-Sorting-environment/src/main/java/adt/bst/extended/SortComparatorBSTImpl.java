package adt.bst.extended;

import java.util.Comparator;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em suas funcionalidades
 * e possui um metodo de ordenar um array dado como parametro, retornando o resultado do percurso
 * desejado que produz o array ordenado. 
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

	private Comparator<T> comparator;
	
	public SortComparatorBSTImpl(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}
	
	@Override
	public BSTNode<T> search(T element) {
		if (this.root.isEmpty()) {
			return new BSTNode<T>();
		} else {
			return search(this.root, element);
		}
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (node.getData().equals(element)) {
			return node;
		} else {
			if (comparator.compare(node.getData(), element) > 0) { 
				if (node.getLeft().isEmpty()) {
					return new BSTNode<T>();
				} else {
					return search((BSTNode<T>) node.getLeft(), element);
				}
			} else {
				if (node.getRight().isEmpty()) {
					return new BSTNode<T>();
				} else {
					return search((BSTNode<T>) node.getRight(), element);
				}
			}
		}
	}
	
	@Override
	public void insert(T element) {
		if (this.root.isEmpty()) {
			this.root.setData(element);
			this.root.setLeft(new BSTNode<T>());
			this.root.setRight(new BSTNode<T>());
			this.root.setParent(new BSTNode<T>());
			this.root.getLeft().setParent(this.root);
			this.root.getRight().setParent(this.root);
		} else {
			insert(this.root, element);
		}
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		} else {
			if (comparator.compare(node.getData(), element) > 0) { 
				insert((BSTNode<T>) node.getLeft(), element);
			} else {
				insert((BSTNode<T>) node.getRight(), element);
			}
		}
	}

	@Override
	public T[] sort(T[] array) {
		while (!isEmpty()) remove(this.root.getData());
		
		for (int i = 0; i < array.length; i++) {
			this.insert(array[i]);
		}
		
		return this.order();
	}

	@Override
	public T[] reverseOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		return orderReverse(this.root, array);
	}

	private T[] orderReverse(BSTNode<T> node, T[] array) {
		
		if (!node.isEmpty()) {
			orderReverse((BSTNode<T>) node.getRight(), array);
			int i = 0;
			while (array[i] != null) {
				i++;
			}
			array[i] = node.getData();
			orderReverse((BSTNode<T>) node.getLeft(), array);
		}

		return array;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}
	
}
