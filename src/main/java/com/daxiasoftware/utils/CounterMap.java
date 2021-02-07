package com.daxiasoftware.utils;

import java.util.HashMap;
import java.util.Map;

public class CounterMap<K> {

    private Map<K, Integer> map = new HashMap<K, Integer>();
    
    public void put(K key) {
        Integer count = map.get(key);
        if (count == null) {
            count = 1;
        } else {
            count ++;
        }
        map.put(key, count);
    }
    
    public int count(K key) {
        Integer count = map.get(key);
        if (count == null) {
            return 0;
        }
        return count;
    }
}
