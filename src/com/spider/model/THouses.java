package com.spider.model;

/**
 * Created by zhangyan on 17/7/19.
 * 楼盘原型
 */
public class THouses {

    public THouses () {

    }

    private String housesId = null;  // 楼盘ID
    private String housesName = null;  // 楼盘名称
    private String sfwUrl = null;  // 搜房网URL
    private String address = null;  // 地址
    private String pRebId = null;  // 所属房产商ID
    private String pRebName = null;  // 所属房产商名称


    public String getHousesId() {
        return housesId;
    }

    public void setHousesId(String housesId) {
        this.housesId = housesId;
    }

    public String getHousesName() {
        return housesName;
    }

    public void setHousesName(String housesName) {
        this.housesName = housesName;
    }

    public String getSfwUrl() {
        return sfwUrl;
    }

    public void setSfwUrl(String sfwUrl) {
        this.sfwUrl = sfwUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
