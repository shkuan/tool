package com.skuan.tool;

import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skuan.common.OfflineProperties;
import com.skuan.common.SystemDynamicProperties;
import com.skuan.common.SystemProperties;

public class ConfigurationInitializer {

    private ConfigurationInitializer() {}

    public static void initialize() {
        ENV_Build();
        loadGlobalCfg();
    }
 
    public static void ENV_Build(){
        System.setProperty("javax.xml.parsers .DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl"); 
        System.setProperty("LOG4J_CONFIGURATION_FILE", new StringBuffer(getAppFilePath()).append("log4j2.xml").toString());
    }

    private static String getAppFilePath(){
        StringBuffer appPath = new StringBuffer(ConfigurationInitializer.class.getResource("/").getFile());
        if (StringUtils.endsWith(appPath, "classes/")) {
            appPath.delete(StringUtils.indexOf(appPath, "classes/"), appPath.length()) ;
        }
        appPath.append("resources/");
        return appPath.toString();
    }

    /**
     * 解析系统配置
     */
    public static void loadGlobalCfg(){
        StringBuffer globalFile =  new  StringBuffer( getAppFilePath() ).append("gloable.json");
        FileInputStream  fis = null;
        InputStreamReader inReader = null;
        StringBuffer sb = new StringBuffer();
        try {
            fis = new FileInputStream(globalFile.toString());
            inReader = new InputStreamReader(fis,"utf-8");
            int ch = 0;
            while ((ch = inReader.read()) != -1) 
                sb.append((char) ch);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            sb.delete(0, sb.length()); 
        }finally{
            try {
                fis.close();
            } catch (IOException e) {
                fis = null;
            }
            try {
                inReader.close();
            } catch (IOException e) {
                inReader = null;
            }
        }
        if(sb.length() != 0){
             Object jsonobj =  JSONObject.parse(sb.toString());
             decodeJson(null ,jsonobj);
        }
    }


    private static void decodeJson(String key ,Object jsonobj){
        if(jsonobj instanceof JSONObject){
            Map<String, Object> map = ((JSONObject)jsonobj).getInnerMap();
            map.forEach((k ,v) -> decodeJson(k , v));
        }else if(jsonobj instanceof JSONArray){
            JSONArray array = (JSONArray)jsonobj;
            StringBuffer vars = new StringBuffer();
            array.forEach(var->{
                if(StringUtils.isNotBlank((String)var))
                    vars.append(String.valueOf(var).trim()).append(",");
            });
            if(vars.length() > 0) 
                vars.delete(vars.length() - 1, vars.length());
            SystemProperties.addProperty(key, vars.toString());
        }else if(jsonobj instanceof String){
            SystemProperties.addProperty(key, String.valueOf(jsonobj));
        }
    }

    public static void loadDynamicParms(){
        Point2D syscenter = OfflineProperties.getSYSTEM_CENTER();
        if(syscenter != null){
            SystemDynamicProperties.addProperty("dynamic.systemcenter", new Point2D.Double(syscenter.getX() , syscenter.getY()));
        }
    }

    public static void main(String[] args) {
        initialize();
    }

}