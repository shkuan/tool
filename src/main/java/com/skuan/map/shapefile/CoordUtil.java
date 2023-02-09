package com.skuan.map.shapefile;
 
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

 
 
import java.util.Arrays;
import java.util.List;
import java.util.Map;
 
/**
 * @author ZhaiYP 坐标转换
 */
 
/**
 * @author Administrator
 */
public class CoordUtil{
 
	
	
    /**
     * TODO 墨卡托投影prj文件坐标参数
     */
    final static String strWKTMercator = "PROJCS[\"WGS_1984_UTM_Zone_51N\","
            + "GEOGCS[\"GCS_WGS_1984\",DATUM[\"D_WGS_1984\"," + "SPHEROID[\"WGS_1984\",6378137.0,298.257223563]],"
            + "PRIMEM[\"Greenwich\",0.0],UNIT[\"Degree\",0.0174532925199433]]," + "PROJECTION[\"Transverse_Mercator\"],"
            + "PARAMETER[\"False_Easting\",500000.0]," + "PARAMETER[\"False_Northing\",0.0],"
            + "PARAMETER[\"Central_Meridian\",123.0]," + "PARAMETER[\"Scale_Factor\",0.9996],"
            + "PARAMETER[\"Latitude_Of_Origin\",0.0]," + "UNIT[\"Meter\",1.0]]\r\n";
 
   
 
    /**
     * TODO 墨卡托投影51N投影坐标转换为wgs_84经纬度投影
     *
     * @param lon x坐标
     * @param lat y坐标
     * @return double[] 转换后double类型x/y坐标对
     * @throws FactoryException
     * @throws MismatchedDimensionException
     * @throws TransformException           2018年10月16日
     */
    public static double[] convert_51NToWgs84(double lon, double lat) {
        // 传入原始的经纬度坐标
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);
        // 这里是以OGC WKT形式定义的是WGS_1984_UTM_Zone_51N投影
        CoordinateReferenceSystem mercatroCRS;
        Point targetPoint = null;
        try {
            mercatroCRS = CRS.parseWKT(strWKTMercator);
            // CoordinateReferenceSystem wgs_1984 = CRS.parseWKT(wgs_84);
            // 做投影转换，将WCG84坐标转换成世界墨卡托投影转
            MathTransform transform = CRS.findMathTransform(mercatroCRS, DefaultGeographicCRS.WGS84);
            targetPoint = (Point) JTS.transform(sourcePoint, transform);
        } catch (FactoryException e) {
            e.printStackTrace();
        } catch (MismatchedDimensionException e) {
            e.printStackTrace();
        } catch (TransformException e) {
            e.printStackTrace();
        }
        // 返回转换以后的X和Y坐标
        double x = Double.parseDouble(String.format("%.6f", targetPoint.getX()));
        double y = Double.parseDouble(String.format("%.6f", targetPoint.getY()));
        double[] targetCoord = {x, y};
        return targetCoord;
    }
 
    /**
     * TODO wgs_84经纬度坐标点，转换为墨卡托投影51N坐标点
     *
     * @param lon x坐标
     * @param lat y坐标
     * @return double[] 转换后double类型x/y坐标对
     * @throws FactoryException
     * @throws MismatchedDimensionException
     * @throws TransformException           2018年10月16日
     */
    public static double[] convert_WGS84To51N(double lon, double lat) {
        // 传入原始的经纬度坐标
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);
        // 这里是以OGC WKT形式定义的是WGS_1984_UTM_Zone_51N投影
        CoordinateReferenceSystem mercatroCRS;
        Point targetPoint = null;
        try {
            mercatroCRS = CRS.parseWKT(strWKTMercator);
            // CoordinateReferenceSystem wgs_1984 = CRS.parseWKT(wgs_84);
            // 做投影转换，将WCG84坐标转换成世界墨卡托投影转
            MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, mercatroCRS);
            targetPoint = (Point) JTS.transform(sourcePoint, transform);
        } catch (FactoryException e) {
            e.printStackTrace();
        } catch (MismatchedDimensionException e) {
            e.printStackTrace();
        } catch (TransformException e) {
            e.printStackTrace();
        }
        // 返回转换以后的X和Y坐标
        double[] targetCoord = {targetPoint.getX(), targetPoint.getY()};
        return targetCoord;
    }
 
    
    /**
     * 根据hsp文件坐标系
     * @Description
     * @author gxx
     * @date: 2019年7月5日 上午9:19:27
     */
    public static double[] convert(double lon, double lat, String strWKT) {
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);
        CoordinateReferenceSystem mercatroCRS;
        Point targetPoint;
		try {
			mercatroCRS = CRS.parseWKT(strWKT);
			MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, mercatroCRS);
	        targetPoint = (Point) JTS.transform(sourcePoint, transform);
	        double[] targetCoord = {targetPoint.getX(), targetPoint.getY()};
	        return targetCoord;
		} catch (FactoryException | MismatchedDimensionException | TransformException e) {
			// logger.error(e.getMessage(), e);
		}
        return null;
    }
    
    /**
     * 根据hsp文件坐标系，将墨卡托投影51N投影坐标转换为wgs_84经纬度投影
     * @Description
     * @author gxx
     * @date: 2019年7月5日 上午9:19:27
     */
    public static String convert(Geometry geom, String strWKT) {
        CoordinateReferenceSystem mercatroCRS;
		try {
			mercatroCRS = CRS.parseWKT(strWKT);
			MathTransform transform = CRS.findMathTransform(mercatroCRS, DefaultGeographicCRS.WGS84);
			Geometry geometry = JTS.transform(geom, transform);
			return geometry.toString();
		} catch (FactoryException | MismatchedDimensionException | TransformException e) {
			// logger.error(e.getMessage(), e);
		}
        return null;
    }
    
 
    public static List<Map<String, Object>> changeCoord(List<Map<String, Object>> list) {
        long startTime = System.currentTimeMillis();
        for (Map<String, Object> map : list) {
            if ((map.get("wgs84_x") == null || "".equals(map.get("wgs84_x")) || "null".equals(map.get("wgs84_x")))
                    && (map.get("bj54_x") != null && !"".equals(map.get("bj54_x")))) {
                String bj54_x = (String) map.get("bj54_x");
                String bj54_y = (String) map.get("bj54_y");
                List<String> bj54_xList = Arrays.asList(bj54_x.split(","));
                List<String> bj54_yList = Arrays.asList(bj54_y.split(","));
                StringBuilder wgs84_x = new StringBuilder();
                StringBuilder wgs84_y = new StringBuilder();
                for (int i = 0; i < bj54_xList.size(); i++) {
                    double x = Double.parseDouble(bj54_xList.get(i));
                    double y = Double.parseDouble(bj54_yList.get(i));
                    double[] wgs84ByBj54 = convert_51NToWgs84(x, y);
                    String wgs84x = String.valueOf(wgs84ByBj54[0]);
                    String wgs84y = String.valueOf(wgs84ByBj54[1]);
                    wgs84_x.append(wgs84x);
                    wgs84_y.append(wgs84y);
                    if (i < bj54_xList.size() - 1) {
                        wgs84_x.append(",");
                        wgs84_y.append(",");
                    }
                }
 
                map.put("wgs84_x", wgs84_x.toString());
                map.put("wgs84_y", wgs84_y.toString());
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime+ "ms");
        return list;
    }
 
    // main函数进行验证
    public static void main(String[] args) throws Exception {
        double longitude = 761814.915609849242342;
        double latitude = 4861308.17594982343242;
        long startTime = System.currentTimeMillis();
        double[] coordinate_51N = convert_51NToWgs84(longitude, latitude);
        long endtime = System.currentTimeMillis();
        System.out.println((endtime - startTime) / 1000);
        System.out.println("X: " + coordinate_51N[0] + ", Y: " + coordinate_51N[1]);
        // double[] coordinate_51N_ = new
        // CoordConverter().convert_51NToWeb(longitude, latitude);
        // System.out.println("X: " + coordinate_51N_[0] + ", Y: " +
        // coordinate_51N_[1]);
        // double[] coordinate_wgs = new
        // CoordConverter().convert_WGS84To51N(coordinate_51N[0],
        // coordinate_51N[1]);
        // System.out.println("X: " + coordinate_wgs[0] + ", Y: " +
        // coordinate_wgs[1]);
        
        
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
		WKTReader reader = new WKTReader(geometryFactory);
		String multipolygon = "MULTIPOLYGON (((13749736.970548067 5102563.625129204, 13753483.478041079 5102754.125510205, 13754753.480581086 5100150.620303195, 13753483.478041079 5098118.616239186, 13749038.469151061 5098563.11712819, 13749736.970548067 5102563.625129204)))";
		Geometry geom1 = (Geometry) reader.read(multipolygon);
		String convert = convert(geom1, strWKTMercator);
		System.out.println(convert);
    }
}