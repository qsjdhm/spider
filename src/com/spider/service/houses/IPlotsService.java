package com.spider.service.houses;

import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.TPlots;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyan on 17/7/16.
 * 单元楼业务接口
 */
public interface IPlotsService {

    /**
     * 根据全部的地块列表遍历每个地块数据，并且根据每个地块数据下潜获取此地块的单元楼列表数据（包括单元楼详情）
     */
    public Map<String, List> getAllListByAllFloor(List<TFloor> floorList);

    /**
     * 根据地块从政府网抓取此地块的单元楼列表数据，并调用获取单元楼详情方法
     */
    public List<TPlots> getAllListByFloor(TFloor floor);

    /**
     * 根据某一页单元楼的url获取获取这一页的单元楼数据
     */
    public Map<String, List> getPageListByUrl(String url, String floorName, boolean isInfiltrate);

    /**
     * 根据从政府网抓取的每一条单元楼数据下潜获取单元楼的详情数据
     */
    public TPlots getDetailsByElement(Element tr, String floorName);


    /**
     * 根据某个单元楼详细页面的url获取这一个单元楼的详细数据
     */
    public TPlots getDetailsByUrl(String url, String floorName);
}
