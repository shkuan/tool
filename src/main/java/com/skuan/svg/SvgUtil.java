package com.skuan.svg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.bridge.ViewBox;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.renderer.StaticRenderer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGSVGElement;

public class SvgUtil {

  /**
   * 
   */
  static StaticRenderer  renderer;
  static UserAgent  userAgent;
  static DocumentLoader documentLoader;
  static BridgeContext bridgeContext;
  static GVTBuilder gVTBuilder ;

  static{
    renderer = new StaticRenderer();
    renderer.setDoubleBuffered(true);
    userAgent = new UserAgentAdapter();
    documentLoader = new DocumentLoader(userAgent);
    bridgeContext = new BridgeContext(userAgent, documentLoader);
    gVTBuilder = new  GVTBuilder();
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setLocation(new Point(10, 10));
    frame.setSize(new Dimension(400, 300));
    // System.out.println(frame.getin);
    // frame.setSize(d);
    //  (new Dimension(400,300));
    frame.setResizable(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel mainPane = new JPanel(new BorderLayout());
    mainPane.setBackground(Color.green);
    frame.add(mainPane);
    System.out.println("pane1 size :" + mainPane.getSize());
   
    frame.setVisible(true);
    System.out.println("pane size :" + mainPane.getSize());
     File files = new File("E:\\development\\third-party_app\\batik-1.6.1\\samples"); 
    System.out.println(frame.getContentPane().getSize());
    BufferedImage bufferedImage = readSvgImage(files.listFiles()[0],frame.getContentPane().getSize());
    SvgImage svgImage = new SvgImage();
    svgImage.setBufferedImage(bufferedImage);
    mainPane.add(svgImage);
    mainPane.validate();;
  }

  /**
   * 读取SVG文件
   * @param file SVG  File
   * @param size 图片大小
   * @return 图片数据缓存
   */
  public static BufferedImage readSvgImage(File file,Dimension size){
    GraphicsNode graphicsNode = null;
    try {
      Document document = documentLoader.loadDocument(file.toURI().toURL().toString());
      graphicsNode = gVTBuilder.build(bridgeContext, document);
      renderer.setTree(graphicsNode);
      SVGSVGElement sVGSVGElement = ((SVGDocument) document).getRootElement();
      renderer.setTransform(ViewBox.getViewTransform(null, (Element) sVGSVGElement, size.width, size.height));
      renderer.updateOffScreen(size.width, size.height);
      Rectangle rectangle = new Rectangle(0, 0, size.width, size.height);
      renderer.repaint(rectangle);
      BufferedImage image = renderer.getOffScreen();
      return image;
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return  null;
  }
}
