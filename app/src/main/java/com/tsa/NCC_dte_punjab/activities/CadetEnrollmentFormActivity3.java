package com.tsa.NCC_dte_punjab.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.BattalionModel;
import com.tsa.NCC_dte_punjab.models.CadetDetailsModels;
import com.tsa.NCC_dte_punjab.models.HQModel;
import com.tsa.NCC_dte_punjab.models.InstituteModel;
import com.tsa.NCC_dte_punjab.models.StateListModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;
import com.tsa.NCC_dte_punjab.validation.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public class CadetEnrollmentFormActivity3 extends CustomActivity {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private ImageView docImage;
    private Button upload;
    private String imageString;
    private CadetDetailsModels localCadetModel;
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ArrayList<HQModel> hqModelArrayList = new ArrayList<>();
    ArrayList<BattalionModel> battalionArray = new ArrayList<>();
    ArrayList<InstituteModel> instituteModels = new ArrayList<>();
    ArrayList<StateListModel> stateListModels=new ArrayList<>();
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private EditText circumstances;
    private EditText dismissed;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private Spinner battalian;
    private Spinner institute;
    private Spinner wingSp;
    private Spinner hqList;
    private Spinner nccYearSp;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private EditText kinName;
    private EditText kinRelation;
    private EditText kinNumber;
    private EditText kinAddress;
    private EditText schoolEt;
    private Spinner streamSp;
    private Spinner stateSp;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private Button nextBT;
    private Context context;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private RadioButton enrolledRb;
    private RadioButton willingRb;
    private RadioButton convictedRb;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private RadioButton enrolledNoRb;
    private RadioButton willingNoRb;
    private RadioButton convictedNoRb;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private Gson gson;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollment_form3);
        context = CadetEnrollmentFormActivity3.this;

        if (GLOBAL.cadetFormModel!=null)
        {
            localCadetModel= GLOBAL.cadetFormModel;
        }
        else if (GLOBAL.navigationModel.getConvicted() != null)
        {
            localCadetModel= GLOBAL.navigationModel;
        }
        init();
    }

    private void init() {
        /////////////////////////////////////////////////////////////////////////////////
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        groupList();
        stateList();
        //////////////////////////////////////////////////////////////////////////////////
        docImage = findViewById(R.id.doc_iv);
        upload = findViewById(R.id.upload_bt);
        stateSp=findViewById(R.id.state_sp);
        //////////////////////////////////////////////////////////////////////////////////
        enrolledRb = findViewById(R.id.enrollment_yes_rb);
        willingRb = findViewById(R.id.willing_yes_rb);
        convictedRb = findViewById(R.id.convicted_yes_rb);
        //////////////////////////////////////////////////////////////////////////////////
        enrolledNoRb = findViewById(R.id.enrollment_no_rb);
        willingNoRb = findViewById(R.id.willing_no_rb);
        convictedNoRb = findViewById(R.id.convicted_no_rb);
        //////////////////////////////////////////////////////////////////////////////////
        streamSp = findViewById(R.id.stream_et);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        nccYearSp=findViewById(R.id.ncc_year);
        ArrayList nccYearArrayList = new ArrayList();
        Collections.addAll(nccYearArrayList, getResources().getStringArray(R.array.ncc_year));
        ArrayAdapter<String> nccYearAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, nccYearArrayList);
        nccYearSp.setAdapter(nccYearAdapter);
        if (localCadetModel != null) {
            nccYearSp.setSelection(nccYearArrayList.indexOf(localCadetModel.getNccYear()));
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList streamArrayList = new ArrayList();
        Collections.addAll(streamArrayList, getResources().getStringArray(R.array.stream));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, streamArrayList);
        streamSp.setAdapter(dataAdapter);
        if (localCadetModel != null) {
            streamSp.setSelection(streamArrayList.indexOf(localCadetModel.getStream()));
        }
        schoolEt = findViewById(R.id.school_et);
        wingSp = findViewById(R.id.wing_sp);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList wingArrayList = new ArrayList();
        Collections.addAll(wingArrayList, getResources().getStringArray(R.array.wing));
        ArrayAdapter<String> wingAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, wingArrayList);
        wingSp.setAdapter(wingAdapter);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        if (localCadetModel != null) {
            wingSp.setSelection(wingArrayList.indexOf(localCadetModel.getWing()));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        hqList = findViewById(R.id.group_spinner);
        circumstances = findViewById(R.id.for_what_et);
        dismissed = findViewById(R.id.dismiss_et);
        kinName = findViewById(R.id.kin_name);
        kinRelation = findViewById(R.id.kin_relation);
        kinNumber = findViewById(R.id.kin_number);
        kinAddress = findViewById(R.id.kin_address);
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (localCadetModel != null) {
            String email;
            String docImageSt;

            if(GLOBAL.cadetFormModel!=null&& GLOBAL.navigationModel.getEmail()!=null)
            {
                email=localCadetModel.getEmail();
                docImageSt = localCadetModel.getConvictionDocuments();
            }
            else if(GLOBAL.cadetFormModel!=null&& GLOBAL.cadetFormModel.getEmail()!=null)
            {
                email= GLOBAL.cadetFormModel.getEmail();
                docImageSt = GLOBAL.cadetFormModel.getConvictionDocuments();
            }
            else
            {
                email=localCadetModel.getEmail();
                docImageSt = localCadetModel.getConvictionDocuments();
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////
            final String src = "http://nccdtepunjab.in/API/cadre/documents/" + email + "/" + docImageSt;
            Log.e("src_img",src);
            Picasso.get().load(src).into(docImage);
            ////////////////////////////////////////////////////////////////////////////////////////////////
            schoolEt.setText(localCadetModel.getSchoolName());
            circumstances.setText(localCadetModel.getConvictionCircumstances());
            dismissed.setText(localCadetModel.getDismissErlier());
            kinName.setText(localCadetModel.getKinName());
            ////////////////////////////////////////////////////////////////////////////////////////
            kinRelation.setText(localCadetModel.getKinRelationship());
            kinNumber.setText(localCadetModel.getKinTelephone());
            kinAddress.setText(localCadetModel.getKinAddress());
            ////////////////////////////////////////////////////////////////////////////////////////////////////
            if (localCadetModel.getEnrolledErlier().equalsIgnoreCase("yes")) {
                enrolledRb.setChecked(true);
            } else {
                enrolledNoRb.setChecked(true);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////
            if (localCadetModel.getWillingToBeEnrolled().equalsIgnoreCase("yes")) {
                willingRb.setChecked(true);
            } else {
                willingNoRb.setChecked(true);
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            if (localCadetModel.getConvicted().equalsIgnoreCase("yes")) {
                convictedRb.setChecked(true);
            } else {
                convictedNoRb.setChecked(true);
            }

        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        hqList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                batalllionList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        battalian = findViewById(R.id.battalian_spinner);
        battalian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                instList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        institute = findViewById(R.id.inst_spinner);
        institute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //instList(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextBT = findViewById(R.id.next_button);

        nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation3()) {
                    nextBT.setEnabled(false);
                    if (instituteModels.get(institute.getSelectedItemPosition()).getValid().equalsIgnoreCase("yes"))
                        registration3();
                    else {
                        FancyToast.makeText(context, "Enrollment is not started for the selected institute.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        nextBT.setEnabled(true);
                    }
                }
                else
                    Toast.makeText(context, "Fill All Field Properly", Toast.LENGTH_SHORT).show();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (GLOBAL.navigationModel != null) {
            Intent intent = new Intent(CadetEnrollmentFormActivity3.this, CadetEnrollmentFormActivity2.class);
            startActivity(intent);
            finish();
        }
    }

    public void groupList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/hqListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("hqList");
                                ArrayList locatHQArray = new ArrayList();


                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    HQModel hqModel = new HQModel();
                                    hqModel.setHqId(localJson.getString("id"));
                                    hqModel.setHqName(localJson.getString("hq_name"));
                                    locatHQArray.add(localJson.getString("hq_name"));
                                    hqModelArrayList.add(hqModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, locatHQArray);
                                hqList.setAdapter(dataAdapter);
                                if (localCadetModel != null) {
                                    hqList.setSelection(locatHQArray.indexOf(localCadetModel.getHqName()));
                                }
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

    public void batalllionList(final int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/battalianListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                battalionArray.clear();
                                JSONArray jsonArray = json.getJSONArray("battalianList");
                                ArrayList locatBattalianArray = new ArrayList();
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    BattalionModel battalionModel = new BattalionModel();
                                    battalionModel.setBattalionId(localJson.getString("id"));
                                    battalionModel.setBattalionName(localJson.getString("battalion_name"));
                                    locatBattalianArray.add(localJson.getString("battalion_name"));
                                    battalionArray.add(battalionModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, locatBattalianArray);
                                battalian.setAdapter(dataAdapter);
                                if (localCadetModel != null) {
                                    battalian.setSelection(battalionArray.indexOf(localCadetModel.getBattalionName()));
                                }
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
                params.put("hq_id", hqModelArrayList.get(id).getHqId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void instList(final int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/instituteListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("instituteList");
                                ArrayList locatInsArray = new ArrayList();
                                instituteModels.clear();
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    InstituteModel instituteModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), InstituteModel.class);
                                    locatInsArray.add(instituteModel.getInsttName());
                                    instituteModels.add(instituteModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, locatInsArray);
                                institute.setAdapter(dataAdapter);
                                if (localCadetModel != null) {
                                    institute.setSelection(locatInsArray.indexOf(localCadetModel.getInsttName()));
                                }
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
                params.put("battalion_id", battalionArray.get(id).getBattalionId());
                Log.e("params", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void registration3() {
        nextBT.setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/cadreRegistrationAPIStep-3.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        nextBT.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                Intent intent = new Intent(CadetEnrollmentFormActivity3.this, CadetEnrollmentFormActivity4.class);
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
                //params.put("cadet_id","105");
                params.put("hq_name", "" + hqList.getSelectedItem());
                params.put("instt_name", "" + institute.getSelectedItem());
                params.put("battalion_name", "" + battalian.getSelectedItem());
                params.put("school_name", "" + schoolEt.getText());
                params.put("stream", "" + streamSp.getSelectedItem());
                params.put("dismiss_erlier", "" + dismissed.getText());
                params.put("conviction_circumstances", "" + circumstances.getText());
                params.put("conviction_documents", "" + imageString);

                if (convictedRb.isChecked())
                    params.put("convicted", "yes");
                else
                    params.put("convicted", "no");

                if (willingRb.isChecked())
                    params.put("willing_to_be_enrolled", "yes");
                else
                    params.put("willing_to_be_enrolled", "no");

                if (enrolledRb.isChecked())
                    params.put("enrolled_erlier", "yes");
                else
                    params.put("enrolled_erlier", "no");

                params.put("kin_name", "" + kinName.getText());
                params.put("kin_address", "" + kinAddress.getText());
                params.put("kin_relationship", "" + kinRelation.getText());
                params.put("kin_telephone", "" + kinNumber.getText());
                params.put("wing", "" + wingSp.getSelectedItem());
                params.put("ncc_year",""+nccYearSp.getSelectedItem());

                GLOBAL.navigationModel.setConvicted(params.get("convicted"));
                GLOBAL.navigationModel.setConvictionCircumstances(params.get("conviction_circumstances"));
                GLOBAL.navigationModel.setConvictionDocuments(params.get("conviction_documents"));
                GLOBAL.navigationModel.setSchoolName(params.get("school_name"));
                GLOBAL.navigationModel.setWillingToBeEnrolled(params.get("willing_to_be_enrolled"));
                GLOBAL.navigationModel.setStream(params.get("stream"));
                GLOBAL.navigationModel.setEnrolledErlier(params.get("enrolled_erlier"));
                GLOBAL.navigationModel.setDismissErlier("" + dismissed.getText());
                GLOBAL.navigationModel.setKinName("" + kinName.getText());
                GLOBAL.navigationModel.setKinAddress("" + kinAddress.getText());
                GLOBAL.navigationModel.setKinRelationship("" + kinRelation.getText());
                GLOBAL.navigationModel.setKinTelephone("" + kinNumber.getText());
                GLOBAL.navigationModel.setNccYear( params.get("ncc_year"));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean checkValidation3() {
        boolean ret = true;
        if (!Validation.hasText(dismissed)) ret = false;
        if (!Validation.hasText(kinName)) ret = false;
        if (!Validation.hasText(kinAddress)) ret = false;
        if (!Validation.hasText(kinNumber)) ret = false;
        if (!Validation.hasText(kinRelation)) ret = false;

        if (!Validation.hasText(schoolEt)) ret = true;              ///gaurav edited
        if (streamSp.getSelectedItem() == null) ret = true;             ///gaurav edited

        if (battalian.getSelectedItem() == null) ret = false;
        if (institute.getSelectedItem() == null) ret = false;
        if (wingSp.getSelectedItem() == null) ret = false;
        if (hqList.getSelectedItem() == null) ret = false;

        return ret;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Toast.makeText(CadetEnrollmentFormActivity3.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    docImage.setImageBitmap(bitmap);
                    imageString = getStringImage(getResizedBitmap(bitmap, 2000));

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CadetEnrollmentFormActivity3.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            docImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(CadetEnrollmentFormActivity3.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    ///////////////////////////////////////////////
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    ///////////////////////////////////////////////

    //////////////////////////////////////////////
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    //////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    ////////////////////////////////////////////////////////////////

    public void stateList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/stateListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {

                                JSONArray jsonArray = json.getJSONArray("hqList");
                                ArrayList locatStateArray = new ArrayList();
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    StateListModel stateListModel = new StateListModel();
                                    stateListModel.setId(localJson.getString("id"));
                                    stateListModel.setStateName(localJson.getString("state_name"));
                                    locatStateArray.add(localJson.getString("state_name"));
                                    stateListModels.add(stateListModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, locatStateArray);
                                stateSp.setAdapter(dataAdapter);
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


}
