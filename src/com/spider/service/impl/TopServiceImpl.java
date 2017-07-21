package com.spider.service.impl;

import com.spider.service.ITopService;
import com.spider.utils.AnalysisHouseUtil;
import com.spider.utils.PropertiesUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhangyan on 17/7/16.
 */
public class TopServiceImpl implements ITopService {
    /**
     * 获取热门楼盘数据的html内容
     */
    public String getTopHouses() {

        AnalysisHouseUtil analysisHouseUtil = new AnalysisHouseUtil();

        // 获取配置文件内的key
        String housesConfigPath = "spider_config" + File.separator + "house_config.properties";
        PropertiesUtil propertiesUtil = new PropertiesUtil(housesConfigPath);
        String topUrl = propertiesUtil.getValue("top_url");
        String topHouseItem = propertiesUtil.getValue("top_house_item");



        // 根据url爬取页面数据
        Document htmlDoc = null;  // 承载新房排行榜的dom
        try {
            htmlDoc = Jsoup.connect(topUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements tpics = htmlDoc.select(topHouseItem);
        for (Element tpic : tpics) {
            // 从element中解析数据
            String houseName = analysisHouseUtil.extractValidHousesName(tpic.select(".pbtext a").text());
            String housePath = tpic.select(">a").attr("href");
            String houseCover = "";

            Elements imgArray = tpic.select(">a img");
            houseCover = imgArray.eq(imgArray.size() - 1).attr("src");

            // 处理下name

            System.out.println("-------");
            System.out.println(houseName);
            System.out.println(housePath);
            System.out.println(houseCover);
        }


        System.out.println();



        // 这个要服务完成之后要关闭
        propertiesUtil.closeProperties();


        return "";
    }
}
