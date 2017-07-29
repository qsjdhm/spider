package com.spider;

import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.THouses;
import com.spider.entity.houses.TPlots;
import com.spider.entity.houses.TReb;
import com.spider.service.impl.houses.FloorServiceImpl;
import com.spider.service.impl.houses.HousesServiceImpl;
import com.spider.service.impl.houses.PlotsServiceImpl;
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


        RebServiceImpl rebService = new RebServiceImpl();
        TReb reb = rebService.getRebDetailsByUrl("http://www.jnfdc.gov.cn/kfqy/show/f96724fb-d1cd-447c-9906-bf56e579a71e.shtml");

        System.out.println(reb.getRebName());
        System.out.println(reb.getRegisteredCapital());

    }
}

