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
 *
 * 主要对外接口描述
 * 1. 可获取全部列表数据  getAllList        （可单独调用）
 * 2. 可获取单页列表数据  getPageListByUrl  （可单独调用）
 * 3. 可获取单个详情数据  getDetailsByUrl   （可单独调用）
 */
public interface IHousesService {

    /**
     * 从搜房网获取全部楼盘数据，包含全部的楼盘、全部的地块、全部的单元楼
     */
    public Map<String, List> getAllList();

    /**
     * 根据某一页楼盘列表页面的url获取获取这一页的楼盘数据和下潜的数据（比如：它们的地块、它们的单元楼）
     * @param url 某一页楼盘列表页面的url
     * @param isInfiltrate 是否继续获取下潜数据（比如：它们的地块、它们的单元楼）
     */
    public Map<String, List> getPageListByUrl(String url, boolean isInfiltrate);

    /**
     * 根据从搜房网抓取的每一条楼盘数据下潜获取楼盘的详情数据
     * @param li 抓取的每一条楼盘DOM数据
     */
    public THouses getDetailsByElement(Element li);

    /**
     * 根据某个楼盘详细页面的url获取这一个楼盘的详细数据
     * @param url 某个楼盘详细页面的url
     */
    public THouses getDetailsByUrl(String url);


}
