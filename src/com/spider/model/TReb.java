package com.spider.model;

/**
 * Created by zhangyan on 17/7/19.
 * 房产商原型
 */
public class TReb {

    public TReb () {

    }

    private String rebId = null;  // 房产商ID
    private String rebName = null;  // 房产商名称
    private String fdcUrl = null;  // 政府网URL
    private String qualificationLevel = null;  // 资质等级
    private String qualificationId = null;  // 资质编号
    private String LegalPerson = null;  // 法人代表
    private String address = null;  // 企业地址
    private String phone = null;  // 联系电话
    private String mail = null;  // 企业邮箱
    private String type = null;  // 企业类型
    private String introduction = null;  // 企业简介


    public String getRebId() {
        return rebId;
    }

    public void setRebId(String rebId) {
        this.rebId = rebId;
    }

    public String getRebName() {
        return rebName;
    }

    public void setRebName(String rebName) {
        this.rebName = rebName;
    }

    public String getFdcUrl() {
        return fdcUrl;
    }

    public void setFdcUrl(String fdcUrl) {
        this.fdcUrl = fdcUrl;
    }

    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
    }

    public String getLegalPerson() {
        return LegalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        LegalPerson = legalPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
