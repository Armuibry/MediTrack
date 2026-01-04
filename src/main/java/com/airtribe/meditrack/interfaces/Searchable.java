package com.airtribe.meditrack.interfaces;

public interface Searchable {
    
    /**
     * Check if entity matches the search criteria
     * @param query Search query
     * @return true if matches, false otherwise
     */
    boolean matches(String query);
    
    /**
     * Get searchable text representation
     * @return Searchable text
     */
    String getSearchableText();
}

