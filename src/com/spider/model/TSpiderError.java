package com.spider.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyan on 17/7/25.
 * 用于存放抓取搜房网、政府网数据过程中的异常错误
 * 在方法调用的时候会将这些错误返回给调用方
 */
public class TSpiderError {

    public TSpiderError () {

    }

    // 错误列表
    private static List<Map<String, String>> errorList = new ArrayList<Map<String, String>>();


    /**
     * 外部会逐条往errorList添加错误信息
     */
    public static void addError(Map<String, String> error) {
        errorList.add(error);
    }

    /**
     * 外部获取全部的错误列表
     * @param isClear 是否需要清除错误列表
     */
    public static List<Map<String, String>> getErrorList(boolean isClear) {
        if (isClear) {
            List<Map<String, String>> tempErrorList = new ArrayList<Map<String, String>>(TSpiderError.errorList);
            return tempErrorList;
        } else {
            return errorList;
        }
    }

    /**
     * 清空错误列表
     */
    public static void clearErrorList() {
        TSpiderError.errorList.clear();
    }

}

