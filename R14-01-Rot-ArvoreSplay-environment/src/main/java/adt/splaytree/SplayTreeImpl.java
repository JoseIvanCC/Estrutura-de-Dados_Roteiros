package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements SplayTree<T> {

	static final String LEFT = "left";
	static final String RIGHT = "right";

	private void splay(BSTNode<T> node) {
		
		if (node == null || node.isEmpty() || node.equals(super.root)) return;
		
		if (node.getParent().equals(root)) {
			zig(root);
			return;
		}
		else {
			BSTNode<T> grandPa = (BSTNode<T>) node.getParent().getParent();
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			if (grandPa.getLeft().equals(parent) && parent.getLeft().equals(node)) {
				zigZig(node, LEFT);
			} else if (grandPa.getRight().equals(parent) && parent.getRight().equals(node)) {
				zigZig(node, RIGHT);
			}

			else if (grandPa.getLeft().equals(parent) && parent.getRight().equals(node)) {
				zigZag(node, LEFT);
			} else {
				zigZag(node, RIGHT);
			}
		}
		splay(node);
	}

	private void zig(BSTNode<T> node) {
		
		if (!node.getLeft().isEmpty() && node.getLeft().getParent().equals(node)) {
			this.root = Util.rightRotation(node);
		} else if (!node.getRight().isEmpty() && node.getRight().getParent().equals(node)) {
			this.root = Util.leftRotation(node);
		}
	}

	private void zigZig(BSTNode<T> node, String position) {

		BSTNode<T> grandPa = (BSTNode<T>) node.getParent().getParent();
		BSTNode<T> parent = (BSTNode<T>) node.getParent();

		if (position == LEFT) {
			Util.rightRotation(grandPa);
			BSTNode<T> aux = Util.rightRotation(parent);
			if (aux.getParent() == null) this.root = aux;
		} 
			else {
			Util.leftRotation(grandPa);
			BSTNode<T> aux = Util.leftRotation(parent);
			if (aux.getParent() == null) this.root = aux;
		}
	}

	private void zigZag(BSTNode<T> node, String position) {

		BSTNode<T> grandPa = (BSTNode<T>) node.getParent().getParent();
		BSTNode<T> parent = (BSTNode<T>) node.getParent();

		if (position == LEFT) {
			Util.leftRotation(parent);
			BSTNode<T> aux = Util.rightRotation(grandPa);
			if (aux.getParent() == null) this.root = aux;
		} else {
			this.root = Util.rightRotation(parent);
			BSTNode<T> aux = this.root = Util.leftRotation(grandPa);
			if (aux.getParent() == null) this.root = aux;
		}
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			super.insert(element);
			this.splay(search(element));
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		if (element == null)
			return null;

		BSTNode<T> node = super.search(element);

		if (!node.isEmpty()) {
			this.splay(node);
		} else {
			this.splay((BSTNode<T>) node.getParent());
		}
		return node;
	}

	@Override
	public void remove(T element) {
		if (element == null)
			return;

		BSTNode<T> node = super.search(element);

		if (node.isEmpty()) {
			this.splay((BSTNode<T>) node.getParent());
		} else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			super.remove(element);
			this.splay(parent);
		}
	}
}
