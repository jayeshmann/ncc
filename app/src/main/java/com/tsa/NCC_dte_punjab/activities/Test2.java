
package com.tsa.NCC_dte_punjab.activities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Test2 {

    @SerializedName("battalion_id")
    private String mBattalionId;
    @SerializedName("battalion_name")
    private String mBattalionName;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("user_type")
    private String mUserType;

    public String getBattalionId() {
        return mBattalionId;
    }

    public void setBattalionId(String battalionId) {
        mBattalionId = battalionId;
    }

    public String getBattalionName() {
        return mBattalionName;
    }

    public void setBattalionName(String battalionName) {
        mBattalionName = battalionName;
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

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}
