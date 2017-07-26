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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangyan on 17/7/16.
 * 地块业务功能类
 */
public class FloorServiceImpl implements IFloorService {

    /**
     * 根据搜房网全部楼盘列表遍历每个楼盘数据，并且根据每个楼盘数据下潜获取此楼盘的地块列表数据（包括地块详情）
     * 在获得地块列表数据后，再根据地块列表获取单元楼列表数据（包括单元楼详情）
     */
    @Override
    public HashMap<String, ArrayList> getFloorListByAllHouses(List<THouses> housesList) {

        ArrayList<TFloor> allFloorList = new ArrayList<TFloor>();

        // 组织所有地块数据
        for (THouses houses : housesList) {
            // 根据每个楼盘信息获取它的地块数据
            ArrayList<TFloor> housesFloorList = getFloorListByHouses(houses);

            // 再把每个楼盘的地块遍历出来放到全部地块数组中
            for (TFloor housesFloor : housesFloorList) {
                allFloorList.add(housesFloor);
            }
        }


        // 组织所有单元楼数据
        PlotsServiceImpl plotsService = new PlotsServiceImpl();
        ArrayList<TPlots> allPlotsList = plotsService.getPlotsListByAllFloor(allFloorList).get("allPlotsList");

        // 组织下数据返回格式
        HashMap<String, ArrayList> returnValue = new HashMap<String, ArrayList>();
        // 将获取的所有地块数据放入map中
        returnValue.put("allFloorList", allFloorList);
        // 将获取的所有单元楼数据放入map中
        returnValue.put("allPlotsList", allPlotsList);

        return returnValue;

    }

    /**
     * 根据楼盘从政府网抓取此楼盘的地块列表数据，并调用获取地块详情方法
     */
    @Override
    public ArrayList<TFloor> getFloorListByHouses(THouses houses) {

        ArrayList<TFloor> floorList = new ArrayList<TFloor>();  // 承载地块数据集合
        int fdcUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        Document pageDoc = null;  // 承载抓取到的每页地块数据

        String url = "";

        try {
            do {
                url = "http://www.jnfdc.gov.cn/onsaling/index_"+fdcUrlPageNumer+".shtml?zn=all&pu=all&pn="+houses.getFdcHousesName()+"&en=";
                pageDoc = Jsoup.connect(url).timeout(5000).get();
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

                LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取政府网楼盘["+houses.getHousesName()+"]的>>>>>>全部地块第"+fdcUrlPageNumer+"页数据完成");

                fdcUrlPageNumer++;
            } while (pageDoc != null);
        } catch (IOException e) {

            // 组织错误信息，供返回使用
            SpiderErrorServiceImpl.addError("分页地块", "第"+fdcUrlPageNumer+"页", "http://www.jnfdc.gov.cn/onsaling/index_"+fdcUrlPageNumer+".shtml?zn=all&pu=all&pn="+houses.getFdcHousesName()+"&en=", "抓取[地块]   楼盘["+houses.getFdcHousesName()+"]>>>>>>地块第"+fdcUrlPageNumer+"页数据异常："+e);

            e.printStackTrace();
        }

        return floorList;
    }

    /**
     * 根据从政府网抓取的每一条地块数据下潜获取地块的详情数据
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
            detailedDoc = Jsoup.connect(fdcUrl).timeout(5000).get();
            Elements trs = detailedDoc.select(".message_table tr");

            pRebName = trs.eq(2).select("td").eq(1).text();
            county = trs.eq(2).select("td").eq(3).text();
            scale = trs.eq(3).select("td").eq(1).text();
            totalPlotsNumber = trs.eq(3).select("td").eq(3).text();
            property = trs.eq(5).select("td").eq(1).text();

            houses.setpRebName(pRebName);
            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取[地块]   楼盘["+pHousesName+"]>>>>>>地块["+floorName+"]详细数据完成!");
        } catch (IOException e) {
            // 组织错误信息，供返回使用
            SpiderErrorServiceImpl.addError("地块", floorName, fdcUrl, "抓取[地块]   楼盘["+pHousesName+"]>>>>>>地块["+floorName+"]详细数据异常："+e);

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
