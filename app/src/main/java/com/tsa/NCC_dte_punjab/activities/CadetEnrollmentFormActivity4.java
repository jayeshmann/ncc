package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.CadetDetailsModels;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;
import com.tsa.NCC_dte_punjab.validation.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class CadetEnrollmentFormActivity4 extends CustomActivity {
    /////////////////////////////
    private EditText bankEt;
    private EditText ifscEt;
    private EditText accNoEt;
    /*private EditText aadharEt;*/   ///edited gaurav
    private EditText panNoEt;
    private Context context;
    private Button nextBT;
    ////////////////////////////
    private CadetDetailsModels localCadetdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollment_form4);
        context = CadetEnrollmentFormActivity4.this;

        if (GLOBAL.cadetFormModel!=null)
        {
            localCadetdetails= GLOBAL.cadetFormModel;
        } else if (GLOBAL.navigationModel.getIfscCode() != null) {
            localCadetdetails= GLOBAL.navigationModel;
        }

        init();
    }

    private void init() {
        ///////////////////////////////////////////////
        bankEt = findViewById(R.id.bank_name_et);
        ifscEt = findViewById(R.id.ifsc_et);
        accNoEt = findViewById(R.id.acc_no_et);
        panNoEt = findViewById(R.id.pan_et);
       /* aadharEt = findViewById(R.id.aadhar_et);*/   //gaurav edited
        ///////////////////////////////////////////////
        if (localCadetdetails!=null)
        {
            bankEt.setText(localCadetdetails.getBankName());
            ifscEt.setText(localCadetdetails.getIfscCode());
            accNoEt.setText(localCadetdetails.getAccNo());
            panNoEt.setText(localCadetdetails.getPanNo());
           /* aadharEt.setText(localCadetdetails.getAadhaarNo());*/ //edited gaurav
        }
        nextBT = findViewById(R.id.next_button);

        nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation4()) {
                    nextBT.setEnabled(false);
                    registration4();
                }
                else
                    Toast.makeText(context, "Fill All Field Properly", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (GLOBAL.navigationModel != null) {
            Intent intent = new Intent(CadetEnrollmentFormActivity4.this, CadetEnrollmentFormActivity3.class);
            startActivity(intent);
            finish();
        }
    }

    public void registration4() {
        nextBT.setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/cadreRegistrationAPIStep-4_new.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        nextBT.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                Intent intent = new Intent(CadetEnrollmentFormActivity4.this, CadetEnrollmentFormActivity5.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        nextBT.setEnabled(true);
                        FancyToast.makeText(context,"Some issue in loading", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                if ( GLOBAL.cadetID!=null)
                    params.put("cadet_id", GLOBAL.cadetID);
                else if (GLOBAL.navigationModel.getCadetId() != null)
                    params.put("cadet_id", GLOBAL.navigationModel.getCadetId());
                else if (GLOBAL.cadetFormModel.getCadetId() != null)
                    params.put("cadet_id", GLOBAL.cadetFormModel.getCadetId());
                else
                    params.put("cadet_id", GLOBAL.cadetModel.getCadetId());
                params.put("bank_name", "" + bankEt.getText());
                params.put("ifsc_code", "" + ifscEt.getText());
                params.put("acc_no", "" + accNoEt.getText());
               /* params.put("aadhaar_no", "" + aadharEt.getText());*/ ///gaurav edited
                params.put("pan_no", "" + panNoEt.getText());

                GLOBAL.navigationModel.setBankName(params.get("bank_name"));
                GLOBAL.navigationModel.setIfscCode(params.get("ifsc_code"));
                GLOBAL.navigationModel.setAccNo(params.get("acc_no"));
                /*GLOBAL.navigationModel.setAadhaarNo(params.get("aadhaar_no"));*///gaurav edited
                GLOBAL.navigationModel.setPanNo(params.get("pan_no"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean checkValidation4() {
        boolean ret = true;
        if (!Validation.hasText(ifscEt)) ret = false;
        if (!Validation.hasText(accNoEt)) ret = false;
        if (!Validation.hasText(bankEt)) ret = false;
       /* if (!Validation.hasText(aadharEt)) ret = false;*/    //gaurav edited
        return ret;
    }
}
