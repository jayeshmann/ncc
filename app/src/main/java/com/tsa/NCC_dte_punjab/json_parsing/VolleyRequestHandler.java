package com.tsa.NCC_dte_punjab.json_parsing;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

public class VolleyRequestHandler extends StringRequest implements VolleyRequestHandlerIF, Response.Listener<String>,Response.ErrorListener{
    private Context context;
    int method;
    String url;
    Response.Listener<String> listener;
    Response.ErrorListener errorListener;
    int flag=0;
    private Map params;

    public VolleyRequestHandler(Context context, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Map map) {
        super(method, url, listener, errorListener);
        this.context = context;
        this.method = method;
        this.url = url;
        this.listener =this;
        this.errorListener =this;
        this.params=map;
    }

    @Override
    public String getApiResponse(int flag) {
        this.flag=flag;
        if (context instanceof VolleyRequestHandlerIF) {
            createRequest();
        } else {
            return "Class is not the instance of VolleyRequestHandlerIF";
        }
        return "Waiting For Response";
    }

    @Override
    public void setApiResponse(Map response) {
        VolleyRequestHandlerIF volleyRequestHandlerIF=(VolleyRequestHandlerIF)context;
        volleyRequestHandlerIF.setApiResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    private void createRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(new VolleyRequestHandler(context, method, url, listener, errorListener,params));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("TAG", error.getMessage() );
    }

    @Override
    public void onResponse(String response) {
        Map responseMap=new Hashtable();
        responseMap.put("response_string",response);
        responseMap.put("response_code",this.flag);
        setApiResponse(responseMap);
    }
}
