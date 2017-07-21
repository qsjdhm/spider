package com.spider.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangyan on 2017/7/21.
 * 操作日志文件的工具类
 */
public class LogFile {

    /**
     * 接收参数，向每一天的日志文件写内容
     */
    public static void writerLogFile(String logPath, String type, String msg) throws IOException {

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String detailsDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        PrintWriter logPrint = null;
        String path = LogFile.class.getResource("/").getPath() + logPath + date + ".log";

        File logFile = new File(path);
        if (logFile.exists()) {
            writerContent(path, "["+type+"] " + "[" +detailsDate+"] " + msg);
        } else {
            try {
                logFile.createNewFile();
                writerContent(path, "["+type+"]   " + msg);
            } catch (IOException e) {
                System.out.println("创建文件出错");
                e.printStackTrace();
            }
        }
    }

    /**
     * 向文件写数据
     */
    public static void writerContent (String filePath, String content) throws IOException {
        // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
        FileWriter writer = new FileWriter(filePath, true);
        writer.write(content += "\r\n");
        writer.close();
    }

}
