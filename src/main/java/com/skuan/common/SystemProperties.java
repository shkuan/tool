package com.skuan.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

public class SystemProperties{

    private  static final Map<String, String> properties;

    static {
        properties = new HashMap<>();
    }

    public static String getProperty(String key){
        return properties.get(key);
    }

    public static void addProperty(String key, String value){
        try{
            properties.put(key, value);   
            LogManager.getLogger("configurations load").info("[System properties] name : {} ,value : {} .",key , value);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public static Map<String , String> getInnerMap(){
        return properties;
    }
    
}
