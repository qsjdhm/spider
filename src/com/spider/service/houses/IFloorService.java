package com.spider.service.houses;

import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.THouses;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 地块业务接口
 */
public interface IFloorService {

    /**
     * 根据搜房网全部楼盘列表遍历每个楼盘数据，并且根据每个楼盘数据下潜获取此楼盘的地块列表数据（包括地块详情）
     * 在获得地块列表数据后，再根据地块列表获取单元楼列表数据（包括单元楼详情）
     */
    public HashMap<String, ArrayList> getFloorListByAllHouses(List<THouses> housesList);

    /**
     * 根据楼盘从政府网抓取此楼盘的地块列表数据，并调用获取地块详情方法
     */
    public ArrayList<TFloor> getFloorListByHouses(THouses houses);

    /**
     * 根据从政府网抓取的每一条地块数据下潜获取地块的详情数据
     */
    public TFloor getFloorDetailsByElement(Element tr, THouses houses);


}
