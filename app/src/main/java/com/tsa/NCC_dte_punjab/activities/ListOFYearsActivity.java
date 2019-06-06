package com.tsa.NCC_dte_punjab.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.TableUI.tableview.MainActivity;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ListOFYearsActivity extends CustomActivity {
    private ArrayList<String> listOfYears;
    private Context context;
    private Bundle bundle;
    public static String battalionID;
    private ListView listview;
    private StableArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ofyears);
        listview = findViewById(R.id.list_of_years);
        context = ListOFYearsActivity.this;

        ////////////////////////////////////////////////////////////////////////////////////////////
        bundle = getIntent().getExtras();
        if (bundle != null) {
            battalionID = bundle.getString("battalion_id");
        }

        getListOfYears(battalionID);
        ////////////////////////////////////////////////////////////////////////////////////////////

        listOfYears = new ArrayList<>();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String year = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("battalion_id", battalionID);
                intent.putExtra("enrollment_year", year);
                startActivity(intent);
                finish();
            }

        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getListOfYears(final String battalionId) {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "co_year_list_api.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.dismiss();
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");

                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("instt");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String yearsList;
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    yearsList = localJson.getString("enrollment_year");
                                    listOfYears.add(yearsList);
                                    ////////////////////////////////////////////////////////////////////////////////
                                }

                                adapter = new StableArrayAdapter(context,
                                        R.layout.years_list_card, listOfYears);
                                listview.setAdapter(adapter);
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
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent2 = new Intent(getApplicationContext(), COManageActivity.class);
        intent2.putExtra("battalion_id", battalionID);
        startActivity(intent2);
        finish();
    }
}
