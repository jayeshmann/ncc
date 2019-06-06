package com.tsa.NCC_dte_punjab.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.tsa.NCC_dte_punjab.adaptor.CandidateListAdapter;
import com.tsa.NCC_dte_punjab.adaptor.CandidateOnInsAdapter;
import com.tsa.NCC_dte_punjab.json_parsing.VolleyRequestHandler;
import com.tsa.NCC_dte_punjab.json_parsing.VolleyRequestHandlerIF;
import com.tsa.NCC_dte_punjab.models.CadetDetailsModels;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class CadetListActivity extends CustomActivity implements VolleyRequestHandlerIF {
    RecyclerView candidateList;
    CandidateListAdapter candidateListAdapter;
    CandidateOnInsAdapter candidateOnInsAdapter;
    ArrayList<CadetDetailsModels> candidate_list;
    ArrayList<CadetDetailsModels> cadesOnInsModelArrayList;
    ////////////////////////////////////////////////////
    private Context context;
    private Bundle bundle;
    public static String hqRegID;
    public static String cadetsOnBatallion;
    public static String insttId;
    public static String battalionId;
    public static String anoId;
    public static String userType;
    private Button viewSummary;
    /////////////////////////////////////////////////////
    private int count = 1;
    private static EditText searchBoxET;
    private static ImageView searchBt;
    private String selectionType = "name";
    ///////////////////////////////////////////////////////
    private String insttName;
    private String jd;
    private String jw;
    private String sd;
    private String sw;
    ///////////////////////////////////////////////////////
    final int LIST_OF_PENDING = 0;
    final int LIST_OF_APPROVED = 2;
    final int LIST_OF_RECOMMENDED = 1;
    final int LIST_OF_SEARCHED = 4;
    int LIST_TYPE=LIST_OF_PENDING;
    final int SEARCH_LIST=5;

    ///////////////////////////////////////////////////////

    private FloatingActionMenu floatingArrayMenu;
    private Gson gson;
    private ProgressBar progressBar1;
    String search = "";
    private int searchCount = 0;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static final int PAGE_START = 1;
    private boolean isScrolling = false;
    private int totalItems = 5;
    private int currentItem = PAGE_START;
    private int scrollOutItems = 0;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candiats_list);
        /////////////////////////////////////////////////////////////////////////////////////////////
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        candidate_list = new ArrayList<>();
        cadesOnInsModelArrayList = new ArrayList<>();
        /////////////////////////////////////////////////////////////////////////////////////////////
        context = CadetListActivity.this;
        bundle = getIntent().getExtras();
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (bundle != null) {
            hqRegID = bundle.getString("hq_reg_id");
            cadetsOnBatallion = bundle.getString("cadets_on_batallion");
            insttId = bundle.getString("instt_id");
            battalionId = bundle.getString("battalion_id");
            anoId = bundle.getString("ano_id");
            userType = bundle.getString("user_type");
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        init();
        /////////////////////////////////////////////////////////////////////////////////////////////
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        candidateList.setLayoutManager(mLayoutManager);
        candidateList.setItemAnimator(new DefaultItemAnimator());
        candidateList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                scrollOutItems = mLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItem + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    if (LIST_TYPE == LIST_OF_SEARCHED) {
                        searchCadetList(insttId, battalionId, count, selectionType, "" + searchBoxET.getText());
                    } else {
                        loadCandidates();
                    }
                    count++;
                }
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        loadCandidates();
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (GLOBAL.LOGIN_TYPE == GLOBAL.ANO_LOGIN) {
            viewDetails();
          //viewSummary.setVisibility(View.VISIBLE);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void init() {
        candidateList = findViewById(R.id.candidate_list);
        viewSummary = findViewById(R.id.view_summary_btn);

        floatingArrayMenu = findViewById(R.id.menu);
        floatingArrayMenu.setVisibility(View.VISIBLE);
        progressBar1 = findViewById(R.id.progressBar);

       /*searchBoxET = findViewById(R.id.search_string);*/
        searchBt = findViewById(R.id.search_button);

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateSearch()) {
                    searchFilter();
                } else
                    FancyToast.makeText(context, "Invalid Search String", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });

        candidateList.setAdapter(candidateListAdapter);
        ///////////////////////////////////////////////////////////////////////////////////////////////
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void dgCadetList(final String hqID, final int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadets_on_hq.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");

                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    CadetDetailsModels dgLoginModel = gson.fromJson(localJson.toString(), CadetDetailsModels.class);

                                    /////////////////////////////////////////////////////////////////////////////////
                                    if (dgLoginModel.getCoAprovStatus().equals("2"))
                                        candidate_list.add(dgLoginModel);
                                    ////////////////////////////////////////////////////////////////////////////////
                                }
                                candidateListAdapter = new CandidateListAdapter(candidate_list, context);
                                candidateList.setAdapter(candidateListAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context, "Some issue in loading", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
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
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void coCadetList(final String batallianID, final int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadets_on_batallion.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");

                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    CadetDetailsModels dgLoginModel = gson.fromJson(localJson.toString(), CadetDetailsModels.class);
                                    //////////////////////////////////////////////////////////////////////////////
                                    candidate_list.add(dgLoginModel);
                                    ////////////////////////////////////////////////////////////////////////////////
                                }
                                candidateListAdapter = new CandidateListAdapter(candidate_list, context);
                                candidateList.setAdapter(candidateListAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context, "Some issue in loading", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("battalion_id", batallianID);
                params.put("page", "" + page);
              /*  params.put("battalion_id", "11");
                params.put("page", "1");*/
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void anoCadetList(final String insttId, final String battalionId, final int page, final int status, final String search) {
        Map<String, String> params = new Hashtable<String, String>();
        params.put("instt_id", insttId);
        params.put("battalion_id", battalionId);
        params.put("page", "" + page);
        params.put("status", "" + LIST_TYPE);
        params.put("search", search);
        VolleyRequestHandler volleyRequestHandler = new VolleyRequestHandler(context, Request.Method.POST, GLOBAL.BASE_URL + "cadets_on_ano.php", null, null, params);
        volleyRequestHandler.setRetryPolicy(new DefaultRetryPolicy(
                30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleyRequestHandler.getApiResponse(0);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void printANOList(final String insttId, final String battalionId, final int page) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Map<String, String> params = new Hashtable<String, String>();
        params.put("instt_id", insttId);
        params.put("battalion_id", battalionId);
        params.put("page", "" + page);
        VolleyRequestHandler volleyRequestHandler = new VolleyRequestHandler(context, Request.Method.POST, GLOBAL.BASE_URL + "ano_on_principal_instt.php", null, null, params);
        volleyRequestHandler.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleyRequestHandler.getApiResponse(1);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void loadCandidates() {
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (GLOBAL.LOGIN_TYPE == GLOBAL.DG_LOGIN || GLOBAL.LOGIN_TYPE == GLOBAL.ADG_LOGIN || GLOBAL.LOGIN_TYPE == GLOBAL.GP_LOGIN)
            dgCadetList(hqRegID, count);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        if (GLOBAL.LOGIN_TYPE == GLOBAL.CO_LOGIN)
            coCadetList(cadetsOnBatallion, count);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        if (GLOBAL.LOGIN_TYPE == GLOBAL.ANO_LOGIN)
            anoCadetList(insttId, battalionId, count, LIST_TYPE, search);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        if (GLOBAL.LOGIN_TYPE == GLOBAL.PRINCIPAL_LOGIN)
            printANOList(insttId, battalionId, count);
        ////////////////////////////////////////////////////////////////////////////////////////////
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void viewSummary(View view) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialogue);

        TextView insttName = dialog.findViewById(R.id.instt_name);
        insttName.setText(this.insttName);
        TextView sd = dialog.findViewById(R.id.sd_no);
        sd.setText(this.sd);
        TextView sw = dialog.findViewById(R.id.sw_no);
        sw.setText(this.sw);
        TextView jd = dialog.findViewById(R.id.jd_no);
        jd.setText(this.jd);
        TextView jw = dialog.findViewById(R.id.jw_no);
        jw.setText(this.jw);

        Button okButton = dialog.findViewById(R.id.ok_btn);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void viewDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "ano_instt_wise_summary.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");

                            if (status.equals("0")) {
                                insttName = json.getJSONObject("instt").getString("");
                                if (insttName != null && !("" + insttName.trim()).equals("")) {
                                    jd = "JD: " + json.getJSONObject("JD").getString("");
                                    jw = "JW: " + json.getJSONObject("JW").getString("");
                                    sd = "SD: " + json.getJSONObject("SD").getString("");
                                    sw = "SW: " + json.getJSONObject("SW").getString("");
                                }
                                // viewSummary(null);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context, "Some issue in loading", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("instt_id", insttId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void goNotification(View view) {
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.putExtra("instt_id", insttId);
        intent.putExtra("battalion_id", battalionId);
        intent.putExtra("ano_id", anoId);
        intent.putExtra("user_type", userType);
        intent.putExtra("go", "CadetListActivity");
        startActivity(intent);
        finish();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(context, GPLogin.class));
        finish();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void filter(View view) {
        switch (view.getId()) {
            case R.id.menu_item1:
                //List Of Approved
                LIST_TYPE = LIST_OF_APPROVED;
                search = "y";
                break;
            case R.id.menu_item2:
                //List Of Recommended
                LIST_TYPE = LIST_OF_RECOMMENDED;
                search = "y";
                break;
            case R.id.menu_item3:
                //List Of Pending
                LIST_TYPE = LIST_OF_PENDING;
                search = "y";
                break;
            case R.id.menu_item4:
                LIST_TYPE = 5;
               // LIST_TYPE = LIST_OF_PENDING;
                search = "";
                break;

            case R.id.menu_item6:
                //List Of Pending
                LIST_TYPE = SEARCH_LIST;
                search = "y";
                break;
        }
        count = 1;
        anoCadetList(insttId, battalionId, count, LIST_TYPE, search);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void goToLectureList(View view) {
        Intent intent = new Intent(context, LactureListActivity.class);
        intent.putExtra("instt_id", insttId);
        intent.putExtra("battalion_id", battalionId);
        intent.putExtra("ano_id", anoId);
        intent.putExtra("user_type", userType);
        startActivity(intent);
        finish();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void searchCadetList(final String insttId, final String battalionId, final int page, final String searchType, final String searchValue) {
        //initializing the new search
        searchCount = 0;
        progressBar1.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "searching_cadet/search_cadet_ano.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");
                            progressBar1.setVisibility(View.GONE);
                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                //cadesOnInsModelArrayList.clear();
                                int size = jsonArray.length();
                                for (int i = 0; i < size; i++) {
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    CadetDetailsModels dgLoginModel = gson.fromJson(localJson.toString(), CadetDetailsModels.class);
                                    if (dgLoginModel.getExpiryDate() == null)
                                        dgLoginModel.setExpiryDate("N/A");
                                    //////////////////////////////////////////////////////////////////////////////
                                    cadesOnInsModelArrayList.add(dgLoginModel);
                                    ////////////////////////////////////////////////////////////////////////////////
                                }
                                candidateOnInsAdapter = new CandidateOnInsAdapter(cadesOnInsModelArrayList, context);
                                candidateList.setAdapter(candidateOnInsAdapter);
                            }
                            FancyToast.makeText(context, "" + msg, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                        } catch (JSONException e) {
                            progressBar1.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressBar1.setVisibility(View.GONE);
                        FancyToast.makeText(context, "Some issue in loading", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("instt_id", insttId);
                params.put("battalion_id", battalionId);
                params.put("page", "" + page);
                params.put("field", "" + searchType);
                params.put("val", searchValue);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean validateSearch() {
        if (searchBoxET.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void searchFilter() {

        String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_multi_choice_array);

        int itemSelected = 0;

        new AlertDialog.Builder(this)

                .setTitle("Select Search Type")

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        switch (selectedIndex) {
                            case 0:
                                selectionType = "name";
                                break;
                            case 1:
                                selectionType = "email";
                                break;
                            case 2:
                                selectionType = "mobile";
                                break;
                            case 3:
                                selectionType = "registration_no";
                        }
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchCadetList(insttId, battalionId, searchCount, selectionType, "" + searchBoxET.getText());
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String getApiResponse(int flag) {
        return "" + null;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void setApiResponse(Map map) {
        try {
            int flag = Integer.parseInt(map.get("response_code").toString());
            String response = map.get("response_string").toString();
            JSONObject json = new JSONObject(response);
            if (flag == 0) {
                String status = json.getString("status");

                if (status.equals("0")) {
                    JSONArray jsonArray = json.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        /////////////////////////////////////////////////////////////////////////////////////////////////
                        JSONObject localJson = jsonArray.getJSONObject(i);
                        CadetDetailsModels dgLoginModel = gson.fromJson(localJson.toString(), CadetDetailsModels.class);
                        candidate_list.add(dgLoginModel);
                        /////////////////////////////////////////////////////////////////////////////////////////////////
                    }
                    candidateListAdapter = new CandidateListAdapter(candidate_list, context);
                    candidateList.setAdapter(candidateListAdapter);
                } else if (flag==1){
                    String msg = json.getString("msg");
                    if (status.equals("0")) {
                        JSONArray jsonArray = json.getJSONArray("data");
                        int size = jsonArray.length();
                        for (int i = 0; i < size; i++) {
                            //////////////////////////////////////////////////////////////////////////////
                            JSONObject localJson = jsonArray.getJSONObject(i);
                            CadetDetailsModels dgLoginModel = gson.fromJson(localJson.toString(), CadetDetailsModels.class);
                            if (dgLoginModel.getExpiryDate() == null)
                                dgLoginModel.setExpiryDate("N/A");

                            //////////////////////////////////////////////////////////////////////////////
                            cadesOnInsModelArrayList.add(dgLoginModel);

                            ////////////////////////////////////////////////////////////////////////////////
                        }
                        candidateOnInsAdapter = new CandidateOnInsAdapter(cadesOnInsModelArrayList, context);
                        candidateList.setAdapter(candidateOnInsAdapter);
                    }
                    FancyToast.makeText(context, "" + msg, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}