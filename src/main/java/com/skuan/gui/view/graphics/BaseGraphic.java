package com.skuan.gui.view.graphics;

import java.awt.Graphics2D;
import java.awt.Polygon;

public abstract class BaseGraphic {

    protected String name;
    protected String id;
    protected boolean visible;
    protected boolean selected;

    abstract public Polygon getBounds();

    abstract public void  paint(Graphics2D g2d);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    

}
