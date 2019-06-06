package com.tsa.NCC_dte_punjab.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.adaptor.CandidateOnInsAdapter1;
import com.tsa.NCC_dte_punjab.models.CadesOnInsModel;
import com.tsa.NCC_dte_punjab.models.InstituteModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class CadetsOnInstituteActivity extends CustomActivity {
    private Context context;
    private ArrayList<String> arrayList;
    private Spinner instituteSpinner;
    ////////////////////////////////////////////////////
    RecyclerView candidateList;
    CandidateOnInsAdapter1 candidateOnInsAdapter;
    ArrayList<CadesOnInsModel> cadesOnInsModelArrayList;
    ////////////////////////////////////////////////////
    private Bundle bundle;
    public static String hqRegID;
    public static String hqID;
    public static String cadetsOnBatallion;
    public static String insttId;
    public static String battalionId;
    /////////////////////////////////////////////////////
    private ImageView leftIV;
    private ImageView rightIV;
    private int count = 1;
    //////////////////////////////////////////////////////
    private ArrayList<InstituteModel> instituteModels;
    //////////////////////////////////////////////////////
    private FloatingActionMenu floatingArrayMenu;
    ///////////////////////////////////////////////////////
    int status = 0;
    String search = "";
    Gson gson;

    boolean isNavigating = true;

    ///////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadets_on_institute);
        context = CadetsOnInstituteActivity.this;
        candidateList = findViewById(R.id.ints_list);
        /////////////////////////////////////////////////////////////////////////////////////////////
        cadesOnInsModelArrayList = new ArrayList<>();
        instituteModels = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        /////////////////////////////////////////////////////////////////////////////////////////////
        context = CadetsOnInstituteActivity.this;
        count = 1;
        bundle = getIntent().getExtras();
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (bundle != null) {
            hqRegID = bundle.getString("hq_reg_id");
            cadetsOnBatallion = bundle.getString("cadets_on_batallion");
            insttId = bundle.getString("instt_id");
            battalionId = bundle.getString("battalion_id");
            hqID = bundle.getString("");
        }
        //battalionId="60";
        anoCadetList(insttId, battalionId, count, 0, "YES");
        getSchoolList();
        init();
    }

    private void init() {
        /////////////////////////////////////////////////////////////////////////////////////////////////
        arrayList = new ArrayList<>();
        /////////////////////////////////////////////////////////////////////////////////////////////////
        instituteSpinner = findViewById(R.id.institute_sp);
        /////////////////////////////////////////////////////////////////////////////////////////////////

        instituteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    insttId = instituteModels.get(position - 1).getmInsttID();
                    anoCadetList(insttId, battalionId, 1, 0, "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        candidateList.setLayoutManager(mLayoutManager);
        candidateList.setItemAnimator(new DefaultItemAnimator());
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        candidateList = findViewById(R.id.ints_list);
        ////////////////////////////////////////////////////////////////////////////////////////////
        floatingArrayMenu = findViewById(R.id.menu);
        floatingArrayMenu.setVisibility(View.VISIBLE);
        ////////////////////////////////////////////////////////////////////////////////////////////
        leftIV = findViewById(R.id.left_iv);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1)
                    count -= 1;
                loadCandidates();
            }
        });
        rightIV = findViewById(R.id.right_iv);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNavigating = true;
                count += 1;
                loadCandidates();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, COManageActivity.class);
        intent.putExtra("battalion_id", battalionId);
        startActivity(intent);
        finish();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void anoCadetList(final String insttId, final String battalionId, final int page, final int status,
                             final String search) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadets_on_instt_battalion.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");

                            try {
                                progressDialog.dismiss();
                            } catch (IllegalArgumentException ex) {
                                Toast.makeText(CadetsOnInstituteActivity.this, "Slow Network Issue", Toast.LENGTH_SHORT).show();
                            }

                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                cadesOnInsModelArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    CadesOnInsModel cadesOnInsModel = new CadesOnInsModel();
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    cadesOnInsModel.setId(localJson.getString("id"));
                                    cadesOnInsModel.setRegisterId(localJson.getString("register_id"));
                                    cadesOnInsModel.setHqId(localJson.getString("hq_id"));
                                    cadesOnInsModel.setBattalionId(localJson.getString("battalion_id"));
                                    cadesOnInsModel.setInsttId(localJson.getString("instt_id"));
                                    cadesOnInsModel.setGender(localJson.getString("gender"));
                                    cadesOnInsModel.setMaritalStatus(localJson.getString("marital_status"));
                                    cadesOnInsModel.setScRegNo(localJson.getString("sc_reg_no"));
                                    cadesOnInsModel.setDob(localJson.getString("dob"));
                                    cadesOnInsModel.setClassId(localJson.getString("class_id"));
                                    cadesOnInsModel.setSchoolCollage(localJson.getString("school_collage"));
                                    cadesOnInsModel.setLocation(localJson.getString("location"));
                                    cadesOnInsModel.setNccExp(localJson.getString("ncc_exp"));
                                    /////////////////////////////////////////////////////////////////////////////
                                    cadesOnInsModel.setNccNo(localJson.getString("ncc_no"));
                                    cadesOnInsModel.setDepartment(localJson.getString("department"));
                                    cadesOnInsModel.setExpDepartment(localJson.getString("exp_department"));
                                    cadesOnInsModel.setResultFor(localJson.getString("result_for"));
                                    cadesOnInsModel.setResultStatus(localJson.getString("result_status"));
                                    cadesOnInsModel.setStatus(localJson.getString("status"));
                                    cadesOnInsModel.setIsDeleted(localJson.getString("is_deleted"));
                                    cadesOnInsModel.setCreatedDate(localJson.getString("created_date"));
                                    cadesOnInsModel.setNccJointypeId(localJson.getString("ncc_jointype_id"));
                                    cadesOnInsModel.setName(localJson.getString("name"));
                                    cadesOnInsModel.setEmail(localJson.getString("email"));
                                    cadesOnInsModel.setMobile(localJson.getString("mobile"));
                                    cadesOnInsModel.setAadhaarNo(localJson.getString("aadhaar_no"));
                                    cadesOnInsModel.setRegistrationNo(localJson.getString("registration_no"));
                                    ////////////////////////////////////////////////////////////////////////////////
                                    cadesOnInsModel.setHqDgId(localJson.getString("hq_dg_id"));
                                    cadesOnInsModel.setDirectorateId(localJson.getString("directorate_id"));
                                    cadesOnInsModel.setHqName(localJson.getString("hq_name"));
                                    cadesOnInsModel.setBattalionName(localJson.getString("battalion_name"));
                                    cadesOnInsModel.setPrincipalId(localJson.getString("principal_id"));
                                    cadesOnInsModel.setInsttName(localJson.getString("instt_name"));
                                    ////////////////////////////////////////////////////////////////////////////////
                                    cadesOnInsModel.setInsttDoc(localJson.getString("instt_doc"));
                                    ////////////////////////////////////////////////////////////////////////////////
                                    cadesOnInsModel.setCadetId(localJson.getString("cadet_id"));
                                    cadesOnInsModel.setAnoRecomStatus(localJson.getString("ano_recom_status"));
                                    cadesOnInsModel.setCoAprovStatus(localJson.getString("co_aprov_status"));
                                    /////////////////////////////////////////////////////////////////////////////////
                                    cadesOnInsModelArrayList.add(cadesOnInsModel);
                                    ////////////////////////////////////////////////////////////////////////////////
                                }
                                candidateOnInsAdapter = new CandidateOnInsAdapter1(cadesOnInsModelArrayList, context);
                                candidateList.setAdapter(candidateOnInsAdapter);
                            } else {
                                if (isNavigating) {
                                    count--;
                                    loadCandidates();
                                }
                            }
                            FancyToast.makeText(context, "" + msg, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        FancyToast.makeText(context, "Some issue in loading", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("instt_id", insttId);
                params.put("battalion_id", battalionId);
                params.put("page", "" + page);
                params.put("status", "" + status);
                params.put("search", search);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getSchoolList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "institute_list_on_battalion.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("data");
                                arrayList = new ArrayList();
                                arrayList.add("--Select--");
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    InstituteModel instituteModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), InstituteModel.class);
                                    instituteModels.add(instituteModel);
                                    arrayList.add(instituteModel.getInsttName());
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, arrayList);
                                instituteSpinner.setAdapter(dataAdapter);
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                anoCadetList(instituteModels.get(0).getmInsttID(), battalionId, 1, 0, "");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context, "Some Issue in Loading", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("battalion_id", battalionId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void loadCandidates() {
        anoCadetList(insttId, battalionId, count, 0, "");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void filter(View view) {
        isNavigating = false;
        switch (view.getId()) {
            case R.id.menu_item1:
                count = 1;
                anoCadetList(insttId, battalionId, count, 3, "YES");
                break;
            case R.id.menu_item2:
                count = 1;
                anoCadetList(insttId, battalionId, count, 1, "YES");
                break;
            case R.id.menu_item3:
                count = 1;
                anoCadetList(insttId, battalionId, count, 0, "YES");
                break;
            case R.id.menu_item4:
                count = 1;
                anoCadetList(insttId, battalionId, count, 2, "YES");
                break;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
