package com.skuan.common;

import java.util.HashMap;
import java.util.Map;

public class SystemDynamicProperties {
    
    private static Map<String, Object> innermap;

    static{
        innermap = new HashMap<>();
    }

    public static <T> T getProperty(String key, Class<T> clz){
        return (T) innermap.get(key);
    }

    public static void addProperty(String key, Object value){
        innermap.put(key, value);
    }

}
