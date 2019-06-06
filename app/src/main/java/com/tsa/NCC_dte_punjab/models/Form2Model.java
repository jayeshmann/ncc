package com.tsa.NCC_dte_punjab.models;

import java.io.Serializable;

public class Form2Model implements Serializable {

    private String mClass;
    private String mMarks;
    private String mIdentificationMark1;
    private String mIdentificationMark2;

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }

    public String getmMarks() {
        return mMarks;
    }

    public void setmMarks(String mMarks) {
        this.mMarks = mMarks;
    }

    public String getmIdentificationMark1() {
        return mIdentificationMark1;
    }

    public void setmIdentificationMark1(String mIdentificationMark1) {
        this.mIdentificationMark1 = mIdentificationMark1;
    }

    public String getmIdentificationMark2() {
        return mIdentificationMark2;
    }

    public void setmIdentificationMark2(String mIdentificationMark2) {
        this.mIdentificationMark2 = mIdentificationMark2;
    }
}
