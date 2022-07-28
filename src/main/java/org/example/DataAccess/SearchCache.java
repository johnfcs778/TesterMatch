package org.example.DataAccess;


import org.example.Models.Command;
import org.example.Models.Tester;

import java.util.HashMap;
import java.util.List;

public class SearchCache {
    private HashMap<Command, List<Tester>> mCache;

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
    public List<Tester> containsAndGet(Command cmd) {
        for(Command key : mCache.keySet()){
            if(key.equals(cmd)) {
                return mCache.get(key);
            }
        }
        return null;
    }

    public void put(Command cmd, List<Tester> testers) {
        mCache.put(cmd, testers);
    }
}
