package com.spider.service;

import com.spider.entity.THouses;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务接口
 */
public interface IHousesService {

    /**
     * 从搜房网获取全部楼盘数据,包含全部的楼盘、全部的地块、全部的单元楼数据
     */
    public HashMap<String, Object> getAllHouses();

    /**
     * 根据从搜房网抓取的每一条楼盘数据下潜获取楼盘的详情数据
     */
    public THouses getHousesDetailsByElement(Element li);


}
