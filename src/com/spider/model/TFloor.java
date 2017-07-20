package com.spider.model;

/**
 * Created by zhangyan on 17/7/19.
 * 地块原型
 */
public class TFloor {

    public TFloor () {

    }

    private String floorId = null;  // 地块ID
    private String floorName = null;  // 地块名称
    private String fdcUrl = null;  // 楼盘页面政府网URL
    private String canSold = null;  // 可售套数
    private String address = null;  // 项目地址
    private String county = null;  // 所在区县
    private String scale = null;  // 项目规模
    private String totalPlotsNumber = null;  // 总栋数
    private String property = null;  // 物业公司
    private String pHousesId = null;  // 所属楼盘ID
    private String pHousesName = null;  // 所属楼盘名称
    private String pRebId = null;  // 所属房产商ID
    private String pRebName = null;  // 所属房产商名称

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFdcUrl() {
        return fdcUrl;
    }

    public void setFdcUrl(String fdcUrl) {
        this.fdcUrl = fdcUrl;
    }

    public String getCanSold() {
        return canSold;
    }

    public void setCanSold(String canSold) {
        this.canSold = canSold;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getTotalPlotsNumber() {
        return totalPlotsNumber;
    }

    public void setTotalPlotsNumber(String totalPlotsNumber) {
        this.totalPlotsNumber = totalPlotsNumber;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getpHousesId() {
        return pHousesId;
    }

    public void setpHousesId(String pHousesId) {
        this.pHousesId = pHousesId;
    }

    public String getpHousesName() {
        return pHousesName;
    }

    public void setpHousesName(String pHousesName) {
        this.pHousesName = pHousesName;
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
