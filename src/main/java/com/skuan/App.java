package com.skuan;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMonokaiProContrastIJTheme;
import com.skuan.gui.configuration.ParameterizePanel;
import com.skuan.gui.label.LabelEditPanel;
import com.skuan.gui.map.components.MapComponentsPanel;
import com.skuan.gui.net.NetToolPanel;
import com.skuan.gui.toolkit.ToolkitPanel;
import com.skuan.tool.ConfigurationInitializer;
import com.skuan.tool.ScreenUtils;

import net.miginfocom.swing.MigLayout;

public class App extends JFrame{

    public App(){
        //load app configuration
        loadConfigurations();
        SwingUtilities.invokeLater( () -> {
            //set look feel
            FlatMonokaiProContrastIJTheme.setup();
            // FlatDarkLaf.setup();
            
            // "Default", "Blue", "Purple", "Red", "Demo.accent.Orange", "Yellow", "Green"
            // String accentColor = SystemProperties.getProperty("app.theme.accentcolor");
            // FlatLaf.setGlobalExtraDefaults(StringUtils.isBlank(accentColor) ? null : Collections.singletonMap( "@accentColor", "$" + accentColor ));
            // Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
            // try {
            //     FlatLaf.setup(lafClass.newInstance());
            //     FlatLaf.updateUI();
            // } catch (InstantiationException | IllegalAccessException e) {
            //     e.printStackTrace();
            // }
        
            //init windows
            initFrame();
            //init components
            initComponents();
        });
    }
    
    

    public static void main(String[] args) {
         new App().setVisible(true); 

    }


    public void initFrame(){
        setTitle("Tool Dialog");
        setSize(ScreenUtils.SCREEN_SIZE);
        setLocation(ScreenUtils.SCREEN_LOCATION);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JTabbedPane tabbedPane;
    private JProgressBar progressBar;
    private  JPanel contentPanel;

    private void initComponents(){

        contentPanel = new JPanel();
        progressBar = new JProgressBar();
        tabbedPane = new JTabbedPane();
        
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu();
        JMenu toolMenu = new JMenu();
        JMenuItem menuItem1 = new JMenuItem();
        JMenuItem menuItem2 = new JMenuItem();
        JMenuItem menuItem3 = new JMenuItem();
        JMenuItem menuItem4 = new JMenuItem();
        JMenuItem menuItem5 = new JMenuItem();
        JMenuItem menuItem6 = new JMenuItem();
        MapComponentsPanel mapComponentsPanel = new MapComponentsPanel();
        NetToolPanel netToolPanel = new NetToolPanel();
        LabelEditPanel labelEditPanel = new LabelEditPanel();
        ToolkitPanel toolkitPanel = new ToolkitPanel();
        ParameterizePanel parameterizePanel = new ParameterizePanel();

        progressBar.setOrientation(SwingConstants.HORIZONTAL);
        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true);
        progressBar.setMaximum(100);
        progressBar.setMinimum(100);

        contentPanel.setLayout( new MigLayout(
            "insets dialog,hidemode 3", 
            "[grow,fill]",
            "[grow,fill]"
        ));

        // menu bar init
        {
            // menubar -- file menu
            {
                fileMenu.setText("File");
                menuItem1.setText("menuItem1");
                fileMenu.add(menuItem1);
                menuItem2.setText("menuItem2");
                fileMenu.add(menuItem2);
                menuItem3.setText("menuItem3");
                fileMenu.add(menuItem3);
            }
            menubar.add(fileMenu);

            // menubar -- tool menu   
            {
                toolMenu.setText("Tools");
                menuItem4.setText("menuItem4");
                toolMenu.add(menuItem4);
                menuItem5.setText("menuItem5");
                toolMenu.add(menuItem5);
                menuItem6.setText("menuItem6");
                toolMenu.add(menuItem6);
            }
            menubar.add(toolMenu);

            setJMenuBar(menubar);
        }


       //======== tabbedPane ========
        {
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            tabbedPane.addChangeListener(e -> selectedTabChanged());
            tabbedPane.addTab("Map View", mapComponentsPanel);
            tabbedPane.addTab("Net Tool", netToolPanel);
            tabbedPane.addTab("Label Editor", labelEditPanel);
            tabbedPane.addTab("Toolkit", toolkitPanel);
            tabbedPane.addTab("Mid Test", toolkitPanel);
            tabbedPane.addTab("Parameterize", parameterizePanel);
        }
        contentPanel.add(tabbedPane, "cell 0 0");

        getContentPane().add(contentPanel);

    }


    private void selectedTabChanged(){

    }

    public static void loadConfigurations(){
        ConfigurationInitializer.initialize();
        ConfigurationInitializer.loadDynamicParms();
    }
    

}
