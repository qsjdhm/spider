package com.spider.service.houses;

import com.spider.entity.houses.TReb;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by zhangyan on 17/7/16.
 * 房产商业务接口
 */
public interface IRebService {
    /**
     * 从政府网获取所有房产商数据
     */
    public List<TReb> getAllReb();

    /**
     * 根据某一页房产商的url获取获取这一页的房产商数据
     */
    public List<TReb> getRebListByUrl(String url);

    /**
     * 根据从政府网抓取的每一条房产商数据下潜获取房产商的详情数据
     */
    public TReb getRebDetailsByElement(Element tr);

    /**
     * 根据某个房产商详细页面的url获取这一个房产商的详细数据
     */
    public TReb getRebDetailsByUrl(String url);
}

