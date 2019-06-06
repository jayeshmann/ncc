package com.tsa.NCC_dte_punjab.adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.activities.LactureListActivity;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.LactureListModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Akhil Tripathi on 24-01-2018.
 */

public class LactureListAdapter extends RecyclerView.Adapter<LactureListAdapter.ViewHolder> {

    private ArrayList<LactureListModel> lactureListModels;
    private Context context;

    ////////////////////////////////////////////////////////////////////////////////////
    public LactureListAdapter(ArrayList<LactureListModel> lactureListModels, Context context) {
        this.context = context;
        this.lactureListModels = lactureListModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lacture_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        final LactureListModel lactureListModel = lactureListModels.get(position);

        holder.lactureNameTV.setText("" + lactureListModel.getLname());
        holder.dateTV.setText("" + lactureListModel.getLdate());

        holder.locationCB.setChecked(lactureListModel.isGeoTgged());
        holder.cameraCB.setChecked(lactureListModel.isImageUploaded());
        holder.wing.setText(""+lactureListModel.getWing());

        holder.cameraIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lactureListModel.isGeoTgged()) {
                    if (context instanceof LactureListActivity) {
                        if (!lactureListModel.isImageCaptured())
                        {
                            ((LactureListActivity)context).selectImage(lactureListModels,position);
                        }
                    }
                }
                else
                {
                    if (lactureListModel.isImageCaptured()&&lactureListModel.isGeoTagApp())
                    {
                        uploadImage(lactureListModels.get(position));
                    }
                    FancyToast.makeText(context,"Image Allready Uploaded",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
                }
            }
        });

        holder.locationIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!lactureListModel.isImageUploaded()) {
                    if (context instanceof LactureListActivity) {
                        if (!lactureListModel.isGeoTagApp())
                        {
                            ((LactureListActivity)context).getLatLng(lactureListModels,position);
                        }
                    }

                }
                else
                {
           FancyToast.makeText(context,"Allready Geo Tagged",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
                }

            }
        });

        holder.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!lactureListModel.isImageUploaded()) {
                    if (context instanceof LactureListActivity) {
                        if (lactureListModel.isImageCaptured()&&lactureListModel.isGeoTagApp())
                        {
                            uploadImage(lactureListModels.get(position));
                        }
                    }
                }
                else
                {
                    FancyToast.makeText(context,"Allready Submitted",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
                }

            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
        return lactureListModels.size();
    }
    /////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder {
        /////////////////////////
        TextView lactureNameTV;
        TextView dateTV;
        TextView wing;
        /////////////////////////
        ImageView cameraIV;
        ImageView locationIV;
        ////////////////////////
        /////////////////////////
        CheckBox cameraCB;
        CheckBox locationCB;
        /////////////////////////
        Button submitBtn;


        public ViewHolder(View view) {
            super(view);

            submitBtn=view.findViewById(R.id.submit_btn);
            ///////////////////////////////////////////////////
            lactureNameTV = view.findViewById(R.id.lacture_name_tv);
            dateTV = view.findViewById(R.id.date_tv);
            wing=view.findViewById(R.id.wing_name);
            //////////////////////////////////////////////////////
            cameraIV = view.findViewById(R.id.camera_iv);
            locationIV = view.findViewById(R.id.location_iv);
            //////////////////////////////////////////////////////
            //////////////////////////////////////////////////////
            cameraCB = view.findViewById(R.id.image_uploaded_cb);
            locationCB = view.findViewById(R.id.geo_tagged_cb);
            //////////////////////////////////////////////////////
        }
    }


    public void uploadImage(final LactureListModel lactureListModel) {
        final ProgressDialog progressBar=new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "uploadLectureImageAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg=json.getString("msg");
                            Log.e("json",json.toString());
                            if (status.equals("0")) {
                                if (context instanceof LactureListActivity) {
                                    ((LactureListActivity)context).getLactures();
                                }
                            }
                            else {
                                FancyToast.makeText(context, "" + msg, FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
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
                        FancyToast.makeText(context ,"Some issue in loading" ,FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("lecture_id", ""+lactureListModel.getLid());
                params.put("image_name", lactureListModel.getImageCapturedApp());
                params.put("lat", lactureListModel.getLat());
                params.put("lng", lactureListModel.getLng());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}
