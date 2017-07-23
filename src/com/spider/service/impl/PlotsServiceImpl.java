package com.spider.service.impl;

import com.spider.config.Constant;
import com.spider.model.TFloor;
import com.spider.model.TPlots;
import com.spider.service.IPlotsService;
import com.spider.utils.LogFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by zhangyan on 17/7/16.
 * 单元楼业务功能类
 */
public class PlotsServiceImpl implements IPlotsService {

    /**
     * 根据全部的地块从政府网获取它们的单元楼列表
     */
    @Override
    public HashMap<String, Object> getPlotsListByAllFloor(List<TFloor> floorList) {

        ArrayList<TPlots> allPlotsList = new ArrayList<TPlots>();

        for (TFloor floor : floorList) {
            // 根据每个地块信息获取它的单元楼数据
            ArrayList<TPlots> floorPlotsList = getPlotsListByFloor(floor);

            // 再把每个地块的单元楼遍历出来放到全部单元楼数组中
            for (TPlots floorPlots : floorPlotsList) {
                allPlotsList.add(floorPlots);
            }
        }

        // 组织下数据返回格式
        HashMap<String, Object> returnValue = new HashMap<String, Object>();
        returnValue.put("allPlotsList", allPlotsList);

        return returnValue;
    }


    /**
     * 根据地块详情获取它的单元楼列表
     */
    @Override
    public ArrayList<TPlots> getPlotsListByFloor(TFloor floor) {

        ArrayList<TPlots> plotsList = new ArrayList<TPlots>();  // 承载单元楼数据集合
        int fdcUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        Document pageDoc = null;  // 承载抓取到的每页单元楼数据

        try {
            do {
                String url = floor.getFdcUrl().replace("show", "show_"+fdcUrlPageNumer);
                pageDoc = Jsoup.connect(url).timeout(5000).get();
                Elements trs = pageDoc.select(".project_table tr");

                // 因为抓取到的数据不规范，所以要自己组织为规范的数据格式
                for (Element tr : trs) {
                    // 只获取有效数据的值
                    if (tr.select("td").size() > 1) {
                        // 抓取详细信息
                        plotsList.add(getPlotsDetailsByElement(tr, floor));
                    }
                }

                // 如果获取的td是空数据  就设置pageDoc为null
                if (trs.size() <= 2) {
                    pageDoc = null;
                }

                LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取政府网地块["+floor.getFloorName()+"]的>>>>>>全部单元楼第"+fdcUrlPageNumer+"页数据完成");

                fdcUrlPageNumer++;
            } while (pageDoc != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return plotsList;
    }

    /**
     * 根据抓取的单元楼详细数据获取单个单元楼详细数据
     */
    @Override
    public TPlots getPlotsDetailsByElement(Element tr, TFloor floor) {

        TPlots plots = new TPlots();
        Elements tds = tr.select("td");

        UUID plotsId = UUID.randomUUID();  // 单元楼ID
        String plotsName = tds.eq(1).attr("title");  // 单元楼名称
        String fdcUrl = "http://www.jnfdc.gov.cn/onsaling/" + tds.eq(1).select("a").attr("href");  // 单元楼页面政府网URL
        String area = null;  // 建筑面积
        String decoration = null;  // 装修标准
        String use = null;  // 规划用途
        String mortgage = null;  // 有无抵押
        String salePermit = null;  // 商品房预售许可证
        String landUseCertificate = null;  // 国有土地使用证
        String planningPermit = null;  // 建设工程规划许可证
        String constructionPermit = null;  // 建设工程施工许可证
        UUID pFloorId = floor.getFloorId();  // 所属地块ID
        String pFloorName = floor.getFloorName();  // 所属地块名称
        UUID pHousesId = floor.getpHousesId();  // 所属楼盘ID
        String pHousesName = floor.getpHousesName();  // 所属楼盘名称
        String pRebId = null;  // 所属房产商ID
        String pRebName = floor.getpRebName();  // 所属房产商名称

        // 根据url继续下潜抓取详细信息
        Document detailedDoc = null;  // 承载抓取到的房产商详细数据
        try {
            detailedDoc = Jsoup.connect(fdcUrl).timeout(5000).get();
            Elements trs = detailedDoc.select(".message_table tr");

            area = trs.eq(4).select("td").eq(3).text()+"(万m²)";  // 建筑面积
            decoration = trs.eq(5).select("td").eq(3).text();  // 装修标准
            use = trs.eq(6).select("td").eq(1).text();  // 规划用途
            mortgage = trs.eq(6).select("td").eq(3).text();  // 有无抵押
            salePermit = trs.eq(7).select("td").eq(1).text();  // 商品房预售许可证
            landUseCertificate = trs.eq(7).select("td").eq(3).text();  // 国有土地使用证
            planningPermit = trs.eq(8).select("td").eq(1).text();  // 建设工程规划许可证
            constructionPermit = trs.eq(8).select("td").eq(3).text();  // 建设工程施工许可证

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取[单元楼]   楼盘["+pHousesName+"]>>>>>>地块["+pFloorName+"]>>>>>>单元楼["+plotsName+"]详细数据完成!");
        } catch (IOException e) {
            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "抓取[单元楼]   楼盘["+pHousesName+"]>>>>>>地块["+pFloorName+"]>>>>>>单元楼["+plotsName+"]详细数据异常："+e);
            e.printStackTrace();
        }

        plots.setPlotsId(plotsId);
        plots.setPlotsName(plotsName);
        plots.setFdcUrl(fdcUrl);
        plots.setArea(area);
        plots.setDecoration(decoration);
        plots.setUse(use);
        plots.setMortgage(mortgage);
        plots.setSalePermit(salePermit);
        plots.setLandUseCertificate(landUseCertificate);
        plots.setPlanningPermit(planningPermit);
        plots.setConstructionPermit(constructionPermit);
        plots.setpFloorId(pFloorId);
        plots.setpFloorName(pFloorName);
        plots.setpHousesId(pHousesId);
        plots.setpHousesName(pHousesName);
        plots.setpRebId(pRebId);
        plots.setpRebName(pRebName);

        return plots;
    }
}
