package com.tsa.NCC_dte_punjab.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tsa.NCC_dte_punjab.models.InstituteModel;
import com.tsa.NCC_dte_punjab.network.NetworkCheck;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class ANORegActivity extends CustomActivity {
    private LinearLayout dobLayout;
    private LinearLayout appDOBLayout;

    private TextView dobTv;
    private TextView appDobTv;

    private EditText appointmentEt;
    private EditText qualificationET;
    private Spinner schoolSpinner;

    ////////////////////////////////////////////////////
    private Context context;
    private Button register;
    ///////////////////////////////////////////////////
    private ArrayList<InstituteModel> instituteModels;
    private int selectedSchoolPosition=-1;
    private Bundle bundle;
    ////////////////////////////////////////////////////

    private String regID="12355";
    private String insttId;
    private String dob;
    private String appoinment;
    private String appoinmentDate;
    private String educationQualifucation;
    private int schoolListSize=0;
    private String schoolName="";

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    //list of permissions
    ArrayList spinnerArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ano);
        context=ANORegActivity.this;
        bundle=getIntent().getExtras();

        if(bundle!=null){
            regID=bundle.getString("register_id");
        }
        init();

        dobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dobTv.setText(dayOfMonth + "/" + (1 + monthOfYear) + "/" + year);
                            }
                        }, mYear, mMonth, mDay).show();
            }
        });

        appDOBLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                appDobTv.setText(dayOfMonth + "/" + (1 + monthOfYear) + "/" + year);
                            }
                        }, mYear, mMonth, mDay).show();
            }
        });

    }

    private void init() {
        /////////////////////////////////////////////////////////////////////////////////
        instituteModels=new ArrayList<>();
        register=findViewById(R.id.register);
        ///////////////////////////////////////////////////////////////////////////////
        dobLayout = findViewById(R.id.dob_layout);
        appDOBLayout = findViewById(R.id.app_date_layout);
        ////////////////////////////////////////////////////////////////////////////////
        dobTv=findViewById(R.id.dob_tv);
        appDobTv=findViewById(R.id.app_dob_tv);
        appointmentEt=findViewById(R.id.appointment);
        qualificationET=findViewById(R.id.qualification);
        /////////////////////////////////////////////////////////////////////////////////

        schoolSpinner = findViewById(R.id.school_spinner);
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSchoolPosition=position-1;
                if(position==schoolListSize)
                {
                    final String[] localString = {""};
                    new MaterialDialog.Builder(context)
                            .title(R.string.input)
                            .inputRangeRes(2, 20, R.color.red)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                  spinnerArray.add(localString[0]);
                                  insttId="0";
                                  schoolName=localString[0];
                                }
                            })
                            .input(null, null, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    localString[0] =""+input;
                                }
                            }).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                educationQualifucation= ""+qualificationET.getText();
                dob=""+dobTv.getText();
                appoinmentDate=""+appDobTv.getText();
                appoinment=""+appointmentEt.getText();
                insttId = instituteModels.get(selectedSchoolPosition).getId();
                getRegistered();
            }
        });

        if (NetworkCheck.isNetworkAvailable(context)) {
            getSchoolList();
        } else {
            FancyToast.makeText(context,"No Network Available",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        }
    }

    public void getRegistered() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "add_ano_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg=json.getString("msg");
                            Log.e("json",json.toString());
                            if (status.equals("0")) {

                               startActivity(new Intent(ANORegActivity.this,HomeActivity.class));
                            }
                            Toast.makeText(context,""+msg, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("register_id", regID);
                params.put("instt_id", "" + insttId);
                params.put("dob", "" +dob);
                params.put("appoinment", "" + appoinment);
                params.put("appoinment_date", "" + appoinmentDate);
                params.put("education_qualifucation", "" + educationQualifucation);
                params.put("school_college", "" + schoolName);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getSchoolList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "institute_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("data");
                                 spinnerArray = new ArrayList();
                                 spinnerArray.add("--Select--");
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    InstituteModel instituteModel = new InstituteModel();
                                    instituteModel.setId(localJson.getString("instt_id"));
                                    instituteModel.setInsttName(localJson.getString("instt_name"));
                                    spinnerArray.add(localJson.getString("instt_name"));
                                    instituteModels.add(instituteModel);
                                }
                                spinnerArray.add("Others");
                                schoolListSize=spinnerArray.size();

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerArray);
                                schoolSpinner.setAdapter(dataAdapter);
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
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(context,SelectRegActivity.class));
        finish();
    }

}
