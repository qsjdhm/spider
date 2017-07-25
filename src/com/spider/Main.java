package com.spider;

import com.spider.entity.TReb;
import com.spider.service.impl.HousesServiceImpl;
import com.spider.service.impl.RebServiceImpl;

import java.io.IOException;
import java.util.HashMap;
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
        System.out.println();


        /**
         * 这里要把Object换成List！！！！！
         */
        Map<String, Object> allData = housesService.getAllHouses();
        Map<String, Object> allHousesList = (Map<String, Object>) allData.get("allHousesList");
        Map<String, Object> allFloorList = (Map<String, Object>) allData.get("allFloorList");
        Map<String, Object> allPlotsList = (Map<String, Object>) allData.get("allPlotsList");


    }
}

