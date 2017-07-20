package com.spider.service.impl;

import com.spider.model.TReb;
import com.spider.service.IRebService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyan on 2017/7/20.
 * 处理房产商业务功能
 */
public class RebServiceImpl implements IRebService {

    /**
     * 从政府网获取所有房产商数据
     */
    @Override
    public List<TReb> getAllReb () {

        // 承载房产商数据集合
        ArrayList<TReb> reb = new ArrayList<TReb>();
        // 政府网url页数索引，会进行累加数值直至获取不到数据
        int fdcUrlPageNumer = 1;


        Document pageDoc = null;  // 承载抓取到的每页房产商数据
        try {
            pageDoc = Jsoup.connect("http://www.jnfdc.gov.cn/kfqy/index_"+fdcUrlPageNumer+".shtml").get();
            Elements trs = pageDoc.select(".project_table tr");


            // 因为抓取到的数据不规范，所以要自己组织为规范的数据格式
            Elements items = null;
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                if (tds.size() > 1) {
                    String url = "http://www.jnfdc.gov.cn/kfqy/" + tds.eq(1).select("a").attr("href");
                    String name = tds.eq(1).select("a").text();
                    String qualificationLevel = tds.eq(2).text();
                    String qualificationId = tds.eq(3).text();
                    String LegalPerson = tds.eq(4).text();

                    System.out.println(tds);
                    System.out.println(url);
                    System.out.println(name);
                    System.out.println(qualificationLevel);
                    System.out.println(qualificationId);
                    System.out.println(LegalPerson);
                    System.out.println("-------");

                }
            }


//            do {
//                pageDoc = Jsoup.connect("http://www.jnfdc.gov.cn/kfqy/index_"+fdcUrlPageNumer+".shtml").get();
//
//                // 如果获取的td是空数据  就设置pageDoc为null
//                if (true) {
//                    pageDoc = null;
//                }
//                fdcUrlPageNumer++;
//                System.out.println(pageDoc);
//            } while (pageDoc != null);
        } catch (IOException e) {
            System.out.println("抓取房产商全部数据异常："+e);
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<TReb> analysisRebDataByDocument(Document rebDoc) {
        return null;
    }
}
