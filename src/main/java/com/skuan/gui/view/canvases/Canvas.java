package com.skuan.gui.view.canvases;

import java.util.List;
import java.util.Observer;

import com.skuan.gui.view.graphics.BaseGraphic;

public abstract class Canvas implements Observer{

    public static enum LayerName{
        MAP_CANVAS,
        MARK_CANVAS,
        AUXILIARY_CANVAS,
        TARGET_CANVAS,
        RANGE_CANVAS;
    }

    protected LayerName name;

    protected boolean visible;

    public Canvas(LayerName name , boolean visible) {
        this.name = name;
        this.visible = visible;
    }

    abstract public List<BaseGraphic> getGraphics();

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LayerName getName() {
        return name;
    }

    public void setName(LayerName name) {
        this.name = name;
    }
}
