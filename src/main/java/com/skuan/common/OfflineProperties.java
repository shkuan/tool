package com.skuan.common;

import java.awt.geom.Point2D;

import com.skuan.tool.GeoUtils;

import org.apache.commons.lang3.StringUtils;
import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class OfflineProperties {
    
    private static Point2D SYSTEM_CENTER;

    public static CoordinateReferenceSystem UTM_CRS;

    static{
		String wgsCenterP = SystemProperties.getProperty("basic.params.mapcenter");
		String[] lonlat = StringUtils.split(wgsCenterP, ";");
		double lon_digi = GeoUtils.lonstr2digit(lonlat[0],true);
		double lat_digi = GeoUtils.latstr2digit(lonlat[1],true);
		try {
			UTM_CRS = CRS.decode("EPSG:" + GeoUtils.EPSG_code(lon_digi),true);
			SYSTEM_CENTER =  GeoUtils.wgs84toutm(lon_digi,lat_digi,UTM_CRS);
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

    public static Point2D getSYSTEM_CENTER() {
        return SYSTEM_CENTER;
    }

}
