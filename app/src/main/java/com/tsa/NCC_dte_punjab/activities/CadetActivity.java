package com.tsa.NCC_dte_punjab.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.ClassListModel;
import com.tsa.NCC_dte_punjab.models.InstituteModel;
import com.tsa.NCC_dte_punjab.network.NetworkCheck;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;
import com.tsa.NCC_dte_punjab.validation.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class CadetActivity extends CustomActivity {
    //list of permissions
    ////////////////////////////////
    private int mYear, mMonth, mDay;
    RadioButton yes, no;
    private LinearLayout dobLayout;
    ////////////////////////////////
    TextInputLayout enrolmentLayout;
    private int REQUEST_CAMERA = 0;
    private Uri imageUri;
    private final int CAMERA_RESULT = 101;
    private Bitmap thumbnail;
    private String base64Image;
    ////////////////////////////////

    ////////////////////////////////
    private TextView dobTv;
    private Context context;
    ////////////////////////
    Spinner classSpinner;
    Spinner schoolSpinner;
    private int selectedClassPosition;
    private int selectedSchoolPosition = 0;
    ////////////////////////////////////////////////////
    private Bundle bundle;
    ////////////////////////////////////////////////////
    private ArrayList<InstituteModel> instituteModels;
    private ArrayList<ClassListModel> classListModels;
    ///////////////////////////////////////////////////
    private RadioButton maleRB;
    private RadioButton feMaleRB;
    private RadioButton marriedRB;
    private RadioButton unmarriedRB;
    ///////////////////////////////////////////////////
    private String gender = "Male";
    private String married = "Unmarried";
    private String registerId;
    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    private Button regesterBtn;

    private int schoolListSize = 0;
    private String schoolName = "";
    private String insttId;

    ArrayList spinnerArray;

    private EditText bankNameET;
    private EditText accNumberET;
    private EditText IFSCET;

    private ImageView captureIV;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadidate);

        ////////////////////////////////////////////////////////////////////////////////////////////
        bundle = getIntent().getExtras();
        if (bundle != null) {
            registerId = bundle.getString("register_id");
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        regesterBtn = findViewById(R.id.register);
        regesterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation())
                    candidateReg();
                else
                    FancyToast.makeText(context, "Please Fill All The Fields Properly", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        context = CadetActivity.this;
        init();
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (NetworkCheck.isNetworkAvailable(context)) {
            getSchoolList();
            getClassList();
        } else {
            FancyToast.makeText(context, "No Network Available", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("WrongViewCast")
    private void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        ////////////////////////////////////////////////////////////////
        dobLayout = findViewById(R.id.dob_layout);
        dobTv = findViewById(R.id.dob_tv);
        enrolmentLayout = findViewById(R.id.register_no);
        ////////////////////////////////////////////////////////////////
        bankNameET = findViewById(R.id.bank_name_et);
        accNumberET = findViewById(R.id.acc_no_et);
        IFSCET = findViewById(R.id.ifsc_code_et);
        ////////////////////////////////////////////////////////////////
        captureIV = findViewById(R.id.capture_img_iv);

        maleRB = findViewById(R.id.male);
        feMaleRB = findViewById(R.id.female);
        maleRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /////////////////////////////////////////////////////////////////////////
                if (isChecked)
                    gender = "" + maleRB.getText();
                else
                    gender = "" + feMaleRB.getText();
                ////////////////////////////////////////////////////////////////////////
            }
        });
        marriedRB = findViewById(R.id.married);
        unmarriedRB = findViewById(R.id.unmarried);
        marriedRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /////////////////////////////////////////////////////////////////////////
                if (isChecked)
                    married = "" + marriedRB.getText();
                else
                    married = "" + unmarriedRB.getText();
                ////////////////////////////////////////////////////////////////////////
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        classSpinner = findViewById(R.id.class_spinner);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClassPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        captureIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        schoolSpinner = findViewById(R.id.school_spinner);
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSchoolPosition = position;
                if (position == schoolListSize) {
                    final String[] localString = {""};
                    new MaterialDialog.Builder(context)
                            .title(R.string.input)
                            .inputRangeRes(2, 20, R.color.red)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    spinnerArray.add(localString[0]);
                                    insttId = "0";
                                    schoolName = localString[0];
                                }
                            })
                            .input(null, null, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    localString[0] = "" + input;
                                    schoolName = localString[0];
                                }
                            }).show();
                } else {
                    if (position != 0) {
                        insttId = instituteModels.get(position - 1).getId();
                        schoolName = instituteModels.get(position - 1).getInsttName();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        yes = findViewById(R.id.yes);

        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enrolmentLayout.setVisibility(View.VISIBLE);
                } else {
                    enrolmentLayout.setVisibility(View.GONE);
                }
            }
        });

        no = findViewById(R.id.no);
        dobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dobTv.setText(dayOfMonth + "/" + (1 + monthOfYear) + "/" + year);
                            }
                        }, mYear, mMonth, mDay).show();
            }
        });

        classListModels = new ArrayList<>();
        instituteModels = new ArrayList<>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void getSchoolList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "institute_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("data");
                                spinnerArray = new ArrayList();
                                spinnerArray.add("--Select--");
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    InstituteModel instituteModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), InstituteModel.class);
                                    instituteModels.add(instituteModel);
                                }

                                spinnerArray.add("Others");
                                schoolListSize = spinnerArray.size() - 1;

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerArray);
                                schoolSpinner.setAdapter(dataAdapter);
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
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
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void getClassList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "class_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("data");
                                ArrayList spinnerArray = new ArrayList();
                                spinnerArray.add("--Select--");
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    ClassListModel classListModel = new ClassListModel();
                                    classListModel.setClassId(localJson.getString("class_id"));
                                    classListModel.setClassName(localJson.getString("class_name"));
                                    spinnerArray.add(localJson.getString("class_name"));
                                    classListModels.add(classListModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerArray);
                                classSpinner.setAdapter(dataAdapter);
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void candidateReg() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "add_cadre_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");

                            FancyToast.makeText(context,""+msg,FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
                            if (status.equals("0")) {
                                goHome();
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
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();

                ////////////////////////////////////////////////////////////////////////////////////
                params.put("register_id", "" + registerId);
                params.put("dob", "" + dobTv.getText());
                params.put("marital_status", "" + married);
                params.put("gender", "" + gender);
                ////////////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////////
                params.put("instt_id", "" + insttId);
                params.put("registration_no", "" + registerId);
                params.put("school_collage", "" + schoolName);
                params.put("class_id", "" + classListModels.get(selectedClassPosition).getClassId());
                ////////////////////////////////////////////////////////////////////////////////////
                params.put("bank_name", "" + bankNameET.getText());
                params.put("acc_no", "" + accNumberET.getText());
                params.put("ifsc_code", "" + IFSCET.getText());
                params.put("image_name", "" + base64Image);
                ////////////////////////////////////////////////////////////////////////////////////

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void goHome() {
        GLOBAL.isRegistered = true;
        startActivity(new Intent(context, HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        GLOBAL.isRegistered = true;
        startActivity(new Intent(context, HomeActivity.class));
        finish();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void selectImage() {
        if (ContextCompat.checkSelfPermission(CadetActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            dispatchTakenPictureIntent();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_RESULT);
            }
        }
    }

    ////////////////////////////////////////////////////////
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(bankNameET)) ret = false;
        if (!Validation.hasText(accNumberET)) ret = false;
        if (!Validation.hasText(IFSCET)) ret = false;

        if (base64Image.equals("") || base64Image == null) ret = false;
        if (selectedSchoolPosition == 0) ret = false;
        if (selectedClassPosition == 0) ret = false;
        return ret;
    }

    private void dispatchTakenPictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_RESULT) {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                captureIV.setImageBitmap(bitmap);
                base64Image = getStringImage(bitmap);
                // ivPic.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_RESULT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakenPictureIntent();
            } else {
                Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
