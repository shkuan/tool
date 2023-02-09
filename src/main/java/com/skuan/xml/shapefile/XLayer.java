package com.skuan.xml.shapefile;

public class XLayer {
    /**
     * Layer name
     */
    private String name;
    /**
     * Set Layer visible
     */
    private boolean visible;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    @Override
    public String toString() {
        return "XLayer [name=" + name + ", visible=" + visible + "]";
    }
}
