package com.spider;

import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.THouses;
import com.spider.entity.houses.TPlots;
import com.spider.entity.houses.TReb;
import com.spider.service.impl.houses.HousesServiceImpl;
import com.spider.service.impl.houses.RebServiceImpl;
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


        /**
         * 测试获取单页、单个的功能
         */
        // 楼盘
        HousesServiceImpl housesService = new HousesServiceImpl();
        Map<String, List> allData = housesService.getHousesListByUrl("http://newhouse.jn.fang.com/house/dianshang/b92", false);
        List<THouses> allHousesList = allData.get("allHousesList");
        List<Map<String, String>> allErrorList = allData.get("allErrorList");


        System.out.println("---------------------抓取的异常数据列表---------------------");
        System.out.println("异常个数"+allErrorList.size());
        // 抓取过程中的错误信息，为了再次更新错误楼盘
        for(Map<String, String> errorItem : allErrorList) {
            for(String k : errorItem.keySet()) {
                System.out.println(k+":"+errorItem.get(k));
            }
        }
        System.out.println("---------------------楼盘数据---------------------");
        System.out.println("楼盘个数"+allHousesList.size());
        System.out.println("楼盘名称");
        for (THouses houses : allHousesList) {
            System.out.println(houses.getHousesName());
            System.out.println(houses.getpRebName());
            System.out.println(houses.getAveragePrice());
        }









    }
}

