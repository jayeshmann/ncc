package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.ANOModel;
import com.tsa.NCC_dte_punjab.models.COModel;
import com.tsa.NCC_dte_punjab.models.CadetDetailsModels;
import com.tsa.NCC_dte_punjab.models.HQModel;
import com.tsa.NCC_dte_punjab.models.PrincipalModel;
import com.tsa.NCC_dte_punjab.network.NetworkCheck;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;
import com.tsa.NCC_dte_punjab.validation.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class GPLogin extends CustomActivity {
    private Context context;
    private TextView headingTV;

    private Button submit;
    private EditText userET;
    private EditText passwordET;

    private String url;

    private ANOModel anoModel;

    private COModel coModel;
    private HQModel hqModel;
    private PrincipalModel principalModel;

    SharedPreferences sharedPref;
    private Gson gson;
    private TextView text_forgot;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_ncc);


        text_forgot = (TextView)findViewById(R.id.text_forgot);

        text_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgot = new Intent(GPLogin.this,Forgotpassword.class);
                startActivity(forgot);
            }
        });
        context = GPLogin.this;
        sharedPref = context.getSharedPreferences(
                "login", Context.MODE_PRIVATE);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        init();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void init() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        headingTV = findViewById(R.id.heading_tv);
        submit = findViewById(R.id.submit);
        userET = findViewById(R.id.user_name_et);
        userET.setHintTextColor(getResources().getColor(R.color.red));
        passwordET = findViewById(R.id.password_et);
        passwordET.setHintTextColor(getResources().getColor(R.color.red));
        ////////////////////////////////////////////////////////////////////////////////////////////
        {
            if (GLOBAL.LOGIN_TYPE == GLOBAL.DG_LOGIN) {
                headingTV.setText("Directorate Login");
                url = "dg_login.php";
            } else if (GLOBAL.LOGIN_TYPE == GLOBAL.ADG_LOGIN) {
                headingTV.setText("Directorate Login");
                url = "directorate_login.php";
            } else if (GLOBAL.LOGIN_TYPE == GLOBAL.GP_LOGIN) {
                headingTV.setText("Group Login");
                url = "gp_login.php";
            } else if (GLOBAL.LOGIN_TYPE == GLOBAL.CO_LOGIN) {
                headingTV.setText("Unit Login");
                url = "co_login.php";
            } else if (GLOBAL.LOGIN_TYPE == GLOBAL.ANO_LOGIN) {
                headingTV.setText("ANO Login");
                url = "ano_login.php";
            } else if (GLOBAL.LOGIN_TYPE == GLOBAL.CADET_LOGIN) {
                headingTV.setText("Cadet Login");
                // passwordET.setVisibility(View.GONE);
                url = "cadet_login.php";
            } else if (GLOBAL.LOGIN_TYPE == GLOBAL.PRINCIPAL_LOGIN) {
                headingTV.setText("Principal Login");
                url = "principal_login.php";
            }

            submit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        submitTask();
                        return true;
                    }
                    return false;
                }
            });
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitTask();
                }
            });

        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void dgLogin(final String userName, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        submit.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                submit.setText("Login");
                                disableInPut();
                                ///////////////////////////////////////////////////
                                hqModel = new HQModel();
                                hqModel.setStatus(json.getString("status"));
                                hqModel.setHqId(json.getString("hq_id"));
                                hqModel.setHqName(json.getString("hq_name"));
                                hqModel.setMsg(json.getString("msg"));
                                ////////////////////////////////////////////////////
                                storeToSharePre(userName, password, GLOBAL.DG_LOGIN);
                                ////////////////////////////////////////////////////
                                GLOBAL.isRegistered = false;
                                Intent intent3 = new Intent(getApplicationContext(), Manage1Activity.class);
                                clearInPut();
                                ///////////////////////////////////////////////////////////////////////////////////////////////
                                intent3.putExtra("hq_reg_id", hqModel.getHqId());
                                startActivity(intent3);
                                finish();
                            } else {

                                FancyToast.makeText(context,"Invalid Username/Password",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        submit.setEnabled(true);
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("username", userName);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void directorateLogin(final String userName, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        submit.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                GLOBAL.isRegistered = false;
                                GLOBAL.USER_TYPE= json.getString("user_type");
                                ////////////////////////////////////////////////////////////////////////////
                                Intent intent4 = new Intent(getApplicationContext(), Manage2Activity.class);
                                startActivity(intent4);
                                /////////////////////////////////////////////////////////////////////////////
                                finish();
                                }

                             else {
                                FancyToast.makeText(context,"Invalid Username/Password",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        submit.setEnabled(true);
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("username", userName);
                params.put("password", password);
                params.put("usr_typ_id",""+GLOBAL.LOGIN_TYPE_ID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void groupLogin(final String userName, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        submit.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");
                            GLOBAL.USER_TYPE= json.getString("user_type");

                            if (status.equals("0")) {
                                hqModel = new HQModel();
                                hqModel.setStatus(json.getString("status"));
                                hqModel.setHqId(json.getString("hq_id"));
                                hqModel.setHqName(json.getString("hq_name"));
                                hqModel.setMsg(json.getString("msg"));
                                submit.setText("Login");
                                disableInPut();
                                storeToSharePre(userName, password, GLOBAL.GP_LOGIN);
                                GLOBAL.isRegistered = false;

                                Intent intent4 = new Intent(getApplicationContext(), Manage2Activity.class);
                                clearInPut();
                                ///////////////////////////////////////////////////////////////////////////////////////////////
                                intent4.putExtra("hq_reg_id", hqModel.getHqId());
                                startActivity(intent4);
                                finish();
                            } else {
                                FancyToast.makeText(context,msg,FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        submit.setEnabled(true);
                        FancyToast.makeText(context,volleyError.getMessage(),FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("username", userName);
                params.put("password", password);
                params.put("usr_typ_id",""+GLOBAL.LOGIN_TYPE_ID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void coLogin(final String userName, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        submit.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg=json.getString("msg");
                            if (status.equals("0")) {
                                submit.setText("Login");
                                disableInPut();
                                GLOBAL.USER_TYPE= json.getString("user_type");
                                storeToSharePre(userName, password, GLOBAL.CO_LOGIN);
                                ///////////////////////////////////////////////////////////////////////////////////////////////
                                coModel = new COModel();
                                coModel.setStatus(json.getString("status"));
                                coModel.setBattalionId(json.getString("battalion_id"));
                                coModel.setBattalionName(json.getString("battalion_name"));
                                coModel.setMsg(json.getString("msg"));
                                GLOBAL.isRegistered = false;
                                ///////////////////////////////////////////////////////////////////////////////////////////////
                                Intent intent2 = new Intent(getApplicationContext(), COManageActivity.class);
                                clearInPut();
                                ///////////////////////////////////////////////////////////////////////////////////////////////
                                intent2.putExtra("battalion_id", coModel.getBattalionId());
                                startActivity(intent2);
                                finish();
                            } else {
                                FancyToast.makeText(context,msg,FancyToast.LENGTH_LONG,FancyToast.WARNING,true);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        submit.setEnabled(true);
                        FancyToast.makeText(context,volleyError.getMessage(),FancyToast.LENGTH_LONG,FancyToast.WARNING,true);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();

                params.put("username", userName);
                params.put("password", password);
                params.put("usr_typ_id", ""+GLOBAL.LOGIN_TYPE_ID);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void anoLogin(final String userName, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        submit.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");
                            if (status.equals("0")) {
                                ////////////////////////////////////////////////////////////////////

                                anoModel = new ANOModel();
                                anoModel.setStatus(json.getString("status"));
                                anoModel.setInsttId(json.getString("instt_id"));
                                anoModel.setBattalionId(json.getString("battalion_id"));
                                anoModel.setInsttName(json.getString("instt_name"));
                                anoModel.setMsg(json.getString("msg"));
                                anoModel.setAnoID(json.getString("ano_id"));
                                anoModel.setUserType(json.getString("user_type"));
                                ////////////////////////////////////////////////////////////////////
                                submit.setText("Login");
                                storeToSharePre(userName, "NONE", GLOBAL.ANO_LOGIN);
                                ////////////////////////////////////////////////////////////////////////////////
                                Intent intent = new Intent(getApplicationContext(), CadetListActivity.class);
                                intent.putExtra("instt_id", anoModel.getInsttId());
                                intent.putExtra("battalion_id", anoModel.getBattalionId());
                                intent.putExtra("ano_id", anoModel.getAnoID());
                                intent.putExtra("user_type", anoModel.getUserType());
                                startActivity(intent);
                                finish();
                                ////////////////////////////////////////////////////////////////////////////////
                                GLOBAL.isRegistered = false;
                                ////////////////////////////////////////////////////////////////////////////////
                                FancyToast.makeText(context, msg, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                            } else {
                                FancyToast.makeText(context,msg,FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        submit.setEnabled(true);
                        FancyToast.makeText(context,volleyError.getMessage(),FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("username", userName);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void cadetLogin(final String userName, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadet_login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        submit.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");

                            //GLOBAL.LOGIN_TYPE_ID=Integer.parseInt(json.getString("user_type"));

                            ///////////////////////////////////////////////////////////////
                            if (status.equals("0")) {
                                ///////////////////////////////////////////////////////////////
                                String hqName = json.getString("hq_name");
                                String battalionName = json.getString("battalion_name");
                                String insttName = json.getString("instt_name");
                                String directrateID = json.getString("directorate_id");
                                String anoID = json.getString("ano_id");
                                CadetDetailsModels cadetModel = gson.fromJson(json.getJSONObject("cadet_data").toString(), CadetDetailsModels.class);
                                //////////////////////////////////////////////////////////////////////////////
                                cadetModel.setHqName(hqName);
                                cadetModel.setHqName(battalionName);
                                cadetModel.setInsttName(insttName);
                                cadetModel.setDirectorateId(directrateID);
                                cadetModel.setAnoID(anoID);
                                cadetModel.setBattalionName(battalionName);
                                //cadetModel.setAno(anoID);

                                GLOBAL.cadetModel = cadetModel;
                                GLOBAL.isRegistered = true;
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                                ///////////////////////////////////////////////////////////////////////////
                            } else {
                                FancyToast.makeText(context, msg, FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        submit.setEnabled(true);
                        FancyToast.makeText(context,volleyError.getMessage(),FancyToast.LENGTH_LONG,FancyToast.WARNING,true);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("userid", userName);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void principleLogin(final String userName, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        submit.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                disableInPut();
                                storeToSharePre(userName, password, GLOBAL.PRINCIPAL_LOGIN);
                                ////////////////////////////////////////////////////////////////////
                                principalModel = new PrincipalModel();
                                ////////////////////////////////////////////////////////////////////
                                principalModel.setStatus(json.getString("status"));
                                principalModel.setBattalionId(json.getString("battalion_id"));
                                principalModel.setInsttName(json.getString("instt_name"));
                                principalModel.setInsttId(json.getString("instt_id"));
                                principalModel.setMsg(json.getString("msg"));
                                ////////////////////////////////////////////////////////////////////
                                GLOBAL.isRegistered = false;
                                ///////////////////////////////////////////////////////////////////////////
                                Intent intent1 = new Intent(getApplicationContext(), ANOListActivity.class);
                                clearInPut();
                                ///////////////////////////////////////////////////////////////////////////////////////////////
                                intent1.putExtra("instt_id", principalModel.getInsttId());
                                startActivity(intent1);
                                finish();
                            } else {
                                FancyToast.makeText(context,"Invalid Username/Password",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        submit.setEnabled(true);
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("username", userName);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(userET)) ret = false;
        if (!Validation.hasText(passwordET)) ret = false;
        return ret;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void disableInPut() {
        userET.setEnabled(false);
        userET.setEnabled(false);
        passwordET.setEnabled(false);
        passwordET.setEnabled(false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void clearInPut() {
        userET.setText("");
        userET.setText("");
        passwordET.setText("");
        passwordET.setText("");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        GLOBAL.cadetModel = null;
        startActivity(new Intent(GPLogin.this, HomeActivity.class));
        finish();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void storeToSharePre(String userName, String pwd, int loginType) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("u_name", userName);
        editor.putString("pwd", pwd);
        editor.putInt("type", loginType);
        editor.commit();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void submitTask() {
        if (NetworkCheck.isNetworkAvailable(context)) {
            ////////////////////////////////////////
            String userName = "" + userET.getText();
            String password = "" + passwordET.getText();
            ////////////////////////////////////////
            switch (GLOBAL.LOGIN_TYPE) {
                case GLOBAL.DG_LOGIN:
                    if (checkValidation()) {
                        submit.setEnabled(false);
                        dgLogin(userName, password);
                    }
                    break;
                case GLOBAL.ADG_LOGIN:
                    if (checkValidation()) {
                        submit.setEnabled(false);
                        directorateLogin(userName, password);
                    }
                    break;
                case GLOBAL.GP_LOGIN:
                    if (checkValidation()) {
                        submit.setEnabled(false);
                        groupLogin(userName, password);
                    }
                    break;
                case GLOBAL.CO_LOGIN:
                    if (checkValidation()) {
                        submit.setEnabled(false);
                        coLogin(userName, password);
                    }
                    break;
                case GLOBAL.ANO_LOGIN: {
                    submit.setEnabled(false);
                    anoLogin(userName, password);
                }
                break;
                case GLOBAL.CADET_LOGIN: {
                    submit.setEnabled(false);
                    cadetLogin(userName, password);
                }
                break;
                case GLOBAL.PRINCIPAL_LOGIN:
                    if (checkValidation()) {
                        submit.setEnabled(false);
                        principleLogin(userName, password);
                    }
                    break;
            }
        } else {
            FancyToast.makeText(context, "Network Not Available", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }
}


