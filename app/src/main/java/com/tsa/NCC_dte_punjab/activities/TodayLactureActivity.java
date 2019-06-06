package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.custom.MyWebViewClient;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Akhil Tripathi on 19-03-2018.
 */

public class TodayLactureActivity extends CustomActivity {
    private WebView webView;
    private Context context;
    private ArrayList<String> urlsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_show_pdf);
        context = TodayLactureActivity.this;
        init();
    }

    private void init() {

        webView=findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());

        urlsArray=new ArrayList<>();
        lacture();
        /////////////////////////////////////////////////////////////////////////
    }

    public void lacture() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "lectureListingAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            ///////////////////////////////////////////////////////////////

                            ///////////////////////////////////////////////////////////////
                            if (status.equals("0")) {
                                //////////////////////////////////////////////////////////////////////////////
                                JSONArray lactures = json.getJSONArray("lectures");
                                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                                for(int i=0;i<lactures.length();i++)
                                {
                                    JSONObject jsonObject=lactures.getJSONObject(i);
                                    urlsArray.add(jsonObject.getString("lec_path"));
                                }
                                ///////////////////////////////////////////////////////////////////////////

                                String url = "http://docs.google.com/gview?embedded=true&url=" +urlsArray.get(0);
                                webView.getSettings().setJavaScriptEnabled(true);
                                webView.loadUrl(url);
                            }
                            else
                            {
                                Toast.makeText(context, "No Lacture Found", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context,"Some issue in loading", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("ano_id", GLOBAL.cadetModel.getAnoID());
                params.put("wing", GLOBAL.cadetModel.getWing());
                params.put("ncc_year", GLOBAL.cadetModel.getNccYear());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(context,HomeActivity.class));
        finish();
    }
}
