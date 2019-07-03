package com.teajey.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TreeNode<T> extends AbstractList<TreeNode<? extends T>> {
	protected T content;
	protected TreeNode<? super T> tree = this;
	protected TreeNode<? super T> parent = null;
	protected ArrayList<TreeNode<? extends T>> children = new ArrayList<>();
	protected int size = 1;
	protected int depth = 0;

	public TreeNode() {
		this(null);
	}

	protected TreeNode(TreeNode<? super T> parent) {
		if (parent != null) {
			this.tree = parent.tree;
			this.depth = parent.depth + 1;
		}
		this.parent = parent;
	}

	public void setContent(T content) {
		this.content = content;
	}

	protected void childrenAdded(int amount) {
		this.size += amount;
		if (this.parent != null)
			this.parent.childrenAdded(amount);
	}

	@Override
	public TreeNodeIterator iterator() {
		return new TreeNodeIterator();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public TreeNode<? extends T> get(int index) {
		return this.children.get(index);
	}

	public void addNodes(int amount) {
		for (int i = 0; i < amount; i++) {
			this.addNode();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addNodes(int amount, T content) {
		for (int i = 0; i < amount; i++) {
			this.addNode();
			((TreeNode<T>)this.getNode(i)).setContent(content);
		}
	}

	public boolean addNode() {
		this.childrenAdded(1);
		return this.children.add(new TreeNode<T>(this));
	}

	public boolean addNode(TreeNode<? extends T> node) {
		this.childrenAdded(node.size());
		node.setParent(this);
		return this.children.add(node);
	}

	public boolean addAllNodes(int index, Collection<? extends TreeNode<? extends T>> c) {
		this.childrenAdded(c.size());
		return this.children.addAll(index, c);
	}

	public TreeNode<? extends T> removeNode(int index) {
		this.childrenAdded(-1);
		return this.children.remove(index).setParent(null);
	}

	@Override
	public void clear() {
		this.children.clear();
	}

	private TreeNode<T> thisNode = this;
	public class TreeNodeIterator implements Iterator<TreeNode<? extends T>> {
		int childIndex = 0;
		private boolean returnedSelf = false;
		private TreeNodeIterator iteratorOfChildNode;

		@Override
		public boolean hasNext() {
			return this.hasNextChild() || !returnedSelf;
		}

		private boolean hasNextChild() {
			boolean thisNodeHasChildren = thisNode.getChildren().size() > 0;
			boolean childrenToCheck = childIndex < thisNode.getChildren().size();
			return thisNodeHasChildren && childrenToCheck;
		}

		private TreeNode<? extends T> nextChild() {
			TreeNode<? extends T> returnNode = thisNode.children.get(childIndex++);
			return returnNode;
		}
		
		public TreeNode<T> currentNode() {
			return thisNode;
		}

		@SuppressWarnings("unchecked")
		@Override
		public TreeNode<? extends T> next() {
			if (iteratorOfChildNode == null && this.hasNextChild()) {
				iteratorOfChildNode = (TreeNode<T>.TreeNodeIterator) this.nextChild().iterator();
			}
			
			if ((!this.hasNextChild() && this.iteratorOfChildNode == null) || (!this.hasNextChild() && !this.iteratorOfChildNode.hasNext())) {
				this.returnedSelf = true;
				return thisNode;
			} 
			
			if (this.iteratorOfChildNode != null && !iteratorOfChildNode.hasNext()) {
				iteratorOfChildNode = (TreeNode<T>.TreeNodeIterator) this.nextChild().iterator();
			}
			
			return iteratorOfChildNode.next();
		}
	}

	public String toString() {
		String string = "";
		TreeNodeIterator iterator = new TreeNodeIterator();
		while (iterator.hasNext()) {
			TreeNode<? extends T> nextNode = iterator.next();
			for (int i = 0; i < nextNode.getDepth(); i++) {
				string += "|";
			}
			string += "Node '" + nextNode.getContent() + "' at depth " + nextNode.getDepth() + " of size " + nextNode.size() + "\n";
		}
		return string;
	}

	public TreeNode<? super T> getParent() {
		return parent;
	}

	private TreeNode<T> setParent(TreeNode<? super T> parent) {
		this.parent.remove(this);
		this.parent = parent;
		this.depth = parent.depth + 1;
		return this;
	}

	public T getContent() {
		return content;
	}

	/*
	 * public String getContentAsString() { if (this.content != null) { return
	 * this.content.toString(); } else { return "null"; } }
	 */

	public TreeNode<? super T> getTree() {
		return tree;
	}

	public ArrayList<TreeNode<? extends T>> getChildren() {
		return children;
	}

	public TreeNode<? extends T> getNode(int index) {
		return this.children.get(index);
	}
	
	public int getDepth() {
		return this.depth;
	}
}
