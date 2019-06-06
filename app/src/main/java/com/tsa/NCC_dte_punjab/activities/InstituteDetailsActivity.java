package com.tsa.NCC_dte_punjab.activities;

import android.content.Intent;
import android.os.Bundle;

import com.tsa.NCC_dte_punjab.R;


public class InstituteDetailsActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_details);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(InstituteDetailsActivity.this,SelectRegActivity.class);
        startActivity(intent);
        finish();
    }
}
