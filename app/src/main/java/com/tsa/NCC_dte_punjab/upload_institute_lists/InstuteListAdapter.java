package com.tsa.NCC_dte_punjab.upload_institute_lists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.InstituteModel;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Akhil Tripathi on 24-01-2018.
 */

public class InstuteListAdapter extends RecyclerView.Adapter<InstuteListAdapter.ViewHolder> {

    private ArrayList<InstituteModel> instituteModels;
    private Context context;
    ArrayList anoArrayList;

    public InstuteListAdapter(ArrayList<InstituteModel> instituteModels, Context context) {
        this.instituteModels = instituteModels;
        this.context = context;
        anoArrayList = new ArrayList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approve_ins_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ////////////////////////////////////////////////////////////////////////////////////////////
        final InstituteModel instituteModel = instituteModels.get(position);
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FilterInsActivity.class);
                if (context instanceof ListOfInsToUploadActivity)
                intent.putExtra("battalion_id",ListOfInsToUploadActivity.battalionID);
                context.startActivity(intent);
            }
        });
        holder.insName.setText(instituteModel.getInsttName());
        ////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////
    }


    //////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
        return instituteModels.size();
    }
    /////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rootLayout;
        FancyButton browseBt;
        FancyButton submitBt;
        TextView insName;

        public ViewHolder(View view) {
            super(view);
            rootLayout = view.findViewById(R.id.root);
            browseBt = view.findViewById(R.id.browse_bt);
            submitBt=view.findViewById(R.id.submit_bt);
            insName=view.findViewById(R.id.ins_tv);
        }
    }
    ////////////////////////////////////////////////////////////////
}
