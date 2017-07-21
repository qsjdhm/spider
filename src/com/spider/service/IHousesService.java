package com.spider.service;

import com.spider.model.TFloor;
import com.spider.model.THouses;
import com.spider.model.TPlots;

import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务接口
 */
public interface IHousesService {

    /**
     * 从搜房网获取全部楼盘数据
     */
    public List<THouses> getAllHouses();

    /**
     * 根据楼盘名称从政府网获取它的地块列表
     */
    public List<TFloor> getFloorListByHousesName();

    /**
     * 根据地块url获取单个地块详细数据，包括单元楼
     */
    public TFloor getFloorDetailsByFloorUrl();

    /**
     * 根据地块url获取它的单元楼列表
     */
    public List<TPlots> getPlotsListByFloorUrl();

    /**
     * 根据单元楼url获取单个单元楼详细数据
     */
    public List<TPlots> getPlotsDetailsByPlotsUrl();

}
