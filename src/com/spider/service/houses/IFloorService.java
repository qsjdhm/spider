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
 */
public interface IFloorService {

    /**
     * 根据搜房网全部楼盘列表遍历每个楼盘数据，并且根据每个楼盘数据下潜获取此楼盘的地块列表数据（包括地块详情）
     * 在获得地块列表数据后，再根据地块列表获取单元楼列表数据（包括单元楼详情）
     */
    public Map<String, List> getFloorListByAllHouses(List<THouses> housesList);

    /**
     * 根据楼盘从政府网抓取此楼盘的地块列表数据，并调用获取地块详情方法
     */
    public List<TFloor> getFloorListByHouses(THouses houses);

    /**
     * 根据某一页地块的url获取获取这一页的地块数据
     */
    public List<TFloor> getFloorListByUrl(String url);

    /**
     * 根据从政府网抓取的每一条地块数据下潜获取地块的详情数据
     */
    public TFloor getFloorDetailsByElement(Element tr, THouses houses);

    /**
     * 根据某个地块详细页面的url获取这一个地块的详细数据
     */
    public TFloor getFloorDetailsByUrl(String url);


}
