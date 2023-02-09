package com.skuan.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.skuan.xml.shapefile.XLayer;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLMapper {

    public static void main(String[] args) {
       Document document = readXml("E:\\work\\asmgcs\\sdd_2.0.3\\config\\cq\\AirNet\\map\\systemmap\\default.xml");
        
       loadXLayersOxm(document);


    }

    public static Document readXml(String pathname) {
        try {
            File xmlFile = new File(pathname);
            if (!xmlFile.exists()) return null;
            SAXReader saxReader = new SAXReader();
            return saxReader.read(xmlFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<XLayer> loadXLayersOxm( Document document) {
        List<?> elements = document.selectNodes("/Layers/layerlist/Layer");
        List<XLayer> xLayers = new ArrayList<>();
        for (Object element : elements) {
            if(element instanceof Element){
                Element _element = (Element)element;
                Element element_enName = _element.element("enName");
                if(element_enName == null) continue;
                XLayer xLayer = new XLayer();
                xLayer.setName(element_enName.getTextTrim());
                Element element_visible = _element.element("visible");
                xLayer.setVisible( element_visible == null ? false : StringUtils.equalsIgnoreCase(element_visible.getText(), "true"));
                xLayers.add(xLayer);
                System.out.println(xLayer);
            }
           
        }
        return xLayers;
    }

}