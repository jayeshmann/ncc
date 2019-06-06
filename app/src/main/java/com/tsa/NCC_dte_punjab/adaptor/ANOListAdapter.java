package com.tsa.NCC_dte_punjab.adaptor;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.activities.ANOProfileActivity;
import com.tsa.NCC_dte_punjab.activities.COManageActivity;
import com.tsa.NCC_dte_punjab.activities.Manage2Activity;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.ANOAppModel;
import com.tsa.NCC_dte_punjab.models.ANOListModel;
import com.tsa.NCC_dte_punjab.models.CORelatedModels.ANORecModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Akhil Tripathi on 24-01-2018.
 */

public class ANOListAdapter extends RecyclerView.Adapter<ANOListAdapter.ViewHolder> {

    private ArrayList<ANOListModel> anoListModels;
    private Context context;
    ArrayList anoArrayList;
    private String toastMsg = "";

    ////////////////////////////////////////////////////////////////////////////////////
    public ANOListAdapter(ArrayList<ANOListModel> anoListModels, Context context) {
        this.anoListModels = anoListModels;
        this.context = context;
        anoArrayList = new ArrayList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ano_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        final ANOListModel anoListModel = anoListModels.get(position);
        holder.nameTV.setText("" + anoListModel.getName());
        holder.emailTV.setText("" + anoListModel.getEmail());
        holder.mobileTV.setText("" + anoListModel.getMobile());
        holder.instituteTV.setText("" + anoListModel.getInsttName());
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (position % 2 == 0)
            holder.root.setBackgroundResource(R.drawable.red_background);
        else
            holder.root.setBackgroundResource(R.drawable.green_background);
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (GLOBAL.LOGIN_TYPE == GLOBAL.PRINCIPAL_LOGIN) {
            if (anoListModel.getPrincipalRecomStatus().equals("0")) {
                holder.approved.setBackgroundResource(R.drawable.yellow_button);
                holder.approved.setText(context.getResources().getString(R.string.recommand));
                holder.approved.setEnabled(true);
                toastMsg = "Successfully recommended to CO";
            }
            else if (anoListModel.getPrincipalRecomStatus().equals("4")) {
                holder.approved.setBackgroundResource(R.drawable.green_button);
                holder.approved.setText(context.getResources().getString(R.string.approved));
                holder.approved.setEnabled(false);
                //toastMsg = "Successfully recommended to CO";
            }
            else {
                holder.approved.setBackgroundResource(R.drawable.green_button);
                holder.approved.setText(context.getResources().getString(R.string.recommanded));
                holder.approved.setEnabled(false);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        else if (GLOBAL.LOGIN_TYPE == GLOBAL.CO_LOGIN) {
            if (anoListModel.getCoRecomStatus().equals("0")) {
                holder.approved.setBackgroundResource(R.drawable.yellow_button);
                holder.approved.setText(context.getResources().getString(R.string.wating));
                holder.approved.setEnabled(false);
            } else if (anoListModel.getCoRecomStatus().equals("1")) {
                holder.approved.setBackgroundResource(R.drawable.red_button);
                holder.approved.setTextColor(context.getResources().getColor(R.color.white));
                holder.approved.setText(context.getResources().getString(R.string.recommand));
                holder.approved.setEnabled(true);
                toastMsg = "Successfully recommended to Gp Cdr";
            } else if (anoListModel.getCoRecomStatus().equals("4")) {
                holder.approved.setBackgroundResource(R.drawable.green_button);
                holder.approved.setText(context.getResources().getString(R.string.approved));
                holder.approved.setEnabled(false);
            } else {
                holder.approved.setBackgroundResource(R.drawable.green_button);
                holder.approved.setText(context.getResources().getString(R.string.recommanded));
                holder.approved.setEnabled(false);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        else if (GLOBAL.LOGIN_TYPE == GLOBAL.GP_LOGIN) {
            if (anoListModel.getGpRecomStatus().equals("2")) {
                holder.approved.setBackgroundResource(R.drawable.red_button);
                holder.approved.setTextColor(context.getResources().getColor(R.color.white));
                holder.approved.setText(context.getResources().getString(R.string.recommand));
                holder.approved.setEnabled(true);
                toastMsg = "Successfully " + context.getResources().getString(R.string.recommand) + " to ADG";
            } else if (anoListModel.getGpRecomStatus().equals("3")) {
                holder.approved.setBackgroundResource(R.drawable.green_button);
                holder.approved.setText(context.getResources().getString(R.string.recommanded));
                holder.approved.setEnabled(false);
            } else if (anoListModel.getGpRecomStatus().equals("4")) {
                holder.approved.setBackgroundResource(R.drawable.green_button);
                holder.approved.setText(context.getResources().getString(R.string.approved));
                holder.approved.setEnabled(false);
            } else {
                holder.approved.setBackgroundResource(R.drawable.yellow_button);
                holder.approved.setText(context.getResources().getString(R.string.wating));
                holder.approved.setEnabled(false);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        else if (GLOBAL.LOGIN_TYPE == GLOBAL.ADG_LOGIN) {
            if (anoListModel.getGpRecomStatus().equals("3")) {
                holder.approved.setBackgroundResource(R.drawable.red_button);
                holder.approved.setTextColor(context.getResources().getColor(R.color.white));
                holder.approved.setText(context.getResources().getString(R.string.approve));
                holder.approved.setEnabled(true);
                toastMsg = "Successfully " + context.getResources().getString(R.string.approved);
            } else if (anoListModel.getGpRecomStatus().equals("4")) {
                holder.approved.setBackgroundResource(R.drawable.green_button);
                holder.approved.setText(context.getResources().getString(R.string.approved));
                holder.approved.setEnabled(false);
            } else {
                holder.approved.setBackgroundResource(R.drawable.yellow_button);
                holder.approved.setText(context.getResources().getString(R.string.wating));
                holder.approved.setEnabled(false);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        holder.approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GLOBAL.LOGIN_TYPE == GLOBAL.PRINCIPAL_LOGIN) {
                    approveANOBYPrincipal(anoListModel);
                } else if (GLOBAL.LOGIN_TYPE == GLOBAL.CO_LOGIN) {
                    approveByCO(anoListModel);
                } else if (GLOBAL.LOGIN_TYPE == GLOBAL.GP_LOGIN) {
                    approveByGP(anoListModel);
                } else if (GLOBAL.LOGIN_TYPE == GLOBAL.ADG_LOGIN) {
                    approveByADG(anoListModel);
                } else if (GLOBAL.LOGIN_TYPE == GLOBAL.DG_LOGIN) {
                    approveByDG(anoListModel);
                }
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GLOBAL.anoModel=anoListModel;
                if (context instanceof Manage2Activity) {
                    Intent intent=new Intent(context, ANOProfileActivity.class);
                    intent.putExtra("hq_reg_id",Manage2Activity.hqId);
                    intent.putExtra("go_to","2");
                    context.startActivity(intent);
                } else if (context instanceof COManageActivity)
                {
                    Intent intent=new Intent(context, ANOProfileActivity.class);
                    intent.putExtra("battalion_id", COManageActivity.battalionId);
                    intent.putExtra("go_to","1");
                    context.startActivity(intent);
                }
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
        return anoListModels.size();
    }
    /////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder {
        /////////////////////////
        TextView nameTV;
        TextView emailTV;
        TextView mobileTV;
        TextView instituteTV;
        /////////////////////////
        LinearLayout root;
        Button approved;
        /////////////////////////

        public ViewHolder(View view) {
            super(view);
            ///////////////////////////////////////////////////
            nameTV = view.findViewById(R.id.name_tv);
            emailTV = view.findViewById(R.id.email_tv);
            mobileTV = view.findViewById(R.id.mobile_tv);
            instituteTV = view.findViewById(R.id.institute_tv);
            //////////////////////////////////////////////////////
            root = view.findViewById(R.id.root);
            approved = view.findViewById(R.id.approved);
            //////////////////////////////////////////////////////
        }
    }

    /////////////////////////////////////////////////////////////////////////
    public void approveANOBYPrincipal(final ANOListModel anoListModel) {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "ano_principal_recom_status.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.dismiss();
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            ANOAppModel anoAppModel = new ANOAppModel();
                            if (status.equals("0")) {
                                Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
                                ///////////////////////////////////////////////////////////////////////
                                anoAppModel.setStatus(json.getString("status"));
                                anoAppModel.setRegisterId(json.getString("register_id"));
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
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        progressBar.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("ano_id", anoListModel.getAnoId());
                params.put("register_id", anoListModel.getRegisterId());
                params.put("principal_recom_status", anoListModel.getPrincipalRecomStatus());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //////////////////////////////////////////////////////////////
    public void approveByCO(final ANOListModel dgLoginModel) {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "ano_co_recom_status.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.dismiss();
                            Toast.makeText(context, context.getResources().getString(R.string.recommanded_msg), Toast.LENGTH_SHORT).show();

                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            ANORecModel anoRecModel = new ANORecModel();
                            if (status.equals("0")) {
                                Toast.makeText(context, context.getResources().getString(R.string.recommanded_msg), Toast.LENGTH_SHORT).show();
                                ///////////////////////////////////////////////////////////////////////
                                anoRecModel.setStatus(json.getString("status"));
                                anoRecModel.setInsttId(json.getString("instt_id"));
                                anoRecModel.setRegisterId(json.getString("register_id"));
                                anoRecModel.setCoRecommStatus(json.getString("co_recomm_status"));
                                anoRecModel.setGpRecommStatus(json.getString("gp_recomm_status"));
                                anoRecModel.setAdgApprovStatus(json.getString("adg_approv_status"));
                                anoRecModel.setMsg(json.getString("msg"));
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
                        FancyToast.makeText(context,""+toastMsg,FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("register_id", dgLoginModel.getRegisterId());
                params.put("co_recom_status", dgLoginModel.getCoRecomStatus());
                params.put("ano_id", dgLoginModel.getAnoId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //////////////////////////////////////////////////////////////
    public void approveByGP(final ANOListModel dgLoginModel) {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "ano_gp_recom_status.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            ANORecModel anoRecModel = new ANORecModel();
                            if (status.equals("0")) {
                                ///////////////////////////////////////////////////////////////////////
                                anoRecModel.setStatus(json.getString("status"));
                                anoRecModel.setInsttId(json.getString("instt_id"));
                                anoRecModel.setRegisterId(json.getString("register_id"));
                                anoRecModel.setCoRecommStatus(json.getString("co_recomm_status"));
                                anoRecModel.setGpRecommStatus(json.getString("gp_recomm_status"));
                                anoRecModel.setAdgApprovStatus(json.getString("adg_approv_status"));
                                anoRecModel.setMsg(json.getString("msg"));
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
                params.put("register_id", dgLoginModel.getRegisterId());
                params.put("gp_recom_status", dgLoginModel.getGpRecomStatus());
                params.put("ano_id", dgLoginModel.getAnoId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //////////////////////////////////////////////////////////////
    public void approveByDG(final ANOListModel dgLoginModel) {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "instt_co_recom_status.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.dismiss();
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            ANORecModel anoRecModel = new ANORecModel();
                            if (status.equals("0")) {
                                ///////////////////////////////////////////////////////////////////////
                                anoRecModel.setStatus(json.getString("status"));
                                anoRecModel.setInsttId(json.getString("instt_id"));
                                anoRecModel.setRegisterId(json.getString("register_id"));
                                anoRecModel.setCoRecommStatus(json.getString("co_recomm_status"));
                                anoRecModel.setGpRecommStatus(json.getString("gp_recomm_status"));
                                anoRecModel.setAdgApprovStatus(json.getString("adg_approv_status"));
                                anoRecModel.setMsg(json.getString("msg"));
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
                params.put("register_id", dgLoginModel.getRegisterId());
                params.put("co_recomm_status", dgLoginModel.getCoRecomStatus());
                params.put("instt_id", dgLoginModel.getInsttId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    //////////////////////////////////////////////////////////////
    public void approveByADG(final ANOListModel dgLoginModel) {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "ano_adg_approv_status.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            ANORecModel anoRecModel = new ANORecModel();
                            if (status.equals("0")) {
                                Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
                                ///////////////////////////////////////////////////////////////////////
                                anoRecModel.setStatus(json.getString("status"));
                                anoRecModel.setInsttId(json.getString("instt_id"));
                                anoRecModel.setRegisterId(json.getString("register_id"));
                                anoRecModel.setCoRecommStatus(json.getString("co_recomm_status"));
                                anoRecModel.setGpRecommStatus(json.getString("gp_recomm_status"));
                                anoRecModel.setAdgApprovStatus(json.getString("adg_approv_status"));
                                anoRecModel.setMsg(json.getString("msg"));
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
                params.put("ano_id", dgLoginModel.getAnoId());
                params.put("register_id", dgLoginModel.getRegisterId());
                params.put("adg_approv_status", dgLoginModel.getAdgApprovStatus());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
