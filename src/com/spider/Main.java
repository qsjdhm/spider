package com.spider;

import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.THouses;
import com.spider.entity.houses.TPlots;
import com.spider.service.impl.houses.HousesServiceImpl;
import com.spider.service.impl.system.SpiderErrorServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyan on 17/7/16.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        // 获取房产商全部数据
//        RebServiceImpl rebService = new RebServiceImpl();
//        List<TReb> allReb = rebService.getAllReb();

        // 获取楼盘全部数据，包括地块、单元楼
        HousesServiceImpl housesService = new HousesServiceImpl();

        Map<String, ArrayList> allData = housesService.getAllHouses();
        ArrayList<THouses> allHousesList = allData.get("allHousesList");
        ArrayList<TFloor> allFloorList = allData.get("allFloorList");
        ArrayList<TPlots> allPlotsList = allData.get("allPlotsList");


        // 抓取过程中的错误信息，为了再次更新错误楼盘
        List<Map<String, String>> errors = SpiderErrorServiceImpl.getErrorList(true);
        for(Map<String, String> errorItem : errors) {
            for(String k : errorItem.keySet()) {
                System.out.println(k+":"+errorItem.get(k));
                System.out.println("=======");
            }
        }


        System.out.println("00---------------------");
        System.out.println("楼盘个数"+allHousesList.size());
        System.out.println("楼盘名称");
        for (THouses houses : allHousesList) {
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


    }
}

