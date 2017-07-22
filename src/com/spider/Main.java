package com.spider;

import com.spider.model.TReb;
import com.spider.service.impl.HousesServiceImpl;
import com.spider.service.impl.RebServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 */
public class Main {
    public static void main(String[] args) throws IOException {


        //Document doc = Jsoup.connect("http://newhouse.jn.fang.com/top/?ctm=1.jn.xf_search.head.142").get();

//        Spider spider = new Spider();
//        String html = spider.getHtml("http://newhouse.jn.fang.com/top/?ctm=1.jn.xf_search.head.142","GET","gb2312");
        //System.out.println(doc.body().getElementById("Toprs").select(".tpic"));
        //System.out.println(doc);

//        HousesServiceImpl housesService = new HousesServiceImpl();
//        System.out.print(housesService.getTop5Houses());

//        RebServiceImpl rebService = new RebServiceImpl();
//        List<TReb> allReb = rebService.getAllReb();
//
//        System.out.print(allReb);

        HousesServiceImpl housesService = new HousesServiceImpl();
        System.out.print(housesService.getAllHouses());

    }
}

