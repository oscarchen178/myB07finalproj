package com.example.cscb07final;

public class Order {
    public String OID;
    public String UID;
    public String SID;
    public boolean isComplete;

    public Order() {

    }

    public Order(String OID, String UID, String SID) {
        this.OID = OID;
        this.UID = UID;
        this.SID = SID;
        isComplete = false;
    }

    public Order(String OID, String UID, String SID, boolean isComplete) {
        this.OID = OID;
        this.UID = UID;
        this.SID = SID;
        this.isComplete = isComplete;
    }

    public void setComplete() {
        isComplete = true;
    }
}
