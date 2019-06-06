package com.tsa.NCC_dte_punjab.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.tsa.NCC_dte_punjab.R;

import java.util.ArrayList;

public class COFormActivity extends CustomActivity {
    private SearchableSpinner gpHQSP;
    private SearchableSpinner batallionSP;
    private SearchableSpinner instituteSP;
    private SearchableSpinner departmentSP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coform);

        ArrayList spinnerArray = new ArrayList();
        spinnerArray.add("--Select--");
        spinnerArray.add("Cadet");
        spinnerArray.add("ANO");
        spinnerArray.add("Institute");
        spinnerArray.add("Others--");

         gpHQSP=findViewById(R.id.gp_sp);
         batallionSP=findViewById(R.id.batallion_sp);
         instituteSP=findViewById(R.id.institute_sp);
         departmentSP=findViewById(R.id.dept_sp);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerArray);
        gpHQSP.setAdapter(dataAdapter);
        gpHQSP.setTitle("Select Item");
        gpHQSP.setPositiveButton("OK");
        batallionSP.setAdapter(dataAdapter);
        batallionSP.setTitle("Select Item");
        batallionSP.setPositiveButton("OK");
        instituteSP.setAdapter(dataAdapter);
        instituteSP.setTitle("Select Item");
        instituteSP.setPositiveButton("OK");
        departmentSP.setAdapter(dataAdapter);
        departmentSP.setTitle("Select Item");
        departmentSP.setPositiveButton("OK");
    }
}
