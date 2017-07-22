package com.spider.service;

import com.spider.model.TReb;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

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
     * 根据抓取的数据获取每个房产商的详情数据
     */
    public TReb getRebDetailsByElement(Element tr);
}
