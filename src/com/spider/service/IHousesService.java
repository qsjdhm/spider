package com.spider.service;

import com.spider.model.THouses;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务接口
 */
public interface IHousesService {

    /**
     * 从搜房网获取全部楼盘数据
     */
    public List<THouses> getAllHouses();

    /**
     * 根据抓取的数据获取每个楼盘的详情数据
     */
    public THouses getHousesDetailsByElement(Element li);

    /**
     * 根据楼盘设置楼盘开发商名称
     */
    public void setRebNameByHuouses(THouses houses);


}
