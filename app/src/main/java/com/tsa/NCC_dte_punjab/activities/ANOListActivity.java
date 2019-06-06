package com.tsa.NCC_dte_punjab.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.adaptor.ANOListAdapter;
import com.tsa.NCC_dte_punjab.models.ANOListModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class ANOListActivity extends CustomActivity {
    RecyclerView candidateList;
    ANOListAdapter anoListAdapter;
    ArrayList<ANOListModel> anoListModelArrayList;

    private Context context;
    private Bundle bundle;
    private String insttId;
    private TextView title;

    private ImageView leftIV;
    private ImageView rightIV;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candiats_list);
        anoListModelArrayList = new ArrayList<>();
        context = ANOListActivity.this;
        bundle = getIntent().getExtras();
        if (bundle != null) {
            insttId = bundle.getString("instt_id");
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
        init();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        candidateList.setLayoutManager(mLayoutManager);
        candidateList.setItemAnimator(new DefaultItemAnimator());
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void init() {
        candidateList = findViewById(R.id.candidate_list);
        title=findViewById(R.id.title);
        title.setText("List OF ANOs");
        prinANOList(insttId,1);
        leftIV = findViewById(R.id.left_iv);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1)
                    count-=1;
                prinANOList(insttId,count);
            }
        });
        rightIV = findViewById(R.id.right_iv);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count+=1;
                prinANOList(insttId,count);
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void prinANOList(final String insttId,final int page) {
        final ProgressDialog progressDialogue=new ProgressDialog(context);
        progressDialogue.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "ano_on_principal_instt.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialogue.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");

                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    ANOListModel anoListModel = new ANOListModel();
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    anoListModel.setId(localJson.getString("id"));
                                    anoListModel.setRegisterId(localJson.getString("register_id"));
                                    anoListModel.setUsername(localJson.getString("username"));
                                    anoListModel.setBattalionId(localJson.getString("battalion_id"));
                                    anoListModel.setInsttId(localJson.getString("instt_id"));
                                    anoListModel.setPassword(localJson.getString("password"));
                                    anoListModel.setAppoinment(localJson.getString("appoinment"));
                                    anoListModel.setAppoinmentDate(localJson.getString("appoinment_date"));
                                    anoListModel.setDob(localJson.getString("dob"));
                                    anoListModel.setEducationQualifucation(localJson.getString("education_qualifucation"));
                                    anoListModel.setAnoId(localJson.getString("ano_id"));
                                    /////////////////////////////////////////////////////////////////////////////
                                    anoListModel.setTotalVaccancy(localJson.getString("total_vaccancy"));
                                    anoListModel.setStatus(localJson.getString("status"));
                                    anoListModel.setIsDeleted(localJson.getString("is_deleted"));
                                    /////////////////////////////////////////////////////////////////////////
                                    anoListModel.setCreatedDate(localJson.getString("created_date"));
                                    anoListModel.setNccJointypeId(localJson.getString("ncc_jointype_id"));
                                    anoListModel.setName(localJson.getString("name"));
                                    anoListModel.setEmail(localJson.getString("email"));
                                    anoListModel.setMobile(localJson.getString("mobile"));
                                    anoListModel.setAadhaarNo(localJson.getString("aadhaar_no"));
                                    anoListModel.setRegistrationNo(localJson.getString("registration_no"));
                                    ////////////////////////////////////////////////////////////////////////////////
                                    anoListModel.setBattalionId(localJson.getString("battalion_id"));
                                    anoListModel.setPrincipalId(localJson.getString("principal_id"));
                                    anoListModel.setInsttName(localJson.getString("instt_name"));
                                    anoListModel.setInsttDoc(localJson.getString("instt_doc"));
                                    ///////////////////////////////////////////////////////////////////////////////
                                    anoListModel.setPrincipalRecomStatus(localJson.getString("principal_recom_status"));
                                    anoListModel.setCoRecomStatus(localJson.getString("co_recom_status"));
                                    anoListModel.setGpRecomStatus(localJson.getString("gp_recom_status"));
                                    anoListModel.setAdgApprovStatus(localJson.getString("adg_approv_status"));
                                    anoListModel.setPrincipalRecomStatus(localJson.getString("principal_recom_status"));
                                    //////////////////////////////////////////////////////////////////////////////

                                    anoListModelArrayList.add(anoListModel);
                                    ////////////////////////////////////////////////////////////////////////////////
                                }
                                anoListAdapter = new ANOListAdapter(anoListModelArrayList, context);
                                candidateList.setAdapter(anoListAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialogue.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("instt_id", insttId);
                params.put("page", "" + page);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ANOListActivity.this,GPLogin.class));
        finish();
    }
}
