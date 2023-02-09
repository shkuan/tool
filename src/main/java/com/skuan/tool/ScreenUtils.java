package com.skuan.tool;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import com.skuan.common.SystemProperties;

import org.apache.commons.lang3.StringUtils;

public class ScreenUtils {

    public static Dimension SCREEN_SIZE;

    public static Point SCREEN_LOCATION;
    
    static{
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        String scale = SystemProperties.getProperty("app.window.scale");
        if(StringUtils.isBlank(scale)){
            SCREEN_SIZE = screenSize;
        }else{
            float sc = Float.parseFloat(scale);
            SCREEN_SIZE = new Dimension((int)( screenSize.getWidth() * sc), (int)(screenSize.getHeight() * sc));
        }
        String location = SystemProperties.getProperty("app.window.location");
        if(StringUtils.isBlank(location)){
            SCREEN_LOCATION = new Point(0, 0);
        }else{  
            String[] strarry = StringUtils.split(location, "x");
            SCREEN_LOCATION = new Point(Integer.parseInt(strarry[0]), Integer.parseInt(strarry[1]));
        }
    }

}
