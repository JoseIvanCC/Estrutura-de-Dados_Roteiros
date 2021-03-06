package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	protected int calculateBalance(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return 0;
		}
		BSTNode<T> nodeLeft = (BSTNode<T>) node.getLeft();
		BSTNode<T> nodeRight = (BSTNode<T>) node.getRight();
		return height(nodeRight) - height(nodeLeft);
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		super.remove(element);
		rebalanceUp((BSTNode<T>) node.getParent());
	}

	protected void rebalance(BSTNode<T> node) {
		BSTNode<T> newNodeLeft = (BSTNode<T>) node.getLeft();
		BSTNode<T> newNodeRight = (BSTNode<T>) node.getRight();

		int balance = calculateBalance(node);

		if (balance < -1) {
			if (calculateBalance(newNodeLeft) > 0) {
				leftRotation(newNodeLeft);
			}
			rightRotation(node);
		} else if (balance > 1) {
			if (calculateBalance(newNodeRight) < 0) {
				rightRotation(newNodeRight);
			}
			leftRotation(node);
		}
	}

	protected void rebalanceUp(BSTNode<T> node) {
		if (node != null) {
			rebalance(node);
			rebalanceUp((BSTNode<T>) node.getParent());
		}
	}

	@Override
	public void insert(T element) {
		super.insert(element);
		BSTNode<T> node = search(element);
		rebalanceUp(node);
	}

	protected void leftRotation(BSTNode<T> node) {
		BSTNode<T> newNode = Util.leftRotation(node);
		if (newNode.getParent() == null) {
			root = newNode;
		}
	}

	protected void rightRotation(BSTNode<T> node) {
		BSTNode<T> newNode = Util.rightRotation(node);
		if (newNode.getParent() == null) {
			root = newNode;
		}
	}
}