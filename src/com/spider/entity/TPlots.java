package com.spider.entity;

import java.util.UUID;

/**
 * Created by zhangyan on 17/7/19.
 * 单元楼原型
 */
public class TPlots {

    public TPlots () {

    }

    private UUID plotsId = null;  // 单元楼ID
    private String plotsName = null;  // 单元楼名称
    private String fdcUrl = null;  // 单元楼页面政府网URL
    private String area = null;  // 建筑面积
    private String decoration = null;  // 装修标准
    private String use = null;  // 规划用途
    private String mortgage = null;  // 有无抵押
    private String salePermit = null;  // 商品房预售许可证
    private String landUseCertificate = null;  // 国有土地使用证
    private String planningPermit = null;  // 建设工程规划许可证
    private String constructionPermit = null;  // 建设工程施工许可证
    private UUID pFloorId = null;  // 所属地块ID
    private String pFloorName = null;  // 所属地块名称
    private UUID pHousesId = null;  // 所属楼盘ID
    private String pHousesName = null;  // 所属楼盘名称
    private String pRebId = null;  // 所属房产商ID
    private String pRebName = null;  // 所属房产商名称


    public UUID getPlotsId() {
        return plotsId;
    }

    public void setPlotsId(UUID plotsId) {
        this.plotsId = plotsId;
    }

    public String getPlotsName() {
        return plotsName;
    }

    public void setPlotsName(String plotsName) {
        this.plotsName = plotsName;
    }

    public String getFdcUrl() {
        return fdcUrl;
    }

    public void setFdcUrl(String fdcUrl) {
        this.fdcUrl = fdcUrl;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }

    public String getSalePermit() {
        return salePermit;
    }

    public void setSalePermit(String salePermit) {
        this.salePermit = salePermit;
    }

    public String getLandUseCertificate() {
        return landUseCertificate;
    }

    public void setLandUseCertificate(String landUseCertificate) {
        this.landUseCertificate = landUseCertificate;
    }

    public String getPlanningPermit() {
        return planningPermit;
    }

    public void setPlanningPermit(String planningPermit) {
        this.planningPermit = planningPermit;
    }

    public String getConstructionPermit() {
        return constructionPermit;
    }

    public void setConstructionPermit(String constructionPermit) {
        this.constructionPermit = constructionPermit;
    }

    public UUID getpFloorId() {
        return pFloorId;
    }

    public void setpFloorId(UUID pFloorId) {
        this.pFloorId = pFloorId;
    }

    public String getpFloorName() {
        return pFloorName;
    }

    public void setpFloorName(String pFloorName) {
        this.pFloorName = pFloorName;
    }

    public UUID getpHousesId() {
        return pHousesId;
    }

    public void setpHousesId(UUID pHousesId) {
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
