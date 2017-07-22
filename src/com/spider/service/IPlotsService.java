package com.spider.service;

import com.spider.model.TPlots;

import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 单元楼业务接口
 */
public interface IPlotsService {

    /**
     * 根据地块url获取它的单元楼列表
     */
    public List<TPlots> getPlotsListByFloorUrl();

    /**
     * 根据单元楼url获取单个单元楼详细数据
     */
    public List<TPlots> getPlotsDetailsByPlotsUrl();

}
