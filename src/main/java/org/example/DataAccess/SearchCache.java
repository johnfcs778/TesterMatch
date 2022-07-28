package org.example.DataAccess;


import org.example.Models.Command;
import org.example.Models.SearchResult;
import org.example.Models.Tester;

import java.util.HashMap;
import java.util.List;

/**
 * Cache object wrapper for a map of commands to Tester results for quick
 * lookup of results for duplicated commands
 */
public class SearchCache {
    private HashMap<Command, SearchResult> mCache;

    public SearchCache() {
        mCache = new HashMap<>();
    }


    /**
     * Returns a List of Tester if the key exists in the cache, otherwise return null
     * Ideally this would be constant lookup and could just hash the cmd object but this
     * is still much quicker than the whole search
     * @param cmd
     * @return
     */
    public SearchResult containsAndGet(Command cmd) {
        for(Command key : mCache.keySet()){
            if(key.equals(cmd)) {
                return mCache.get(key);
            }
        }
        return null;
    }

    /**
     * Adds a new command to result mapping in the cache
     * @param cmd
     * @param searchResult
     */
    public void put(Command cmd, SearchResult searchResult) {
        mCache.put(cmd, searchResult);
    }
}
