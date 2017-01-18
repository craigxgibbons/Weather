package com.develogical;

import com.weather.Forecast;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ape07 on 18/01/2017.
 */
public class WeatherCache {
    Map<String, Forecast> cache = new LinkedHashMap<>();
    private static final int MAX_CACHE_SIZE = 3;

    public void addItem(String key, Forecast value) {
        if (!cache.containsKey(key)){
            if(!(cache.size() < MAX_CACHE_SIZE)){
                Map.Entry<String, Forecast> next = cache.entrySet().iterator().next();

                cache.remove(next.getKey());
            }

            cache.put(key,value);
        }
    }

    public Forecast getItem(String key) {
        if(!hasItem(key))
            return null;

        return cache.get(key);
    }

    public boolean hasItem(String key) {
        return cache.containsKey(key);
    }

    public int size() {
        return cache.size();
    }
}
