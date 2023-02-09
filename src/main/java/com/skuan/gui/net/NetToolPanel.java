package com.skuan.gui.net;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

public class NetToolPanel extends JPanel{

    private JTabbedPane tabbedPane;

    public NetToolPanel(){
        initComponents();
    }

    private void initComponents(){
        tabbedPane = new JTabbedPane();
        UDPPanel udpPanel = new UDPPanel();
        TCPPanel tcpPanel = new TCPPanel();
        RabbitMQPanel rabbitMQPanel = new RabbitMQPanel();


        this.setLayout(new MigLayout(
            "insets 0",
            "[grow,fill]",
            "15[grow,fill]"
        ));
        //tabbed init
        {
            tabbedPane.setTabPlacement(JTabbedPane.LEFT);
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            // tabbedPane.putClientProperty( "JTabbedPane.hasFullBorder", true ); 
            tabbedPane.putClientProperty( "JTabbedPane.tabType", "card" );  //underlined  , card
            tabbedPane.addTab("UDP", udpPanel);
            tabbedPane.addTab("TCP", tcpPanel);
            tabbedPane.addTab("RabbitMQ", rabbitMQPanel);
        }

        this.add(tabbedPane);

    }
    
}
