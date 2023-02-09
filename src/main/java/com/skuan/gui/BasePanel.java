package com.skuan.gui;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel{
    
    public BasePanel(){

        initVariate();

        initComponents();

        loadData();

        installListener();

    }

    abstract protected void initVariate();

    abstract protected void initComponents();

    abstract protected void loadData();

    abstract protected void installListener();

    abstract public void reLoadData();

}
