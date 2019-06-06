
package com.tsa.NCC_dte_punjab.models;

public class ANOModel {

    private String mBattalionId;
    private String mInsttId;
    private String mInsttName;
    private String mMsg;
    private String mStatus;
    private String anoID;
    private String userType;

    public String getAnoID() {
        return anoID;
    }

    public void setAnoID(String anoID) {
        this.anoID = anoID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBattalionId() {
        return mBattalionId;
    }

    public void setBattalionId(String battalionId) {
        mBattalionId = battalionId;
    }

    public String getInsttId() {
        return mInsttId;
    }

    public void setInsttId(String insttId) {
        mInsttId = insttId;
    }

    public String getInsttName() {
        return mInsttName;
    }

    public void setInsttName(String insttName) {
        mInsttName = insttName;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
