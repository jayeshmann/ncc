
package com.tsa.NCC_dte_punjab.models;

import com.google.gson.annotations.SerializedName;

public class StateListModel {

    @SerializedName("id")
    private String mId;
    @SerializedName("state_name")
    private String mStateName;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getStateName() {
        return mStateName;
    }

    public void setStateName(String stateName) {
        mStateName = stateName;
    }

}
