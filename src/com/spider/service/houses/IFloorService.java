package com.spider.service.houses;

import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.THouses;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyan on 17/7/16.
 * 地块业务接口
 *
 * 主要对外接口描述
 * 1. 可根据楼盘列表获取全部数据     getAllListByAllHouses
 * 2. 可根据单个楼盘获取它的全部数据  getAllListByHouses
 * 3. 可获取单页列表数据            getPageListByUrl       （可单独调用）
 * 4. 可获取单个详情数据            getDetailsByUrl        （可单独调用）
 */
public interface IFloorService {

    /**
     * 根据全部楼盘列表获取它们的全部地块列表数据和下潜的数据（比如：它们的单元楼）
     * @param housesList 楼盘列表对象
     */
    public Map<String, List> getAllListByAllHouses(List<THouses> housesList);

    /**
     * 根据楼盘从政府网抓取此楼盘的地块列表数据，并调用获取地块详情方法
     * @param houses 某个楼盘对象
     */
    public List<TFloor> getAllListByHouses(THouses houses);

    /**
     * 根据某一页地块列表页面的url获取获取这一页的地块数据
     * @param url 某一页地块列表页面的url
     * @param housesName 所属楼盘名称（解析后在政府网查询使用）
     * @param isInfiltrate 是否继续获取下潜数据（比如：它们的单元楼）
     */
    public Map<String, List> getPageListByUrl(String url, String housesName, boolean isInfiltrate);

    /**
     * 根据从政府网抓取的每一条地块数据下潜获取地块的详情数据
     * @param tr 抓取的每一条地块DOM数据
     * @param housesName 所属楼盘名称（解析后在政府网查询使用）
     */
    public TFloor getDetailsByElement(Element tr, String housesName);

    /**
     * 根据某个地块详细页面的url获取这一个地块的详细数据
     * @param url 某个地块详细页面的url
     * @param housesName 所属楼盘名称（解析后在政府网查询使用）
     */
    public TFloor getDetailsByUrl(String url, String housesName);


}
