package com.tsa.NCC_dte_punjab.models;

/**
 * Created by Akhil Tripathi on 24-03-2018.
 */

public class CandidateListModel {
    public String getGroupHQ() {
        return groupHQ;
    }

    public void setGroupHQ(String groupHQ) {
        this.groupHQ = groupHQ;
    }

    public String getBattalion() {
        return battalion;
    }

    public void setBattalion(String battalion) {
        this.battalion = battalion;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAadhaar() {
        return Aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        Aadhaar = aadhaar;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getCamp() {
        return Camp;
    }

    public void setCamp(String camp) {
        Camp = camp;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String groupHQ;
    private String battalion;
    private String institute;
    private String name;
    private String Email;
    private String Mobile;
    private String Aadhaar;
    private String maritalStatus;
    private String Gender;
    private String DOB;
    private String regNo;
    private String className;
    private String Department;
    private String Camp;
    private String Result;
    private String status;

    public CandidateListModel(String groupHQ, String battalion, String institute, String name) {
        this.groupHQ = groupHQ;
        this.battalion = battalion;
        this.institute = institute;
        this.name = name;
    }

}
