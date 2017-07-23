package com.spider.service.impl;

import com.spider.config.Constant;
import com.spider.model.TFloor;
import com.spider.model.THouses;
import com.spider.model.TPlots;
import com.spider.service.IHousesService;
import com.spider.utils.AnalysisHouseUtil;
import com.spider.utils.LogFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务功能类
 */
public class HousesServiceImpl implements IHousesService {

    /**
     * 从搜房网获取全部楼盘数据
     */
    @Override
    public HashMap<String, Object> getAllHouses() {

        ArrayList<THouses> allHousesList = new ArrayList<THouses>();  // 承载所有楼盘数据集合
        ArrayList<TFloor> floorList = new ArrayList<TFloor>();  // 承载所有地块数据集合

        int sfwUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        Document pageDoc = null;  // 承载抓取到的每页房产商数据

        try {
            // 获取单页搜房网楼盘列表
            pageDoc = Jsoup.connect("http://newhouse.jn.fang.com/house/s/b9"+sfwUrlPageNumer).timeout(5000).get();
            Elements lis = pageDoc.select("#newhouse_loupai_list li");

            for (Element li : lis) {
                // 抓取详细信息
                allHousesList.add(getHousesDetailsByElement(li));
            }
            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取搜房网第"+sfwUrlPageNumer+"页楼盘数据完成");


            // 获取所有搜房网楼盘列表
//            do {
//                pageDoc = Jsoup.connect("http://newhouse.jn.fang.com/house/s/b9"+sfwUrlPageNumer).get();
//                Elements lis = pageDoc.select("#newhouse_loupai_list li");
//
//                for (Element li : lis) {
//                    // 抓取详细信息
//                    allHousesList.add(getHousesDetailsByElement(li));
//                }
//
//                // 如果获取的td是空数据  就设置pageDoc为null
//                if (lis.size() == 0) {
//                    pageDoc = null;
//                }
//
//                LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取搜房网第"+sfwUrlPageNumer+"页楼盘数据完成");
//
//                sfwUrlPageNumer++;
//            } while (pageDoc != null);
        } catch (IOException e) {
            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "抓取搜房网第"+sfwUrlPageNumer+"页楼盘数据异常："+e);
            e.printStackTrace();
        }

        /**
         * 跑真实数据这里要放开
         */
        // 根据所有楼盘名称，从政府网获取所有地块的数据
//        FloorServiceImpl floorService = new FloorServiceImpl();
//        HashMap<String, Object> allData = new HashMap<String, Object>();
//
//        HashMap<String, Object> floorAndPlotsData = floorService.getFloorListByAllHouses(allHousesList);
//        allData.put("allHousesList", allHousesList);
//        allData.put("allFloorList", floorAndPlotsData.get("allFloorList"));
//        allData.put("allPlotsList", floorAndPlotsData.get("allPlotsList"));



        // 简单测试数据
        ArrayList<THouses> tempHousesList = new ArrayList<THouses>();

        for(int i = 0; i < 2; i++){
            tempHousesList.add(allHousesList.get(i));
        }

        // 根据所有楼盘名称，从政府网获取所有地块的数据
        FloorServiceImpl floorService = new FloorServiceImpl();
        HashMap<String, Object> allData = new HashMap<String, Object>();
        HashMap<String, Object> floorAndPlotsData = floorService.getFloorListByAllHouses(tempHousesList);

        allData.put("allHousesList", tempHousesList);
        allData.put("allFloorList", floorAndPlotsData.get("allFloorList"));
        allData.put("allPlotsList", floorAndPlotsData.get("allPlotsList"));


        System.out.println("00---------------------");
        System.out.println("楼盘个数"+tempHousesList.size());
        System.out.println("楼盘名称");
        for (THouses houses : tempHousesList) {
            System.out.println(houses.getHousesName());
            System.out.println(houses.getpRebName());
            System.out.println(houses.getAveragePrice());
        }
        System.out.println("11---------------------");
        System.out.println("地块个数"+((ArrayList<TFloor>)allData.get("allFloorList")).size());
        System.out.println("地块名称");
        for (TFloor floor : (ArrayList<TFloor>)allData.get("allFloorList")) {
            System.out.println(floor.getFloorName());
            System.out.println(floor.getTotalPlotsNumber());
            System.out.println(floor.getFdcUrl());
            System.out.println(floor.getpHousesName());
        }
        System.out.println("22---------------------");
        System.out.println("单元楼个数"+((ArrayList<TPlots>)allData.get("allPlotsList")).size());
        System.out.println("单元楼名称");
        for (TPlots plots : (ArrayList<TPlots>)allData.get("allPlotsList")) {
            System.out.println(plots.getPlotsName());
            System.out.println(plots.getMortgage());
            System.out.println(plots.getFdcUrl());
            System.out.println(plots.getpFloorName());
        }


        return allData;
    }

    /**
     * 根据抓取的数据获取每个楼盘的详情数据
     */
    @Override
    public THouses getHousesDetailsByElement(Element li) {

        THouses houses = new THouses();

        String sfwUrl = li.select(".nlc_details .nlcd_name a").attr("href");
        String name = li.select(".nlc_details .nlcd_name a").text();
        String cover = li.select(".nlc_details .nlc_img img").eq(1).attr("src");
        String address = li.select(".nlc_details .address a").text();
        String averagePrice = null;
        String openingDate = null;

        // 根据url继续下潜抓取详细信息
        Document detailedDoc = null;  // 承载抓取到的楼盘详细数据
        try {
            detailedDoc = Jsoup.connect(sfwUrl).timeout(5000).get();
            averagePrice = detailedDoc.select(".prib").text();
            openingDate = detailedDoc.select(".kaipan").text();

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取[楼盘]   ["+name+"]详细数据完成!");
        } catch (IOException e) {
            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "抓取[楼盘]   ["+name+"]详细数据异常："+e);
            e.printStackTrace();
        }

        houses.setHousesId(UUID.randomUUID());
        houses.setHousesName(name);
        houses.setFdcHousesName(AnalysisHouseUtil.extractValidHousesName(name));
        houses.setSfwUrl(sfwUrl);
        houses.setCover(cover);
        houses.setAddress(address);
        houses.setAveragePrice(averagePrice);
        houses.setOpeningDate(openingDate);

        return houses;
    }


}
