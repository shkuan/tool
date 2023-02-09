package com.skuan.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.geometry.Rectangle2D;

public class AffineTransformTest extends JFrame{



    public static void main(String[] args) {
        new AffineTransformTest().setVisible(true);
    }

    public AffineTransformTest(){
        innit();
    }


    public void innit(){
        setTitle("Tool Dialog");
        setSize(new Dimension(600, 400));
        setLocation(new Point(0, 0));
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myCom pane  = new myCom();


        JPanel bg =  new JPanel(new BorderLayout());
        bg.setBackground(Color.black);
        bg.add(pane);
        this.add(bg);
        pane.scale();

        // Thread t = new Thread(new Runnable() {

        //     @Override
        //     public void run() {
        //         for (int i = 0; i < 5; i++) {
        //             pane.scale();
        //         }
        //     }
            
        // });
        // t.start();


    }
    
}

class myCom extends JComponent{
    private Point2D p0;
    private Point2D p1;

    private Point2D mp0;
    private Point2D mp1;

    private AffineTransform affineTransform;

    public myCom(){
        init();
    }

    public void init(){
        this.setLayout(new BorderLayout());
        p0 = new Point2D.Double(0, 0);
        p1 = new Point2D.Double(p0.getX() + 80, p0.getY() + 80);
    }

    @Override
    protected void paintComponent(Graphics g) {
      
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.GREEN);
        g2d.drawRect((int)p0.getX(), (int)p0.getY(), (int)Math.abs(p0.getX() - p1.getX()) , (int)Math.abs(p0.getY() - p1.getY()));
        g2d.drawString("p0", (int)p0.getX() + 2, (int)p0.getY() + 2);
        g2d.drawString("p1", (int)p1.getX() + 2, (int)p1.getY() + 2);
        if(mp0 != null){
            g2d.setColor(Color.ORANGE);
            g2d.drawRect((int)mp0.getX(), (int)mp0.getY(), (int)Math.abs(mp0.getX() - mp1.getX()) , (int)Math.abs(mp0.getY() - mp1.getY()));
            g2d.drawString("mp0", (int)mp0.getX() + 2, (int)mp0.getY() + 2);
            g2d.drawString("mp1", (int)mp1.getX() + 2, (int)mp1.getY() + 2);
        }
       
    }

    public void scale(){
        double width  = Math.abs(p0.getX() - p1.getX());
        double height = Math.abs(p0.getY() - p1.getY());
        affineTransform = new AffineTransform();
        
        
        affineTransform.translate( - width / 2, - height /2);
        mp0 = new Point2D.Double(p0.getX() + 5, p0.getY() + 5);
        mp1 = new Point2D.Double(p1.getX() - 5, p1.getY() - 5);
        double scale  = calScale(p0 , p1, mp0, mp1);
        // System.out.println(scale);

        // int step = 40;
        // double p0width  = Math.abs(p0.getX() - p1.getX());
        // double p0height = Math.abs(p0.getY() - p1.getY());
        // double xscale = (step + p0width )/ p0width;
        // double yscale = (step + p0height) / p0height;
        
        // double xoff = centerM.getX() * scale - centerP.getX();
        // double yoff = centerM.getY() * scale + centerP.getY();

       
        // affineTransform.scale(xscale, yscale);
        // affineTransform.translate(  - p0.getX(),  -  p0.getY());
        // Point2D pcenter = new Point2D.Double((p0.getX() + p1.getX())/2 ,(p0.getY() + p1.getY()) / 2 );
        // affineTransform.rotate(100, pcenter.getX()  , pcenter.getY());
       
        
        // affineTransform.translate( width / 2,  height /2);
        mp0 = new Point2D.Double();
        mp1 = new Point2D.Double();
        affineTransform.transform(p0, mp0);
        affineTransform.transform(p1, mp1);
        System.out.println("mp0 : "+ mp0 + " , map1 : "+mp1 );
    }

    public double calScale(Point2D pmin, Point2D pmax, Point2D mmin, Point2D mmax){
        double pwidth  = Math.abs(pmin.getX() - pmax.getX());
        double pheight = Math.abs(pmin.getY() - pmax.getY());
        
        Rectangle2D rectP = new Rectangle2D(pmin.getX(), pmin.getY(), pwidth, pheight);
        Point2D centerP = new Point2D.Double( (pmin.getX() + pmax.getX()) / 2 , (pmin.getY() + pmax.getY()) / 2);

        double mwidth  = Math.abs(mmin.getX() - mmax.getX());
        double mheight = Math.abs(mmin.getY() - mmax.getY());
        Rectangle2D rectm = new Rectangle2D(mmin.getX(), mmin.getY(), mwidth, mheight);
        Point2D centerM = new Point2D.Double( (mmin.getX() + mmax.getX()) / 2 , (mmin.getY() + mmax.getY()) / 2);

        double xscale =  pwidth / mwidth;
        double yscale =  pheight / mheight;

        double scale = Math.min(xscale, yscale);

        

        // worldToScreen = new AffineTransform(scale, 0, 0, -scale, -xoff, yoff)
        return scale;
    }

}
