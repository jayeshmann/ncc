package com.tsa.NCC_dte_punjab.adaptor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.tsa.NCC_dte_punjab.activities.CadetsOnInstituteActivity;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.ANOAppModel;
import com.tsa.NCC_dte_punjab.models.CadesOnInsModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Akhil Tripathi on 24-01-2018.
 */

public class CandidateOnInsAdapter1 extends RecyclerView.Adapter<CandidateOnInsAdapter1.ViewHolder> {

    private ArrayList<CadesOnInsModel> cadesOnInsModels;
    private Context context;
    ArrayList anoArrayList;

    public CandidateOnInsAdapter1(ArrayList<CadesOnInsModel> cadesOnInsModels, Context context) {
        this.cadesOnInsModels = cadesOnInsModels;
        this.context = context;
        anoArrayList=new ArrayList();
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
        final CadesOnInsModel cadesOnInsModel = cadesOnInsModels.get(position);
            holder.canNameTV.setText("" + cadesOnInsModel.getName());
            holder.instituteTV.setText("" + cadesOnInsModel.getInsttName());
            holder.HQTV.setText("" + cadesOnInsModel.getHqName());
            holder.emailTV.setText("" + cadesOnInsModel.getEmail());
            holder.battailanTV.setText("" + cadesOnInsModel.getBattalionName());
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (position % 2 == 0)
            holder.root.setBackgroundResource(R.drawable.red_background);
        else
            holder.root.setBackgroundResource(R.drawable.green_background);
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        if(cadesOnInsModel.getAnoRecomStatus().equals("0")&&cadesOnInsModel.getCoAprovStatus().equals("0"))
        {
            holder.approved.setBackgroundResource(R.drawable.yellow_button);
            holder.approved.setText("Pending");
            holder.approved.setEnabled(false);
        }
        else if(cadesOnInsModel.getAnoRecomStatus().equals("1")&&cadesOnInsModel.getCoAprovStatus().equals("1"))
        {
            holder.approved.setBackgroundResource(R.drawable.green_button);
            holder.approved.setText("Recommended");
            holder.approved.setEnabled(true);
        }
        else if(cadesOnInsModel.getAnoRecomStatus().equals("2")&&cadesOnInsModel.getCoAprovStatus().equals("2"))
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
                    //recommand(cadesOnInsModel);
                    createApproveDialogue(cadesOnInsModel);
                }
            });

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   Intent intent=new Intent(context, ProfileActivity.class);
                intent.putExtra("goTo",""+context.getClass().getName());
                if (context instanceof CadetListActivity) {
                    intent.putExtra("hq_reg_id", CadetListActivity.hqRegID);
                    intent.putExtra("cadets_on_batallion", CadetListActivity.cadetsOnBatallion);
                    intent.putExtra("instt_id", CadetListActivity.insttId);
                    intent.putExtra("battalion_id", CadetListActivity.battalionId);
                }

                context.startActivity(intent);

                GLOBAL.cadetModel=new CadetDetailsModels();
                GLOBAL.cadetModel.setAadhaarNo(cadesOnInsModel.getAadhaarNo());
                GLOBAL.cadetModel.setBattalionName(cadesOnInsModel.getBattalionName());
                GLOBAL.cadetModel.setHqName(cadesOnInsModel.getHqName());
                GLOBAL.cadetModel.setInsttName(cadesOnInsModel.getInsttName());
                GLOBAL.cadetModel.setName(cadesOnInsModel.getName());
                GLOBAL.cadetModel.setMobile(cadesOnInsModel.getMobile());
                GLOBAL.cadetModel.setEmail(cadesOnInsModel.getEmail());
                GLOBAL.cadetModel.setmSchoolName(cadesOnInsModel.getSchoolCollage());
                GLOBAL.cadetModel.setGender(cadesOnInsModel.getGender());
                GLOBAL.cadetModel.setMaritalStatus(cadesOnInsModel.getMaritalStatus());

                GLOBAL.cadetModel.setmBankName(cadesOnInsModel.getmBankName());
                GLOBAL.cadetModel.setmIfscCode(cadesOnInsModel.getmIFSC());
                GLOBAL.cadetModel.setmAccountNo(cadesOnInsModel.getmAccountNo());
*/
            }
        });
        }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
        return cadesOnInsModels.size();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////
    public void recommand(final CadesOnInsModel dgLoginModel) {
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
                                ((CadetsOnInstituteActivity) context).anoCadetList(dgLoginModel.getInsttId(), dgLoginModel.getBattalionId(), 1, 0, "YES");
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
                        FancyToast.makeText(context,"Some Issue in Login",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("cadet_id", dgLoginModel.getCadetId());
                params.put("register_id", dgLoginModel.getRegisterId());
                params.put("co_aprov_status", dgLoginModel.getCoAprovStatus());
                params.put("instt_id", dgLoginModel.getInsttId());
                params.put("registration_no", dgLoginModel.getRegistrationNo());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void createApproveDialogue(final CadesOnInsModel candidateListModel) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.enrollment_dialogue);
        final EditText enrollmentNumberET = dialog.findViewById(R.id.enroll_no);
        Button okButton = dialog.findViewById(R.id.ok_btn);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                candidateListModel.setRegistrationNo("" + enrollmentNumberET.getText());
                recommand(candidateListModel);
                dialog.cancel();
            }
        });

        dialog.show();
    }
    ////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////
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
}
