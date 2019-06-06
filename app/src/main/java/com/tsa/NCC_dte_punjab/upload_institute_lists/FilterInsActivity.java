package com.tsa.NCC_dte_punjab.upload_institute_lists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsa.NCC_dte_punjab.activities.CustomActivity;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.CadesOnInsModel;

import java.util.ArrayList;

public class FilterInsActivity extends CustomActivity {

    RecyclerView insList;
    private ArrayList<CadesOnInsModel> cadesOnInsModels;
    private CandidateOnInsAdapterUpload instuteListAdapter;
    private Context context;
    private Bundle bundle;
    private String battalionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_ins);
        ////////////////////////////////////////////////////////////////////////////////////////////
        init();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        insList.setLayoutManager(mLayoutManager);
        insList.setItemAnimator(new DefaultItemAnimator());
        ////////////////////////////////////////////////////////////////////////////////////////////

        for (int i=0;i<100;i++)
        {
            CadesOnInsModel cadesOnInsModel=new CadesOnInsModel();
            cadesOnInsModel.setInsttId(""+(i+1));
            cadesOnInsModel.setInsttName("Institute"+(i+1));
            cadesOnInsModel.setName("Cadet"+(i+1));
            cadesOnInsModel.setEmail("Email"+(i+1));
            cadesOnInsModel.setMobile("Mobile"+(i+1));
            cadesOnInsModels.add(cadesOnInsModel);
        }

        instuteListAdapter=new CandidateOnInsAdapterUpload(cadesOnInsModels,context);
        insList.setAdapter(instuteListAdapter);
    }

    private void init() {
        context=FilterInsActivity.this;
        insList = findViewById(R.id.ints_list);
        cadesOnInsModels=new ArrayList<>();
        bundle=getIntent().getExtras();
        /////////////////////////////////////////////////////////
        if (bundle != null)
            battalionID = bundle.getString("battalion_id");
        /////////////////////////////////////////////////////////
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context, ListOfInsToUploadActivity.class);
        intent.putExtra("battalion_id",battalionID);
        startActivity(intent);
    }

}
