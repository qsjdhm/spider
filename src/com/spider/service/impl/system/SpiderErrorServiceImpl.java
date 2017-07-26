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
     */
    public static void addError(String type, String name, String url, String content) {
        Map<String, String> error = new HashMap<String, String>();
        error.put("type", type);
        error.put("name", name);
        error.put("url", url);
        error.put("content", content);
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
