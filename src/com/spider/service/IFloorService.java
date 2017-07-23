package com.spider.service;

import com.spider.model.TFloor;
import com.spider.model.THouses;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangyan on 17/7/16.
 * 地块业务接口
 */
public interface IFloorService {

    /**
     * 根据全部的楼盘从政府网获取它们的地块列表
     */
    public HashMap<String, Object> getFloorListByAllHouses(List<THouses> housesList);

    /**
     * 根据楼盘从政府网获取它的地块列表
     */
    public ArrayList<TFloor> getFloorListByHouses(THouses houses);

    /**
     * 根据抓取的地块数据获取单个地块详细数据，包括单元楼
     */
    public TFloor getFloorDetailsByElement(Element tr, THouses houses);


}
