package com.skuan.gui.map.components;

import java.awt.Color;

import com.skuan.tool.ColorUtils;

public class ColorComboBoxItem {

    private String colorStr;
    private String name;

    public ColorComboBoxItem(String colorStr, String name) {
        this.colorStr = colorStr;
        this.name = name;
    }

    public ColorComboBoxItem(Color color, String name){
        this.colorStr = ColorUtils.color2string(color);
        this.name = name;
    }

    public String getColorStr() {
        return colorStr;
    }

    public void setColorStr(String colorStr) {
        this.colorStr = colorStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
