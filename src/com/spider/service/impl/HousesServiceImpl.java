package com.spider.service.impl;

import com.spider.service.IHousesService;
import com.spider.utils.AnalysisHouseUtil;
import com.spider.utils.PropertiesUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务功能类
 */
public class HousesServiceImpl implements IHousesService {


    /**
     * 根据house_config.properties配置的url抓取热门楼盘数据
     * @return 前5热门楼盘名称、url、封面信息
     */
    public List<Map<String, String>> getTop5Houses () {
        ArrayList<Map<String, String>> housesList = new ArrayList<Map<String, String>>();

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
            String housesName = analysisHouseUtil.AnalysisName(tpic.select(".pbtext a").text());
            String housesUrl = tpic.select(">a").attr("href");
            String housesCover = "";

            Elements imgArray = tpic.select(">a img");
            housesCover = imgArray.eq(imgArray.size() - 1).attr("src");

            Map<String, String> housesItem = new HashMap<String, String>();
            housesItem.put("primaryName", tpic.select(".pbtext a").text());
            housesItem.put("name", housesName);
            housesItem.put("primaryUrl", tpic.select(">a").attr("href"));
            housesItem.put("url", housesUrl);
            housesItem.put("cover", housesCover);

            housesList.add(housesItem);
        }

        // 这个要服务完成之后要关闭
        propertiesUtil.closeProperties();
        return housesList;
    }

}
