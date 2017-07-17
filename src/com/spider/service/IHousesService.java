package com.spider.service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyan on 17/7/16.
 * 楼盘业务接口
 */
public interface IHousesService {
    /**
     * 获取前5热门楼盘名称、url、封面信息
     */
    public List< Map<String, String> > getTop5Houses();

}
