package com.tsa.NCC_dte_punjab.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.JoinAsModel;
import com.tsa.NCC_dte_punjab.models.RegestrationModel;
import com.tsa.NCC_dte_punjab.network.NetworkCheck;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;
import com.tsa.NCC_dte_punjab.validation.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class DashboardActivity extends CustomActivity {
    private Context context;
    private EditText nameET;
    private EditText emailET;
    private EditText mobileET;
    private EditText aadharET;
    private RegestrationModel regestrationModel;
    private Spinner categorySpinner;
    private int selectedPos = 0;
    private ArrayList<JoinAsModel> joinAsModels;
    private String nccJoinType;
    private Button submit;
    private TextView allreadyLogedFilledTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context = DashboardActivity.this;
        init();
    }

    private void init() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        nameET = findViewById(R.id.name_et);
        nameET.setHintTextColor(getResources().getColor(R.color.red));
        emailET = findViewById(R.id.email_et);
        emailET.setHintTextColor(getResources().getColor(R.color.red));
        mobileET = findViewById(R.id.mobile_et);
        mobileET.setHintTextColor(getResources().getColor(R.color.red));
        aadharET = findViewById(R.id.aadhar_et);
        aadharET.setHintTextColor(getResources().getColor(R.color.red));
        submit = findViewById(R.id.register);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        allreadyLogedFilledTV = findViewById(R.id.allready_filled_tv);
        allreadyLogedFilledTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title(R.string.input)
                        .inputRangeRes(2, 20, R.color.red)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent intent = new Intent(DashboardActivity.this, CadetActivity.class);
                                intent.putExtra("register_id", "45");
                                startActivity(intent);
                                finish();
                            }
                        })
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                            }
                        }).show();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        regestrationModel = new RegestrationModel();
        /////////////////////////////////////////////
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkCheck.isNetworkAvailable(context)) {
                    if (checkValidation()) {
                        regestrationModel.setName("" + nameET.getText());
                        regestrationModel.setMobile("" + mobileET.getText());
                        regestrationModel.setEmail("" + emailET.getText());
                        regestrationModel.setAadhaar("" + aadharET.getText());
                        regestration(regestrationModel);
                    }
                    else
                    {
                     FancyToast.makeText(context,"Please Fill All Fields And Select Join Type",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    }
                } else {
                    FancyToast.makeText(context,"No Network Available",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList spinnerArray=new ArrayList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerArray);
        spinnerArray.add("--Select--");
        spinnerArray.add("Cadet");
        spinnerArray.add("ANO");
        spinnerArray.add("Institute");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        categorySpinner = findViewById(R.id.category);
        categorySpinner.setAdapter(dataAdapter);
        joinAsModels = new ArrayList<>();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (NetworkCheck.isNetworkAvailable(context))
            getJoinAs();
        else
            FancyToast.makeText(context,"No Network Available",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Drop down layout style - list view with radio button
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
                if (!joinAsModels.isEmpty() && position != 0)
                    nccJoinType = joinAsModels.get(selectedPos - 1).getNccJointypeId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    public void goHome() {
        if (selectedPos == 1) {
            regestrationModel.setName("" + nameET.getText());
            regestrationModel.setMobile("" + mobileET.getText());
            regestrationModel.setEmail("" + emailET.getText());
            regestrationModel.setAadhaar("" + aadharET.getText());

            Intent intent = new Intent(DashboardActivity.this, CadetActivity.class);
            intent.putExtra("name", "" + regestrationModel.getName());
            intent.putExtra("email", "" + regestrationModel.getEmail());
            intent.putExtra("mobile", "" + regestrationModel.getMobile());
            intent.putExtra("aadhaar_no", "" + regestrationModel.getAadhaar());
            startActivity(intent);
            finish();
        }

    }

    public void regestration(final RegestrationModel regestrationModel) {
        final ProgressDialog progressBar=new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "sign_up.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.dismiss();
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");
                            Log.e("json", json.toString());
                            if (status.equals("0")) {

                                if (selectedPos == 1) {
                                    Intent intent = new Intent(DashboardActivity.this, CadetActivity.class);
                                    intent.putExtra("register_id", json.getString("register_id"));
                                    startActivity(intent);
                                    finish();
                                } else if (selectedPos == 2) {
                                    Intent intent = new Intent(DashboardActivity.this, ANORegActivity.class);
                                    intent.putExtra("register_id", json.getString("register_id"));
                                    startActivity(intent);
                                    finish();
                                } else if (selectedPos == 3) {
                                    Intent intent = new Intent(DashboardActivity.this,
                                            InstituteFormActivity.class);
                                    intent.putExtra("register_id", json.getString("register_id"));
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            FancyToast.makeText(context,""+msg,FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressBar.dismiss();
                        FancyToast.makeText(context,"Some Issue ",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("name", "" + regestrationModel.getName());
                params.put("email", "" + regestrationModel.getEmail());
                params.put("mobile", "" + regestrationModel.getMobile());
                params.put("aadhaar_no", "" + regestrationModel.getAadhaar());
                params.put("ncc_jointype_id", "" + nccJoinType);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getJoinAs() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "join_type_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("data");
                                ArrayList spinnerArray = new ArrayList();
                                spinnerArray.add("--Select--");
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    JoinAsModel joinAsModel = new JoinAsModel();
                                    joinAsModel.setJoinType(localJson.getString("join_type"));
                                    joinAsModel.setNccJointypeId(localJson.getString("ncc_jointype_id"));
                                    spinnerArray.add(localJson.getString("join_type"));
                                    joinAsModels.add(joinAsModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerArray);
                                categorySpinner.setAdapter(dataAdapter);
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context,"Some Issue in Loading",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("name", "" + regestrationModel.getName());
                params.put("email", "" + regestrationModel.getEmail());
                params.put("mobile", "" + regestrationModel.getMobile());
                params.put("aadhaar_no", "" + regestrationModel.getAadhaar());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(nameET)) ret = false;
        if (!Validation.isEmailAddress(emailET, true)) ret = false;
        if (!Validation.isPhoneNumber(mobileET, true)) ret = false;
        if (!Validation.hasText(aadharET)) ret = false;
        if (selectedPos==0)ret=false;
        return ret;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,HomeActivity.class));
        finish();
    }
}
