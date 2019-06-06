package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.adaptor.NotificationAdapter;
import com.tsa.NCC_dte_punjab.models.NotificationModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class NotificationActivity extends CustomActivity {
    private RecyclerView chapList;
    private Context context;
    private NotificationAdapter notificationAdapter;
    private ArrayList<NotificationModel> notificationModelArrayList;
    public static String hqRegID;
    public static String hqID;
    public static String cadetsOnBatallion;
    public static String insttId;
    public static String battalionId;
    public static String anoId;
    public static String userType;
    private String go;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        bundle=getIntent().getExtras();
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (bundle != null) {
            hqRegID = bundle.getString("hq_reg_id");
            cadetsOnBatallion = bundle.getString("cadets_on_batallion");
            insttId = bundle.getString("instt_id");
            battalionId = bundle.getString("battalion_id");
            anoId = bundle.getString("ano_id");
            userType = bundle.getString("user_type");
            go=bundle.getString("go");
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        notificationModelArrayList=new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(NotificationActivity.this, R.color.light_vilate));
        }

        context=NotificationActivity.this;

        chapList = findViewById(R.id.chapter_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        chapList.setLayoutManager(mLayoutManager);
        chapList.setItemAnimator(new DefaultItemAnimator());

        getNotification();
    }

    ///////////////////////////////////
    public void getNotification() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "getAllAppNotification.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");

                            if (status.equals("0")) {
                                JSONArray notyList = json.getJSONArray("notify_data");

                                for (int i = 0; i < notyList.length(); i++) {
                                    JSONObject localJson = notyList.getJSONObject(i);
                                    final NotificationModel notificationModel = new NotificationModel();
                                    notificationModel.setmNid(localJson.getString("id"));
                                    notificationModel.setmName(localJson.getString("title"));

                                    notificationModel.setmDate(localJson.getString("notify_date"));
                                    notificationModel.setmDes(localJson.getString("des"));
                                    notificationModel.setmLink(localJson.getString("url"));
                                    notificationModelArrayList.add(notificationModel);
                                }
                                notificationAdapter=new NotificationAdapter(notificationModelArrayList,NotificationActivity.this);
                                chapList.setAdapter(notificationAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Showing toast
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters
                if (userType!=null)
                params.put("usertype", userType);
                if (battalionId!=null)
                params.put("battalian_id", battalionId);
               //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (go.equals("Manage2Activity")) {
            intent.setClass(context, Manage2Activity.class);
        }
        else if (go.equals("ManageActivity")) {
            intent.setClass(context, COManageActivity.class);
        }
        else if (go.equals("ManageActivity")) {
            intent.setClass(context, COManageActivity.class);
        }
        else if (go.equals("Profile")) {
            intent.setClass(context, HomeActivity.class);
        }
        else if (go.equals("CadetListActivity")) {
            intent.setClass(context, CadetListActivity.class);
        }
        else
        {
            intent.setClass(context, HomeActivity.class);
        }
        intent.putExtra("instt_id", insttId);
        intent.putExtra("battalion_id", battalionId);
        intent.putExtra("ano_id", anoId);
        intent.putExtra("user_type", userType);
        intent.putExtra("hq_reg_id", hqRegID);

        startActivity(intent);
        finish();
    }

}
