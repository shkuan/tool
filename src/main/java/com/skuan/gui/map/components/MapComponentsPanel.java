package com.skuan.gui.map.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.styling.Style;

import com.skuan.common.SystemProperties;
import com.skuan.gui.map.geotools.MapViewPanel;
import com.skuan.map.shapefile.ShapeFileUtils;
import com.skuan.tool.SwingComponentsUtils;
import com.skuan.tool.TableModel;

import net.miginfocom.swing.MigLayout;

public class MapComponentsPanel extends JPanel {


    private MapViewPanel mapView;
    private StatusBar statusBar;
    private JTable layersTable;
    private JTable tracksTable;

    public MapComponentsPanel() {
        setLayout(new BorderLayout());
        initVariate();
        initComponents();
        initValues();
    }

    private void initVariate() {

    }

    private enum LayersTabHeader{

        LayerName("layerName"),

        Visible("visible");

        private String title;

        private  LayersTabHeader(String title){
            this.title = title;
        }

        public static String[] getTitles(){
            String[] tits = new String[LayersTabHeader.values().length];
            for (int i = 0; i < tits.length; i++) {
                tits[i] = LayersTabHeader.values()[i].getTitle();
            }
            return tits;
        }

        public String getTitle() {
            return title;
        }

    }

    // "Callsign","TrackID","Color","Visible"
    private enum TracksTabHeader{

        Callsign("Callsign"),
        TrackID("TrackId"),
        Color("Color"),
        Visible("Visible");

        private String title;

        private  TracksTabHeader(String title){
            this.title = title;
        }

        public static String[] getTitles(){
            String[] tits = new String[TracksTabHeader.values().length];
            for (int i = 0; i < tits.length; i++) {
                tits[i] = TracksTabHeader.values()[i].getTitle();
            }
            return tits;
        }

        public String getTitle() {
            return title;
        }


    }


    private void initValues(){
        {
            Map < String, SimpleFeatureSource > shapes = ShapeFileUtils.getSHPFeatures(SystemProperties.getProperty("file.path.map"), SystemProperties.getProperty("map.airport.layers"));
            Map < String, Style > slds = ShapeFileUtils.getSldStyle(SystemProperties.getProperty("file.path.map"), SystemProperties.getProperty("map.airport.layers"));
            if (shapes != null) {
                DefaultTableModel model = (DefaultTableModel) layersTable.getModel();
                shapes.forEach((k, v) -> {
                    Vector<Object> data = new Vector<>();
                    data.add(k);
                    data.add(true);
                    Vector<Object> dataVector = model.getDataVector();
                    dataVector.addElement(data);
                });
            }
            mapView.loadMap(shapes, slds);
        }
        {
            DefaultTableModel model = (DefaultTableModel) tracksTable.getModel();
            Vector<Object> dataVector = model.getDataVector();
            Vector<Object> data = new Vector<>();
            data.add("CES0012");
            data.add("123");
            data.add(new  ColorComboBoxItem(Color.RED, "Red"));
            data.add(true);
            dataVector.addElement(data);
            data = new Vector<>();
            data.add("CSN6666");
            data.add("777");
            data.add(new  ColorComboBoxItem(Color.GREEN, "GREEN"));
            data.add(true);
            dataVector.addElement(data);
        }
    }

    private void initComponents() {
        JSplitPane splitPane = new JSplitPane();
        JPanel leftPanel = new JPanel();
        JPanel panel = new JPanel();
        JButton resetBtn = new JButton();

        JTextField textField1 = new JTextField();
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JButton button = new JButton();
        Panel panel2 = new Panel();

        JPanel panel3 = new JPanel();
        JCheckBox distance = new JCheckBox();
        JCheckBox auxiliary = new JCheckBox();


        JPanel panel4 = new JPanel();
        JLabel layerTitLab = new JLabel();
        JScrollPane scrollPanel = new JScrollPane();
        JPanel panel6 = new JPanel();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JButton button4 = new JButton();
        JButton button5 = new JButton();
        JButton button8 = new JButton();
        JButton button9 = new JButton();

        JPanel panel5 = new JPanel();
        JLabel label = new JLabel();
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel7 = new JPanel();
        JButton button6 = new JButton();
        JButton button7 = new JButton();

        JPanel panel8 = new JPanel();
        mapView = new MapViewPanel();
        statusBar = new StatusBar();

        // content panel split
        {

            splitPane.setBackground(Color.GRAY);
            splitPane.setEnabled(false);
            splitPane.setDividerLocation(310); {
                leftPanel.setLayout(new MigLayout(
                    "insets 0",
                    "[]",
                    "12[][][]30[]30[]"
                ));
                //row1
                {
                    // panel.setBackground(Color.RED);
                    panel.setLayout(new MigLayout(
                        "insets 0",
                        "[]",
                        "[]"
                    ));
                    resetBtn.setText("Reset");
                    resetBtn.addActionListener(e -> {
                        mapView.resetView();
                    });
                    panel.add(resetBtn);
                }
                leftPanel.add(panel, "wrap");
                //row 2
                {
                    // panel2.setBackground(Color.BLUE);
                    panel2.setLayout(new MigLayout(
                        "",
                        "[][][]5[]",
                        "[]"
                    ));
                    label1.setText("Range:");
                    panel2.add(label1);
                    panel2.add(textField1, "w 60!, h 20!");
                    label2.setText("km");
                    panel2.add(label2);
                    button.setText("OK");
                    panel2.add(button, "w 55!,h 20!");
                }
                leftPanel.add(panel2, "wrap");
                //row3
                {
                    // panel3.setBackground(Color.RED);
                    panel3.setLayout(new MigLayout(
                        "insets 0",
                        "[]5[]",
                        "[]"
                    ));
                    distance.setText("Distance");
                    auxiliary.setText("auxiliary line");
                    panel3.add(distance);
                    panel3.add(auxiliary);
                }
                leftPanel.add(panel3, "wrap");
                //row4
                {
                    // panel4.setBackground(Color.BLUE);
                    panel4.setLayout(new MigLayout(
                        "insets 0",
                        "[300!]",
                        "[][][]"
                    ));
                    layerTitLab.setText("Layer List:");
                    panel4.add(layerTitLab, "growx,wrap");
                    layersTable = new JTable();
                    SwingComponentsUtils.tabInit(
                        layersTable, 
                        LayersTabHeader.getTitles(), 
                        new Class<?>[] { String.class,Boolean.class }, 
                        new boolean[] { false, true }
                    );

                    layersTable.getModel().addTableModelListener(e -> tableValueChanged(e));

                    SwingComponentsUtils.tabSetColumnWidth(200, LayersTabHeader.LayerName.ordinal(), layersTable);
                    SwingComponentsUtils.scrollPanelInit(scrollPanel, layersTable, true, false);
                    panel4.add(scrollPanel, "growx, h 200!,wrap");
                    button2.setText("↑");
                    button2.addActionListener(e -> layerUpChanged());
                    button3.setText("↓");
                    button3.addActionListener(e -> layerDownChanged());
                    button4.setText("+");
                    button4.addActionListener(e -> layerAddChanged());
                    button5.setText("-");
                    button5.addActionListener(e -> layerDeleteChanged());
                    button8.setText("OK");
                    button8.addActionListener(e -> layerReloadChanged());
                    panel6 = new JPanel(new MigLayout(
                        "insets 0",
                        "[][][][][]",
                        "[]"
                    ));
                    panel6.add(button2,"w 35!,h 20!");
                    panel6.add(button3,"w 35!,h 20!");
                    panel6.add(button4,"w 35!,h 20!");
                    panel6.add(button5,"w 35!,h 20!");
                    panel6.add(button8,"w 60!,h 20!");
                    panel4.add(panel6);
                }
                leftPanel.add(panel4, "wrap");
                
                //row 5
                {
                    panel5 = new JPanel(new MigLayout(
                        "insets 0",
                        "[300!]",
                        "[][][]"
                    ));
                    tracksTable = new JTable();
                    SwingComponentsUtils.tabInit(
                        tracksTable, 
                        TracksTabHeader.getTitles(), 
                        new Class<?>[] { String.class,Integer.class,String.class,Boolean.class },  
                        new boolean[] { false, false, true, true }
                    );
                    
                    SwingComponentsUtils.tabSetColumnComboBoxModel(
                        tracksTable, 
                        TracksTabHeader.Color.ordinal(), 
                        new ColorComboBoxItem[]{
                            new ColorComboBoxItem(Color.ORANGE, "ORANGE"),
                            new ColorComboBoxItem(Color.RED, "Red"),
                            new ColorComboBoxItem(Color.BLACK, "BLACK"),
                            new ColorComboBoxItem(Color.BLUE, "BLUE"),
                            new ColorComboBoxItem(Color.CYAN, "CYAN"),
                            new ColorComboBoxItem(Color.GREEN, "GREEN"),
                            new ColorComboBoxItem(Color.MAGENTA, "MAGENTA"), 
                            new ColorComboBoxItem(Color.PINK, "PINK"), 
                            new ColorComboBoxItem(Color.WHITE, "WHITE"),
                            new ColorComboBoxItem(Color.YELLOW, "YELLOW"),
                            new ColorComboBoxItem(Color.DARK_GRAY, "DARK_GRAY"),
                            new ColorComboBoxItem(Color.LIGHT_GRAY, "LIGHT_GRAY") 
                        }
                    );
                    
                    label.setText("Track List:");
                    panel5.add(label, "growx,wrap");
                    SwingComponentsUtils.scrollPanelInit(scrollPane, tracksTable, true, false);
                    panel5.add(scrollPane, "growx, h 200!,wrap");
                    button6.setText("+");
                    button6.addActionListener(e -> targetAddChanged());
                    button7.setText("-");
                    button7.addActionListener(e -> targetDeleteChanged());
                    button9.setText("OK");
                    button9.addActionListener(e -> targetReloadChanged());

                    panel7 = new JPanel(new MigLayout(
                        "insets 0",
                        "[][][]",
                        "[]"
                    ));
                    panel7.add(button6,"w 35!,h 20!");
                    panel7.add(button7,"w 35!,h 20!");
                    panel7.add(button9,"w 60!,h 20!");
                    panel5.add(panel7, "wrap");

                }

                leftPanel.add(panel5, "wrap");

            }

            splitPane.setLeftComponent(leftPanel);

            {
                panel8.setLayout(new MigLayout(
                    "insets 0,gapy 0",
                    "[grow,fill]",
                    "[grow,fill][]"
                ));
                panel8.add(mapView,"wrap");
                panel8.add(statusBar);
            }

            splitPane.setRightComponent(panel8);
        }

        add(splitPane);
    }

    private void tableValueChanged(TableModelEvent e){
        if(e.getColumn() == LayersTabHeader.Visible.ordinal()){
            boolean isSelected = (boolean) ((TableModel)e.getSource()).getValueAt(e.getLastRow(), e.getColumn());
            String layerName = (String) ((TableModel)e.getSource()).getValueAt(e.getLastRow(),LayersTabHeader.LayerName.ordinal());
            System.out.println( layerName +":"+isSelected);
        }
    }

    private void layerUpChanged(){

    }

    private void layerDownChanged(){

    }


    private void layerAddChanged(){

    }

    private void layerDeleteChanged(){

    }

    private void layerReloadChanged(){

    }

    private void targetAddChanged(){

    }

    private void targetDeleteChanged(){

    }

    private void targetReloadChanged(){


    }
   


}