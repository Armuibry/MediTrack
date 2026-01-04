package com.airtribe.meditrack.util;

import java.util.*;

public class DataStore<T> {
    
    private final List<T> items;
    private final Map<Integer, T> idMap;
    
    public DataStore() {
        this.items = new ArrayList<>();
        this.idMap = new HashMap<>();
    }
    
    /**
     * Add item to store
     * @param id Item ID (must be extractable via getId() method)
     * @param item Item to add
     */
    public void add(int id, T item) {
        items.add(item);
        idMap.put(id, item);
    }
    
    /**
     * Get item by ID
     * @param id Item ID
     * @return Item if found, null otherwise
     */
    public T getById(int id) {
        return idMap.get(id);
    }
    
    /**
     * Get all items
     * @return List of all items
     */
    public List<T> getAll() {
        return new ArrayList<>(items); // Return copy for immutability
    }
    
    /**
     * Remove item by ID
     * @param id Item ID
     * @return Removed item if found, null otherwise
     */
    public T remove(int id) {
        T item = idMap.remove(id);
        if (item != null) {
            items.remove(item);
        }
        return item;
    }
    
    /**
     * Check if item exists
     * @param id Item ID
     * @return true if exists, false otherwise
     */
    public boolean contains(int id) {
        return idMap.containsKey(id);
    }
    
    /**
     * Get size
     * @return Number of items
     */
    public int size() {
        return items.size();
    }
    
    /**
     * Clear all items
     */
    public void clear() {
        items.clear();
        idMap.clear();
    }
    
    /**
     * Get iterator for items
     * @return Iterator
     */
    public Iterator<T> iterator() {
        return items.iterator();
    }
}

