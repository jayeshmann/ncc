package com.tsa.NCC_dte_punjab.models;

/**
 * Created by Akhil Tripathi on 19-03-2018.
 */

public class RegestrationModel {
    private String name;
    private String email;
    private String mobile;
    private String aadhaar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }
}
