package com.spider.service.impl.houses;

import com.spider.config.Constant;
import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.THouses;
import com.spider.entity.houses.TPlots;
import com.spider.service.houses.IHousesService;
import com.spider.service.impl.system.SpiderErrorServiceImpl;
import com.spider.utils.AnalysisHouseUtil;
import com.spider.utils.LogFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务功能类
 */
public class HousesServiceImpl implements IHousesService {


    /**
     * 从搜房网获取全部楼盘数据,包含全部的楼盘、全部的地块、全部的单元楼数据
     */
    @Override
    public Map<String, List> getAllHouses() {

        Map<String, List> allData = new HashMap<String, List>();  // 承载此接口返回的所有数据
        List<THouses> allHousesList = new ArrayList<THouses>();  // 承载所有楼盘数据集合
        List<TFloor> allFloorList = new ArrayList<TFloor>();  // 承载所有地块数据集合
        List<TPlots> allPlotsList = new ArrayList<TPlots>();  // 承载所有单元楼数据集合
        ArrayList<HashMap<String, String>> allErrorList = new ArrayList<HashMap<String, String>>();  // 承载所有抓取错误数据集合
        int sfwUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        boolean isContinue = true;  // 是否继续循环


        // 获取所有搜房网楼盘列表
        do {
            /**
             * 跑全部数据这里要放开
             */
            //String sfwUrl = "http://newhouse.jn.fang.com/house/s/b9"+sfwUrlPageNumer;
            /**
             * 跑简要数据这里要放开
             */
            String sfwUrl = "http://newhouse.jn.fang.com/house/dianshang/b9"+sfwUrlPageNumer;

            Map<String, List> pageAllData = getHousesListByUrl(sfwUrl, true);

            List<THouses> pageHousesList = pageAllData.get("allHousesList");
            List<TFloor> pageFloorList = pageAllData.get("allFloorList");
            List<TPlots> pagePlotsList = pageAllData.get("allPlotsList");
            ArrayList<HashMap<String, String>> pageErrorList = (ArrayList<HashMap<String, String>>) pageAllData.get("allErrorList");

            // 得到每页的楼盘数据放到allHousesList中方便接下来获取下潜数据
            for (THouses houses : pageHousesList) {
                allHousesList.add(houses);
            }

            // 得到每页的楼盘数据放到allHousesList中方便接下来获取下潜数据
            for (TFloor floor : pageFloorList) {
                allFloorList.add(floor);
            }

            // 得到每页的楼盘数据放到allHousesList中方便接下来获取下潜数据
            for (TPlots plots : pagePlotsList) {
                allPlotsList.add(plots);
            }

            // 得到每页的楼盘数据放到allHousesList中方便接下来获取下潜数据
            for (HashMap<String, String> error : pageErrorList) {
                allErrorList.add(error);
            }

            // 如果获取的td是空数据  就设置pageDoc为null
            if (pageHousesList.size() == 0) {
                isContinue = false;
            }

            sfwUrlPageNumer++;
        } while (isContinue);

        // 根据楼盘列表获取它们的地块、单元楼数据列表
        //Map<String, List> floorAndPlotsData = getFloorAndPlotsListByHousesList(allHousesList);
        allData.put("allHousesList", allHousesList);
        allData.put("allFloorList", allFloorList);
        allData.put("allPlotsList", allPlotsList);
        allData.put("allErrorList", allErrorList);

        return allData;
    }


    /**
     * 根据某一页楼盘的url获取获取这一页的楼盘数据和下潜的数据
     */
    @Override
    public Map<String, List> getHousesListByUrl(String url, boolean isAll) {

        Map<String, List> allData = new HashMap<String, List>();  // 承载此接口返回的所有数据
        List<THouses> allHousesList = new ArrayList<THouses>();  // 承载所有楼盘数据集合
        Document pageDoc = null;  // 承载抓取到的每页房产商数据

        // 获取这一页搜房网楼盘列表
        try {
            pageDoc = Jsoup.connect(url).timeout(5000).get();
            Elements lis = pageDoc.select("#newhouse_loupai_list li");

            for (Element li : lis) {
                // 抓取详细信息
                allHousesList.add(getHousesDetailsByElement(li));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        allData.put("allHousesList", allHousesList);
        allData.put("allErrorList", SpiderErrorServiceImpl.getErrorList(true));

        if (isAll) {
            // 根据楼盘列表获取它们的地块、单元楼数据列表
            Map<String, List> floorAndPlotsData = getFloorAndPlotsListByHousesList(allHousesList);
            allData.put("allFloorList", floorAndPlotsData.get("allFloorList"));
            allData.put("allPlotsList", floorAndPlotsData.get("allPlotsList"));
        }

        return allData;
    }


    /**
     * 根据楼盘列表获取它们的地块、单元楼数据列表
     */
    @Override
    public Map<String, List> getFloorAndPlotsListByHousesList(List<THouses> housesList) {

        /**
         * 跑简要数据这里要放开
         */
        //List<THouses> tempHousesList = housesList;
        List<THouses> tempHousesList = new ArrayList<THouses>();
        if (housesList.size() > 0) {
            for(int i = 0; i < 4; i++){
                tempHousesList.add(housesList.get(i));
            }
        }

        Map<String, List> allData = new HashMap<String, List>();  // 承载此接口返回的所有数据
        // 根据楼盘列表，从政府网获取所有地块、单元楼的数据
        FloorServiceImpl floorService = new FloorServiceImpl();
        Map<String, List> floorAndPlotsData = floorService.getFloorListByAllHouses(tempHousesList);

        allData.put("allFloorList", floorAndPlotsData.get("allFloorList"));
        allData.put("allPlotsList", floorAndPlotsData.get("allPlotsList"));
        return allData;
    }


    /**
     * 根据从搜房网抓取的每一条楼盘数据下潜获取楼盘的详情数据
     */
    @Override
    public THouses getHousesDetailsByElement(Element li) {

        String sfwUrl = li.select(".nlc_details .nlcd_name a").attr("href");

        /**
         * 为了避免获取详情出错导致无法获取楼盘名称，如果没有楼盘名称，就无法获取政府网的地块、单元楼数据了
         * 所以这里需要尽可能的从列表获取一些数据，在获取完详情数据后，再覆盖从详情页面获取到的值
         */
        String name = li.select(".nlc_details .nlcd_name a").text();
        String cover = li.select(".nlc_img img").eq(1).attr("src");
        String address = li.select(".nlc_details .address a").text();

        THouses houses = getHousesDetailsByUrl(sfwUrl);

        houses.setHousesName(name);
        houses.setCover(cover);
        houses.setAddress(address);

        return houses;
    }

    /**
     * 根据某个楼盘详细页面的url获取这一个楼盘的详细数据
     */
    @Override
    public THouses getHousesDetailsByUrl(String url) {

        THouses houses = new THouses();

        String sfwUrl = url;
        String name = null;
        String cover = null;
        String address = null;
        String averagePrice = null;
        String openingDate = null;

        // 根据url继续下潜抓取详细信息
        Document detailedDoc = null;  // 承载抓取到的楼盘详细数据
        try {
            detailedDoc = Jsoup.connect(url).timeout(5000).get();

            // 如果是公寓类型
            if (detailedDoc.select(".inf_left1 strong").text().equals("")) {
                name = detailedDoc.select(".lp-name span").text();
                cover = detailedDoc.select(".banner-img").attr("src");
                address = detailedDoc.select(".lp-type").eq(3).select("i").text();
                averagePrice = detailedDoc.select(".l-price strong").text();
                openingDate = detailedDoc.select(".lp-type").eq(2).select("a").text();
            } else {
                name = detailedDoc.select(".inf_left1 strong").text();
                cover = detailedDoc.select(".bannerbg_pos a img").attr("src");
                address = detailedDoc.select("#xfdsxq_B04_12 span").text();
                averagePrice = detailedDoc.select(".prib").text();
                openingDate = detailedDoc.select(".kaipan").text();
            }

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取[楼盘]   ["+name+"]详细数据完成!");
        } catch (IOException e) {

            // 组织错误信息，供返回使用
            SpiderErrorServiceImpl.addError(
                    "详情",
                    "楼盘",
                    "楼盘["+name+"]",
                    sfwUrl,
                    e.toString(),
                    "",
                    "",
                    ""
            );

            // 错误信息写入到自己的日志中
            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "抓取[楼盘]   ["+name+"]详细数据异常："+e);

            e.printStackTrace();
        }

        houses.setHousesId(UUID.randomUUID());
        houses.setHousesName(name);
        houses.setFdcHousesName(AnalysisHouseUtil.extractValidHousesName(name));
        houses.setSfwUrl(sfwUrl);
        houses.setCover(cover);
        houses.setAddress(address);
        houses.setAveragePrice(averagePrice);
        houses.setOpeningDate(openingDate);

        return houses;
    }


}
