package com.tsa.NCC_dte_punjab.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.activities.CustomActivity;

public class Test extends CustomActivity {

    private TextView firstName;
    private TextView fFirstName;
    private TextView mFirstName;
    private TextView villCityTV, landMark, pincodeTV;
    private TextView mobile, email;
    private TextView nearRailway, nearPolice;
    private TextView dobTv;
    private TextView nation;
    private TextView state;
    private TextView district;
    private TextView bloodGP;

    private TextView classSp;
    private TextView identityMark1;
    private TextView identityMark2;
    private TextView marks;

    private TextView circumstances;
    private TextView dismissed;

    private TextView battalian;
    private TextView institute;
    private TextView wingSp;
    private TextView hqList;

    private TextView kinName;
    private TextView kinRelation;
    private TextView kinNumber;
    private TextView kinAddress;
    private TextView schoolTV;
    private TextView streamSp;

    private TextView enrolledTV;
    private TextView willingTV;
    private TextView convictedTV;

    private TextView bankTV;
    private TextView ifscTV;
    private TextView accNoTV;
    private TextView aadharTV;
    private TextView panNoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadet_enrollment_form);
        init();
    }

    private void init() {
        firstName = findViewById(R.id.cadet_name_tv);
        fFirstName = findViewById(R.id.p_name_tv);
        mFirstName = findViewById(R.id.m_name_tv);
        villCityTV = findViewById(R.id.vill_city_tv);
        landMark = findViewById(R.id.landmark_tv);
        
        pincodeTV = findViewById(R.id.pin_tv);
        mobile = findViewById(R.id.mobile_tv);
        email = findViewById(R.id.email_tv);
        
        //nearRailway = findViewById(R.id.nearest_police_tv);
        //nearPolice = findViewById(R.id.nearest_police_tv);
        dobTv = findViewById(R.id.dob_tv);
        nation = findViewById(R.id.nationality_tv);
        state = findViewById(R.id.state_tv);

        district = findViewById(R.id.district_tv);
        bloodGP = findViewById(R.id.blood_gp_tv);

        classSp = findViewById(R.id.class_tv);
        identityMark1 = findViewById(R.id.ide_mark1_tv);
        identityMark2 = findViewById(R.id.ide_mark2_tv);
        marks = findViewById(R.id.marks_tv);

        //circumstances = findViewById(R.id.circumstances_tv);
        dismissed = findViewById(R.id.dismiss_tv);

        battalian = findViewById(R.id.battalian_tv);
        institute = findViewById(R.id.institute_tv);
        wingSp = findViewById(R.id.wing_tv);
        //hqList = findViewById(R.id.hq_tv);

        //kinName = findViewById(R.id.kin_tv);
        kinRelation = findViewById(R.id.kin_relation_tv);
        kinNumber = findViewById(R.id.kin_number_tv);
        kinAddress = findViewById(R.id.kin_address_tv);
        schoolTV = findViewById(R.id.school_tv);
        streamSp = findViewById(R.id.stream_tv);

        //enrolledTV = findViewById(R.id.enrollment_tv);
        willingTV = findViewById(R.id.willing_tv);
        convictedTV = findViewById(R.id.convicted_tv);

        bankTV = findViewById(R.id.bank_name_tv);
        ifscTV = findViewById(R.id.ifsc_tv);
        accNoTV = findViewById(R.id.acc_no_tv);
        aadharTV = findViewById(R.id.aadhaar_tv);
        panNoTV = findViewById(R.id.pan_tv);

    }

}
    
