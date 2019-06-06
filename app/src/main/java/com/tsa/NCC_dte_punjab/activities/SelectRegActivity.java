package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;

public class SelectRegActivity extends CustomActivity {

    private RadioButton cadetRb;
    private RadioButton anoRb;
    private RadioButton instituteRb;

    private Button continueBt;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_reg);


        init();
    }

    private void init() {
        context=SelectRegActivity.this;
        cadetRb=findViewById(R.id.cadet_rb);
        anoRb=findViewById(R.id.ano_rb);
        instituteRb=findViewById(R.id.institute_rb);
        continueBt=findViewById(R.id.countinue_button);



        continueBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                if (cadetRb.isChecked())
                {
                    intent.setClass(context,LoginRegActivity.class);
                }
                else if(anoRb.isChecked())
                {
                    intent.setClass(context,ANOFormActivity.class);
                }
                else if(instituteRb.isChecked())
                {
                    intent.setClass(context,InstituteFormActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(context,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
