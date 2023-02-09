package com.skuan.gui.view;

import java.awt.image.BufferedImage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locationtech.jts.geom.util.AffineTransformation;

public class View {

    private Logger logger = LogManager.getLogger(View.class);

    /**
     * 视图缓存
     */
    private BufferedImage bufferedImage;
    /**
     * 视图矩阵变换器
     */
    private AffineTransformation affineTransformation;

    /**
     * 视图渲染器
     */
    private Renderer renderer;


    
}
