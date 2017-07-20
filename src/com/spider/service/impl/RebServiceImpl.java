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
import java.util.UUID;

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
        ArrayList<TReb> rebList = new ArrayList<TReb>();
        // 政府网url页数索引，会进行累加数值直至获取不到数据
        int fdcUrlPageNumer = 1;


        Document pageDoc = null;  // 承载抓取到的每页房产商数据
        try {
            pageDoc = Jsoup.connect("http://www.jnfdc.gov.cn/kfqy/index_"+fdcUrlPageNumer+".shtml").get();
            Elements trs = pageDoc.select(".project_table tr");

            // 因为抓取到的数据不规范，所以要自己组织为规范的数据格式
            for (Element tr : trs) {
                // 只获取有效数据的值
                if (tr.select("td").size() > 1) {
                    rebList.add(analysisRebDataByElement(tr));
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

    /**
     * 根据抓取的数据解析出每个房产商的数据
     */
    @Override
    public TReb analysisRebDataByElement(Element tr) {

        TReb reb = new TReb();

        Elements tds = tr.select("td");
        String url = "http://www.jnfdc.gov.cn/kfqy/" + tds.eq(1).select("a").attr("href");
        String name = tds.eq(1).select("a").text();
        String qualificationLevel = tds.eq(2).text();
        String qualificationId = tds.eq(3).text();
        String LegalPerson = tds.eq(4).text();
        String address = null;
        String phone = null;
        String mail = null;
        String type = null;
        String introduction = null;


        // 根据url继续下潜抓取详细信息
        Document detailedDoc = null;  // 承载抓取到的房产商详细数据
        try {
            detailedDoc = Jsoup.connect(url).get();
            Elements trs = detailedDoc.select(".message_table tr");
            System.out.println(trs);
//            address = trs.eq(1).select("td").eq(1).text();
//            phone = trs.eq(2).select("td").eq(3).text();
//
//
//            mail = null;  // 企业邮箱
//            type = null;  // 企业类型
//            introduction = null;  // 企业简介



        } catch (IOException e) {
            System.out.println("抓取房产商详细数据异常："+e);
            e.printStackTrace();
        }

        reb.setRebId(UUID.randomUUID());
        reb.setRebName(name);
        reb.setFdcUrl(url);
        reb.setQualificationLevel(qualificationLevel);
        reb.setQualificationId(qualificationId);
        reb.setLegalPerson(LegalPerson);
        reb.setAddress(address);
        reb.setPhone(phone);
        reb.setMail(mail);
        reb.setType(type);
        reb.setIntroduction(introduction);

        return reb;
    }
}
