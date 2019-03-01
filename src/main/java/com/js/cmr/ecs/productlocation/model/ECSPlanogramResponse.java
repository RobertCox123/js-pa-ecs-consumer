package com.js.cmr.ecs.productlocation.model;

public class ECSPlanogramResponse {
    Status status;
    String planoID, aisle, bay, shelfType, shelf, shelfSeq;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPlanoID() {
        return planoID;
    }

    public void setPlanoID(String planoID) {
        this.planoID = planoID;
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

    public ECSPlanogramResponse(Status status, String planoID, String aisle, String bay, String shelfType, String shelf, String shelfSeq) {
        this.status = status;
        this.planoID = planoID;
        this.aisle = aisle;
        this.bay = bay;
        this.shelfType = shelfType;
        this.shelf = shelf;
        this.shelfSeq = shelfSeq;
    }

    public ECSPlanogramResponse() {

    }
}
