package com.tsa.NCC_dte_punjab.adaptor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.activities.CadetListActivity;
import com.tsa.NCC_dte_punjab.activities.ProfileActivity;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.ANOAppModel;
import com.tsa.NCC_dte_punjab.models.CadetDetailsModels;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Akhil Tripathi on 24-01-2018.
 */

public class CandidateOnInsAdapter extends RecyclerView.Adapter<CandidateOnInsAdapter.ViewHolder> {

    private ArrayList<CadetDetailsModels> cadesOnInsModels;
    private Context context;

    public CandidateOnInsAdapter(ArrayList<CadetDetailsModels> cadesOnInsModels, Context context) {
        this.cadesOnInsModels = cadesOnInsModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidate_card, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ////////////////////////////////////////////////////////////////////////////////////////////
        final CadetDetailsModels cadesOnInsModel = cadesOnInsModels.get(position);
            holder.canNameTV.setText("" + cadesOnInsModel.getName());
            holder.instituteTV.setText("" + cadesOnInsModel.getInsttName());
            holder.HQTV.setText("" + cadesOnInsModel.getHqName());
        holder.emailTV.setText("" + cadesOnInsModel.getEmail());
            holder.battailanTV.setText("" + cadesOnInsModel.getBattalionName());
        holder.expiryDate.setText("" + cadesOnInsModel.getExpiryDate());
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (GLOBAL.LOGIN_TYPE == GLOBAL.ANO_LOGIN)
            holder.expiryRoot.setVisibility(View.VISIBLE);
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (position % 2 == 0)
            holder.root.setBackgroundResource(R.drawable.red_background);
        else
            holder.root.setBackgroundResource(R.drawable.green_background);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        if (cadesOnInsModel.getAnoRecomStatus().equals("0") && cadesOnInsModel.getCoAprovStatus().equals("0"))
        {
            holder.approved.setBackgroundResource(R.drawable.yellow_button);
            holder.approved.setText("Pending");
            holder.approved.setEnabled(true);
        } else if (cadesOnInsModel.getAnoRecomStatus().equals("1") && cadesOnInsModel.getCoAprovStatus().equals("1"))
        {
            holder.approved.setBackgroundResource(R.drawable.green_button);
            holder.approved.setText("Recommended");
            holder.approved.setEnabled(false);
        } else if (cadesOnInsModel.getAnoRecomStatus().equals("2") && cadesOnInsModel.getCoAprovStatus().equals("2"))
        {
            holder.approved.setBackgroundResource(R.drawable.green_button);
            holder.approved.setText("Approved");
            holder.approved.setEnabled(false);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////

            holder.approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    createPendingDialogue(cadesOnInsModel);
                }
            });

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ProfileActivity.class);
                intent.putExtra("goTo",""+context.getClass().getName());
                if (context instanceof CadetListActivity) {
                    intent.putExtra("hq_reg_id", CadetListActivity.hqRegID);
                    intent.putExtra("cadets_on_batallion", CadetListActivity.cadetsOnBatallion);
                    intent.putExtra("instt_id", CadetListActivity.insttId);
                    intent.putExtra("battalion_id", CadetListActivity.battalionId);
                }

                context.startActivity(intent);

                GLOBAL.cadetModel=cadesOnInsModel;

            }
        });
        }
    ////////////////////////////////////////////////////////////////////////////////////////////////
   /////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
        return cadesOnInsModels.size();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////
    public void recommand(final CadetDetailsModels cadesOnInsModel) {
        final ProgressDialog progressBar=new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "ano_recom_status.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.dismiss();
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            ANOAppModel anoAppModel=new ANOAppModel();
                            if (status.equals("0")) {
                                ////////////////////////////////////////////////////////////////////////
                                anoAppModel.setStatus(json.getString("status"));
                                anoAppModel.setCadetId(json.getString("cadet_id"));
                                anoAppModel.setRegisterId(json.getString("register_id"));
                                anoAppModel.setAnoRecomStatus(json.getString("ano_recom_status"));
                                anoAppModel.setCoAprovStatus(json.getString("co_aprov_status"));
                                anoAppModel.setMsg(json.getString("msg"));
                                ////////////////////////////////////////////////////////////////////////

                                if (GLOBAL.LOGIN_TYPE==GLOBAL.ANO_LOGIN)
                                {
                                    if(context instanceof CadetListActivity){
                                        ((CadetListActivity)context).anoCadetList(cadesOnInsModel.getInsttId(),cadesOnInsModel.getBattalionId(),1,0,"");
                                    }
                                }
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
                params.put("cadet_id", cadesOnInsModel.getCadetId());
                params.put("register_id", cadesOnInsModel.getRegisterId());
                params.put("co_aprov_status", cadesOnInsModel.getAnoRecomStatus());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    public void createPendingDialogue(final CadetDetailsModels dgLoginModel) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.pending_dialogue);
        Button okButton = dialog.findViewById(R.id.ok_btn);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommand(dgLoginModel);
                dialog.cancel();
            }
        });
        Button cancleButton = dialog.findViewById(R.id.cancle_btn);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    ///////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView canNameTV;
        TextView instituteTV;
        TextView HQTV;
        TextView battailanTV;
        TextView emailTV;
        TextView expiryDate;
        LinearLayout expiryRoot;
        LinearLayout root;
        Button approved;

        public ViewHolder(View view) {
            super(view);
            canNameTV = view.findViewById(R.id.name_tv);
            instituteTV = view.findViewById(R.id.institute_tv);
            HQTV = view.findViewById(R.id.group_hq__tv);
            emailTV = view.findViewById(R.id.email1_tv);
            battailanTV = view.findViewById(R.id.battalion_tv);
            root = view.findViewById(R.id.root);
            approved = view.findViewById(R.id.approved);
            expiryDate = view.findViewById(R.id.exp_tv);
            expiryRoot = view.findViewById(R.id.exp_root);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////
}
