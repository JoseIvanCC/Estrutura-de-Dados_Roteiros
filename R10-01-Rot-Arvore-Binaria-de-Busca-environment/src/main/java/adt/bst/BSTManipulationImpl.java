package adt.bst;

import java.awt.List;
import java.util.ArrayList;

import adt.bt.BTNode;

public class BSTManipulationImpl <T extends Comparable<T>> implements
BSTManipulation<T>{

	@Override
	public boolean equals(BST<T> tree1, BST<T> tree2) {
		return equalsRecursive((BSTNode<T>)tree1.getRoot(), (BSTNode<T>)tree2.getRoot());
	}

	private boolean equalsRecursive(BSTNode<T> no1, BSTNode<T> no2) {
		if (no1.isEmpty() && no2.isEmpty()) return true;
		else if (!(no1.isEmpty()) && no2.isEmpty()) return false;
		else if (no1.isEmpty() && !(no2.isEmpty())) return false;
		else if (!(no1.getData().equals(no2.getData()))) return false;
		else {
			return equalsRecursive((BSTNode<T>)no1.getLeft(), (BSTNode<T>)no2.getLeft()) && equalsRecursive((BSTNode<T>)no1.getRight(),(BSTNode<T>) no2.getRight());
		}
	}

	@Override
	public boolean isSimilar(BST<T> tree1, BST<T> tree2) {
		return isSimilarRecursive((BSTNode<T>)tree1.getRoot(), (BSTNode<T>)tree2.getRoot());
	}

	private boolean isSimilarRecursive(BSTNode<T> no1, BSTNode<T> no2) {
		if (no1.isEmpty() && no2.isEmpty()) return true;
		else if (!(no1.isEmpty()) && no2.isEmpty()) return false;
		else if (no1.isEmpty() && !(no2.isEmpty())) return false;
		else {
			return isSimilarRecursive((BSTNode<T>)no1.getLeft(), (BSTNode<T>)no2.getLeft()) && isSimilarRecursive((BSTNode<T>)no1.getRight(), (BSTNode<T>)no2.getRight());
		}
	}

	@Override
	public boolean contains(BST<T> tree, BST<T> subtree) {
		return containsRecursive((BSTNode<T>)tree.getRoot(), (BSTNode<T>)subtree.getRoot());
	}

	private boolean containsRecursive(BSTNode<T> no1, BSTNode<T> no2) {
		if (this.equalsRecursive(no1, no2)) return true;
		if (no1.isEmpty() && !(no2.isEmpty())) return false;
		else {
			return containsRecursive((BSTNode<T>) no1.getLeft(), no2) && containsRecursive((BSTNode<T>) no1.getRight(), no2);
		}
		
	}

	@Override
	public BSTNode<T> commonAncestor(BSTNode<T> node1, BSTNode<T> node2) {
		ArrayList<BSTNode> node1List = new ArrayList<BSTNode>();
		ArrayList<BSTNode> node2List = new ArrayList<BSTNode>();
		if (node1.isEmpty() && node2.isEmpty()) return null;
		if (node1.getParent().isEmpty() || node2.getParent().isEmpty()) return null;
		return ancestorRecursive(node1, node2, node1List, node2List);
	}

	private BSTNode<T> ancestorRecursive(BSTNode<T> node1, BSTNode<T> node2, ArrayList<BSTNode> node1List,
			ArrayList<BSTNode> node2List) {
		if (!node1.getParent().isEmpty()) {
			node1List.add((BSTNode) node1.getParent());
			if (!node1.getParent().getParent().isEmpty()) {
				return ancestorRecursive((BSTNode<T>) node1.getParent(), node2, node1List, node2List);
			}
		}else if (!node2.getParent().isEmpty()) {
			node2List.add((BSTNode) node2.getParent());
			if (!node2.getParent().getParent().isEmpty()) {
				return ancestorRecursive(node1, (BSTNode<T>) node2.getParent(), node1List, node2List);
			}
		}
		
		for (int i = 0; i < node2List.size(); i++) {
			if (node1List.contains(node2List.get(i))) {
				return (BSTNode<T>) node2List.get(i);
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		BSTManipulationImpl teste = new BSTManipulationImpl();
		BST<Integer> bst1 = new BSTImpl<Integer>();
		BST<Integer> bst2 = new BSTImpl<Integer>();
		
		System.out.println("true-> " + teste.equals((BST)bst1, (BST)bst2));
		System.out.println("null-> " + teste.commonAncestor((BSTNode)bst1.getRoot(), (BSTNode)bst1.getRoot()));
		
		bst1.insert(50);
		System.out.println("false-> " + teste.equals((BST)bst1, (BST)bst2));
		

		System.out.println("null-> " + teste.commonAncestor((BSTNode)bst1.getRoot(), (BSTNode)bst1.getRoot()));
		
		bst1.insert(40);
		bst1.insert(60);
		System.out.println("50-> " + teste.commonAncestor((BSTNode)bst1.getRoot().getLeft(), (BSTNode)bst1.getRoot().getRight()).getData());
		
		bst1.insert(70);
		System.out.println("50-> " + teste.commonAncestor((BSTNode)bst1.getRoot().getLeft(), (BSTNode)bst1.getRoot().getRight().getRight()).getData());
		
		System.out.println("50-> " + teste.commonAncestor((BSTNode)bst1.getRoot().getRight(), (BSTNode)bst1.getRoot().getRight().getRight()).getData());
		
		bst1.insert(65);
		System.out.println("60-> " + teste.commonAncestor((BSTNode)bst1.getRoot().getRight().getRight(), (BSTNode)bst1.getRoot().getRight().getRight().getLeft()).getData());
		
		System.out.println("50-> " + teste.commonAncestor((BSTNode)bst1.getRoot().getRight(), (BSTNode)bst1.getRoot().getRight()).getData());
		
		bst1.insert(45);
		bst1.insert(42);
		System.out.println("50-> " + teste.commonAncestor((BSTNode)bst1.getRoot().getLeft().getRight().getLeft(), (BSTNode)bst1.getRoot().getRight().getRight().getLeft()).getData());
	}


}