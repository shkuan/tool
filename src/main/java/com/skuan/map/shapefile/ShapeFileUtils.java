package com.skuan.map.shapefile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.StyleFactoryImpl;
import org.geotools.xml.styling.SLDParser;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class ShapeFileUtils {

    // public static void readShapeFile(String filepath){

    //     try {
    //         ShapefileDataStore sfds = new ShapefileDataStore(new File(filepath).toURI().toURL());
    //          //设置编码，防止中文读取乱码
    //          sfds.setCharset(Charset.forName("UTF-8"));
    //     } catch (MalformedURLException e) {
    //         e.printStackTrace();
    //     }
    // }

	private static StyleFactory STYLE_FACTORY;

	static{
		STYLE_FACTORY = new StyleFactoryImpl();
	}

    //读shp文件【几何信息+属性信息】
    public static List < Property > SHPReader(String path) throws Exception {

		try{
			List < Property > propertyList  = new ArrayList < > ();
			//构建shapefile数据存储的实例
			ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
			File file = new File(path);
			//构建一个已存在的shapfile数据源
			//ShapefileDataStore:数据存储实现，允许从Shapefiles读取和写入
			ShapefileDataStore ds = (ShapefileDataStore) dataStoreFactory.createDataStore(file.toURI().toURL());
			//设置编码，防止中文读取乱码
			ds.setCharset(Charset.forName("UTF-8"));
			System.out.println(ds.getSchema().getCoordinateReferenceSystem().toWKT());
			//getFeatureSource():ContentFeatureSource
			//这个特性是由 FeatureCollection提供的操作完成的。单独的特征记忆实现由子类提供:
			//SimpleFeatureSource特征资源明确地使用FeatureCollection【集合】，可迭代
			SimpleFeatureSource featureSource = ds.getFeatureSource();
	
			//getFeatures():以FeatureCollection的形式检索所有特性。
			//一个用于处理FeatureCollection的实用工具类。提供一个获取FeatureCollection实例的机制
			FeatureCollection < SimpleFeatureType, SimpleFeature > result = featureSource.getFeatures();
	
			System.out.println("几何对象总共有：" + result.size());
			//features():返回一个FeatureIterator迭代器
			SimpleFeatureIterator it = (SimpleFeatureIterator) result.features();
	
			while (it.hasNext()) {
				SimpleFeature feature = it.next();
				//迭代属性【属性我们可以理解为一个几何对象的属性节点，也就是对一个几何图形的描述字段】
				Iterator < Property > ip = feature.getProperties().iterator();
				//            System.out.println("========================");
				//再来个while
				while (ip.hasNext()) {
					Property pro = ip.next();
					//                System.out.println(pro.getName()+" = "+pro.getValue());
					propertyList.add(pro);
				} //end 属性迭代
			}
			it.close();
			return propertyList;
		}catch(Exception e){
			System.out.println("error : " + path);
			return null;

		}
    } 

	 //读shp文件【几何信息+属性信息】
	 public static SimpleFeatureSource getSHPFeatureSource(String path) throws Exception {
        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        File file = new File(path);
		if(file.exists()){
			ShapefileDataStore ds = (ShapefileDataStore) dataStoreFactory.createDataStore(file.toURI().toURL());
			ds.setCharset(Charset.forName("UTF-8"));
			SimpleFeatureSource featureSource = ds.getFeatureSource();
			if(featureSource != null){
				return featureSource;
			}
		}
        
		return null;
	
    }

    /**
	 * 读取shp文件
	 * 
	 * @Description
	 * @author gxx
	 * @date: 2019年7月3日 上午9:33:43
	 */
	public static List<Map<String, Object>> readFile(String shapePath, String prjPath) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ShapefileDataStoreFactory factory = new ShapefileDataStoreFactory();
		ShapefileDataStore dataStore = null;
		try {
			dataStore = (ShapefileDataStore) factory.createDataStore(new File(shapePath).toURI().toURL());
			if (dataStore != null) {
				dataStore.setCharset(Charset.forName("UTF-8"));
			}
			String wkt = "";
			if(StringUtils.isNotBlank(prjPath)) {
				wkt = readTxtFile(prjPath);
				// logger.info("获取到shp文件坐标系：{}", wkt);
			}
			boolean change = false;
			//如果是投影坐标系，则进行坐标转换
			if(wkt.startsWith("PROJCS")) {
				change = true;
			}
			SimpleFeatureSource featureSource = dataStore.getFeatureSource();
			SimpleFeatureIterator itertor = featureSource.getFeatures().features();
//			一个用于处理FeatureCollection的实用工具类。提供一个获取FeatureCollection实例的机制
//      		FeatureCollection<SimpleFeatureType, SimpleFeature> result=featureSource.getFeatures();
//      		
//      		FeatureIterator<SimpleFeature> iterator = result.features();
			while (itertor.hasNext()) {
				SimpleFeature feature = itertor.next();
				Iterator<Property> it = feature.getProperties().iterator();
				Map<String, Object> map = new HashMap<String, Object>();
				while (it.hasNext()) {
					Property pro = it.next();
					if(change && "the_geom".equals(String.valueOf(pro.getName()))) {
						map.put(String.valueOf(pro.getName()), CoordUtil.convert((Geometry)pro.getValue(), wkt));
					} else {
						map.put(String.valueOf(pro.getName()), String.valueOf(pro.getValue()));
					}
				}
				list.add(map);
//				SimpleFeature feature = (SimpleFeature) iterator.next();
//	            // Feature转GeoJSON
//	            FeatureJSON fjson = new FeatureJSON();
//	            StringWriter writer = new StringWriter();
//	            fjson.writeFeature(feature, writer);
//	            String sjson = writer.toString();
//	            System.out.println("sjson=====  >>>>  "  + sjson);
			}
			itertor.close();
			return list;
		} catch (Exception e) {
			// logger.error(e.getMessage(), e);
		} finally {
			if(dataStore != null) {
				dataStore.dispose();
			}
		}
		return null;
	}


    /**
	 * 读取txt文件
	 * 
	 * @Description
	 * @author gxx
	 * @date: 2019年8月5日 下午5:47:11
	 */
	public static String readTxtFile(String path) {
		StringBuilder stringBuilder = new StringBuilder();
		InputStreamReader inputStreamReader = null;
		FileInputStream inputStream = null;
		try {
            inputStream = new FileInputStream(new File(path));
			inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String s = null;
			while ((s = br.readLine()) != null) {
				stringBuilder.append(s);
			}
		} catch (Exception e) {
			// logger.error(e.getMessage(), e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// logger.error(e.getMessage(), e);
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					// logger.error(e.getMessage(), e);
				}
			}
		}
		return stringBuilder.toString();
	}

	public static Map<String, SimpleFeatureSource> getSHPFeatures(String shapeFile,String includeLayers){
		File[] shpfiles = getFiles("shp", shapeFile);
		Map<String, SimpleFeatureSource> data = null;
		if(shpfiles != null){
			data = new HashMap<>();
			List<String> layername = null;
			if(StringUtils.isNotBlank(includeLayers)){
				layername = Arrays.asList(StringUtils.split(includeLayers, ","));
			}
			for (File file : shpfiles) {
				StringBuffer sb = new StringBuffer(file.getName());
				sb.delete(StringUtils.lastIndexOf(file.getName(), "."), file.getName().length());
				if(layername != null && !layername.contains(sb.toString())){
					continue;
				}
				try {
					SimpleFeatureSource simpleFeatureSource = getSHPFeatureSource(file.getPath());
					data.put(simpleFeatureSource.getInfo().getName(), simpleFeatureSource);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return data == null ? null : data;
	}

	public static Map<String ,Style> getSldStyle(String sldFile ,String includeLayers){
		File[] shpfiles = getFiles("sld", sldFile);
		Map<String, Style> slds = null;
		if(shpfiles == null || shpfiles.length == 0)
			return slds;
		slds = new HashMap<>();
		List<String> layername = null;
		if(StringUtils.isNotBlank(includeLayers)){
			layername = Arrays.asList(StringUtils.split(includeLayers, ","));
		}
		for (File file : shpfiles) {
			try {
				StringBuffer sb = new StringBuffer(file.getName());
				sb.delete(StringUtils.lastIndexOf(file.getName(), "."), file.getName().length());
				if(layername != null && !layername.contains(sb.toString())){
					continue;
				}
				SLDParser sld = new SLDParser(STYLE_FACTORY, file);
				Style style = sld.readXML()[0];
				slds.put(style.getName(), style);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return slds;
	}


    public static void main(String[] args) {
        try {
			File[] shpfiles = getFiles("shp", "E:\\development\\airport_map\\zuck");
			if(shpfiles == null) return ;
			for (File file : shpfiles) {
				List < Property > properties = SHPReader(file.getPath());
				if(properties != null)
					for (Property property: properties) {
						if(property.getValue() instanceof MultiPolygon){
							System.out.println("=================================");
							System.out.println(property.getValue().toString());
						}
					}
			}

			getSHPFeatures("E:\\development\\airport_map\\zuck", null);
			getSldStyle("E:\\development\\airport_map\\zuck",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static File[] getFiles(String suffix, String filepath){
		File file = new File(filepath);
		if(! file.exists()) return null;
		if(StringUtils.endsWith(filepath, new StringBuffer().append(".").append(suffix).toString()))
			return new File[]{file};
		File[] files = file.listFiles();
		if(files != null  && files.length != 0){
			List<File> flist = new ArrayList<>();
			for (int i = 0; i < files.length; i++) {
				if(StringUtils.endsWith(files[i].getName(), new StringBuffer().append(".").append(suffix).toString()) )
					flist.add(files[i]);
			}
			if(flist.size() != 0){
				return flist.toArray(new File[0]);
			}
		}
		return null;
	}

}