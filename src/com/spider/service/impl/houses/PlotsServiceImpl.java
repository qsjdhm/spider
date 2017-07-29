package com.spider.service.impl.houses;

import com.spider.config.Constant;
import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.TPlots;
import com.spider.service.houses.IPlotsService;
import com.spider.service.impl.system.SpiderErrorServiceImpl;
import com.spider.utils.LogFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by zhangyan on 17/7/16.
 * 单元楼业务功能类
 *
 * 主要对外接口描述
 * 1. 可根据地块列表获取全部数据     getAllListByAllFloor
 * 2. 可根据单个地块获取它的全部数据  getAllListByFloor
 * 3. 可获取单页列表数据            getPageListByUrl       （可单独调用）
 * 4. 可获取单个详情数据            getDetailsByUrl        （可单独调用）
 */
public class PlotsServiceImpl implements IPlotsService {

    /**
     * 根据全部地块列表获取它们的全部单元楼列表数据
     * @param floorList 地块列表对象
     */
    @Override
    public Map<String, List> getAllListByAllFloor(List<TFloor> floorList) {

        List<TPlots> allPlotsList = new ArrayList<TPlots>();

        for (TFloor floor : floorList) {
            // 根据每个地块信息获取它的单元楼数据
            ArrayList<TPlots> floorPlotsList = getAllListByFloor(floor);

            // 再把每个地块的单元楼遍历出来放到全部单元楼数组中
            for (TPlots floorPlots : floorPlotsList) {
                allPlotsList.add(floorPlots);
            }
        }


        // 组织下数据返回格式
        Map<String, List> returnValue = new HashMap<String, List>();
        returnValue.put("allPlotsList", allPlotsList);

        return returnValue;
    }


    /**
     * 根据地块从政府网抓取此地块的单元楼列表数据，并调用获取单元楼详情方法
     * @param floor 某个地块对象
     */
    @Override
    public ArrayList<TPlots> getAllListByFloor(TFloor floor) {

        ArrayList<TPlots> allPlotsList = new ArrayList<TPlots>();  // 承载单元楼数据集合
        String floorName = floor.getFloorName();

        int fdcUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        boolean isContinue = true;  // 是否继续循环

        do {
            String fdcUrl = floor.getFdcUrl().replace("show", "show_"+fdcUrlPageNumer);

            Map<String, List> allData = getPageListByUrl(fdcUrl, floorName, false);
            List<TPlots> pagePlotsList = allData.get("allPlotsList");

            for (TPlots plots : pagePlotsList) {
                allPlotsList.add(plots);
            }

            if (pagePlotsList.size() == 0) {
                isContinue = false;
            }

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取政府网地块["+floor.getFloorName()+"]的>>>>>>全部单元楼第"+fdcUrlPageNumer+"页数据完成");

            fdcUrlPageNumer++;
        } while (isContinue);

        return allPlotsList;
    }

    /**
     * 根据某一页单元楼列表页面的url获取获取这一页的单元楼数据
     * @param url 某一页单元楼列表页面的url
     * @param floorName 所属地块名称
     * @param isInfiltrate 是否继续获取下潜数据（比如：每一户数据）
     */
    @Override
    public Map<String, List> getPageListByUrl(String url, String floorName, boolean isInfiltrate) {

        Map<String, List> allData = new HashMap<String, List>();  // 承载此接口返回的所有数据
        List<TPlots> allPlotsList = new ArrayList<TPlots>();  // 承载单元楼数据集合

        try {
            Document pageDoc = Jsoup.connect(url).timeout(5000).get();
            Elements trs = pageDoc.select(".project_table tr");

            for (Element tr : trs) {
                if (tr.select("td").size() > 1) {
                    allPlotsList.add(getDetailsByElement(tr, floorName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        allData.put("allPlotsList", allPlotsList);
        //allData.put("allErrorList", SpiderErrorServiceImpl.getErrorList(true));

        return allData;
    }

    /**
     * 根据从政府网抓取的每一条单元楼数据下潜获取单元楼的详情数据
     * @param tr 抓取的每一条单元楼DOM数据
     * @param floorName 所属地块名称
     */
    @Override
    public TPlots getDetailsByElement(Element tr, String floorName) {

        Elements tds = tr.select("td");
        String plotsName = tds.eq(1).attr("title");  // 单元楼名称
        String fdcUrl = "http://www.jnfdc.gov.cn/onsaling/" + tds.eq(1).select("a").attr("href");  // 单元楼页面政府网URL
        String salePermit = tds.eq(2).attr("title");  // 商品房预售许可证
        String pFloorName = floorName;  // 所属地块名称

        TPlots plots = getDetailsByUrl(fdcUrl, floorName);

        plots.setPlotsName(plotsName);
        plots.setFdcUrl(fdcUrl);
        plots.setSalePermit(salePermit);
        plots.setpFloorName(pFloorName);

        return plots;
    }

    /**
     * 根据某个单元楼详细页面的url获取这一个单元楼的详细数据
     * @param url 某个地块详细页面的url
     * @param floorName 所属地块名称
     */
    @Override
    public TPlots getDetailsByUrl(String url, String floorName) {

        TPlots plots = new TPlots();

        UUID plotsId = UUID.randomUUID();  // 单元楼ID
        String plotsName = null;  // 单元楼名称
        String fdcUrl = url;  // 单元楼页面政府网URL
        String area = null;  // 建筑面积
        String decoration = null;  // 装修标准
        String use = null;  // 规划用途
        String mortgage = null;  // 有无抵押
        String salePermit = null;  // 商品房预售许可证
        String landUseCertificate = null;  // 国有土地使用证
        String planningPermit = null;  // 建设工程规划许可证
        String constructionPermit = null;  // 建设工程施工许可证
        UUID pFloorId = null;  // 所属地块ID
        String pFloorName = floorName;  // 所属地块名称
        UUID pHousesId = null;  // 所属楼盘ID
        String pHousesName = null;  // 所属楼盘名称
        String pRebId = null;  // 所属房产商ID
        String pRebName = null;  // 所属房产商名称

        // 根据url继续下潜抓取详细信息
        Document detailedDoc = null;  // 承载抓取到的房产商详细数据
        try {
            detailedDoc = Jsoup.connect(fdcUrl).timeout(5000).get();
            Elements trs = detailedDoc.select(".message_table tr");

            plotsName = trs.eq(1).select("td").eq(1).text();  // 单元楼名称
            pRebName = trs.eq(2).select("td").eq(1).text();  // 所属房产商名称
            area = trs.eq(4).select("td").eq(3).text()+"(万m²)";  // 建筑面积
            decoration = trs.eq(5).select("td").eq(3).text();  // 装修标准
            use = trs.eq(6).select("td").eq(1).text();  // 规划用途
            mortgage = trs.eq(6).select("td").eq(3).text();  // 有无抵押
            salePermit = trs.eq(7).select("td").eq(1).text();  // 商品房预售许可证
            landUseCertificate = trs.eq(7).select("td").eq(3).text();  // 国有土地使用证
            planningPermit = trs.eq(8).select("td").eq(1).text();  // 建设工程规划许可证
            constructionPermit = trs.eq(8).select("td").eq(3).text();  // 建设工程施工许可证

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取[单元楼]   地块["+pFloorName+"]>>>>>>单元楼["+plotsName+"]详细数据完成!");
        } catch (IOException e) {

            // 组织错误信息，供返回使用
            SpiderErrorServiceImpl.addError(
                    "详情",
                    "单元楼",
                    "单元楼["+plotsName+"]",
                    fdcUrl,
                    e.toString(),
                    pRebName,
                    "",
                    floorName
            );

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "抓取[单元楼]   地块["+pFloorName+"]>>>>>>单元楼["+plotsName+"]详细数据异常："+e);
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
