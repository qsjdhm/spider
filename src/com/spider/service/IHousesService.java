package com.spider.service;

import com.spider.model.THouses;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务接口
 */
public interface IHousesService {

    /**
     * 从搜房网获取全部楼盘数据,包含楼盘、地块、单元楼数据
     */
    public HashMap<String, Object> getAllHouses();

    /**
     * 根据抓取的数据获取每个楼盘的详情数据
     */
    public THouses getHousesDetailsByElement(Element li);


}
