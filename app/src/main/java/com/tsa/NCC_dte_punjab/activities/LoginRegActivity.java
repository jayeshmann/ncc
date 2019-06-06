package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.tsa.NCC_dte_punjab.network.NetworkCheck;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class LoginRegActivity extends CustomActivity {
    private Context context;

    private Button submit;
    private EditText userET;
    private EditText passwordET;


    private CadetDetailsModels cadetModel;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_register);


        context = LoginRegActivity.this;
        init();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void init() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        submit = findViewById(R.id.submit);
        userET = findViewById(R.id.user_name_et);
        passwordET = findViewById(R.id.password_et);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkCheck.isNetworkAvailable(context)) {
                        regLogin(""+userET.getText(),""+passwordET.getText());
                        }
                     else {
                        FancyToast.makeText(context, "Network Not Available", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                }
            });
        }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void regLogin(final String userName,final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/loginRegCheck.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String currStep=json.getString("curr_step");
                            String prevStep=json.getString("prev_step");
                            String msg=json.getString("msg");
                            Intent intent=new Intent();

                            if (currStep.equals("1"))
                            {
                                intent.setClass(context,CadetEnrollmentFormActivity2.class);
                            }
                            else if (currStep.equals("2"))
                            {
                                intent.setClass(context,CadetEnrollmentFormActivity3.class);
                            }
                            else if (currStep.equals("3"))
                            {
                                intent.setClass(context,CadetEnrollmentFormActivity4.class);
                            }
                            else if (currStep.equals("4"))
                            {
                                intent.setClass(context,CadetEnrollmentFormActivity5.class);
                            }
                            FancyToast.makeText(context,msg,FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();

                           ///////////////////////////////////////////////////////////////////////////////////////
                            cadetModel = new CadetDetailsModels();
                            if (status.equals("0")) {
                                //////////////////////////////////////////////////////////////////////////////
                                JSONObject localJson1 = json.getJSONObject("form1_data");
                                JSONObject localJson2 = json.getJSONObject("form2_data");
                                JSONObject localJson3 = json.getJSONObject("form3_data");
                                JSONObject localJson4 = json.getJSONObject("form4_data");
                                //////////////////////////////////////////////////////////////////////////////
                                cadetModel.newBuilder().cadetId(localJson1.getString("cadet_id"));
                                cadetModel.setName(localJson1.getString("name"));
                                cadetModel.setEmail(localJson1.getString("email"));
                                cadetModel.setMobile(localJson1.getString("mobile"));
                                cadetModel.setNationality(localJson1.getString("nationality"));
                                cadetModel.setParentsAnnualIncome(localJson1.getString("parents_annual_income"));
                                cadetModel.setDob(localJson1.getString("dob"));
                                cadetModel.setFatherName(localJson1.getString("father_name"));
                                cadetModel.setMotherName(localJson1.getString("mother_name"));
                                cadetModel.setLandmark(localJson1.getString("landmark"));
                                cadetModel.setStateId(localJson1.getString("state_name"));
                                cadetModel.setDistrictName(localJson1.getString("district_name"));
                                cadetModel.setCity(localJson1.getString("city"));
                                cadetModel.setPin(localJson1.getString("pin"));
                                cadetModel.setBloodGroup(localJson1.getString("blood_group"));
                                cadetModel.setGender(localJson1.getString("gender"));
                                cadetModel.setNearestRailwayStation(localJson1.getString("nearest_railway_station"));
                                cadetModel.setNearestPoliceStation(localJson1.getString("nearest_police_station"));
                                cadetModel.setProfileImage(localJson1.getString("profile_image"));
                                ////////////////////////////////////////////////////////////////////////////////////////
                                cadetModel.setMarks(localJson2.getString("marks"));
                                cadetModel.setIdentificationMark1(localJson2.getString("identification_mark1"));
                                cadetModel.setIdentificationMark2(localJson2.getString("identification_mark2"));
                                cadetModel.setClassName(localJson2.getString("class_name"));
                                ////////////////////////////////////////////////////////////////////////////////////////
                                //cadetModel.setHqId(localJson3.getString("hq_id"));
                                cadetModel.setConvicted(localJson3.getString("convicted"));
                                cadetModel.setConvictionCircumstances(localJson3.getString("conviction_circumstances"));
                                cadetModel.setConvictionDocuments(localJson3.getString("conviction_documents"));
                                cadetModel.setSchoolName(localJson3.getString("school_name"));
                                cadetModel.setWillingToBeEnrolled(localJson3.getString("willing_to_be_enrolled"));
                                cadetModel.setEnrolledErlier(localJson3.getString("enrolled_erlier"));
                                cadetModel.setDismissErlier(localJson3.getString("dismiss_erlier"));
                                cadetModel.setKinName(localJson3.getString("kin_name"));
                                cadetModel.setKinAddress(localJson3.getString("kin_address"));
                                cadetModel.setKinRelationship(localJson3.getString("kin_relationship"));
                                cadetModel.setKinTelephone(localJson3.getString("kin_telephone"));
                                ///////////////////////////////////////////////////////////////////////////////////////////////
                                cadetModel.setHqName(localJson3.getString("hq_name"));
                                cadetModel.setBattalionName(localJson3.getString("battalion_name"));
                                cadetModel.setInsttName(localJson3.getString("instt_name"));
                                cadetModel.setStream(localJson3.getString("stream"));
                                cadetModel.setNccYear(localJson3.getString("ncc_year"));
                                ///////////////////////////////////////////////////////////////////////
                                cadetModel.setBankName(localJson4.getString("bank_name"));
                                cadetModel.setAccNo(localJson4.getString("acc_no"));
                                cadetModel.setIfscCode(localJson4.getString("ifsc_code"));
                                cadetModel.setPanNo(localJson4.getString("pan_no"));
                                cadetModel.setAadhaarNo(localJson4.getString("aadhaar_no"));
                                ///////////////////////////////////////////////////////////////////////////
                                GLOBAL.cadetFormModel=cadetModel;
                                startActivity(intent);
                            } else {
                                FancyToast.makeText(context,"Some Issue in Login",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context,volleyError.getMessage(),FancyToast.LENGTH_LONG,FancyToast.WARNING,true);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("unique_login_id", userName);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void goToAlreadyReg(View view)
    {
        startActivity(new Intent(LoginRegActivity.this, CadetEnrollmentFormActivity.class));
        finish();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginRegActivity.this, HomeActivity.class));
        finish();
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////


