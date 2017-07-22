package com.spider.service;

import com.spider.model.TFloor;

import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 地块业务接口
 */
public interface IFloorService {

    /**
     * 根据楼盘名称从政府网获取它的地块列表
     */
    public List<TFloor> getFloorListByHousesName();

    /**
     * 根据地块url获取单个地块详细数据，包括单元楼
     */
    public TFloor getFloorDetailsByFloorUrl();

}
