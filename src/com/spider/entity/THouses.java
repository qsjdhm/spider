package com.spider.entity;

import java.util.UUID;

/**
 * Created by zhangyan on 17/7/19.
 * 楼盘原型
 */
public class THouses {

    public THouses () {

    }

    private UUID housesId = null;  // 楼盘ID
    private String housesName = null;  // 楼盘名称
    private String fdcHousesName = null;  // 政府网查询时用的楼盘名称
    private String sfwUrl = null;  // 搜房网URL
    private String cover = null;  // 封面
    private String address = null;  // 地址
    private String averagePrice = null;  // 均价
    private String openingDate = null;  // 开盘日期
    private String pRebId = null;  // 所属房产商ID
    private String pRebName = null;  // 所属房产商名称


    public UUID getHousesId() {
        return housesId;
    }

    public void setHousesId(UUID housesId) {
        this.housesId = housesId;
    }

    public String getHousesName() {
        return housesName;
    }

    public void setHousesName(String housesName) {
        this.housesName = housesName;
    }

    public String getFdcHousesName() {
        return fdcHousesName;
    }

    public void setFdcHousesName(String fdcHousesName) {
        this.fdcHousesName = fdcHousesName;
    }

    public String getSfwUrl() {
        return sfwUrl;
    }

    public void setSfwUrl(String sfwUrl) {
        this.sfwUrl = sfwUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getpRebId() {
        return pRebId;
    }

    public void setpRebId(String pRebId) {
        this.pRebId = pRebId;
    }

    public String getpRebName() {
        return pRebName;
    }

    public void setpRebName(String pRebName) {
        this.pRebName = pRebName;
    }
}
