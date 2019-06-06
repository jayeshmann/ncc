package com.tsa.NCC_dte_punjab.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.tsa.NCC_dte_punjab.models.BattalionModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class InstituteFormActivity extends CustomActivity {
    private Context context;
    private String registerId="";
    private String insttName = "";
    private String totalStudent;

    private String teacherAvailable = "No";
    private String paradeStoreAvailable = "No";
    private String shortRangeAvailable = "No";
    private String bathroomAvailable = "No";

    private String bathroomImages = "";
    private String nocBySocietyDoc = "";
    private String instituteDoc = "";

    private String affilRegNo;
    ///////////////////////////////////////////////////
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "InstituteFormActivity";
    ////////////////////////////////////
    private Spinner typesOfInstitute;
    private Spinner listOFBatallion;
    private EditText numberOfStudentsET;
    private EditText nameOfInstituteET;
    ////////////////////////////////////

    private View teacherRoom;
    private View storeRoom;
    private View bathRoom;
    private View firingRange;

    private int selectedInstitutePosition = 0;
    private int selectedBattalionPosition = 0;

    private RadioButton yes1, yes2, yes3, yes4, no1, no2, no3, no4;
    private Button submit;

    private Bundle bundle;

    private ImageView BathroomIV;
    private ImageView NOCIV;
    private ImageView DocIV;

    private final int BATHROOM = 1;
    private final int NOC = 2;
    private final int DOC = 3;

    private int SELECTEDIMG = 0;
    private EditText affNo;

    private ArrayList<BattalionModel> battalionModelArrayList;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_form);
        context = InstituteFormActivity.this;

        ///////////////////////////////////////////////////
        bundle = getIntent().getExtras();
        if (bundle != null) {
            registerId = bundle.getString("register_id");
        }
        init();
        ////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void init() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        battalionModelArrayList=new ArrayList<>();
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        teacherRoom = findViewById(R.id.teachers_room_view);
        storeRoom = findViewById(R.id.pared_view);
        bathRoom = findViewById(R.id.bath_room_view);
        firingRange = findViewById(R.id.firing_range_view);
        submit = findViewById(R.id.submit_btn);
        affNo=findViewById(R.id.aff_no_et);
        ////////////////////////////////////////////////////////////////////////////////////////////
        NOCIV = findViewById(R.id.noc_iv);
        BathroomIV = findViewById(R.id.bathroom_iv);
        DocIV = findViewById(R.id.ins_doc);
        ////////////////////////////////////////////////////////////////////////////////////////////
        listOFBatallion = findViewById(R.id.list_of_batttalion);
        listOFBatallion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBattalionPosition = position-1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        typesOfInstitute = findViewById(R.id.types_of_institute);
        typesOfInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedInstitutePosition = position-1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        NOCIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTEDIMG = NOC;
                openImageChooser();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        BathroomIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTEDIMG = BATHROOM;
                openImageChooser();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        DocIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTEDIMG = DOC;
                openImageChooser();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStudent = "" + numberOfStudentsET.getText();
                insttName = "" + nameOfInstituteET.getText();
                affilRegNo=""+affNo.getText();
                getRegistered();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        getBattalianList();
        ////////////////////////////////////////////////////////////////////////////////////////////
        numberOfStudentsET = findViewById(R.id.number_student_et);
        ////////////////////////////////////////////////////////////////////////////////////////////
        nameOfInstituteET = findViewById(R.id.institute_name_et);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        yes1 = teacherRoom.findViewById(R.id.yes_rb);
        yes1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yes1.isChecked())
                    teacherAvailable = "Yes";
                else
                    teacherAvailable = "No";
            }
        });
        no1 = teacherRoom.findViewById(R.id.no_rb);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        yes2 = storeRoom.findViewById(R.id.yes_rb);
        yes2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yes2.isChecked())
                    paradeStoreAvailable = "Yes";
                else
                    paradeStoreAvailable = "No";
            }
        });
        no2 = storeRoom.findViewById(R.id.no_rb);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        yes3 = bathRoom.findViewById(R.id.yes_rb);
        yes3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yes3.isChecked())
                    shortRangeAvailable = "Yes";
                else
                    shortRangeAvailable = "No";
            }
        });
        no3 = bathRoom.findViewById(R.id.no_rb);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        yes4 = firingRange.findViewById(R.id.yes_rb);
        yes4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yes1.isChecked())
                    bathroomAvailable = "Yes";
                else
                    bathroomAvailable = "No";
            }
        });
        no4 = firingRange.findViewById(R.id.no_rb);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList spinnerArray = new ArrayList();
        spinnerArray.add("--Select--");
        spinnerArray.add("Govrnment aided institution");
        spinnerArray.add("Govrnmented institution");
        spinnerArray.add("Private institution");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerArray);
        typesOfInstitute.setAdapter(dataAdapter);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void getRegistered() {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "add_instt_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");
                            Toast.makeText(context, "" + msg, Toast.LENGTH_LONG).show();

                            if (status.equals("0")) {
                                startActivity(new Intent(InstituteFormActivity.this, HomeActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressBar!=null)
                        progressBar.dismiss();
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("battalion_id", "" + battalionModelArrayList.get(selectedBattalionPosition).getBattalionId());
                params.put("register_id", "" + registerId);
                params.put("instt_name", "" + insttName);
                params.put("total_student", "" + totalStudent);

                params.put("teacher_available", "" + teacherAvailable);
                params.put("parade_store_available", "" + paradeStoreAvailable);
                params.put("short_range_available", "" + shortRangeAvailable);
                params.put("bathroom_available", "" + bathroomAvailable);
                params.put("bathroom_images", "" + bathroomImages);
                params.put("noc_by_society_doc", "" + nocBySocietyDoc);

                params.put("instt_type", "" + typesOfInstitute.getSelectedItem());
                params.put("affil_reg_no", "" + affilRegNo);
                params.put("instt_doc", "" + instituteDoc);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void getBattalianList() {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "battalion_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                JSONArray jsonArray = json.getJSONArray("data");
                                ArrayList spinnerArray = new ArrayList();
                                spinnerArray.add("--Select--");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //////////////////////////////////////////////////////////////////////////////
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    BattalionModel battalionModel1=new BattalionModel();
                                    battalionModel1.setBattalionId(localJson.getString("battalion_id"));
                                    battalionModel1.setBattalionName(localJson.getString("battalion_name"));
                                    battalionModelArrayList.add(battalionModel1);
                                    /////////////////////////////////////////////////////////////////////////////////////////////////////////

                                    spinnerArray.add(localJson.getString("battalion_name"));
                                    //joinAsModels.add(joinAsModel);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerArray);
                                listOFBatallion.setAdapter(dataAdapter);
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    setImage(selectedImage);
                    getStringImage(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setImage(Bitmap bm) {
        if (SELECTEDIMG == BATHROOM) {
            BathroomIV.setImageBitmap(bm);
        } else if (SELECTEDIMG == NOC) {
            NOCIV.setImageBitmap(bm);
        } else if (SELECTEDIMG == DOC) {
            DocIV.setImageBitmap(bm);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////TO Convert Bitmap To Base64///////////////////////////////
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        if (SELECTEDIMG == BATHROOM) {
            bathroomImages = encodedImage;
        } else if (SELECTEDIMG == NOC) {
            nocBySocietyDoc = encodedImage;
        } else if (SELECTEDIMG == DOC) {
            instituteDoc = encodedImage;
        }
        return encodedImage;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(context,HomeActivity.class));
        finish();
    }
}
