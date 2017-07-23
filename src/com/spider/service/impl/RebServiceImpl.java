package com.spider.service.impl;

import com.spider.config.Constant;
import com.spider.model.TReb;
import com.spider.service.IRebService;
import com.spider.utils.LogFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
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

        ArrayList<TReb> rebList = new ArrayList<TReb>();  // 承载房产商数据集合
        int fdcUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        Document pageDoc = null;  // 承载抓取到的每页房产商数据

        try {
            do {
                pageDoc = Jsoup.connect("http://www.jnfdc.gov.cn/kfqy/index_"+fdcUrlPageNumer+".shtml").timeout(5000).get();
                Elements trs = pageDoc.select(".project_table tr");

                // 因为抓取到的数据不规范，所以要自己组织为规范的数据格式
                for (Element tr : trs) {
                    // 只获取有效数据的值
                    if (tr.select("td").size() > 1) {
                        // 抓取详细信息
                        rebList.add(getRebDetailsByElement(tr));
                    }
                }

                // 如果获取的td是空数据  就设置pageDoc为null
                if (trs.size() <= 2) {
                    pageDoc = null;
                }

                LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取政府网第"+fdcUrlPageNumer+"页房产商数据完成");

                fdcUrlPageNumer++;
            } while (pageDoc != null);
        } catch (IOException e) {
            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "抓取政府网第"+fdcUrlPageNumer+"页房产商数据异常："+e);
            e.printStackTrace();
        }
        LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "----------------------------------");
        LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "共抓取"+rebList.size()+"条房产商数据!");
        return rebList;
    }

    /**
     * 根据抓取的数据获取每个房产商的详情数据
     */
    @Override
    public TReb getRebDetailsByElement(Element tr) {

        TReb reb = new TReb();

        Elements tds = tr.select("td");
        String spiderUrl = "http://www.jnfdc.gov.cn/kfqy/" + tds.eq(1).select("a").attr("href");
        String name = tds.eq(1).select("a").text();
        String qualificationLevel = tds.eq(2).text();
        String qualificationId = tds.eq(3).text();
        String LegalPerson = tds.eq(4).text();
        String address = null;
        String phone = null;
        String mail = null;
        String registeredCapital = null;
        String type = null;
        String introduction = null;

        // 根据url继续下潜抓取详细信息
        Document detailedDoc = null;  // 承载抓取到的房产商详细数据
        try {
            detailedDoc = Jsoup.connect(spiderUrl).timeout(5000).get();
            Elements trs = detailedDoc.select(".message_table tr");

            address = trs.eq(1).select("td").eq(1).text();
            phone = trs.eq(3).select("td").eq(3).text();
            mail = trs.eq(4).select("td").eq(3).text();  // 企业邮箱
            registeredCapital = trs.eq(5).select("td").eq(1).text();  // 注册资金
            type = trs.eq(6).select("td").eq(1).text();  // 企业类型
            introduction = trs.eq(7).select("td").eq(1).text();  // 企业简介

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取房产商["+name+"]详细数据完成!");
        } catch (IOException e) {
            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "抓取房产商["+name+"]详细数据异常："+e);
            e.printStackTrace();
        }

        reb.setRebId(UUID.randomUUID());
        reb.setRebName(name);
        reb.setFdcUrl(spiderUrl);
        reb.setQualificationLevel(qualificationLevel);
        reb.setQualificationId(qualificationId);
        reb.setLegalPerson(LegalPerson);
        reb.setAddress(address);
        reb.setPhone(phone);
        reb.setMail(mail);
        reb.setRegisteredCapital(registeredCapital);
        reb.setType(type);
        reb.setIntroduction(introduction);

        return reb;
    }
}
