package com.skuan.gui.net;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import com.skuan.gui.BasePanel;
import com.skuan.tool.SwingComponentsUtils;

import net.miginfocom.swing.MigLayout;

public class UDPPanel extends BasePanel{

    private JTable castRecvTable;
    private JTable castSendTable;

    private enum castTabHeader{
        Protocol("Protocol"),
        RemoteIP("RemoteIP"),
        RecvPort("RecvPort"),
        LocalIP("LocalIP"),
        Eth("Eth"),
        ENABLE("Enable");

        private String title;
        private castTabHeader(String title){
            this.title = title;
        }

        public String getTitle(){
            return title;
        }

        public static String[] getTitles(){
            String[] tits = new String[castTabHeader.values().length];
            for (int i = 0; i < tits.length; i++) {
                tits[i] = castTabHeader.values()[i].getTitle();
            }
            return tits;
        }
    }


    public UDPPanel(){

    }

    @Override
    protected void initVariate() {
        
    }

    @Override
    protected void initComponents() {
        JLabel label0 = new JLabel();
        JLabel label = new JLabel();
        JScrollPane scrollPane = new JScrollPane();
        castRecvTable = new JTable();
        JPanel panel = new JPanel();
        JButton button0 = new JButton();
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JLabel label2 = new JLabel();
        JScrollPane scrollPane1 = new JScrollPane();
        JTextPane textPane = new JTextPane();
        JButton button4 = new JButton();
        JCheckBox checkBox = new JCheckBox();
        JPanel panel3 = new JPanel();
        JPanel panel1 = new JPanel();

        JPanel panel2 = new JPanel();
        JLabel labe5 = new JLabel();
        JLabel label3 = new JLabel();
        JScrollPane scrollPane2 = new JScrollPane();
        JTextPane textPane1 = new JTextPane();
        JPanel panel4 = new JPanel();
        JButton button = new JButton();
        JLabel label4 = new JLabel();
        JScrollPane scrollPane3 = new JScrollPane();
        castSendTable = new JTable();
        JPanel panel5 = new JPanel();
        JButton button5= new JButton();
        JButton button6 = new JButton();
        JButton button7 = new JButton();
        JButton button8 = new JButton();



        setLayout( new MigLayout(
            "insets 0",
            "[]",
            "[]"
        ));

        // recv
        {
            // panel1.setBackground(Color.pink);
            panel1.setLayout(new MigLayout(
                "insets 0",
                "20[][][][]10",
                "5[]10[][][]5[][]"
            ));
            //headline
            {
                label0.setText("<html><b><font size=\"5\" color=\"orange\">Receive data on UDP</font></b></html>");
            }
            panel1.add(label0,"gapleft 0, span ,wrap");

            //net cfg table
            {
                SwingComponentsUtils.tabInit(
                    castRecvTable, 
                    castTabHeader.getTitles(), 
                    new Class<?>[] { String.class,String.class,String.class,String.class,String.class,Boolean.class },  
                    new boolean[] { true, true, true, true, true, true }
                );
                
                SwingComponentsUtils.tabSetColumnComboBoxModel(
                    castRecvTable, 
                    castTabHeader.Protocol.ordinal(), 
                    new String[]{
                        "multicast","broadcast","unicast"
                    }
                );
                SwingComponentsUtils.scrollPanelInit(scrollPane, castRecvTable, true, false);
                SwingComponentsUtils.tabSetColumnWidth(60, castTabHeader.ENABLE.ordinal(), castRecvTable);
                panel1.add(scrollPane,"h 180!, w 550!,span,growx,wrap");
            }
            //operate button
            {
                Dimension size = new Dimension(60, 25);
                panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                button0.setText("+");
                SwingComponentsUtils.componentSetSize(size, button0);
                panel.add(button0);
                button1.setText("-");
                SwingComponentsUtils.componentSetSize(size, button1);
                panel.add(button1);
                button2.setText("OK");
                SwingComponentsUtils.componentSetSize(size, button2);
                panel.add(button2);
                button3.setText("OV");
                SwingComponentsUtils.componentSetSize(size, button3);
                panel.add(button3);
                panel1.add(panel,"growx,span,wrap");
            }
           
            //package infomation
            {
                label2.setText("<html><b><font size=\"5\" color=\"orange\">Package Info:</font></b></html>");
                panel1.add(label2,"span,wrap");
                textPane.setContentType("text/html");
                textPane.setAutoscrolls(true);
                SwingComponentsUtils.scrollPanelInit(scrollPane1, textPane, true, true);
                panel1.add(scrollPane1,"h 280!,w 550!,span,growx");
                button4.setText("cls");
                SwingComponentsUtils.componentSetSize(new Dimension(60, 25), button3);
                panel3.setLayout(new FlowLayout(FlowLayout.RIGHT));
                panel3.add(button4);
                panel1.add(panel3,"growx,span,wrap");
            }
        }
        //Send
        {


        }
        add(panel1);
        // add(panel2);
    }

    @Override
    public void loadData() {
        for (int i = 0; i < 20; i++) {
            Vector<Object> data = new Vector<>();
            data.add("multicast");
            data.add("230.0.1.12");
            data.add("56120,5692");
            data.add("192.168.5.150");
            data.add("eno1");
            data.add(true);
            SwingComponentsUtils.tabDataAdd(castRecvTable, data);
        }
    }

    @Override
    protected void installListener() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void reLoadData() {
        // TODO Auto-generated method stub
        
    }
 
}
