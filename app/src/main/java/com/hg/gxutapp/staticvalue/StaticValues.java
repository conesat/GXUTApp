package com.hg.gxutapp.staticvalue;

import com.hg.gxutapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class StaticValues {
    public static List<News> newsOne=new ArrayList<>();
    public static List<News> newsTow=new ArrayList<>();
    public static List<News> newsThree=new ArrayList<>();
    public static String STUDEN_LOGIN_URL="http://online.gxut.edu.cn:81/cmzx/StudentsMediaCentre.php/Index/login/kebiao/1.html";

    public static String STUDEN_SCORE_URL="http://online.gxut.edu.cn:81/cmzx/StudentsMediaCentre.php/Index/login/chengji/1.html";

    public static String STUDEN_CLASS_URL="http://online.gxut.edu.cn:81/cmzx/StudentsMediaCentre.php/Index/login/kebiao/1.html";

    public static String[][] MY_CLASS=new String[13][8];

    public static List<String[]> MY_SCORE=new ArrayList<String[]>();

    public static String html="<!DOCTYPE html>\n" +
            "<html>\n" +
            "\t<head>\n" +
            "\t\t<meta charset=\"UTF-8\">\n" +
            "\t\t<title></title>\n" +
            "\t\t<style>\n" +
            "\t\t\n" +
            "\t\t\thtml{\n" +
            "\t\t\t\twidth: 100%;\n" +
            "\t\t\t}\n" +
            "body {\n" +
            "\t\t\t\tmargin: 0 auto;\n" +
            "\t\t\t\twidth: 98%;\n" +
            "\t\t\t\toverflow-x: hidden;\n" +
            "\t\t\t}"+
            "\t\t</style>\n" +
            "\t</head>\n" +
            "\t<body>\n" +
            "\t\t<div style=\"padding: 0 20px;margin: 10px 0;font-size: 18px;\">NEWS_ONE</div>\n" +
            "\t\t<div style=\"width: 100%;background: #62cdf1;height: 2px;\"></div>\n" +
            "\t\t<div style=\"padding: 10px 20px;font-size: 12px;color: #9c9c9c;\">NEWS_TOW</div>\n" +
            "\t\t<div style=\"padding: 10 20px;\">NEWS_CONTENT</div>\n" +
            "\t</body>\n" +
            "</html>\n";

}
