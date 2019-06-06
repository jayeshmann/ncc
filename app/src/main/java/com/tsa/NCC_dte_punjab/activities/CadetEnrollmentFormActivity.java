package com.tsa.NCC_dte_punjab.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.CityModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;
import com.tsa.NCC_dte_punjab.validation.Validation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class CadetEnrollmentFormActivity extends CustomActivity {
    //////////////////////////////////////////////////////////////////////
    private int GALLERY = 1, CAMERA = 2;
    private ImageView cadetImage;
    private Button upload;
    private String imageString;
    ///////////////////////////////////////////////////////////////////////
    static int page = 0;
    ///////////////////////////////////////////////////////////////////////

    ArrayList<CityModel> cityArray = new ArrayList();
    ////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    /////////////////////////////////////////////////////////////////////////
    private EditText firstName, midName, lastName;
    private EditText fFirstName, fMidName, fLastName;
    private EditText mFirstName, mMidName, mLastName;
    private EditText villCityEt, landMark, pincodeEt;
    private EditText mobile, email;
    private EditText nearRailway, nearPolice;
    //////////////////////////////////////////////////////////////////////////
    private TextView dobTv;
    private Spinner nation;
    private Spinner state;
    private Spinner district;
    private Spinner bloodGP;
    private Spinner incomeSP;
    private Spinner spinner_language;
    ///////////////////////////////////////////////////////////////////////////
    private LinearLayout appDOBLayout;
    private Button nextBT;
    private Context context;
    private RadioButton maleRb;

    Locale myLocale;
    String currentLanguage = "en", currentLang;
    ImageView arrow_up;



    ////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollment_form1);

//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.app_name));

        currentLanguage = getIntent().getStringExtra(currentLang);

        context = CadetEnrollmentFormActivity.this;
        init();

    }




    private void init() {
        ///////////////////////////////////////////////////
        cadetImage = findViewById(R.id.cadet_iv);
        upload = findViewById(R.id.upload_bt);
        ///////////////////////////////////////////////////

        firstName = findViewById(R.id.first_name_et);
        midName = findViewById(R.id.mid_name_et);
        lastName = findViewById(R.id.last_name_et);
        //////////////////////////////////////////////////////

        fFirstName = findViewById(R.id.p_first_name_et);
        fMidName = findViewById(R.id.p_mid_name_et);
        fLastName = findViewById(R.id.p_last_name_et);
        ///////////////////////////////////////////////////////

        mFirstName = findViewById(R.id.m_first_name_et);
        mMidName = findViewById(R.id.m_mid_name_et);
        mLastName = findViewById(R.id.m_last_name_et);
        ////////////////////////////////////////////////////////

        email = findViewById(R.id.email_et);
        mobile = findViewById(R.id.mobile_no_et);
        pincodeEt = findViewById(R.id.pin_et);
        villCityEt = findViewById(R.id.vill_city_et);
        landMark = findViewById(R.id.landmark_et);
        ////////////////////////////////////////////////////////

        nearRailway = findViewById(R.id.nearest_railway_et);
        nearPolice = findViewById(R.id.nearest_police_et);
        ////////////////////////////////////////////

        nation = findViewById(R.id.nationality_sp);
        incomeSP = findViewById(R.id.income_spinner);
        /////////////////////////////////////////////////////////
        appDOBLayout = findViewById(R.id.app_date_layout);
        /////////////////////////////////////////////////////////
        dobTv = findViewById(R.id.app_dob_tv);
        maleRb = findViewById(R.id.male_rb);

//        spinner_language = (Spinner) findViewById(R.id.spinner_language);
        arrow_up = (ImageView)findViewById(R.id.arrow_up);
        loadlocale();

//        ArrayList<String> CadetName = new ArrayList<>();
//        Collections.addAll(CadetName, getResources().getStringArray(R.array.CadetName));
//        ArrayAdapter<String> CadetAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item2, CadetName);
//        spinner_language.setAdapter(CadetAdapter);
//
//
//        List<String> categories = new ArrayList<String>();
//        categories.add("select language");
//        categories.add("English");
//        categories.add("Hindi");
//
//        spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                switch (position) {
//                    case 0:
//                        break;
//                    case 1:
//                        setLocale("en");
//                        break;
//                    case 2:
//                        setLocale("hi");
//
//                }
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//    }
//
//    public void setLocale(String localeName) {
//        if (!localeName.equals(currentLanguage)) {
//            myLocale = new Locale(localeName);
//            Resources res = getResources();
//            DisplayMetrics dm = res.getDisplayMetrics();
//            Configuration conf = res.getConfiguration();
//            conf.locale = myLocale;
//            res.updateConfiguration(conf, dm);
//            Intent refresh = new Intent(this, CadetEnrollmentFormActivity.class);
//            refresh.putExtra(currentLang, localeName);
//            startActivity(refresh);
//        } else {
//            Toast.makeText(CadetEnrollmentFormActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
//        }
        arrow_up.setOnClickListener(new View.OnClickListener() {

            private int modePrivate;

            @Override
            public void onClick(View view) {
                showChangeLangdialogue();
            }

            private void showChangeLangdialogue() {

                final String[] listitems = new String[]{"English", "हिंदी"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(CadetEnrollmentFormActivity.this);
                mbuilder.setTitle("Choose Language");
                mbuilder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0){
                            // Hindi
                            setLocale("hi");
                            recreate();
                        }
                        else if (i == 1){
                            // English
                            setLocale("en");
                            recreate();
                        }
                         dialogInterface.dismiss();
                    }


                });

                AlertDialog mdialog = mbuilder.create();
                mdialog.show();



            }
//

            public void setLocale(String localeName) {

                Locale myLocale = new Locale(localeName);
                Resources res = context.getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
            }

            });





        /////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> nationArrayList = new ArrayList<>();
        Collections.addAll(nationArrayList, getResources().getStringArray(R.array.nationality));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, nationArrayList);
        nation.setAdapter(dataAdapter);
        if (GLOBAL.cadetFormModel != null) {
            nation.setSelection(nationArrayList.indexOf(GLOBAL.cadetFormModel.getNationality()));
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        state = findViewById(R.id.state_sp);
        ArrayList<String> stateArrayList = new ArrayList<>();
        Collections.addAll(stateArrayList, getResources().getStringArray(R.array.state));
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, stateArrayList);
        state.setAdapter(stateAdapter);
        if (GLOBAL.cadetFormModel != null) {
            state.setSelection(stateArrayList.indexOf(GLOBAL.cadetFormModel.getStateName()));
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        bloodGP = findViewById(R.id.blood_gp_spinner);
        ArrayList<String> bloodArrayList = new ArrayList<>();
        Collections.addAll(bloodArrayList, getResources().getStringArray(R.array.blood_group));
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, bloodArrayList);
        bloodGP.setAdapter(bloodAdapter);
        if (GLOBAL.cadetFormModel != null) {
            bloodGP.setSelection(bloodArrayList.indexOf(GLOBAL.cadetFormModel.getBloodGroup()));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList incomeArrayList = new ArrayList();
        Collections.addAll(incomeArrayList, getResources().getStringArray(R.array.income));
        ArrayAdapter<String> incomeAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item1, incomeArrayList);
        incomeSP.setAdapter(incomeAdapter);
        if (GLOBAL.cadetFormModel != null) {
            incomeSP.setSelection(incomeArrayList.indexOf(GLOBAL.cadetFormModel.getParentsAnnualIncome()));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        appDOBLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dobTv.setText(year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay).show();
            }
        });

        district = findViewById(R.id.district_sp);

        nextBT = findViewById(R.id.next_button);

        nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation1()) {
                    nextBT.setEnabled(false);
                    registration1();
                }
                else
                    FancyToast.makeText(context, "Fill All Fields", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityList(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }



    // bilingual values
    private void loadlocale() {
        SharedPreferences preferences = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_lang", "");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CadetEnrollmentFormActivity.this, SelectRegActivity.class);
        startActivity(intent);
        finish();
    }

    public void cityList(final int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/districtListAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                Log.d("page1", "" +page );
                                JSONArray jsonArray = json.getJSONArray("distList");
                                ArrayList locatCityArray = new ArrayList();
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject localJson = jsonArray.getJSONObject(i);
                                    CityModel cityModel = new CityModel();
                                    cityModel.setId(localJson.getString("id"));
                                    cityModel.setDistrictName(localJson.getString("district_name"));
                                    locatCityArray.add(localJson.getString("district_name"));
                                    cityArray.add(cityModel);
                                }

                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, locatCityArray);
                                district.setAdapter(dataAdapter);
                                if (GLOBAL.cadetFormModel != null) {
                                    district.setSelection(cityArray.indexOf(GLOBAL.cadetFormModel.getDistrictName()));
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
                        FancyToast.makeText(context, "Some issue in loading", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("state_id", "" + id);
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void registration1() {
        nextBT.setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/cadreRegistrationAPIStep-1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");
                            if (status.equals("0")) {
                                GLOBAL.cadetID = json.getString("cadet_id");
                                GLOBAL.cadetName = "" + firstName.getText() + " " + midName.getText() + " " + lastName.getText();
                                FancyToast.makeText(context, msg, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                Intent intent = new Intent(CadetEnrollmentFormActivity.this, CadetEnrollmentFormActivity2.class);
                                startActivity(intent);
                                finish();
                            } else {
                                FancyToast.makeText(context, "" + msg, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        nextBT.setEnabled(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context, "Some issue in loading", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        nextBT.setEnabled(true);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("name", "" + firstName.getText() + " " + midName.getText() + " " + lastName.getText());
                params.put("nationality", "" + nation.getSelectedItem());
                params.put("dob", "" + dobTv.getText());
                params.put("father_name", "" + fFirstName.getText() + " " + fMidName.getText() + " " + fLastName.getText());
                params.put("mother_name", "" + mFirstName.getText() + " " + mMidName.getText() + " " + mLastName.getText());
                params.put("landmark", "" + landMark.getText());
                params.put("state", "" + state.getSelectedItem());
                params.put("district", "" + district.getSelectedItem());
                params.put("city", "" + villCityEt.getText());
                params.put("pin", "" + pincodeEt.getText());
                params.put("mobile_no", "" + mobile.getText());
                params.put("email", "" + email.getText());
                params.put("blood_group", "" + bloodGP.getSelectedItem());
                params.put("image_name", "" + imageString);
                params.put("parents_annual_income", "" + incomeSP.getSelectedItem());

                if (maleRb.isChecked())
                    params.put("gender", "Male");
                else
                    params.put("gender", "Female");

                params.put("nearest_railway_station", "" + nearRailway.getText());
                params.put("nearest_police_station", "" + nearPolice.getText());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean checkValidation1() {
        boolean ret = true;
        if (!Validation.hasText(firstName)) ret = false;
        if (!Validation.hasText(fFirstName)) ret = false;
        if (!Validation.hasText(mFirstName)) ret = false;
        if (!Validation.hasText(villCityEt)) ret = false;
        if (!Validation.hasText(landMark)) ret = false;
        if (!Validation.hasText(pincodeEt)) ret = false;

      /*  if (imageString != null)
            if (imageString.equals("")) ret = false;*/

        if (nation.getSelectedItem() == null) ret = false;
        if (state.getSelectedItem() == null) ret = false;
        if (district.getSelectedItem() == null) ret = false;
        if (bloodGP.getSelectedItem() == null) ret = false;
        if (incomeSP.getSelectedItem() == null) ret = false;

        if (!Validation.hasText(nearPolice)) ret = false;
        if (!Validation.hasText(nearRailway)) ret = false;

        if (!Validation.isPhoneNumber(mobile, true)) ret = false;
        /* if (!Validation.isEmailAddress(email, false)) ret = false; */  ///gaurav edited

        return ret;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery", "Capture Image"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                if (ContextCompat.checkSelfPermission(CadetEnrollmentFormActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                    cameraIntent();
                                } else {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                            FancyToast.makeText(context, "Permission Needed", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                                        }
                                    }
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA);
                                    }
                                }

                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                    Toast.makeText(CadetEnrollmentFormActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    cadetImage.setImageBitmap(bitmap);
                    imageString = getStringImage(getResizedBitmap(bitmap, 2000));

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CadetEnrollmentFormActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            cadetImage.setImageBitmap(thumbnail);
            imageString = getStringImage(getResizedBitmap(thumbnail, 2000));
            //saveImage(thumbnail);
            Toast.makeText(CadetEnrollmentFormActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

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

    public void sendPhysicalMail(View view) {
    }

    ////////////////////////////////////////////////////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakenPictureIntent();
            } else {
                Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void dispatchTakenPictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA);
        }
    }

}
