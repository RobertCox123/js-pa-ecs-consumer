package com.techprimers.kafka.springbootkafkaconsumerexample.model;

import java.util.Date;

public class PlanogramDetails {
    String homeLoc, hierCd, hierTypCd, aisle, bay, shelfType, shelf, shelfSeq, defaultStyle, prdCd,prdTypCd,
            attr_1,attr_2,attr_3,attr_4,attr_5,attr_6;
    Date startDate, endDate;
    Integer facings, depth,defaultPrtQty;
    Float prdWidth,prdHeight;
    Boolean checkProduct;

    public String getHomeLoc() {
        return homeLoc;
    }

    public void setHomeLoc(String homeLoc) {
        this.homeLoc = homeLoc;
    }

    public String getHierCd() {
        return hierCd;
    }

    public void setHierCd(String hierCd) {
        this.hierCd = hierCd;
    }

    public String getHierTypCd() {
        return hierTypCd;
    }

    public void setHierTypCd(String hierTypCd) {
        this.hierTypCd = hierTypCd;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getBay() {
        return bay;
    }

    public void setBay(String bay) {
        this.bay = bay;
    }

    public String getShelfType() {
        return shelfType;
    }

    public void setShelfType(String shelfType) {
        this.shelfType = shelfType;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getShelfSeq() {
        return shelfSeq;
    }

    public void setShelfSeq(String shelfSeq) {
        this.shelfSeq = shelfSeq;
    }

    public String getDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(String defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    public String getPrdCd() {
        return prdCd;
    }

    public void setPrdCd(String prdCd) {
        this.prdCd = prdCd;
    }

    public String getPrdTypCd() {
        return prdTypCd;
    }

    public void setPrdTypCd(String prdTypCd) {
        this.prdTypCd = prdTypCd;
    }

    public String getAttr_1() {
        return attr_1;
    }

    public void setAttr_1(String attr_1) {
        this.attr_1 = attr_1;
    }

    public String getAttr_2() {
        return attr_2;
    }

    public void setAttr_2(String attr_2) {
        this.attr_2 = attr_2;
    }

    public String getAttr_3() {
        return attr_3;
    }

    public void setAttr_3(String attr_3) {
        this.attr_3 = attr_3;
    }

    public String getAttr_4() {
        return attr_4;
    }

    public void setAttr_4(String attr_4) {
        this.attr_4 = attr_4;
    }

    public String getAttr_5() {
        return attr_5;
    }

    public void setAttr_5(String attr_5) {
        this.attr_5 = attr_5;
    }

    public String getAttr_6() {
        return attr_6;
    }

    public void setAttr_6(String attr_6) {
        this.attr_6 = attr_6;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getFacings() {
        return facings;
    }

    public void setFacings(Integer facings) {
        this.facings = facings;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getDefaultPrtQty() {
        return defaultPrtQty;
    }

    public void setDefaultPrtQty(Integer defaultPrtQty) {
        this.defaultPrtQty = defaultPrtQty;
    }

    public Float getPrdWidth() {
        return prdWidth;
    }

    public void setPrdWidth(Float prdWidth) {
        this.prdWidth = prdWidth;
    }

    public Float getPrdHeight() {
        return prdHeight;
    }

    public void setPrdHeight(Float prdHeight) {
        this.prdHeight = prdHeight;
    }

    public Boolean getCheckProduct() {
        return checkProduct;
    }

    public void setCheckProduct(Boolean checkProduct) {
        this.checkProduct = checkProduct;
    }

    public PlanogramDetails(){

    }

    public PlanogramDetails(String homeLoc, String hierCd, String hierTypCd, String aisle, String bay, String shelfType, String shelf, String shelfSeq, String defaultStyle, String prdCd, String prdTypCd, String attr_1, String attr_2, String attr_3, String attr_4, String attr_5, String attr_6, Date startDate, Date endDate, Integer facings, Integer depth, Integer defaultPrtQty, Float prdWidth, Float prdHeight, Boolean checkProduct) {
        this.homeLoc = homeLoc;
        this.hierCd = hierCd;
        this.hierTypCd = hierTypCd;
        this.aisle = aisle;
        this.bay = bay;
        this.shelfType = shelfType;
        this.shelf = shelf;
        this.shelfSeq = shelfSeq;
        this.defaultStyle = defaultStyle;
        this.prdCd = prdCd;
        this.prdTypCd = prdTypCd;
        this.attr_1 = attr_1;
        this.attr_2 = attr_2;
        this.attr_3 = attr_3;
        this.attr_4 = attr_4;
        this.attr_5 = attr_5;
        this.attr_6 = attr_6;
        this.startDate = startDate;
        this.endDate = endDate;
        this.facings = facings;
        this.depth = depth;
        this.defaultPrtQty = defaultPrtQty;
        this.prdWidth = prdWidth;
        this.prdHeight = prdHeight;
        this.checkProduct = checkProduct;
    }
}
