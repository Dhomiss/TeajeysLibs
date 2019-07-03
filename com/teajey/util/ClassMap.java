package com.teajey.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ClassMap<SuperType> implements Iterable<SuperType> {
	private ClassObjectListHashMap contents;
	private Class<SuperType> topSuperClass;
	
	private class ClassObjectListHashMap extends HashMap<Class<? extends SuperType>, ArrayList<? extends SuperType>> {
		private static final long serialVersionUID = -5953385724491047846L;

		@SuppressWarnings("unchecked")
		public <U extends SuperType> ArrayList<U> getList(Class<? super U> key) {
			return (ArrayList<U>) super.get(key);
		}

		@SuppressWarnings("unchecked")
		public <U extends SuperType> ArrayList<U> putList(Class<U> key, ArrayList<U> value) {
			return (ArrayList<U>) super.put(key, value);
		}

		@Deprecated
		@Override
		public ArrayList<? extends SuperType> get(Object key) {
			return null;
		}

		@Deprecated
		@Override
		public ArrayList<? extends SuperType> put(Class<? extends SuperType> key,
				ArrayList<? extends SuperType> value) {
			return null;
		}
	}
	
	public ClassMap(Class<SuperType> topSuperClass) {
		this.contents = new ClassObjectListHashMap();
		this.contents.putList(topSuperClass, new ArrayList<>());
		this.topSuperClass = topSuperClass;
	}
	
	public <U extends SuperType> boolean add(U newItem) {
		//boolean wasAdded = false;
		@SuppressWarnings("unchecked")
		Class<U> newItemClass = (Class<U>) newItem.getClass();
		return induct(newItem, newItemClass);
	}
	
	@SuppressWarnings("unchecked")
	private <U extends SuperType> boolean induct(U newItem, Class<?> newItemClass) {
		boolean classFits = (newItemClass != null && topSuperClass.isAssignableFrom(newItemClass));
		if (classFits) {
			if (!newItemClass.equals(topSuperClass)) {
				induct(newItem, newItemClass.getSuperclass());
				if (!contents.containsKey(newItemClass)) {
					contents.putList((Class<U>)newItemClass, new ArrayList<U>());
				}
			}
			((ArrayList<U>)contents.getList(newItemClass)).add(newItem);
		}
		return classFits;
	}
	
	public <U extends SuperType> ArrayList<U> getList(Class<U> _class) {
		ArrayList<U> list = (ArrayList<U>) contents.getList(_class);
		if (list == null) {
			return new ArrayList<U>();
		} else {
			return list;
		}
	}

	@Override
	public Iterator<SuperType> iterator() {
		return (Iterator<SuperType>) contents.getList(topSuperClass).iterator();
	}
	
	public int size() {
		return contents.getList(topSuperClass).size();
	}
}
