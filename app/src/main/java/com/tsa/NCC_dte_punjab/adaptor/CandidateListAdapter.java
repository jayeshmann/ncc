package com.tsa.NCC_dte_punjab.adaptor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.tsa.NCC_dte_punjab.activities.Manage1Activity;
import com.tsa.NCC_dte_punjab.activities.Manage2Activity;
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

public class CandidateListAdapter extends RecyclerView.Adapter<CandidateListAdapter.ViewHolder> {

    private ArrayList<CadetDetailsModels> candidateListModels;
    private Context context;
    ArrayList anoArrayList;

    public CandidateListAdapter(ArrayList<CadetDetailsModels> candidateListModels, Context context) {
        this.candidateListModels = candidateListModels;
        this.context = context;
        anoArrayList=new ArrayList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidate_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        final CadetDetailsModels candidateListModel = candidateListModels.get(position);
            holder.canNameTV.setText("" + candidateListModel.getName());
            holder.instituteTV.setText("" + candidateListModel.getInsttName());
            holder.HQTV.setText("" + candidateListModel.getHqName());
            holder.battailanTV.setText("" + candidateListModel.getBattalionName());
            holder.emailTV.setText("" + candidateListModel.getEmail());
        ////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////////////////
        if(GLOBAL.LOGIN_TYPE==GLOBAL.ANO_LOGIN)
        {
            if (candidateListModel.getCoAprovStatus().equals("2") && candidateListModel.getAnoRecomStatus().equals("2")) {
                holder.approved.setBackgroundResource(R.drawable.green_button);
                holder.approved.setVisibility(View.VISIBLE);
                holder.approved.setText(context.getResources().getString(R.string.recommanded));
            } else if (candidateListModel.getCoAprovStatus().equals("0") && candidateListModel.getAnoRecomStatus().equals("0")) {
                holder.approved.setBackgroundResource(R.drawable.red_button);
                holder.approved.setTextColor(context.getResources().getColor(R.color.white));
                holder.approved.setVisibility(View.VISIBLE);
                holder.approved.setText(context.getResources().getString(R.string.recommand));
            }
            else
            {
                holder.approved.setBackgroundResource(R.drawable.yellow_button);
                holder.approved.setVisibility(View.VISIBLE);
                holder.approved.setText(context.getResources().getString(R.string.wating));
            }
        }
        else if(GLOBAL.LOGIN_TYPE==GLOBAL.ADG_LOGIN||GLOBAL.LOGIN_TYPE==GLOBAL.GP_LOGIN)
        {
            if(candidateListModel.getAnoRecomStatus().equals("0"))
            {
                holder.approved.setText(context.getResources().getString(R.string.wating));
                holder.approved.setBackgroundResource(R.drawable.yellow_button);
            }
            else if(candidateListModel.getAnoRecomStatus().equals("1"))
            {
                holder.approved.setText(context.getResources().getString(R.string.recommanded));
                holder.approved.setBackgroundResource(R.drawable.green_button);
            }
            else if(candidateListModel.getAnoRecomStatus().equals("2"))
            {
                holder.approved.setText(context.getResources().getString(R.string.approved));
                holder.approved.setBackgroundResource(R.drawable.green_button);
            }
            holder.approved.setEnabled(false);
        }
        //////////////////////////////////////////////////
        else if(GLOBAL.LOGIN_TYPE==GLOBAL.CO_LOGIN) {
            if (candidateListModel.getCoAprovStatus().equals("2") && candidateListModel.getAnoRecomStatus().equals("2")) {
                holder.approved.setBackgroundResource(R.drawable.green_button);
                holder.approved.setVisibility(View.VISIBLE);
                holder.approved.setEnabled(false);
                holder.approved.setText(context.getResources().getString(R.string.approved));

            } else if (candidateListModel.getCoAprovStatus().equals("0") && candidateListModel.getAnoRecomStatus().equals("0")) {
                holder.approved.setBackgroundResource(R.drawable.yellow_button);
                holder.approved.setVisibility(View.VISIBLE);
                holder.approved.setText(context.getResources().getString(R.string.wating));
                holder.approved.setEnabled(false);
            }
            else
            {
                holder.approved.setBackgroundResource(R.drawable.red_button);
                holder.approved.setTextColor(context.getResources().getColor(R.color.white));
                holder.approved.setVisibility(View.VISIBLE);
                holder.approved.setText(context.getResources().getString(R.string.approve));
                holder.approved.setEnabled(true);
            }

            holder.approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (GLOBAL.LOGIN_TYPE == GLOBAL.ANO_LOGIN && candidateListModel.getCoAprovStatus().equals("2") && candidateListModel.getAnoRecomStatus().equals("2"))
                    {
                        //recommand(candidateListModel);
                        recommand(candidateListModel);
                    }
                    if (GLOBAL.LOGIN_TYPE == GLOBAL.CO_LOGIN && candidateListModel.getCoAprovStatus().equals("1") && candidateListModel.getAnoRecomStatus().equals("1"))
                    {
                          createApproveDialogue(candidateListModel);
                    }
                }
            });
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        if (position % 2 == 0)
            holder.root.setBackgroundResource(R.drawable.red_background);
        else
            holder.root.setBackgroundResource(R.drawable.green_background);
        ////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProfileActivity.class);
                GLOBAL.cadetModel=candidateListModel;

                //battalionId = bundle.getString("battalion_id");
                intent.putExtra("battalion_id",candidateListModels.get(position).getBattalionId());
                intent.putExtra("goTo",context.getClass().getName());
                if (context instanceof Manage2Activity)
                intent.putExtra("hq_reg_id", Manage2Activity.hqId);
                if (context instanceof Manage1Activity)
                    intent.putExtra("hq_reg_id", Manage1Activity.hqID);
                context.startActivity(intent);
                //finish();

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
        return candidateListModels.size();
    }
    /////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////
    public void approveANO(final CadetDetailsModels dgLoginModel) {
        final ProgressDialog progressBar=new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "co_aprov_status.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.dismiss();
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");
                            ANOAppModel anoAppModel=new ANOAppModel();
                            if (status.equals("0")) {
                                ///////////////////////////////////////////////////////////////////////
                                anoAppModel.setStatus(json.getString("status"));
                                anoAppModel.setCadetId(json.getString("cadet_id"));
                                anoAppModel.setRegisterId(json.getString("register_id"));
                                anoAppModel.setAnoRecomStatus(json.getString("ano_recom_status"));
                                anoAppModel.setCoAprovStatus(json.getString("co_aprov_status"));
                                anoAppModel.setMsg(json.getString("msg"));
                                ///////////////////////////////////////////////////////////////////////
                            }

                            FancyToast.makeText(context,""+msg,FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();


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
                params.put("cadet_id", dgLoginModel.getCadetId());
                params.put("register_id", dgLoginModel.getRegisterId());
                params.put("co_aprov_status", dgLoginModel.getCoAprovStatus());
                params.put("instt_id", dgLoginModel.getInsttId());
                params.put("registration_no",dgLoginModel.getRegistrationNo());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////
    public void recommand(final CadetDetailsModels dgLoginModel) {
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
                                ////////////////////////////////////////////
                                anoAppModel.setStatus(json.getString("status"));
                                anoAppModel.setCadetId(json.getString("cadet_id"));
                                anoAppModel.setRegisterId(json.getString("register_id"));
                                anoAppModel.setAnoRecomStatus(json.getString("ano_recom_status"));
                                anoAppModel.setCoAprovStatus(json.getString("co_aprov_status"));
                                anoAppModel.setMsg(json.getString("msg"));
                                ///////////////////////////////////////////////////////////////////////
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
                params.put("cadet_id", dgLoginModel.getCadetId());
                params.put("register_id", dgLoginModel.getRegisterId());
                params.put("co_aprov_status", dgLoginModel.getCoAprovStatus());
                params.put("instt_id", dgLoginModel.getInsttId());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////
    public void createApproveDialogue(final CadetDetailsModels candidateListModel) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.enrollment_dialogue);
        final EditText enrollmentNumberET=dialog.findViewById(R.id.enroll_no);
        Button okButton = dialog.findViewById(R.id.ok_btn);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                candidateListModel.setRegistrationNo(""+enrollmentNumberET.getText());
                approveANO(candidateListModel);
                dialog.cancel();
            }
        });

        dialog.show();
    }
    ////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView canNameTV;
        TextView instituteTV;
        TextView HQTV;
        TextView battailanTV;
        TextView emailTV;
        LinearLayout root;
        Button approved;

        public ViewHolder(View view) {
            super(view);
            canNameTV = view.findViewById(R.id.name_tv);
            instituteTV = view.findViewById(R.id.institute_tv);
            HQTV = view.findViewById(R.id.group_hq__tv);
            battailanTV = view.findViewById(R.id.battalion_tv);
            emailTV = view.findViewById(R.id.email1_tv);
            root = view.findViewById(R.id.root);
            approved = view.findViewById(R.id.approved);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////
}
