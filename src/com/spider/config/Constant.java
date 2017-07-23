package com.spider.config;

import java.io.File;

import static java.io.File.*;

/**
 * Created by zhangyan on 17/7/23.
 * 用于保存作用域整个项目的常量
 */
public class Constant {
    // 爬虫进度日志路径
    public final static String SPIDER_LOG_PATH = "log" + separator + "spider_schedule" + separator;
    // 方法调用历史日志路径
    public final static String METHOD_CALL_LOG_PATH = "log" + separator + "method_call_history" + separator;


    // 日志信息类型
    public final static String SUCCESS = "success";
    public final static String INFO = "info";
    public final static String ERROR = "error!!!";
}
