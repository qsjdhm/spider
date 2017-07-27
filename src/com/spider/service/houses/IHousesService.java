package com.spider.service.houses;

import com.spider.entity.houses.TFloor;
import com.spider.entity.houses.THouses;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务接口
 */
public interface IHousesService {

    /**
     * 从搜房网获取全部楼盘数据,包含全部的楼盘、全部的地块、全部的单元楼数据
     */
    public Map<String, List> getAllHouses();

    /**
     * 根据某一页楼盘的url获取获取这一页的楼盘数据
     */
    public List<THouses> getHousesListByUrl(String url);

    /**
     * 根据从搜房网抓取的每一条楼盘数据下潜获取楼盘的详情数据
     */
    public THouses getHousesDetailsByElement(Element li);

    /**
     * 根据某个楼盘详细页面的url获取这一个楼盘的详细数据
     */
    public THouses getHousesDetailsByUrl(String url);


}
