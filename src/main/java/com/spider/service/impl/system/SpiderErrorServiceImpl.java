package com.spider.service.impl.system;

import java.util.*;

/**
 * Created by zhangyan on 2017/7/25.
 * 处理爬虫错误业务功能
 */
public class SpiderErrorServiceImpl {

    // 错误列表
    private static List<Map<String, String>> errorList = new ArrayList<Map<String, String>>();


    /**
     * 外部会逐条往errorList添加错误信息
     * 外部根据返回数据拼接成想要的字符串
     * @param type     分页、详情
     * @param from     房产商、楼盘、地块、单元楼
     * @param name     房产商、楼盘、地块、单元楼的第几页数据   房产商、楼盘、地块、单元楼的名称
     * @param url      错误爬虫的源地址
     * @param content  异常内容
     * @param pReb     所属房产商
     * @param pHouses  所属楼盘
     * @param pFloor   所属地块
     */
    public static void addError(String type, String from, String name,
                                String url, String content,
                                String pReb, String pHouses, String pFloor) {
        Map<String, String> error = new HashMap<String, String>();
        error.put("type", type);
        error.put("from", from);
        error.put("name", name);
        error.put("url", url);
        error.put("content", content);
        error.put("pReb", pReb);
        error.put("pHouses", pHouses);
        error.put("pFloor", pFloor);
        errorList.add(error);
    }

    /**
     * 外部获取全部的错误列表
     */
    public static List<Map<String, String>> getErrorList() {
        return errorList;
    }

    /**
     * 外部获取全部的错误列表
     * @param isClear 是否需要清除错误列表
     */
    public static List<Map<String, String>> getErrorList(boolean isClear) {
        List<Map<String, String>> tempErrorList = new ArrayList<Map<String, String>>(errorList);

        // 需要清空数据
        if (isClear) {
            clearErrorList();
        }
        return tempErrorList;
    }

    /**
     * 清空错误列表
     */
    public static void clearErrorList() {
        errorList.clear();
    }


}
