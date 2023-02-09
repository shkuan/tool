package com.skuan.gui.map.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class StatusBar extends JPanel{
    
    public StatusBar(){
        initComponents();
    }

    private void initComponents(){

        this.setLayout(new MigLayout(
            "insets 0",
            "5[][]20[][]20[][]",
            "[40!,center]"
        ));

        JLabel label0 = new JLabel();
        JLabel label4 = new JLabel();

        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();

        JLabel label1 = new JLabel();
        JTextField textField1 = new JTextField();
        
        //airport
        {
            label0.setText("Airport:");
            label4.setText("ZUCK");
            this.add(label0);
            this.add(label4);
        }
        
        //Coordinate
        {
            label1.setText("Coordinate:");
            textField1.setText("E1063903.835;N294351.945");
            textField1.setEditable(false);
            this.add(label1);
            this.add(textField1);
        }
        //track Nunber
        {
            label2.setText("Track Number:");
            label3.setText("0");
            this.add(label2);
            this.add(label3);
        }
        
    }

}
