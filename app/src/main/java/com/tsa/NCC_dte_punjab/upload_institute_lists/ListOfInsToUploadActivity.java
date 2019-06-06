package com.tsa.NCC_dte_punjab.upload_institute_lists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tsa.NCC_dte_punjab.activities.COManageActivity;
import com.tsa.NCC_dte_punjab.activities.CustomActivity;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.InstituteModel;

import java.util.ArrayList;

public class ListOfInsToUploadActivity extends CustomActivity {
    RecyclerView insList;
    private ArrayList<InstituteModel> instituteModels;
    private InstuteListAdapter instuteListAdapter;
    private Context context;
    private Bundle bundle;
    public static String battalionID;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_ins_to_upload);
        ////////////////////////////////////////////////////////////////////////////////////////////
        init();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        insList.setLayoutManager(mLayoutManager);
        insList.setItemAnimator(new DefaultItemAnimator());
        ////////////////////////////////////////////////////////////////////////////////////////////
/*
        InstituteModel instituteModel= gson.fromJson(jsonArray.getJSONObject(i).toString(), InstituteModel.class);
*/

      /*  for (int i=0;i<100;i++)
        {
            InstituteModel instituteModel=new InstituteModel();
            instituteModel.setInsttId(""+(i+1));
            instituteModel.setInsttName("Institute"+(i+1));
            instituteModels.add(instituteModel);
        }
*/
        instuteListAdapter=new InstuteListAdapter(instituteModels,context);
        insList.setAdapter(instuteListAdapter);
    }

    private void init() {
        context=ListOfInsToUploadActivity.this;
        /////////////////////////////////////////////////////////////////
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        ////////////////////////////////////////////////////////////////
        insList = findViewById(R.id.ints_list);
        instituteModels=new ArrayList<>();
        bundle=getIntent().getExtras();
        /////////////////////////////////////////////////////////
        if (bundle != null)
            battalionID = bundle.getString("battalion_id");
        /////////////////////////////////////////////////////////
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, COManageActivity.class);
        intent.putExtra("battalion_id",battalionID);
        startActivity(intent);
    }
}
