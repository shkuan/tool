package com.skuan.gui.map.geotools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.skuan.common.OfflineProperties;
import com.skuan.common.SystemDynamicProperties;
import com.skuan.common.SystemProperties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.MapContent;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;

public class MapViewPanel extends JPanel{

   	private Logger logger = LogManager.getLogger();

	private StreamingRenderer gtRenderer;

	private MapContent mapContent;

	private ReferencedEnvelope referencedEnvelope;

	private BufferedImage mapBufferedImage;

	private double scaleMinStep;

	private Point2D mouseDraggedPointstarted;

	private boolean initBuild;

	public MapViewPanel(){
		super();
		initVariate();
		installListener();
	}

	private void initVariate(){

		scaleMinStep = Double.parseDouble( SystemProperties.getProperty("map.view.scalestep") );
		
		gtRenderer = new StreamingRenderer();
		mapContent = new MapContent();

		initBuild = true;
	
		mapContent.getViewport().setCoordinateReferenceSystem(OfflineProperties.UTM_CRS);
		mapContent.getViewport().setFixedBoundsOnResize(false);
		mapContent.getViewport().setMatchingAspectRatio(true);
		
		gtRenderer.setMapContent(mapContent);
	}

	public void loadMap(Map<String, SimpleFeatureSource> shapes, Map<String, Style> slds){
		shapes.forEach((name , featureSource) -> {
			Style style = null;
			if(slds.get(name) != null)
				style = slds.get(name);
			else{
				logger.warn("layername : {} , has no sld file, use default.", name);
				style = SLD.createSimpleStyle(featureSource.getSchema(),Color.GRAY);
			}
			FeatureLayer layer = new FeatureLayer(featureSource, style);
			layer.setVisible(true);
			mapContent.addLayer(layer);
		});
	}

	public void northView(){
		
	}

	public void resetView(){
		
		initReferencedEnvelope();
		SwingUtilities.invokeLater( () -> {
			rebuildImage();
			repaint();
		});
	}

	public void rotateView(double theta){
		
	}

	private  void initReferencedEnvelope(){

		mapContent.getViewport().setBounds(null);
		resetSystemCenter();
		Point2D centerP = SystemDynamicProperties.getProperty("dynamic.systemcenter", Point2D.class);
		double initRange = Double.parseDouble(SystemProperties.getProperty("map.view.init.visible.range"));
		
		Rectangle screenRect = getVisibleRect(); 
		mapContent.getViewport().setScreenArea(screenRect);

		double swidth = screenRect.getWidth();
		double sheight = screenRect.getHeight();
		double mwidth = 0;
		double mheight = 0;
		double scale = 0;
		if(swidth > sheight){
			scale = (mwidth = initRange) / swidth;
			mheight = sheight * scale;
		}else{
			scale = (mheight = initRange) / sheight;
			mwidth = swidth * scale;
		}
		Rectangle2D rectangle = new Rectangle2D.Double(centerP.getX() - mwidth /2, centerP.getY() - mheight/2, mwidth, mheight);
		referencedEnvelope = ReferencedEnvelope.create(rectangle, OfflineProperties.UTM_CRS);
		mapContent.getViewport().setBounds(referencedEnvelope);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(initBuild){
			resetView();
			initBuild = false;
		}
		((Graphics2D)g).drawImage(mapBufferedImage, null, 0, 0);
	} 

	private void rebuildImage(){
		mapBufferedImage = new BufferedImage(getVisibleRect().width, getVisibleRect().height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d =  mapBufferedImage.createGraphics();
		gtRenderer.paint(g2d,  getVisibleRect() ,referencedEnvelope);
		// g2d.drawRect(getVisibleRect().getLocation().x, getVisibleRect().getLocation().y, getVisibleRect().width, getVisibleRect().height);
		g2d.dispose();
	}

	public void resetSystemCenter(){
		Point2D syscenter = OfflineProperties.getSYSTEM_CENTER();
        if(syscenter != null){
            SystemDynamicProperties.addProperty("dynamic.systemcenter", new Point2D.Double(syscenter.getX() , syscenter.getY()));
        }
	}

	private void rebulidReferencedEnvelope(Point2D systemcenter, double mWidth, double mHeight, boolean rebulidTransform){
		referencedEnvelope.init(systemcenter.getX() - mWidth / 2, systemcenter.getX() + mWidth / 2, systemcenter.getY() - mHeight / 2, systemcenter.getY() + mHeight / 2);
		if(rebulidTransform)
			mapContent.getViewport().setBounds(referencedEnvelope);
	}

	private void rebulidReferencedEnvelope(Point2D systemcenter, boolean rebulidTransform){
		double mWidth = referencedEnvelope.getWidth();
		double mHeight = referencedEnvelope.getHeight();
		referencedEnvelope.init(systemcenter.getX() - mWidth / 2, systemcenter.getX() + mWidth / 2, systemcenter.getY() - mHeight / 2, systemcenter.getY() + mHeight / 2);
		if(rebulidTransform)
			mapContent.getViewport().setBounds(referencedEnvelope);
	}

	private void installListener(){
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				mouseDraggedPointstarted = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// updateTransform = true;
				mouseDraggedPointstarted = null;
				repaint();
			}
			
		});

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
			
				if(mouseDraggedPointstarted != null){
					AffineTransform af = mapContent.getViewport().getScreenToWorld();
					Point endPoint =  e.getPoint();
					Point2D sysstarted = new Point2D.Double();
					Point2D sysend = new Point2D.Double();
					af.transform(mouseDraggedPointstarted, sysstarted);
					af.transform(endPoint, sysend);
					double tx = sysend.getX() - sysstarted.getX();
					double ty = sysend.getY() - sysstarted.getY(); 
					Point2D systemcenter = SystemDynamicProperties.getProperty("dynamic.systemcenter", Point2D.class);
					systemcenter.setLocation(systemcenter.getX() - tx, systemcenter.getY() - ty);
					rebulidReferencedEnvelope(systemcenter, false);
					SwingUtilities.invokeLater( () -> {
						rebuildImage();
						repaint();
					});
					mouseDraggedPointstarted = endPoint;
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
			
		});

		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double step  = 0;
				if(e.getWheelRotation() > 0){
					step = scaleMinStep;
				}else{
					step = -scaleMinStep;
				}
				
				double mHeight = referencedEnvelope.getHeight() + step;
				double mWidth = (mHeight / getVisibleRect().getHeight()) * getVisibleRect().getWidth();

				Point2D mapcenter = SystemDynamicProperties.getProperty("dynamic.systemcenter", Point2D.class);

				rebulidReferencedEnvelope(mapcenter, mWidth, mHeight, true);

				SwingUtilities.invokeLater( () -> {
					rebuildImage();
					repaint();
				});
			}
			
		});
	}
}
