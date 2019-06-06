package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.ClassListModel;
import com.tsa.NCC_dte_punjab.models.Form2Model;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;
import com.tsa.NCC_dte_punjab.validation.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class CadetEnrollmentFormActivity2 extends CustomActivity {
    /////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////
    ArrayList<ClassListModel> classListModels = new ArrayList<>();
    //////////////////////////////////////////////////////////////
    private Spinner classSp;
    private EditText identityMark1;
    private EditText identityMark2;
    private EditText marks;
    private Button nextBT;
    private Context context;
    private Form2Model form2Model;
    /////////////////////////////////////////////////////////////////
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollment_form2);
        context = CadetEnrollmentFormActivity2.this;
        init();
    }

    private void init() {
        form2Model=new Form2Model();
        ////////////////////////////////////////////////////////////////////////////////////////////
        marks = findViewById(R.id.marks_et);
        identityMark1 = findViewById(R.id.ide_mark1_et);
        identityMark2 = findViewById(R.id.ide_mark2_et);
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        classSp = findViewById(R.id.class_spinner);
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (GLOBAL.cadetFormModel!=null)
        {
            marks.setText(GLOBAL.cadetFormModel.getMarks());
            identityMark1.setText(GLOBAL.cadetFormModel.getIdentificationMark1());
            identityMark2.setText(GLOBAL.cadetFormModel.getIdentificationMark2());
        } else if (GLOBAL.navigationModel.getCadetId() != null)
        {
            marks.setText(GLOBAL.navigationModel.getMarks());
            identityMark1.setText(GLOBAL.navigationModel.getIdentificationMark1());
            identityMark2.setText(GLOBAL.navigationModel.getIdentificationMark2());
        }
        classList();
        ////////////////////////////////////////////////////////////////////////////////////////////

        nextBT = findViewById(R.id.next_button);

        nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation2()) {
                    nextBT.setEnabled(false);
                    registration2();
                }
                else
                    Toast.makeText(context, "Fill All Field Properly", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public void onBackPressed() {
      /*  if (GLOBAL.cadetFormModel != null) {
            Intent intent = new Intent(CadetEnrollmentFormActivity2.this, CadetEnrollmentFormActivity.class);
            startActivity(intent);
            finish();
        }*/
    }

    public void classList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/classListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("classList");
                                ArrayList localClassArray = new ArrayList();
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    ClassListModel classListModel = new ClassListModel();
                                    classListModel.setClassId(localJson.getString("id"));
                                    classListModel.setClassName(localJson.getString("class_name"));
                                    localClassArray.add(localJson.getString("class_name"));
                                    classListModels.add(classListModel);
                                }
                                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, localClassArray);
                                classSp.setAdapter(dataAdapter);
                                if (GLOBAL.cadetFormModel!=null)
                                {
                                    classSp.setSelection(classListModels.indexOf(GLOBAL.cadetFormModel.getClassName()));
                                }
                                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context,"Some issue in loading", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                //params.put("battalion_id",battalionArray.get(id).getBattalionId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    
    public void registration2() {
        nextBT.setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/cadreRegistrationAPIStep-2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        nextBT.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                Intent intent = new Intent(CadetEnrollmentFormActivity2.this, CadetEnrollmentFormActivity3.class);
                                intent.putExtra("form2_data",form2Model);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        nextBT.setEnabled(true);
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                if ( GLOBAL.cadetID!=null)
                params.put("cadet_id", GLOBAL.cadetID);
                else if (GLOBAL.navigationModel.getCadetId() != null)
                    params.put("cadet_id", GLOBAL.navigationModel.getCadetId());
                else if (GLOBAL.cadetFormModel.getCadetId() != null)
                    params.put("cadet_id", GLOBAL.cadetFormModel.getCadetId());
                else
                    params.put("cadet_id", GLOBAL.cadetModel.getCadetId());

                params.put("class_name", "" + classSp.getSelectedItem());
                params.put("marks", "" + marks.getText());
                params.put("identification_mark1", "" + identityMark1.getText());
                params.put("identification_mark2", "" + identityMark2.getText());

                GLOBAL.navigationModel.setCadetId(GLOBAL.cadetID);
                GLOBAL.navigationModel.setClassName("" + classSp.getSelectedItem());
                GLOBAL.navigationModel.setMarks("" + marks.getText());
                GLOBAL.navigationModel.setIdentificationMark1("" + identityMark1.getText());
                GLOBAL.navigationModel.setIdentificationMark2("" + identityMark2.getText());

                form2Model.setmClass("" + classSp.getSelectedItem());
                form2Model.setmIdentificationMark1("" + identityMark1.getText());
                form2Model.setmIdentificationMark2("" +identityMark2.getText());
                form2Model.setmMarks("" + marks.getText());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean checkValidation2() {
        boolean ret = true;
        if (!Validation.hasText(identityMark1)) ret = true;  ///gaurav edited
        if (!Validation.hasText(identityMark1)) ret = false;  ///gaurav edited
        if (!Validation.hasText(marks)) ret = false;
        if (classSp.getSelectedItem() == null) ret = false;
        return ret;
    }


}
