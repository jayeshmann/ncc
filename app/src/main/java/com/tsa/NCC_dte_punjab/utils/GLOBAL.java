package com.tsa.NCC_dte_punjab.utils;

import com.tsa.NCC_dte_punjab.models.ANOListModel;
import com.tsa.NCC_dte_punjab.models.ANOModel;
import com.tsa.NCC_dte_punjab.models.CadetDetailsModels;
import com.tsa.NCC_dte_punjab.models.CadetModel;
import com.tsa.NCC_dte_punjab.models.InstituteListModel;

/**
 * Created by Akhil Tripathi on 28-12-2017.
 */

public class GLOBAL {
    public static int tabSelected=0;
    public static final String BASE_URL= "https://www.nccdtepunjab.in/API/";
    public static boolean isRegistered=false;
    ////////////////////////////////////////////////////////////////////////////////////
    public static final int DG_LOGIN=1;
    public static final int ADG_LOGIN=2;
    public static final int GP_LOGIN=3;
    public static final int CO_LOGIN=4;
    public static final int ANO_LOGIN=5;
    public static final int CADET_LOGIN=6;
    public static final int PRINCIPAL_LOGIN=7;
    public static int LOGIN_TYPE=0;
    public static int LOGIN_TYPE_ID=0;
    public static String USER_TYPE="";
    /////////////////////////////////////////////////////////////////////////////////////
    public static CadetDetailsModels cadetModel;
    public static CadetDetailsModels cadetFormModel;
    public static CadetDetailsModels navigationModel=new CadetDetailsModels();
    public static ANOListModel anoModel;
    public static InstituteListModel instituteanoModel;
    public static String cadetID;
    public static String cadetName;
    public static String docURL;
    //////////////////////////////////////////////////////////////////////////////////////
}
