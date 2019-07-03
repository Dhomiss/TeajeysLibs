package com.teajey.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class TypeTree<SuperType> implements Iterable<SuperType> {
	private HashMap<String, TypeTreeNode<? extends SuperType>> nodes;
	private TypeTreeNode<SuperType> root;
	private ArrayList<SuperType> allItems;

	public TypeTree(String className) {
		this.nodes = new HashMap<>();
		this.root = new TypeTreeNode<>(className);
	}

	private class TypeTreeNode<ItemType extends SuperType> extends TreeNode<TypeTreeNodeArrayList<ItemType>> {
		public TypeTreeNode(String className) {
			super();
			nodes.put(className, this);
		}

		public TypeTreeNode(TreeNode<? super TypeTreeNodeArrayList> parent, String className) {
			super(parent);
			nodes.put(className, this);
		}
		
		public TypeTree<SuperType>.TypeTreeNodeArrayList<ItemType> getContent() {
			return this.content;
		}
		
		public TypeTreeNodeArrayList<ItemType> getItemsOfType(String className) {
			TypeTreeNodeArrayList<ItemType> ttnal = new TypeTreeNodeArrayList<>();
			for (TreeNode<? extends TypeTree<SuperType>.TypeTreeNodeArrayList<? extends SuperType>> node : nodes.get(className)) {
				ttnal.addAll((TypeTreeNodeArrayList<? extends ItemType>)node.getContent());
			}
			return ttnal;
		}
	}
	
	private class TypeTreeNodeArrayList<ItemType extends SuperType> extends ArrayList<ItemType> {
		private static final long serialVersionUID = 1750260335566197367L;

		public void addItem(SuperType newItem) {
			this.add((ItemType)newItem);
		}

		@Override
		public boolean add(ItemType e) {
			allItems.add(e);
			return super.add(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <ItemType extends SuperType> ArrayList<ItemType> itemsOfType(String className) {
		return (ArrayList<ItemType>) root.getItemsOfType(className);
	}

	@Override
	public TypeTreeIterator<SuperType> iterator() {
		return new TypeTreeIterator<SuperType>(root);
	}

	public class TypeTreeIterator<ItemType extends SuperType> implements Iterator<ItemType> {
		TreeNode<TypeTreeNodeArrayList<ItemType>>.TreeNodeIterator  nodeIterator;
		Iterator<ItemType> nodeContentListIterator;
		ItemType nextItem;

		public TypeTreeIterator(TreeNode<TypeTreeNodeArrayList<ItemType>> node) {
			this.nodeIterator = node.iterator();
		}

		@Override
		public boolean hasNext() {
			return nodeIterator.hasNext() || nodeContentListIterator.hasNext();
		}
		
		public TreeNode<TypeTreeNodeArrayList<ItemType>> currentNode() {
			return nodeIterator.currentNode();
		}

		@Override
		public ItemType next() {

			if (nodeContentListIterator.hasNext()) {
				nextItem = nodeContentListIterator.next();
			} else if (nodeIterator.hasNext() || nodeContentListIterator == null) {
				nodeContentListIterator = nodeIterator.next().getContent().iterator();
			}

			return nextItem;
		}

	}

	public <ItemType extends SuperType> boolean addItem(ItemType newItem) {
		boolean added = false;
		String className = newItem.getClass().getName();
		String superClassName = newItem.getClass().getSuperclass().getName();

		if (nodes.containsKey(className)) {
			nodes.get(className).getContent().addItem(newItem);
		} else if (nodes.containsKey(superClassName)) {
			TypeTreeNode<SuperType> superNode = (TypeTreeNode<SuperType>) nodes.get(superClassName);
			superNode.addNode((TreeNode) new TypeTreeNode<ItemType>(className));
			nodes.get(className).getContent().addItem(newItem);
		}

		return added;
	}

	public boolean addAll(Collection<? extends SuperType> newItems) { //Good god, please improve this
		boolean added = false;
		for (SuperType item : newItems) {
			added = this.addItem(item);
		}
		return added;
	}

	public void clear() {
		this.root.clear();
	}

	public boolean contains(SuperType query) {
		return allItems.contains(query);
	}

	public boolean containsAll(Collection<? extends SuperType> c) {
		return allItems.containsAll(c);
	}

	public boolean isEmpty() {
		return this.root.size() == 1;
	}

	public boolean remove(SuperType query) {
		boolean wasRemoved = false;
		TypeTreeIterator<SuperType> iterator = this.iterator();
		while(iterator.hasNext()) {
			SuperType item = iterator.next();
			if (query == item) {
				iterator.currentNode().remove(item);
				wasRemoved = true;
			}
		}
		return wasRemoved;
	}

	public boolean removeAll(Collection<? extends SuperType> c) {
		boolean wasRemoved = false;
		for (SuperType item : c) {
			wasRemoved = this.remove(item);
		}
		return wasRemoved;
	}

	/*public boolean retainAll(Collection<? extends SuperType> c) {
		return false;
	}*/

	public int size() {
		return root.size();
	}

	@SuppressWarnings("unchecked")
	public SuperType[] toArray() {
		return (SuperType[]) allItems.toArray();
	}

	public <T> T[] toArray(T[] array) {
		return allItems.toArray(array);
	}
}
