package com.skuan.gui.view.canvases;

import java.util.List;
import java.util.Observable;

import com.skuan.gui.view.graphics.BaseGraphic;

public class TargetCanvas extends Canvas{

    public TargetCanvas() {
        super(LayerName.TARGET_CANVAS, true);
    }

    @Override
    public void update(Observable o, Object arg) {
        
    }

    @Override
    public List<BaseGraphic> getGraphics() {
        return null;
    }
    
}
