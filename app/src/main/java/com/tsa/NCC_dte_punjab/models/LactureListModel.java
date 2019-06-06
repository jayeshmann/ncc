
package com.tsa.NCC_dte_punjab.models;

public class LactureListModel {

    private String mGeoStatus;
    private String mImgStatus;
    private String mLat;
    private String mLdate;
    private String mLid;
    private String mLname;
    private String mLng;

    public String getWing() {
        return wing;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String wing;
    private String address;


    //checking Image and geotag is available in DATABASE or Not
    private boolean isImageUploaded;
    private boolean isGeoTgged;

    public boolean isImageCaptured() {
        return isImageCaptured;
    }

    public void setImageCaptured(boolean imageCaptured) {
        isImageCaptured = imageCaptured;
    }

    public boolean isGeoTagApp() {
        return isGeoTagApp;
    }

    public void setGeoTagApp(boolean geoTagApp) {
        isGeoTagApp = geoTagApp;
    }

    public String getImageCapturedApp() {
        return imageCaptured;
    }

    public void setImageCapturedApp(String imageCaptured) {
        this.imageCaptured = imageCaptured;
    }

    //checking Image and geotag is captured or Not
    private boolean isImageCaptured;
    private boolean isGeoTagApp;

    private String imageCaptured;

    public boolean isImageUploaded() {
        return isImageUploaded;
    }

    public void setImageUploaded(boolean imageUploaded) {
        isImageUploaded = imageUploaded;
    }

    public boolean isGeoTgged() {
        return isGeoTgged;
    }

    public void setGeoTgged(boolean geoTgged) {
        isGeoTgged = geoTgged;
    }

    public String getGeoStatus() {
        return mGeoStatus;
    }

    public void setGeoStatus(String geoStatus) {
        mGeoStatus = geoStatus;
    }

    public String getImgStatus() {
        return mImgStatus;
    }

    public void setImgStatus(String imgStatus) {
        mImgStatus = imgStatus;
    }

    public String getLat() {
        return mLat;
    }

    public void setLat(String lat) {
        mLat = lat;
    }

    public String getLdate() {
        return mLdate;
    }

    public void setLdate(String ldate) {
        mLdate = ldate;
    }

    public String getLid() {
        return mLid;
    }

    public void setLid(String lid) {
        mLid = lid;
    }

    public String getLname() {
        return mLname;
    }

    public void setLname(String lname) {
        mLname = lname;
    }

    public String getLng() {
        return mLng;
    }

    public void setLng(String lng) {
        mLng = lng;
    }

}
