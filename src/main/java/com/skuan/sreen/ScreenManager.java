package com.skuan.sreen;

import java.awt.geom.Point2D;
import java.util.Map;

import com.skuan.common.SystemProperties;


public class ScreenManager {
     
    private static ScreenManager instance;

    private Map<String,Map<Integer,ViewSlice>> slicesCache;

    private int magnification;

    private ScreenManager(){
        magnification = Integer.parseInt(SystemProperties.getProperty("screen.view.magnification"));
    }

    private ScreenManager instance(){
        if(instance == null)
            instance = new ScreenManager();
        return instance;
    }

    public void calculateViewSlices(Point2D location , double widht ,double height){
       

    }

    public boolean isOdd(int a){   
        if((a&1) != 1){    
            return true;   
        }   
        return false;   
    }




}
