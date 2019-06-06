package com.tsa.NCC_dte_punjab.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.adaptor.ANOListAdapter;
import com.tsa.NCC_dte_punjab.adaptor.CandidateListAdapter;
import com.tsa.NCC_dte_punjab.models.ANOAppModel;
import com.tsa.NCC_dte_punjab.models.ANOListModel;
import com.tsa.NCC_dte_punjab.models.CadetDetailsModels;
import com.tsa.NCC_dte_punjab.models.InstituteListModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Manage2Activity extends CustomActivity {
    String successMessage="";
    private Button mgtANOBtn;
    private Button mgtCadetBtn;
    private Button mgtInsBtn;

    private Context context;
    private RecyclerView listView1;
    private RecyclerView listView2;
    private RecyclerView listView3;
    private TextView title;

    public static String hqId;
    private Bundle bundle;
    private InsListAdapter insListAdapter;
    private ANOListAdapter anoListAdapter;
    private CandidateListAdapter candidateListAdapter;

    ArrayList<ANOListModel> anoListModels = new ArrayList<>();
    ArrayList<InstituteListModel> instituteListModels = new ArrayList<>();
    ArrayList<CadetDetailsModels> dgLoginModels = new ArrayList<>();

    private ImageView leftIV;
    private ImageView rightIV;
    private int count1 = 1;
    private int count2 = 1;
    private int count3 = 1;
    private Button viewSummary;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton notificationButton;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        context = Manage2Activity.this;
        bundle = getIntent().getExtras();

        /////////////////////////////////////////////////////////
        if (bundle != null)
            hqId = bundle.getString("hq_reg_id");
        /////////////////////////////////////////////////////////
        init();
    }

    private void init() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        viewSummary = findViewById(R.id.view_summary_btn);
        viewSummary.setVisibility(View.GONE);
        mgtANOBtn = findViewById(R.id.mng_ano_btn);
        mgtCadetBtn = findViewById(R.id.mng_cadet_btn);
        mgtInsBtn = findViewById(R.id.mng_inst_btn);
        listView1 = findViewById(R.id.list_view1);
        listView2 = findViewById(R.id.list_view2);
        listView3 = findViewById(R.id.list_view3);
        title = findViewById(R.id.title);

        floatingActionMenu=findViewById(R.id.menu);
        notificationButton=findViewById(R.id.notification_button);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        if(GLOBAL.LOGIN_TYPE==GLOBAL.ADG_LOGIN||GLOBAL.LOGIN_TYPE==GLOBAL.GP_LOGIN)
        {
            floatingActionMenu.setVisibility(View.GONE);
            notificationButton.setVisibility(View.VISIBLE);
        }
        else
        {
            floatingActionMenu.setVisibility(View.VISIBLE);
            notificationButton.setVisibility(View.GONE);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        leftIV = findViewById(R.id.left_iv);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listView1.getVisibility() == View.VISIBLE) {
                    if (count1 > 1)
                        count1 -= 1;
                    instituteOnBattalian(count1);
                } else if (listView2.getVisibility() == View.VISIBLE) {
                    if (count2 > 1)
                        count2 -= 1;
                    cadetOnBattalian(hqId, count2);
                } else if (listView3.getVisibility() == View.VISIBLE) {
                    if (count3 > 1)
                        count3 -= 1;
                    anoOnBattalian(hqId, count3);
                }
            }
        });
        rightIV = findViewById(R.id.right_iv);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listView1.getVisibility() == View.VISIBLE) {
                    count1 += 1;
                    instituteOnBattalian(count1);
                } else if (listView2.getVisibility() == View.VISIBLE) {
                    count2 += 1;
                    cadetOnBattalian(hqId, count2);
                } else if (listView3.getVisibility() == View.VISIBLE) {
                    count3 += 1;
                    anoOnBattalian(hqId, count3);
                }
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        listView1.setLayoutManager(mLayoutManager1);
        listView1.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        listView2.setLayoutManager(mLayoutManager2);
        listView2.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        listView3.setLayoutManager(mLayoutManager3);
        listView3.setItemAnimator(new DefaultItemAnimator());
        ////////////////////////////////////////////////////////////////////////////////////////////
        set1();
        ////////////////////////////////////////////////////////////////////////////////////////////
        mgtInsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instituteListModels.isEmpty()) {
                    instituteOnBattalian(1);
                }
                title.setText("Manage Institute");
                listView1.setVisibility(View.VISIBLE);
                listView2.setVisibility(View.GONE);
                listView3.setVisibility(View.GONE);

                mgtInsBtn.setTextColor(getResources().getColor(R.color.white));
                mgtInsBtn.setBackgroundResource(R.color.gray);

                mgtCadetBtn.setTextColor(getResources().getColor(R.color.white));
                mgtCadetBtn.setBackgroundResource(R.color.green);

                mgtANOBtn.setTextColor(getResources().getColor(R.color.white));
                mgtANOBtn.setBackgroundResource(R.color.green);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        mgtCadetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set1();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        mgtANOBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (anoListModels.isEmpty()) {
                    anoOnBattalian(hqId, 1);
                }
                title.setText("Manage ANO");

                listView3.setVisibility(View.VISIBLE);
                listView1.setVisibility(View.GONE);
                listView2.setVisibility(View.GONE);

                mgtANOBtn.setTextColor(getResources().getColor(R.color.white));
                mgtANOBtn.setBackgroundResource(R.color.gray);

                mgtInsBtn.setTextColor(getResources().getColor(R.color.white));
                mgtInsBtn.setBackgroundResource(R.color.green);

                mgtCadetBtn.setTextColor(getResources().getColor(R.color.white));
                mgtCadetBtn.setBackgroundResource(R.color.green);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void cadetOnBattalian(final String hqID, final int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadets_on_directorate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");

                            if (status.equals("0")) {
                                dgLoginModels.clear();
                                JSONArray jsonArray = json.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    CadetDetailsModels adgLoginModel = gson.fromJson(localJson.toString(), CadetDetailsModels.class);
                                    //////////////////////////////////////////////////////////////////////////////
                                    dgLoginModels.add(adgLoginModel);
                                    ////////////////////////////////////////////////////////////////////////////////
                                }
                                candidateListAdapter = new CandidateListAdapter(dgLoginModels, context);
                                listView2.setAdapter(candidateListAdapter);
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
                params.put("page", "" + page);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void anoOnBattalian(final String battalionId, final int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "ano_on_directorate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            anoListModels.clear();
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
                                    ////////////////////////////////////////////////////////////////////////////
                                    anoListModel.setTotalVaccancy(localJson.getString("total_vaccancy"));
                                    /////////////////////////////////////////////////////////////////////////
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
                                    ////////////////////////////////////////////////////////////////////////////////////////////
                                    anoListModel.setPrincipalRecomStatus(localJson.getString("principal_recom_status"));
                                    anoListModel.setCoRecomStatus(localJson.getString("co_recom_status"));
                                    anoListModel.setGpRecomStatus(localJson.getString("gp_recom_status"));
                                    anoListModel.setAdgApprovStatus(localJson.getString("adg_approv_status"));
                                    ///////////////////////////////////////////////////////////////////////////////////////////
                                    anoListModels.add(anoListModel);
                                    ////////////////////////////////////////////////////////////////////////////////
                                }
                                anoListAdapter = new ANOListAdapter(anoListModels, context);
                                listView3.setAdapter(anoListAdapter);
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
                params.put("page", "" + page);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void instituteOnBattalian(final int page) {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "instt_on_directorate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.dismiss();
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                instituteListModels.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    InstituteListModel instituteListModel = new InstituteListModel();
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);

                                    instituteListModel.setId(localJson.getString("id"));
                                    instituteListModel.setPrincipalId(localJson.getString("principal_id"));
                                    instituteListModel.setBattalionId(localJson.getString("battalion_id"));
                                    instituteListModel.setRegisterId(localJson.getString("register_id"));
                                    instituteListModel.setInsttName(localJson.getString("instt_name"));

                                    instituteListModel.setTotalStudent(localJson.getString("total_student"));
                                    instituteListModel.setTeacherAvailable(localJson.getString("teacher_available"));
                                    instituteListModel.setParadeStoreAvailable(localJson.getString("parade_store_available"));
                                    instituteListModel.setShortRangeAvailable(localJson.getString("short_range_available"));
                                    instituteListModel.setBathroomAvailable(localJson.getString("bathroom_available"));

                                    instituteListModel.setBathroomAvailable(localJson.getString("bathroom_images"));
                                    instituteListModel.setNocBySocietyDoc(localJson.getString("noc_by_society_doc"));
                                    instituteListModel.setInsttType(localJson.getString("instt_type"));
                                    instituteListModel.setAffilRegNo(localJson.getString("affil_reg_no"));
                                    instituteListModel.setInsttDoc(localJson.getString("instt_doc"));

                                    instituteListModel.setTotalVaccancy(localJson.getString("total_vaccancy"));
                                    instituteListModel.setStatus(localJson.getString("status"));
                                    instituteListModel.setIsDeleted(localJson.getString("is_deleted"));
                                    instituteListModel.setCreatedDate(localJson.getString("created_date"));
                                    instituteListModel.setNccJointypeId(localJson.getString("ncc_jointype_id"));
                                    instituteListModel.setName(localJson.getString("name"));
                                    instituteListModel.setEmail(localJson.getString("email"));
                                    instituteListModel.setMobile(localJson.getString("mobile"));
                                    instituteListModel.setAadhaarNo(localJson.getString("aadhaar_no"));
                                    instituteListModel.setRegistrationNo(localJson.getString("registration_no"));
                                    instituteListModel.setInsttId(localJson.getString("instt_id"));
                                    instituteListModel.setCoRecommStatus(localJson.getString("co_recomm_status"));
                                    instituteListModel.setGpRecommStatus(localJson.getString("gp_recomm_status"));
                                    instituteListModel.setAdgApprovStatus(localJson.getString("adg_approv_status"));

                                    instituteListModels.add(instituteListModel);

                                }
                                insListAdapter = new InsListAdapter(instituteListModels, context);
                                listView1.setAdapter(insListAdapter);
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
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("page", "" + page);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void goToInsFilter(View view) {
       /* Intent intent = new Intent(context, ListOfInsToUploadActivity.class);
        intent.putExtra("hq_reg_id", hqId);
        startActivity(intent);
        finish();*/
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void goNotification(View view)
    {
        Intent intent=new Intent(context,NotificationActivity.class);
        intent.putExtra("user_type", GLOBAL.USER_TYPE);
        intent.putExtra("go", "Manage2Activity");
        intent.putExtra("hq_reg_id", hqId);
        startActivity(intent);
        finish();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    class InsListAdapter extends RecyclerView.Adapter<InsListAdapter.ViewHolder> {

        private ArrayList<InstituteListModel> anoListModels;
        private Context context;
        ArrayList anoArrayList;

        public InsListAdapter(ArrayList<InstituteListModel> anoListModels, Context context) {
            this.anoListModels = anoListModels;
            this.context = context;
            anoArrayList = new ArrayList();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ano_card, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            //////////////////////////////////////////////////////////////////////////////////////
            final InstituteListModel anoListModel = anoListModels.get(position);
            holder.nameTV.setText("" + anoListModel.getName());
            holder.emailTV.setText("" + anoListModel.getEmail());
            holder.mobileTV.setText("" + anoListModel.getMobile());
            holder.instituteTV.setText("" + anoListModel.getInsttName());
            ///////////////////////////////////////////////////////////////////////////////////////\
            ////////////////////////////////////////////////////////////////////////////////////////////
            if (position % 2 == 0)
                holder.root.setBackgroundResource(R.drawable.red_background);
            else
                holder.root.setBackgroundResource(R.drawable.green_background);

            //////////////////////////////////////////////////////////////////////////////////////////////////////
            if (GLOBAL.LOGIN_TYPE == GLOBAL.GP_LOGIN) {
                if (anoListModel.getGpRecommStatus().equals("0")) {
                    holder.recommend.setText(context.getResources().getString(R.string.wating));
                    holder.recommend.setEnabled(false);
                    holder.recommend.setBackgroundResource(R.drawable.yellow_button);
                } else if (anoListModel.getGpRecommStatus().equals("1")) {
                    holder.recommend.setBackgroundResource(R.drawable.red_button);
                    holder.recommend.setTextColor(context.getResources().getColor(R.color.white));
                    holder.recommend.setText(context.getResources().getString(R.string.recommand));
                    holder.recommend.setEnabled(true);
                } else if (anoListModel.getGpRecommStatus().equals("2")) {
                    holder.recommend.setText(context.getResources().getString(R.string.recommanded));
                    holder.recommend.setEnabled(false);
                    holder.recommend.setBackgroundResource(R.drawable.green_button);
                } else if (anoListModel.getGpRecommStatus().equals("3")) {
                    holder.recommend.setText(context.getResources().getString(R.string.approved));
                    holder.recommend.setEnabled(false);
                    holder.recommend.setBackgroundResource(R.drawable.green_button);
                }
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////////////////////
            else if (GLOBAL.LOGIN_TYPE == GLOBAL.ADG_LOGIN) {
                if (anoListModel.getAdgApprovStatus().equals("0")) {
                    holder.recommend.setBackgroundResource(R.drawable.yellow_button);
                    holder.recommend.setText(context.getResources().getString(R.string.wating));
                    holder.recommend.setEnabled(false);
                } else if (anoListModel.getAdgApprovStatus().equals("1")) {
                    holder.recommend.setBackgroundResource(R.drawable.yellow_button);
                    holder.recommend.setText(context.getResources().getString(R.string.wating));
                    holder.recommend.setEnabled(false);
                } else if (anoListModel.getAdgApprovStatus().equals("2")) {
                    holder.recommend.setBackgroundResource(R.drawable.red_button);
                    holder.recommend.setTextColor(context.getResources().getColor(R.color.white));
                    holder.recommend.setText(context.getResources().getString(R.string.approve));
                    holder.recommend.setEnabled(true);
                } else if (anoListModel.getAdgApprovStatus().equals("3")) {
                    holder.recommend.setBackgroundResource(R.drawable.green_button);
                    holder.recommend.setText(context.getResources().getString(R.string.approved));
                    holder.recommend.setEnabled(false);
                }
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////////////////////
            else if (GLOBAL.LOGIN_TYPE == GLOBAL.DG_LOGIN) {
                if (anoListModel.getAdgApprovStatus().equals("0")) {
                    holder.recommend.setText(context.getResources().getString(R.string.approve));
                    holder.recommend.setEnabled(true);
                    holder.recommend.setBackgroundResource(R.drawable.red_button);
                } else {
                    holder.recommend.setText(context.getResources().getString(R.string.approved));
                    holder.recommend.setEnabled(false);
                    holder.recommend.setBackgroundResource(R.drawable.green_button);
                }
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////


            holder.recommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.recommend.isEnabled()&&holder.recommend.getText().equals(context.getResources().getString(R.string.recommand)))
                    {
                         successMessage=context.getResources().getString(R.string.recommanded_msg);
                    }
                    if (holder.recommend.isEnabled()&&holder.recommend.getText().equals(context.getResources().getString(R.string.approve)))
                    {
                        successMessage=context.getResources().getString(R.string.approve);
                    }
                    if (GLOBAL.LOGIN_TYPE == GLOBAL.GP_LOGIN) {
                        recommandGP(anoListModel);
                    } else if (GLOBAL.LOGIN_TYPE == GLOBAL.DG_LOGIN) {
                        recommandDG(anoListModel);
                    } else if (GLOBAL.LOGIN_TYPE == GLOBAL.ADG_LOGIN) {
                        recommandADG(anoListModel);
                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////////////
                }
            });

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GLOBAL.instituteanoModel=anoListModel;
                    Intent intent=new Intent(context,InstituteProfileActivity.class);
                    intent.putExtra("hq_reg_id",hqId);
                    intent.putExtra("go_to","2");
                    startActivity(intent);
                }
            });
        }

        ////////////////////////////////////////////////////////////////
        @Override
        public int getItemCount() {
            return anoListModels.size();
        }
        /////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////
        class ViewHolder extends RecyclerView.ViewHolder {
            /////////////////////////
            TextView nameTV;
            TextView emailTV;
            TextView qualificationTV;
            TextView mobileTV;
            TextView instituteTV;
            /////////////////////////
            LinearLayout root;
            Button recommend;
            /////////////////////////

            public ViewHolder(View view) {
                super(view);
                //////////////////////////////////////////////////////
                nameTV = view.findViewById(R.id.name_tv);
                emailTV = view.findViewById(R.id.email_tv);
                //qualificationTV = view.findViewById(R.id.edu_quli_tv);
                mobileTV = view.findViewById(R.id.mobile_tv);
                instituteTV = view.findViewById(R.id.institute_tv);
                //////////////////////////////////////////////////////
                root = view.findViewById(R.id.root);
                recommend = view.findViewById(R.id.approved);
                //////////////////////////////////////////////////////
            }
        }

        /////////////////////////////////////////////////////////////////////
        public void recommandGP(final InstituteListModel dgLoginModel) {
            final ProgressDialog progressBar = new ProgressDialog(context);
            progressBar.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "instt_gp_recomm_status.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                progressBar.dismiss();
                                JSONObject json = new JSONObject(s);
                                String status = json.getString("status");
                                ANOAppModel anoAppModel = new ANOAppModel();
                                if (status.equals("0")) {
                                    ///////////////////////////////////////////////////////////////////////
                                    anoAppModel.setStatus(json.getString("status"));
                                    anoAppModel.setCadetId(json.getString("cadet_id"));
                                    anoAppModel.setRegisterId(json.getString("register_id"));
                                    anoAppModel.setAnoRecomStatus(json.getString("ano_recom_status"));
                                    anoAppModel.setCoAprovStatus(json.getString("co_aprov_status"));
                                    anoAppModel.setMsg(json.getString("msg"));
                                    ///////////////////////////////////////////////////////////////////////
                                    Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
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
                            FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put("instt_id", dgLoginModel.getInsttId());
                    params.put("register_id", dgLoginModel.getRegisterId());
                    params.put("gp_recomm_status", dgLoginModel.getGpRecommStatus());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
        /////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////
        public void recommandDG(final InstituteListModel dgLoginModel) {
            final ProgressDialog progressBar = new ProgressDialog(context);
            progressBar.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "instt_adg_approv_status.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                progressBar.dismiss();
                                JSONObject json = new JSONObject(s);
                                String status = json.getString("status");
                                ANOAppModel anoAppModel = new ANOAppModel();
                                if (status.equals("0")) {
                                    ///////////////////////////////////////////////////////////////////////
                                    anoAppModel.setStatus(json.getString("status"));
                                    anoAppModel.setCadetId(json.getString("cadet_id"));
                                    anoAppModel.setRegisterId(json.getString("register_id"));
                                    anoAppModel.setAnoRecomStatus(json.getString("ano_recom_status"));
                                    anoAppModel.setCoAprovStatus(json.getString("co_aprov_status"));
                                    anoAppModel.setMsg(json.getString("msg"));
                                    ///////////////////////////////////////////////////////////////////////
                                    Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();

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
                            FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put("instt_id", dgLoginModel.getInsttId());
                    params.put("register_id", dgLoginModel.getRegisterId());
                    params.put("co_recomm_status", dgLoginModel.getCoRecommStatus());
                    params.put("instt_id", dgLoginModel.getInsttId());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
        /////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////
        public void recommandADG(final InstituteListModel dgLoginModel) {
            final ProgressDialog progressBar = new ProgressDialog(context);
            progressBar.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "instt_adg_approv_status.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                progressBar.dismiss();
                                JSONObject json = new JSONObject(s);
                                String status = json.getString("status");
                                ANOAppModel anoAppModel = new ANOAppModel();
                                if (status.equals("0")) {
                                    ///////////////////////////////////////////////////////////////////////
                                    anoAppModel.setStatus(json.getString("status"));
                                    anoAppModel.setCadetId(json.getString("cadet_id"));
                                    anoAppModel.setRegisterId(json.getString("register_id"));
                                    anoAppModel.setAnoRecomStatus(json.getString("ano_recom_status"));
                                    anoAppModel.setCoAprovStatus(json.getString("co_aprov_status"));
                                    anoAppModel.setMsg(json.getString("msg"));
                                    ///////////////////////////////////////////////////////////////////////
                                    Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
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
                            FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put("instt_id", dgLoginModel.getInsttId());
                    params.put("register_id", dgLoginModel.getRegisterId());
                    params.put("adg_approv_status", dgLoginModel.getAdgApprovStatus());

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
        //////////////////////////////////////////////////////////////////////
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void set1() {
        if (dgLoginModels.isEmpty()) {
            cadetOnBattalian(hqId, 1);
        }
        title.setText("Manage Cadet");
        listView2.setVisibility(View.VISIBLE);
        listView1.setVisibility(View.GONE);
        listView3.setVisibility(View.GONE);

        mgtCadetBtn.setTextColor(getResources().getColor(R.color.white));
        mgtCadetBtn.setBackgroundResource(R.color.gray);

        mgtInsBtn.setTextColor(getResources().getColor(R.color.white));
        mgtInsBtn.setBackgroundResource(R.color.green);

        mgtANOBtn.setTextColor(getResources().getColor(R.color.white));
        mgtANOBtn.setBackgroundResource(R.color.green);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Manage2Activity.this, GPLogin.class));
        finish();
    }
}
