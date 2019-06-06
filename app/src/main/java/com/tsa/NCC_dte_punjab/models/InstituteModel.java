
package com.tsa.NCC_dte_punjab.models;

import com.google.gson.annotations.SerializedName;

public class InstituteModel {
    @SerializedName("collg_school_type")
    private String mCollgSchoolType;
    @SerializedName("from_date")
    private String mFromDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("instt_name")
    private String mInsttName;
    @SerializedName("to_date")
    private String mToDate;
    @SerializedName("valid")
    private String valid;
    @SerializedName("instt_id")
    private String mInsttID;

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getmInsttID() {
        return mInsttID;
    }

    public void setmInsttID(String mInsttID) {
        this.mInsttID = mInsttID;
    }

    public String getCollgSchoolType() {
        return mCollgSchoolType;
    }

    public void setCollgSchoolType(String collgSchoolType) {
        mCollgSchoolType = collgSchoolType;
    }

    public String getFromDate() {
        return mFromDate;
    }

    public void setFromDate(String fromDate) {
        mFromDate = fromDate;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getInsttName() {
        return mInsttName;
    }

    public void setInsttName(String insttName) {
        mInsttName = insttName;
    }

    public String getToDate() {
        return mToDate;
    }

    public void setToDate(String toDate) {
        mToDate = toDate;
    }

}
