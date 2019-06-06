package com.tsa.NCC_dte_punjab.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
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
import com.tsa.NCC_dte_punjab.adaptor.CandidateListAdapter;
import com.tsa.NCC_dte_punjab.models.CadetDetailsModels;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Manage1Activity extends CustomActivity {

    private Context context;
    private RecyclerView listView1;
    private int page;

    public static String hqID;
    private Bundle bundle;
    private CandidateListAdapter candidateListAdapter;

    ArrayList<CadetDetailsModels> dgLoginModels = new ArrayList<>();
    private ImageView leftIV;
    private ImageView rightIV;
    private int count = 1;
    private Button viewSummary;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candiats_list);
        context = Manage1Activity.this;
        bundle = getIntent().getExtras();
        page = 1;

        /////////////////////////////////////////////////////////
        if (bundle != null)
            hqID = bundle.getString("hq_reg_id");
        /////////////////////////////////////////////////////////
        init();
    }

    private void init() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        listView1 = findViewById(R.id.candidate_list);
        viewSummary = findViewById(R.id.view_summary_btn);
        viewSummary.setVisibility(View.GONE);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        ////////////////////////////////////////////////////////////////////////////////////////////
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        listView1.setLayoutManager(mLayoutManager1);
        listView1.setItemAnimator(new DefaultItemAnimator());
        cadetOnBattalian(hqID, page);

        ////////////////////////////////////////////////////////////////////////////////////////////
        leftIV = findViewById(R.id.left_iv);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1)
                    count -= 1;
                cadetOnBattalian(hqID, count);
            }
        });
        rightIV = findViewById(R.id.right_iv);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                cadetOnBattalian(hqID, count);
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void cadetOnBattalian(final String hqID, final int page) {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadets_on_hq.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            progressBar.dismiss();
                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                dgLoginModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    CadetDetailsModels dgLoginModel = gson.fromJson(localJson.toString(), CadetDetailsModels.class);
///////////////////////////////////////////////////////////////////
                                    dgLoginModels.add(dgLoginModel);
                                    ////////////////////////////////////////////////////////////////////////////////
                                }
                                candidateListAdapter = new CandidateListAdapter(dgLoginModels, context);
                                listView1.setAdapter(candidateListAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressBar.dismiss();
                        FancyToast.makeText(context,"Some issue in loading", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("hq_id", hqID);
                params.put("page", "" + page);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void goNotification(View view)
    {
        Intent intent=new Intent(context,NotificationActivity.class);
/*      intent.putExtra("instt_id",insttId);
        intent.putExtra("battalion_id",battalionId);
        intent.putExtra("ano_id", anoId);
        intent.putExtra("user_type", userType);*/
        startActivity(intent);
        finish();
    }


   /* public class search{

    }*/

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Manage1Activity.this, GPLogin.class));
        finish();
    }
}


