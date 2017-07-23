package com.spider.service;

import com.spider.model.TFloor;
import com.spider.model.TPlots;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 单元楼业务接口
 */
public interface IPlotsService {

    /**
     * 根据全部的地块从政府网获取它们的单元楼列表
     */
    public HashMap<String, Object> getPlotsListByAllFloor(List<TFloor> floorList);

    /**
     * 根据地块获取它的单元楼列表
     */
    public List<TPlots> getPlotsListByFloor(TFloor floor);

    /**
     * 根据抓取的单元楼详细数据获取单个单元楼详细数据
     */
    public TPlots getPlotsDetailsByElement(Element tr, TFloor floor);

}
