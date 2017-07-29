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
 *
 * 主要对外接口描述
 * 1. 可根据地块列表获取全部数据     getAllListByAllFloor
 * 2. 可根据单个地块获取它的全部数据  getAllListByFloor
 * 3. 可获取单页列表数据            getPageListByUrl       （可单独调用）
 * 4. 可获取单个详情数据            getDetailsByUrl        （可单独调用）
 */
public interface IPlotsService {

    /**
     * 根据全部地块列表获取它们的全部单元楼列表数据
     * @param floorList 地块列表对象
     */
    public Map<String, List> getAllListByAllFloor(List<TFloor> floorList);

    /**
     * 根据地块从政府网抓取此地块的单元楼列表数据，并调用获取单元楼详情方法
     * @param floor 某个地块对象
     */
    public List<TPlots> getAllListByFloor(TFloor floor);

    /**
     * 根据某一页单元楼列表页面的url获取获取这一页的单元楼数据
     * @param url 某一页单元楼列表页面的url
     * @param floorName 所属地块名称
     * @param isInfiltrate 是否继续获取下潜数据（比如：每一户数据）
     */
    public Map<String, List> getPageListByUrl(String url, String floorName, boolean isInfiltrate);

    /**
     * 根据从政府网抓取的每一条单元楼数据下潜获取单元楼的详情数据
     * @param tr 抓取的每一条单元楼DOM数据
     * @param floorName 所属地块名称
     */
    public TPlots getDetailsByElement(Element tr, String floorName);

    /**
     * 根据某个单元楼详细页面的url获取这一个单元楼的详细数据
     * @param url 某个地块详细页面的url
     * @param floorName 所属地块名称
     * @param plotsName 单元楼名称
     */
    public TPlots getDetailsByUrl(String url, String floorName, String plotsName);
}
