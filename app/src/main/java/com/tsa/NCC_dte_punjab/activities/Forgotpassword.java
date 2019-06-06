package com.tsa.NCC_dte_punjab.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;
import com.tsa.NCC_dte_punjab.validation.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Forgotpassword extends AppCompatActivity {

    EditText text_email;
    Button btn_forgot_submit;
    private String satus = "";
    MyDialogue myDialogue;

    public MyDialogue.DialogOnClickListener myListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgotpassword);

        text_email = (EditText) findViewById(R.id.text_email);
        myListener = new MyDialogue.DialogOnClickListener() {
            @Override
            public void onOkClick() {

                if(satus=="0"){
                    startActivity(new Intent(Forgotpassword.this,LoginRegActivity.class));
                    finish();}
                else
                {
                    myDialogue.dismiss();
                }
            }

            @Override
            public void onCancleClick() {
                myDialogue.dismiss();
            }
        };



        btn_forgot_submit = (Button) findViewById(R.id.btn_forgot_submit);

        btn_forgot_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (text_email.getText().toString().equals("")) {
                    Toast.makeText(Forgotpassword.this, "Please enter email address", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(text_email.getText().toString()).matches()) {
                    Toast.makeText(Forgotpassword.this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    ForgotPassword();

                }
            }

        });
    }


    private void ForgotPassword() {

        final ProgressDialog progress = new ProgressDialog(Forgotpassword.this);
        progress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "forgot_password.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            Log.d("Login", s.toString());
                            satus = json.getString("status");
                            String msg = json.getString("msg");

                            if (satus.equals("0")) {
                                Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Forgotpassword.this, LoginRegActivity.class));

                                }

                            if (satus.equals("1")) {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                                }

                            progress.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.dismiss();
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Showing toast
                        Toast.makeText(Forgotpassword.this, "Some issue in loading" + volleyError, Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("email", text_email.getText().toString());
                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(Forgotpassword.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

}








