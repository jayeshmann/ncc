package com.tsa.NCC_dte_punjab.upload_institute_lists;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.CadesOnInsModel;

import java.util.ArrayList;

/**
 * Created by Akhil Tripathi on 24-01-2018.
 */

public class CandidateOnInsAdapterUpload extends RecyclerView.Adapter<CandidateOnInsAdapterUpload.ViewHolder> {

    private ArrayList<CadesOnInsModel> cadesOnInsModels;
    private Context context;
    ArrayList anoArrayList;

    public CandidateOnInsAdapterUpload(ArrayList<CadesOnInsModel> cadesOnInsModels, Context context) {
        this.cadesOnInsModels = cadesOnInsModels;
        this.context = context;
        anoArrayList=new ArrayList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cadet_upload_card, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CadesOnInsModel cadesOnInsModel=cadesOnInsModels.get(position);
        holder.canNameTV.setText(cadesOnInsModel.getName());
        holder.instituteTV.setText(cadesOnInsModel.getInsttName());
        holder.emailTv.setText(cadesOnInsModel.getEmail());
        holder.phoneTV.setText(cadesOnInsModel.getMobile());

            holder.approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    ///////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView canNameTV;

        TextView instituteTV;
        TextView emailTv;
        TextView phoneTV;
        LinearLayout root;
        Button approved;

        public ViewHolder(View view) {
            super(view);
            canNameTV = view.findViewById(R.id.name_tv);
            instituteTV = view.findViewById(R.id.institute_tv);
            emailTv = view.findViewById(R.id.email_tv);
            phoneTV = view.findViewById(R.id.mobile_tv);
            root = view.findViewById(R.id.root);
            approved=view.findViewById(R.id.approved);
        }
    }
}
