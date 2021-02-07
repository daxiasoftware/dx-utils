package com.daxiasoftware.utils;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.time.DateUtils;

public class MemoryCache {
    
    private static ConcurrentHashMap<String, Cache> map = new ConcurrentHashMap<>();

    public static void set(String key, Object value, int seconds) {
        Cache cache = new Cache();
        cache.data = value;
        cache.seconds = seconds;
        cache.expireTime = DateUtils.addSeconds(new Date(), seconds);
        map.put(key, cache);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        Cache cache = map.get(key);
        if (cache == null) {
            return null;
        }
        
        if (cache.expireTime.before(new Date())) {
            map.remove(key);
            return null;
        }
        
        return (T) cache.data;
    }
    
    public static boolean exists(String key) {
        Cache cache = map.get(key);
        if (cache == null) {
            return false;
        }
        if (cache.expireTime.before(new Date())) {
            map.remove(key);
            return false;
        }
        return true;
    }
    
    /**
     * 重置缓存的失效时间
     * @param key
     */
    public static void touch(String key) {
        Cache cache = map.get(key);
        if (cache != null && cache.isValid()) {
            cache.expireTime = DateUtils.addSeconds(new Date(), cache.seconds);
        }
    }
    
    public static void remove(String key) {
        map.remove(key);
    }
    
    public static void cleanExpired() {
        Date date = DateUtils.addSeconds(new Date(), -60);
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Cache cache = map.get(key);
            if (cache.expireTime.before(date)) {
                map.remove(key);
            }
        }
    }
    
    public static int size() {
        return map.size();
    }
    
    private static class Cache {
        Object data;
        Date expireTime;
        int seconds;
        
        public boolean isValid() {
            return this.expireTime.after(new Date());
        }
    }
}
