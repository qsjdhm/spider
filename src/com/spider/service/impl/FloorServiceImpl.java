package com.spider.service.impl;

import com.spider.model.TFloor;
import com.spider.service.IFloorService;
import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 地块业务功能类
 */
public class FloorServiceImpl implements IFloorService {

    /**
     * 根据楼盘名称从政府网获取它的地块列表
     */
    @Override
    public List<TFloor> getFloorListByHousesName() {
        return null;
    }

    /**
     * 根据地块url获取单个地块详细数据，包括单元楼
     */
    @Override
    public TFloor getFloorDetailsByFloorUrl() {
        return null;
    }


}
