package com.skuan.tool;

import java.awt.geom.Point2D;


import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

public class GeoUtils {

	
	
	/**
	 * E1035654   转成小数
	 * @param lon
	 * @param lat
	 */
	public static double lonstr2digit(String lon){
		return lonstr2digit(lon,false);
	}
	/**
	 * is100ms：秒收是否为以100毫秒为单位
	 * @param lon
	 * @param is100ms
	 * @return
	 */
	public static double lonstr2digit(String lon,boolean is100ms){
			double result = 0;
			result = Double.parseDouble(lon.substring(1,4));
			double mid = Double.parseDouble(lon.substring(4,6));
			double sec = Double.parseDouble(lon.substring(6));
			if( is100ms ) {
				sec = sec /10.0;
			}
			result += mid/60.0 + sec / 3600;
			return result;
	}

	/**
	 * N303442     转成小数
	 * @param lat
	 * @return
	 */
	public static double latstr2digit(String lat){
		return latstr2digit(lat,false);
	}
	/**
	 * is100ms：秒收是否为以100毫秒为单位
	 * @param lat
	 * @param is100ms
	 * @return
	 */
	public static double latstr2digit(String lat,boolean is100ms){
			double result = 0;
			result = Double.parseDouble(lat.substring(1,3));
			double mid = Double.parseDouble(lat.substring(3,5));
			double sec = Double.parseDouble(lat.substring(5));
			if( is100ms ) {
				sec = sec / 10.0;
			}
			result += mid/60.0 + sec / 3600;
			return result;
		
	}
	
	/**
	 * 获取EPSC 代码
	 * @param lon
	 * @return
	 */
	public static int  EPSG_code(double lon){
		int code = 0; 
		if( lon >=72 && lon <78  ){
			code = 43;
		}else if( lon >=78 && lon <84  ){
			code = 44;
		}else if( lon >=84 && lon <90  ){
			code = 45;
		}else if( lon >=90 && lon <96  ){
			code = 46;
		}else if( lon >=96 && lon <102  ){
			code = 47;
		}else if( lon >=102 && lon <108  ){
			code = 48;
		}else if( lon >=108 && lon <114  ){
			code = 49;
		}else if( lon >=114 && lon <120  ){
			code = 50;
		}else if( lon >=120 && lon <126  ){
			code = 51;
		}else if( lon >=126 && lon <132  ){
			code = 52;
		}else if( lon >=132 && lon <138  ){
			code = 53;
		}
		return 32600 + code;
	}
	
	
	/**
	 * 
	 * @param lon
	 * @param lat
	 * @return
	 */
	public static Point2D wgs84toutm(double lon,double lat, CoordinateReferenceSystem utmCRS){
		try {
			CoordinateReferenceSystem wgsCRS = DefaultGeographicCRS.WGS84;
			MathTransform transform = CRS.findMathTransform(wgsCRS, utmCRS);
			Coordinate coord = new Coordinate(lon, lat);
			Coordinate coorDst = new Coordinate();
			JTS.transform(coord,coorDst, transform);
			return new Point2D.Double(coorDst.x, coorDst.y);
		} catch (Exception e) {
			return null;
		} 
	}
	
	/**
	 * 
	 * @param lon
	 * @param lat
	 * @return
	 */
	public static Point2D utmtowgs84(double utmx,double utmy, CoordinateReferenceSystem utmCRS){
		try {
			CoordinateReferenceSystem wgsCRS = DefaultGeographicCRS.WGS84;
			MathTransform transform = CRS.findMathTransform(utmCRS,wgsCRS);
			Coordinate coord = new Coordinate(utmx, utmy);
			Coordinate coorDst = new Coordinate();
			JTS.transform(coord,coorDst, transform);
			return new Point2D.Double(coorDst.x, coorDst.y);
		} catch (Exception e) {
			return null;
		} 
	}
	
	/**
	 * 84坐标转本地系统坐标
	 * @param lon
	 * @param lat
	 * @return
	 */
	public static Point2D wgs84tosys(double lon,double lat , CoordinateReferenceSystem utmCRS ,Point2D anchory){
		try {
			Point2D utmpoint = wgs84toutm(lon, lat, utmCRS);
			return new Point2D.Double(utmpoint.getX()-anchory.getX(), utmpoint.getY()-anchory.getY());
		} catch (Exception e) {
			return null;
		} 
	}
	/**
	 * 			   	
	 * 		lon: E + 度三位  + 分两位   + 秒两位加小数
	 * 		lat: N + 度两位  + 分两位   + 秒两位加小数 
	 * 
	//  * @param lon
	//  * @param lat
	//  * @return
	//  */
	// public static Point2D wgs84tosys(String lon,String lat){
	// 	return wgs84tosys(lonstr2digit(lon), latstr2digit(lat));
	// }
	/**
	 * 返回经纬度为小数
	 * @param x
	 * @param y
	 * @return
	 */
	// public static Point2D systowgs84(double x,double y){
	// 	try {
	// 		Point2D utmpoint = utmtowgs84(x+syscenter.getX(), y+syscenter.getY());
	// 		return utmpoint;
	// 	} catch (Exception e) {
	// 		return null;
	// 	} 
	// }
	/**
	 * 
	 * @param coo
	 * @return
	 */
	public static Point2D  coor2point(Coordinate coo){
		if(coo == null ){
			return null;
		}
		return new Point2D.Double(coo.getX(), coo.getY());
	}
	/**
	 * 
	 * @param point
	 * @return
	 */
	public static Coordinate  point2coor(Point2D point){
		if( point == null ){
			return null;
		}
		return new Coordinate(point.getX(), point.getY());
	}
	
}
