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
     * 根据全部的地块列表遍历每个地块数据，并且根据每个地块数据下潜获取此地块的单元楼列表数据（包括单元楼详情）
     */
    public HashMap<String, Object> getPlotsListByAllFloor(List<TFloor> floorList);

    /**
     * 根据地块从政府网抓取此地块的单元楼列表数据，并调用获取单元楼详情方法
     */
    public List<TPlots> getPlotsListByFloor(TFloor floor);

    /**
     * 根据从政府网抓取的每一条单元楼数据下潜获取单元楼的详情数据
     */
    public TPlots getPlotsDetailsByElement(Element tr, TFloor floor);

}
