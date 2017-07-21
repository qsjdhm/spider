package com.spider.service.impl;

import com.spider.model.TFloor;
import com.spider.model.THouses;
import com.spider.model.TPlots;
import com.spider.service.IHousesService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务功能类
 */
public class HousesServiceImpl implements IHousesService {

    /**
     * 从搜房网获取全部楼盘数据
     */
    @Override
    public List<THouses> getAllHouses() {

        int sfwUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        Document pageDoc = null;  // 承载抓取到的每页房产商数据

        try {

//            pageDoc = Jsoup.connect("http://newhouse.jn.fang.com/house/s/b9"+sfwUrlPageNumer).get();
//            Elements lis = pageDoc.select("#newhouse_loupai_list li");
//
//            for (Element li : lis) {
//                String href = li.select(".nlcd_name a").attr("href");
//                String name = li.select(".nlcd_name a").text();
//                String cover = li.select(".nlc_img img").eq(1).attr("src");
//            }

            do {
                pageDoc = Jsoup.connect("http://newhouse.jn.fang.com/house/s/b9"+sfwUrlPageNumer).get();
                Elements lis = pageDoc.select("#newhouse_loupai_list li");

                for (Element li : lis) {
                    String href = li.select(".nlcd_name a").attr("href");
                    String name = li.select(".nlcd_name a").text();
                    String cover = li.select(".nlc_img img").eq(1).attr("src");


                }

                // 如果获取的td是空数据  就设置pageDoc为null
                if (lis.size() == 0) {
                    pageDoc = null;
                }
                sfwUrlPageNumer++;
            } while (pageDoc != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------");
        System.out.println(sfwUrlPageNumer);

        return null;
    }

    /**
     * 根据楼盘名称从政府网获取它的地块列表
     */
    @Override
    public List<TFloor> getFloorListByHousesName() {
        return null;
    }


    /**
     * 根据地块url获取单个地块详细数据，包括单元楼
     */
    @Override
    public TFloor getFloorDetailsByFloorUrl() {
        return null;
    }

    /**
     * 根据地块url获取它的单元楼列表
     */
    @Override
    public List<TPlots> getPlotsListByFloorUrl() {
        return null;
    }

    /**
     * 根据单元楼url获取单个单元楼详细数据
     */
    @Override
    public List<TPlots> getPlotsDetailsByPlotsUrl() {
        return null;
    }


}
