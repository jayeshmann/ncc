package com.tsa.NCC_dte_punjab;

public class LoginResponseModel {


    /**
     * status : 0
     * user_type : co
     * battalion_id : 1
     * battalion_name : 5 Haryana BN NCC
     * msg : Success
     */

    private String status;
    private String user_type;
    private String battalion_id;
    private String battalion_name;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getBattalion_id() {
        return battalion_id;
    }

    public void setBattalion_id(String battalion_id) {
        this.battalion_id = battalion_id;
    }

    public String getBattalion_name() {
        return battalion_name;
    }

    public void setBattalion_name(String battalion_name) {
        this.battalion_name = battalion_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
