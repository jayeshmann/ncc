package com.tsa.NCC_dte_punjab.activities;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.tsa.NCC_dte_punjab.R;

public class DoumentConfirmationActivity extends CustomActivity {
    private TextView uploadDoc;
    private Button nextBt;
    private CheckBox acceptCB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doument_confirmation);
        init();
    }

    private void init() {
        uploadDoc=findViewById(R.id.doc_upload_tv);
        nextBt=findViewById(R.id.countinue_button);
        acceptCB=findViewById(R.id.accept_cb);
        uploadDoc.setText(Html.fromHtml(getString(R.string.upload_doc)));
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acceptCB.isChecked()){
                startActivity(new Intent(DoumentConfirmationActivity.this,CadetEnrollmentFormActivity.class));
                finish();}
                else
                {
                    Toast.makeText(DoumentConfirmationActivity.this, "Accept Conditions To Proceed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(DoumentConfirmationActivity.this,SelectRegActivity.class);
        startActivity(intent);
        finish();
    }
}
