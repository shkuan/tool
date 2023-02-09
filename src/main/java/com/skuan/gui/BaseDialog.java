package com.skuan.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

public abstract class BaseDialog extends JDialog{

    public BaseDialog(){

        initVariate();

        initComponents();

        loadData();
        
        installListener();

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                release();
            }
            
        });

    }

    
    abstract protected void initVariate();

    abstract protected void initComponents();

    abstract public void loadData();

    abstract protected void installListener();

    abstract protected void release();
 
}
