package com.spider.service.impl.houses;

import com.spider.utils.Constant;
import com.spider.entity.houses.TReb;
import com.spider.service.houses.IRebService;
import com.spider.service.impl.system.SpiderErrorServiceImpl;
import com.spider.utils.LogFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by zhangyan on 2017/7/20.
 * 处理房产商业务功能
 *
 * 主要对外接口描述
 * 1. 可获取全部列表数据  getAllList
 * 2. 可获取单页列表数据  getPageListByUrl
 * 3. 可获取单个详情数据  getDetailsByUrl
 */
public class RebServiceImpl implements IRebService {

    /**
     * 从政府网获取所有房产商数据
     */
    @Override
    public List<TReb> getAllList () {

        List<TReb> rebList = new ArrayList<TReb>();  // 承载房产商数据集合
        int fdcUrlPageNumer = 1;  // 政府网url页数索引，会进行累加数值直至获取不到数据
        Document pageDoc = null;  // 承载抓取到的每页房产商DOM数据
        String fdcUrl = "";

        try {
            do {
                fdcUrl = "http://www.jnfdc.gov.cn/kfqy/index_"+fdcUrlPageNumer+".shtml";
                pageDoc = Jsoup.connect(fdcUrl).timeout(5000).get();
                Elements trs = pageDoc.select(".project_table tr");

                // 因为抓取到的数据不规范，所以要自己组织为规范的数据格式
                for (Element tr : trs) {
                    // 只获取有效数据的值
                    if (tr.select("td").size() > 1) {
                        // 抓取详细信息
                        rebList.add(getDetailsByElement(tr));
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

            // 组织错误信息，供返回使用
            SpiderErrorServiceImpl.addError(
                    "分页",
                    "房产商",
                    "第"+fdcUrlPageNumer+"页楼盘列表",
                    fdcUrl,
                    e.toString(),
                    "",
                    "",
                    ""
            );

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "抓取政府网第"+fdcUrlPageNumer+"页房产商数据异常："+e);
            e.printStackTrace();
        }

        LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "抓取房产商结束！共抓取"+rebList.size()+"条房产商数据!");

        return rebList;
    }


    /**
     * 根据某一页房产商列表页面的url获取获取这一页的房产商列表数据
     * @param url 某一页房产商列表页面的url
     */
    @Override
    public List<TReb> getPageListByUrl(String url) {

        List<TReb> rebList = new ArrayList<TReb>();  // 承载房产商数据集合
        Document pageDoc = null;  // 承载抓取到的此页房产商DOM数据

        try {
            pageDoc = Jsoup.connect(url).timeout(5000).get();
            Elements trs = pageDoc.select(".project_table tr");

            for (Element tr : trs) {
                if (tr.select("td").size() > 1) {
                    rebList.add(getDetailsByElement(tr));
                }
            }

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "[根据url]:"+url+"抓取[房产商]分页列表数据完成!");
        } catch (IOException e) {

            // 组织错误信息，供返回使用
            SpiderErrorServiceImpl.addError(
                    "分页",
                    "房产商",
                    "房产商列表url["+url+"]",
                    url,
                    e.toString(),
                    "",
                    "",
                    ""
            );

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR, "[根据url]:"+url+"抓取[房产商]分页列表数据异常："+e);
            e.printStackTrace();
        }

        LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "[根据url]抓取房产商结束！共抓取"+rebList.size()+"条房产商数据!");

        return rebList;
    }


    /**
     * 根据从政府网抓取的每一条房产商数据下潜获取房产商的详情数据
     * @param tr 抓取的每一条房产商DOM数据
     */
    @Override
    public TReb getDetailsByElement(Element tr) {

        Elements tds = tr.select("td");
        String fdcUrl = "http://www.jnfdc.gov.cn/kfqy/" + tds.eq(1).select("a").attr("href");
        String name = tds.eq(1).select("a").text();
        String qualificationLevel = tds.eq(2).text();
        String qualificationId = tds.eq(3).text();
        String LegalPerson = tds.eq(4).text();

        TReb reb = getDetailsByUrl(fdcUrl);

        // 如果从列表抓到的名称没有...，就用列表的名称
        if (name.indexOf("...") == -1) {
            reb.setRebName(name);
        }
        reb.setQualificationLevel(qualificationLevel);
        reb.setQualificationId(qualificationId);
        reb.setLegalPerson(LegalPerson);

        return reb;
    }


    /**
     * 根据某一个房产商详细页面的url获取这一个房产商的详细数据
     * @param url 某一个房产商详细页面的url
     */
    @Override
    public TReb getDetailsByUrl(String url) {

        String name = null;
        String qualificationLevel = null;
        String qualificationId = null;
        String LegalPerson = null;
        String address = null;
        String phone = null;
        String mail = null;
        String registeredCapital = null;
        String type = null;
        String introduction = null;

        Document detailedDoc = null;  // 承载抓取到的房产商详细数据
        try {
            // 根据url继续下潜抓取详细信息
            detailedDoc = Jsoup.connect(url).timeout(5000).get();
            Elements trs = detailedDoc.select(".message_table tr");

            name = trs.eq(0).select("td").eq(1).text();
            qualificationLevel = trs.eq(2).select("td").eq(1).text();
            LegalPerson = trs.eq(5).select("td").eq(3).text();
            address = trs.eq(1).select("td").eq(1).text();
            phone = trs.eq(3).select("td").eq(3).text();
            mail = trs.eq(4).select("td").eq(3).text();  // 企业邮箱
            registeredCapital = trs.eq(5).select("td").eq(1).text();  // 注册资金
            type = trs.eq(6).select("td").eq(1).text();  // 企业类型
            introduction = trs.eq(7).select("td").eq(1).text();  // 企业简介

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.SUCCESS, "[根据url]:"+url+"抓取[房产商]["+name+"]详细数据完成!");
        } catch (IOException e) {

            // 组织错误信息，供返回使用
            SpiderErrorServiceImpl.addError(
                    "详情",
                    "房产商",
                    "房产商详情url["+url+"]",
                    url,
                    e.toString(),
                    "",
                    "",
                    ""
            );

            LogFile.writerLogFile(Constant.SPIDER_LOG_PATH, Constant.ERROR,  "[根据url]:"+url+"抓取[房产商]["+name+"]详细数据异常："+e);
            e.printStackTrace();
        }

        TReb reb = new TReb();
        reb.setRebId(UUID.randomUUID());
        reb.setRebName(name);
        reb.setFdcUrl(url);
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
