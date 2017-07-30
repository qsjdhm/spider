package com.spider.service.houses;

import com.spider.entity.houses.TReb;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by zhangyan on 17/7/16.
 * 房产商业务接口
 *
 * 主要对外接口描述
 * 1. 可获取全部列表数据  getAllList       （可单独调用）
 * 2. 可获取单页列表数据  getPageListByUrl （可单独调用）
 * 3. 可获取单个详情数据  getDetailsByUrl  （可单独调用）
 */
public interface IRebService {
    /**
     * 从政府网获取所有房产商数据
     */
    public List<TReb> getAllList();

    /**
     * 根据某一页房产商列表页面的url获取获取这一页的房产商列表数据
     * @param url 某一页房产商列表页面的url
     */
    public List<TReb> getPageListByUrl(String url);

    /**
     * 根据从政府网抓取的每一条房产商数据下潜获取房产商的详情数据
     * @param tr 抓取的每一条房产商DOM数据
     */
    public TReb getDetailsByElement(Element tr);

    /**
     * 根据某一个房产商详细页面的url获取这一个房产商的详细数据
     * @param url 某一个房产商详细页面的url
     */
    public TReb getDetailsByUrl(String url);
}

