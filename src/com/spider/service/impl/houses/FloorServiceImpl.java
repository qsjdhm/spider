package com.spider.service.impl.houses;

import com.spider.config.Constant;
import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.THouses;
import com.spider.entity.houses.TPlots;
import com.spider.service.houses.IFloorService;
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
 * 地块业务功能类
 *
 * 主要对外接口描述
 * 1. 可根据楼盘列表获取全部数据     getAllListByAllHouses
 * 2. 可根据单个楼盘获取它的全部数据  getAllListByHouses
 * 3. 可获取单页列表数据            getPageListByUrl       （可单独调用）
 * 4. 可获取单个详情数据            getDetailsByUrl        （可单独调用）
 */
public class FloorServiceImpl implements IFloorService {

    /**
     * 根据全部楼盘列表获取它们的全部地块列表数据和下潜的数据（比如：它们的单元楼）
     * @param housesList 楼盘列表对象
     */
    @Override
    public Map<String, List> getAllListByAllHouses(List<THouses> housesList) {

        List<TFloor> allFloorList = new ArrayList<TFloor>();

        // 组织所有地块数据
        for (THouses houses : housesList) {
            // 根据每个楼盘信息获取它的地块数据
            List<TFloor> housesFloorList = getAllListByHouses(houses);

            // 再把每个楼盘的地块遍历出来放到全部地块数组中
            for (TFloor housesFloor : housesFloorList) {
                allFloorList.add(housesFloor);
            }
        }

        // 组织所有单元楼数据
        PlotsServiceImpl plotsService = new PlotsServiceImpl();
        List<TPlots> allPlotsList = plotsService.getAllListByAllFloor(allFloorList).get("allPlotsList");

        // 组织下数据返回格式
        Map<String, List> returnValue = new HashMap<String, List>();
        // 将获取的所有地块数据放入map中
        returnValue.put("allFloorList", allFloorList);
        // 将获取的所有单元楼数据放入map中
        returnValue.put("allPlotsList", allPlotsList);

        return returnValue;
    }

    /**
     * 根据楼盘从政府网抓取此楼盘的地块列表数据，并调用获取地块详情方法
     * @param houses 某个楼盘对象
     */
    @Override
    public List<TFloor> getAllListByHouses(THouses houses) {

        List<TFloor> allFloorList = new ArrayList<TFloor>();  // 承载此楼盘所有地块数据列表
        String housesName = houses.getFdcHousesName();

        int fdcUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        boolean isContinue = true;  // 是否继续循环

        do {
            String fdcUrl = "http://www.jnfdc.gov.cn/onsaling/index_"+fdcUrlPageNumer+".shtml?zn=all&pu=all&pn="+housesName+"&en=";
            Map<String, List> allData = getPageListByUrl(fdcUrl, housesName, false);
            List<TFloor> pageFloorList = allData.get("allFloorList");

            for (TFloor floor : pageFloorList) {
                allFloorList.add(floor);
            }

            if (pageFloorList.size() == 0) {
                isContinue = false;
            }

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取政府网楼盘["+housesName+"]的>>>>>>全部地块第"+fdcUrlPageNumer+"页数据完成");

            fdcUrlPageNumer++;
        } while (isContinue);

        return allFloorList;
    }

    /**
     * 根据某一页地块列表页面的url获取获取这一页的地块数据
     * @param url 某一页地块列表页面的url
     * @param housesName 所属楼盘名称（解析后在政府网查询使用）
     * @param isInfiltrate 是否继续获取下潜数据（比如：它们的单元楼）
     */
    @Override
    public Map<String, List> getPageListByUrl(String url, String housesName, boolean isInfiltrate) {

        Map<String, List> allData = new HashMap<String, List>();  // 承载此接口返回的所有数据
        List<TFloor> allFloorList = new ArrayList<TFloor>();  // 承载地块数据集合

        try {
            Document pageDoc = Jsoup.connect(url).timeout(5000).get();
            Elements trs = pageDoc.select(".project_table tr");

            // 因为抓取到的数据不规范，所以要自己组织为规范的数据格式
            for (Element tr : trs) {
                // 只获取有效数据的值
                if (tr.select("td").size() > 1) {
                    // 抓取详细信息
                    allFloorList.add(getDetailsByElement(tr, housesName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 根据地块列表获取他们的单元楼数据列表
        if (isInfiltrate) {
            PlotsServiceImpl plotsService = new PlotsServiceImpl();
            List<TPlots> allPlotsList = plotsService.getAllListByAllFloor(allFloorList).get("allPlotsList");

            allData.put("allPlotsList", allPlotsList);
        }

        allData.put("allFloorList", allFloorList);
        //allData.put("allErrorList", SpiderErrorServiceImpl.getErrorList(true));

        return allData;
    }

    /**
     * 根据从政府网抓取的每一条地块数据下潜获取地块的详情数据
     * @param tr 抓取的每一条地块DOM数据
     * @param housesName 所属楼盘名称（解析后在政府网查询使用）
     */
    @Override
    public TFloor getDetailsByElement(Element tr, String housesName) {

        Elements tds = tr.select("td");
        String floorName = tds.eq(1).attr("title");  // 地块名称
        String fdcUrl = "http://www.jnfdc.gov.cn" + tds.eq(1).select("a").attr("href");  // 地块详情页面政府网URL
        String canSold = tds.eq(4).text();  // 可售套数
        String address = tds.eq(2).text();  // 项目地址
        String pRebName = tds.eq(3).text();  // 所属房产商名称
        String pHousesName = housesName;  // 所属楼盘名称

        TFloor floor = getDetailsByUrl(fdcUrl, housesName);

        floor.setFloorName(floorName);
        floor.setFdcUrl(fdcUrl);
        floor.setCanSold(canSold);
        floor.setAddress(address);
        floor.setpHousesName(pHousesName);
        floor.setpRebName(pRebName);

        return floor;
    }

    /**
     * 根据某个地块详细页面的url获取这一个地块的详细数据
     * @param url 某个地块详细页面的url
     * @param housesName 所属楼盘名称（解析后在政府网查询使用）
     */
    @Override
    public TFloor getDetailsByUrl(String url, String housesName) {

        TFloor floor = new TFloor();

        String floorName = null;  // 地块名称
        String fdcUrl = url;  // 地块详情页面政府网URL
        String canSold = null;  // 可售套数
        String address = null;  // 项目地址
        String county = null;  // 所在区县
        String scale = null;  // 项目规模
        String totalPlotsNumber = null;  // 总栋数
        String property = null;  // 物业公司
        UUID pHousesId = null;  // 所属楼盘ID
        String pHousesName = housesName;  // 所属楼盘名称
        String pRebId = null;  // 所属房产商ID
        String pRebName = null;  // 所属房产商名称

        // 根据url继续下潜抓取详细信息
        Document detailedDoc = null;  // 承载抓取到的房产商详细数据
        try {
            detailedDoc = Jsoup.connect(url).timeout(5000).get();
            Elements trs = detailedDoc.select(".message_table tr");

            floorName = trs.eq(1).select("td").eq(1).text();
            address = trs.eq(1).select("td").eq(3).text();
            pRebName = trs.eq(2).select("td").eq(1).text();
            county = trs.eq(2).select("td").eq(3).text();
            scale = trs.eq(3).select("td").eq(1).text();
            totalPlotsNumber = trs.eq(3).select("td").eq(3).text();
            property = trs.eq(5).select("td").eq(1).text();

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取[地块]   楼盘["+pHousesName+"]>>>>>>地块["+floorName+"]详细数据完成!");
        } catch (IOException e) {
            // 组织错误信息，供返回使用
            SpiderErrorServiceImpl.addError(
                    "详情",
                    "地块",
                    "地块["+floorName+"]",
                    fdcUrl,
                    e.toString(),
                    "",
                    housesName,
                    ""
            );

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "抓取[地块]   楼盘["+pHousesName+"]>>>>>>地块["+floorName+"]详细数据异常："+e);
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

        return floor;
    }
}
