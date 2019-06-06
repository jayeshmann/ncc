package com.tsa.NCC_dte_punjab.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

public class LactureMgtActivity extends CustomActivity {

    private int REQUEST_CAMERA = 0;
    private Uri imageUri;
    private Bitmap thumbnail;
    private Context context;
    private String base64Image;
    private int clickedPosition;
    private final int CAMERA_RESULT = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lacture_mgt);
        init();
    }

    private void init() {
        context=LactureMgtActivity.this;
        selectImage();
    }

    public void selectImage() {
        if(ContextCompat.checkSelfPermission(LactureMgtActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            dispatchTakenPictureIntent();
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                    FancyToast.makeText(context,"Permission Needed.",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_RESULT);
            }
        }
    }

    private void dispatchTakenPictureIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAMERA_RESULT);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(resultCode == RESULT_OK){
                if(requestCode == CAMERA_RESULT){
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    base64Image=getStringImage(bitmap);                }
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

    public void getRegistered() {
        final ProgressDialog progressBar=new ProgressDialog(context);
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "uploadLectureImageAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressBar.dismiss();
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg=json.getString("msg");
                            Log.e("json",json.toString());
                            if (status.equals("0")) {

                                startActivity(new Intent(LactureMgtActivity.this,HomeActivity.class));
                            }
                            FancyToast.makeText(context,""+msg,FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressBar.dismiss();
                        FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("lecture_id", "" + 4);
                params.put("image_name", base64Image);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void upLoadImage(View view) {
        getRegistered();
    }

    public void enterLatLng(View view) {
/*
        // GPSTracker class
        GPSTracker gps;
        // create class object
        gps = new GPSTracker(LactureMgtActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            FancyToast.makeText(context,"Your Location is - \nLat: " + latitude + "\nLong: " + longitude,FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();


        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_RESULT){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                dispatchTakenPictureIntent();
            }
            else{
                FancyToast.makeText(context,"Some issue in loading",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
