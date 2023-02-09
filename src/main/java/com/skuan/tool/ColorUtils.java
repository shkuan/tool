package com.skuan.tool;

import java.awt.Color;

public class ColorUtils {
    public static void main(String[] args) {
        String hexString = color2string(Color.RED);
        System.out.println("16进制字符串:" + hexString);
        Color color = string2color(hexString);
        System.out.println("16进制字符串转为颜色的ARGB值:("+color+")");
    }

    public static String color2string(Color color) {
        return new StringBuffer().append("#").append(intToHexValue(color.getRed())).append(intToHexValue(color.getGreen())).append(intToHexValue(color.getBlue())).toString();
    }

    public static Color string2color(String color){
        return Color.decode(color);
    }

    private static String intToHexValue(int number) {
        String result = Integer.toHexString(number & 0xff);
        while (result.length() < 2) {
            result = "0" + result;
        }
        return result.toLowerCase();
    }

    private static Color fromStrToARGB(String str) {
        String str1 = str.substring(0, 2);
        String str2 = str.substring(2, 4);
        String str3 = str.substring(4, 6);
        String str4 = str.substring(6, 8);
        int alpha = Integer.parseInt(str1, 16);
        int red = Integer.parseInt(str2, 16);
        int green = Integer.parseInt(str3, 16);
        int blue = Integer.parseInt(str4, 16);
        Color color = new Color(red, green, blue, alpha);
        return color;
    }
}
