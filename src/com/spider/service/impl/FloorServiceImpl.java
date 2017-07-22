package com.spider.service.impl;

import com.spider.model.TFloor;
import com.spider.model.THouses;
import com.spider.model.TPlots;
import com.spider.model.TReb;
import com.spider.service.IFloorService;
import com.spider.utils.LogFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangyan on 17/7/16.
 * 地块业务功能类
 */
public class FloorServiceImpl implements IFloorService {

    // 爬虫进度的日志目录路径
    private String spiderLogPath = "log" + File.separator + "spider_schedule" + File.separator;

    /**
     * 根据全部的楼盘名称从政府网获取它的地块列表
     */
    @Override
    public void getFloorListByAllHouses(List<THouses> housesList) {

        ArrayList<TFloor> allFloorList = new ArrayList<TFloor>();
        ArrayList<TPlots> allPlotsList = new ArrayList<TPlots>();

        for (THouses houses : housesList) {
            // 根据每个楼盘信息获取它的地块数据
            ArrayList<TFloor> housesFloorList = getFloorListByHouses(houses);

            // 再把每个楼盘的地块遍历出来放到全部地块数组中
            for (TFloor housesFloor : housesFloorList) {
                allFloorList.add(housesFloor);
            }
        }


        for (THouses houses : housesList) {
            System.out.println("---------");
            System.out.println(houses.getpRebName());
        }

        //return allFloorList;

    }

    /**
     * 根据楼盘从政府网获取它的地块列表
     */
    @Override
    public ArrayList<TFloor> getFloorListByHouses(THouses houses) {

        ArrayList<TFloor> floorList = new ArrayList<TFloor>();  // 承载地块数据集合
        int sfwUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        Document pageDoc = null;  // 承载抓取到的每页地块数据

        try {
            do {

                pageDoc = Jsoup.connect("http://www.jnfdc.gov.cn/onsaling/index_"+sfwUrlPageNumer+".shtml?zn=all&pu=all&pn="+houses.getFdcHousesName()+"&en=").get();
                Elements trs = pageDoc.select(".project_table tr");


                // 因为抓取到的数据不规范，所以要自己组织为规范的数据格式
                for (Element tr : trs) {
                    // 只获取有效数据的值
                    if (tr.select("td").size() > 1) {
                        // 抓取详细信息
                        floorList.add(getFloorDetailsByElement(tr, houses));
                    }
                }

                // 如果获取的td是空数据  就设置pageDoc为null
                if (trs.size() <= 2) {
                    pageDoc = null;
                }
                sfwUrlPageNumer++;
            } while (pageDoc != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return floorList;
    }

    /**
     * 根据抓取的地块数据获取单个地块详细数据，包括单元楼
     */
    @Override
    public TFloor getFloorDetailsByElement(Element tr, THouses houses) {

        TFloor floor = new TFloor();
        Elements tds = tr.select("td");

        String floorName = tds.eq(1).attr("title");  // 地块名称
        String fdcUrl = "http://www.jnfdc.gov.cn" + tds.eq(1).select("a").attr("href");  // 地块详情页面政府网URL
        String canSold = tds.eq(4).text();  // 可售套数
        String address = tds.eq(2).text();  // 项目地址
        String county = null;  // 所在区县
        String scale = null;  // 项目规模
        String totalPlotsNumber = null;  // 总栋数
        String property = null;  // 物业公司
        UUID pHousesId = houses.getHousesId();  // 所属楼盘ID
        String pHousesName = houses.getHousesName();  // 所属楼盘名称
        String pRebId = null;  // 所属房产商ID
        String pRebName = null;  // 所属房产商名称


        // 根据url继续下潜抓取详细信息
        Document detailedDoc = null;  // 承载抓取到的房产商详细数据
        try {
            detailedDoc = Jsoup.connect(fdcUrl).get();
            Elements trs = detailedDoc.select(".message_table tr");

            pRebName = trs.eq(2).select("td").eq(1).text();
            county = trs.eq(2).select("td").eq(3).text();
            scale = trs.eq(3).select("td").eq(1).text();
            totalPlotsNumber = trs.eq(3).select("td").eq(3).text();
            property = trs.eq(5).select("td").eq(1).text();

            houses.setpRebName(pRebName);
            LogFile.writerLogFile(spiderLogPath, "info", "抓取地块["+floorName+"]详细数据完成!");
        } catch (IOException e) {
            try {
                LogFile.writerLogFile(spiderLogPath, "error", "抓取地块详细数据异常："+e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        floor.setFloorId(UUID.randomUUID());
        floor.setFloorName(floorName);
        floor.setFdcUrl(fdcUrl);
        floor.setCanSold(canSold);
        floor.setAddress(address);
        floor.setCounty(county);
        floor.setScale(scale);
        floor.setTotalPlotsNumber(totalPlotsNumber);
        floor.setProperty(property);
        floor.setpHousesId(pHousesId);
        floor.setpHousesName(pHousesName);
        floor.setpRebName(pRebName);


        // 处理单元楼业务
        PlotsServiceImpl plotsService = new PlotsServiceImpl();
        plotsService.getPlotsListByFloor(floor);


        return floor;
    }
}
