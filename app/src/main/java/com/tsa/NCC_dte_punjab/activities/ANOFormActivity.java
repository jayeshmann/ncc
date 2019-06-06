package com.tsa.NCC_dte_punjab.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.BattalionModel;
import com.tsa.NCC_dte_punjab.models.CityModel;
import com.tsa.NCC_dte_punjab.models.HQModel;
import com.tsa.NCC_dte_punjab.models.InstituteModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;
import com.tsa.NCC_dte_punjab.validation.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class ANOFormActivity extends CustomActivity {
    ///////////////////////////////////////////////////////////////////////
    ArrayList<HQModel> hqModelArrayList = new ArrayList<>();
    ArrayList<BattalionModel> battalionArray = new ArrayList<>();
    ArrayList<InstituteModel> instituteModels = new ArrayList<>();
    ////////////////////////////////////////////////////////////////////////
    private Spinner battalian;
    private Spinner institute;
    private Spinner wingSp;
    private Spinner hqList;

    private EditText firstName, midName, lastName;
    private EditText fFirstName, fMidName, fLastName;
    private EditText appointmentEv,postOffice;
    private LinearLayout appDOBLayout;
    private TextView dobTv;

    private EditText villCityEt;
    private EditText mobile, email,education;
    private EditText served_inforces;

    private Spinner nation;
    private Spinner state;
    private Spinner district;
    private Gson gson;


    ///////////////////////////////////////////////////////////////////////
    ArrayList<CityModel> cityArray = new ArrayList();
    ////////////////////////////////////////////////////////////////////////

    private Button nextBT;
    private Context context;
    private RadioButton appointedRb;
    private RadioButton willingRb;
    private RadioButton serveRb;
    private RadioButton obeyRb;
    private RadioButton marriedRb;
    /////////////////////////////////////////////////////////////////////////

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    /////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ano_form);
        context=ANOFormActivity.this;
        init();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ANOFormActivity.this,SelectRegActivity.class);
        startActivity(intent);
        finish();
    }

    private void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        ///////////////////////////////////////////////////
        firstName = findViewById(R.id.first_name_et);
        midName = findViewById(R.id.mid_name_et);
        lastName = findViewById(R.id.last_name_et);
        //////////////////////////////////////////////////////
        fFirstName = findViewById(R.id.p_first_name_et);
        fMidName = findViewById(R.id.p_mid_name_et);
        fLastName = findViewById(R.id.p_last_name_et);
        served_inforces=findViewById(R.id.served_inforces);

        //////////////////////////////////////////////////////
        education=findViewById(R.id.educationb_et);
        appointmentEv=findViewById(R.id.appointment_et);

        ////////////////////////////////////////////////////////
        email = findViewById(R.id.email_et);
        mobile = findViewById(R.id.mobile_no_et);
        villCityEt = findViewById(R.id.vill_city_et);
        postOffice=findViewById(R.id.post_office_et);
        ////////////////////////////////////////////
        nation = findViewById(R.id.nationality_sp);
        marriedRb=findViewById(R.id.married_yes_rb);
        ///////////////////////////////////////////////////////////////////////////////
        appDOBLayout = findViewById(R.id.app_date_layout);
        ////////////////////////////////////////////////////////////////////////////////
        dobTv = findViewById(R.id.app_dob_tv);
        district=findViewById(R.id.district_sp);
        ////////////////////////////////////////////////////////////////////////////////
        groupList();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String[] nationArrayList = getResources().getStringArray(R.array.nationality);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, nationArrayList);
        nation.setAdapter(dataAdapter);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        state = findViewById(R.id.state_sp);
        String[] stateArrayList = getResources().getStringArray(R.array.state);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, stateArrayList);
        state.setAdapter(stateAdapter);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        appDOBLayout = findViewById(R.id.app_date_layout);
        appDOBLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dobTv.setText(year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay).show();
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        willingRb = findViewById(R.id.willing_yes_rb);
        serveRb = findViewById(R.id.serve_yes_rb);
        appointedRb = findViewById(R.id.military_yes_rb);
        obeyRb = findViewById(R.id.obey_yes_rb);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        wingSp=findViewById(R.id.wing_sp);
        String[] wingArrayList = getResources().getStringArray(R.array.wing);
        ArrayAdapter<String> wingAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, wingArrayList);
        wingSp.setAdapter(wingAdapter);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        hqList = findViewById(R.id.group_spinner);
        //////////////////////////////////////////////////////////////////////////////////
        hqList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                batalllionList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        battalian = findViewById(R.id.battalian_spinner);
        battalian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                instList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        institute = findViewById(R.id.inst_spinner);
        institute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //instList(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        nextBT = findViewById(R.id.next_button);
        ////////////////////////////////////////
        nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation3())
                    registration3();
                else
                    Toast.makeText(context, "Fill All Field Properly", Toast.LENGTH_SHORT).show();
            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityList(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    ////////////////////////////////////////////////////////////////
    public void groupList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/hqListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("hqList");
                                ArrayList locatHQArray = new ArrayList();
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    HQModel hqModel = new HQModel();
                                    hqModel.setHqId(localJson.getString("id"));
                                    hqModel.setHqName(localJson.getString("hq_name"));
                                    locatHQArray.add(localJson.getString("hq_name"));
                                    hqModelArrayList.add(hqModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, locatHQArray);
                                hqList.setAdapter(dataAdapter);
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
    ////////////////////////////////////////////////////////////////
    public void batalllionList(final int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/battalianListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                battalionArray.clear();
                                JSONArray jsonArray = json.getJSONArray("battalianList");
                                ArrayList locatBattalianArray = new ArrayList();
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    BattalionModel battalionModel = new BattalionModel();
                                    battalionModel.setBattalionId(localJson.getString("id"));
                                    battalionModel.setBattalionName(localJson.getString("battalion_name"));
                                    locatBattalianArray.add(localJson.getString("battalion_name"));
                                    battalionArray.add(battalionModel);
                                }


                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, locatBattalianArray);
                                battalian.setAdapter(dataAdapter);
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
                params.put("hq_id", hqModelArrayList.get(id).getHqId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ///////////////////////////////////////////////////////////////
    public void instList(final int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/instituteListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("instituteList");
                                ArrayList locatInsArray = new ArrayList();
                                instituteModels.clear();
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    InstituteModel instituteModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), InstituteModel.class);
                                    locatInsArray.add(instituteModel.getInsttName());
                                    instituteModels.add(instituteModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, locatInsArray);
                                institute.setAdapter(dataAdapter);
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
                params.put("battalion_id", battalionArray.get(id).getBattalionId());
                Log.e("params",params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////
    public void registration3() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "ano_signup/anoRegAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");

                            if (status.equals("0")) {
                                FancyToast.makeText(context, msg, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                Intent intent = new Intent(ANOFormActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                FancyToast.makeText(context, msg, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

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
                /////////////////////////////////////////////////////////
                params.put("edu_qulaification",""+education.getText());
                params.put("serve_bn", ""+hqList.getSelectedItem());
                /////////////////////////////////////////////////////////
                params.put("district", ""+district.getSelectedItem());
                params.put("post_office", ""+postOffice.getText());
                params.put("citizen", ""+nation.getSelectedItem());
                params.put("dob", ""+dobTv.getText());
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                params.put("name","" + firstName.getText() + " " + midName.getText() + " " + lastName.getText());
                params.put("email", ""+email.getText());
                params.put("mobile",""+mobile.getText());
                params.put("applied_for", ""+wingSp.getSelectedItem());
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                params.put("app_date", ""+dobTv.getText());
                params.put("instt_name",""+institute.getSelectedItem());
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                params.put("father_gurad_name", "" + fFirstName.getText() + " " + fMidName.getText() + " " + fLastName.getText());
                params.put("village", ""+villCityEt.getText());
                params.put("address", ""+villCityEt.getText()+","+""+district.getSelectedItem());
                params.put("appointment",  ""+appointmentEv.getText());
                params.put("serve_armed_wac",""+served_inforces.getText());
                params.put("state",""+state.getSelectedItem());
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////////
                if (willingRb.isChecked())
                    params.put("willing_officer", "yes");
                else
                    params.put("willing_officer", "no");
                ////////////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////////
                if (serveRb.isChecked())
                    params.put("serve_corps", "yes");
                else
                    params.put("serve_corps", "no");
                ////////////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////////
                if (obeyRb.isChecked())
                    params.put("obey_officer", "yes");
                else
                    params.put("obey_officer", "no");
                ////////////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////////
                if (appointedRb.isChecked())
                    params.put("military_training", "yes");
                else
                    params.put("military_training", "no");
                ////////////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////////
                if (marriedRb.isChecked())
                    params.put("married", "Married");
                else
                    params.put("married", "UnMarried");
                ////////////////////////////////////////////////////////////////////////////////////

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////
    private boolean checkValidation3() {
        boolean ret = true;
        if (!Validation.hasText(firstName)) ret = false;
        if (!Validation.hasText(fFirstName)) ret = false;
        if (!Validation.hasText(villCityEt)) ret = false;
        if (!Validation.hasText(mobile)) ret = false;

        if (!Validation.hasText(email)) ret = false;

        if (!Validation.hasText(education)) ret = false;

        if (battalian.getSelectedItem()==null) ret = false;
        if (institute.getSelectedItem()==null) ret = false;
        if (wingSp.getSelectedItem()==null) ret = false;
        if (hqList.getSelectedItem()==null) ret = false;

        if (nation.getSelectedItem()==null) ret = false;
        if (state.getSelectedItem()==null) ret = false;
        if (district.getSelectedItem()==null) ret = false;

        return ret;
    }
    ////////////////////////////////////////////////////////////////
    public void cityList(final int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/districtListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("distList");
                                ArrayList locatCityArray = new ArrayList();
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    CityModel cityModel = new CityModel();
                                    cityModel.setId(localJson.getString("id"));
                                    cityModel.setDistrictName(localJson.getString("district_name"));
                                    locatCityArray.add(localJson.getString("district_name"));
                                    cityArray.add(cityModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, locatCityArray);
                                district.setAdapter(dataAdapter);
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
                params.put("state_id", "" + id);
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}
