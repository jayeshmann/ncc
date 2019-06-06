package com.tsa.NCC_dte_punjab.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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
import com.tsa.NCC_dte_punjab.adaptor.LactureListAdapter;
import com.tsa.NCC_dte_punjab.models.LactureListModel;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class LactureListActivity extends CustomActivity {

    /////////////////////////////////////////////////////////
    private RecyclerView lactureList;
    private Context context;
    private ArrayList<LactureListModel> lactureListModels;
    private LactureListAdapter lactureListAdapter;
    private int GALLERY = 1, CAMERA = 2;
    private Uri imageUri;
    private Bitmap thumbnail;
    private String base64Image;
    private String anoId;
    private String userType;
    private int position = 0;
    //////////////////////////////////////////////////////////
    private Bundle bundle;
    public static String insttId;
    public static String battalionId;
    //////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lacture_list);
        bundle = getIntent().getExtras();
        init();
    }

    private void init() {

        if (bundle != null) {
            insttId = bundle.getString("instt_id");
            battalionId = bundle.getString("battalion_id");
            anoId = bundle.getString("ano_id");
            userType = bundle.getString("user_type");
        }

        lactureList = findViewById(R.id.lacture_list);
        context = LactureListActivity.this;
        lactureListModels = new ArrayList<>();
        /////////////////////////////////////////////////////////////////////////////////////////////
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lactureList.setLayoutManager(mLayoutManager);
        lactureList.setItemAnimator(new DefaultItemAnimator());
        ////////////////////////////////////////////////////////////////////////////////////////////
        getLactures();
    }

    public void getLactures() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "lectureListingAPI.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            //String msg = json.getString("msg");
                            JSONArray lectures = json.getJSONArray("lectures");
                            lactureListModels.clear();
                            if (status.equals("0")) {
                                for (int i = 0; i < lectures.length(); i++) {
                                    JSONObject localJson = lectures.getJSONObject(i);
                                    LactureListModel lactureListModel = new LactureListModel();
                                    lactureListModel.setLid(localJson.getString("lid"));
                                    lactureListModel.setLname(localJson.getString("lname"));
                                    lactureListModel.setLat(localJson.getString("lat"));
                                    lactureListModel.setLng(localJson.getString("lng"));
                                    lactureListModel.setImgStatus(localJson.getString("img_status"));
                                    lactureListModel.setGeoStatus(localJson.getString("geo_status"));
                                    lactureListModel.setLdate(localJson.getString("ldate"));
                                    lactureListModel.setWing(localJson.getString("wing"));
                                    lactureListModel.setAddress(localJson.getString("address"));


                                    if (lactureListModel.getGeoStatus().equals("no"))
                                        lactureListModel.setGeoTgged(false);
                                    else
                                        lactureListModel.setGeoTgged(true);

                                    lactureListModel.setImageCaptured(false);
                                    lactureListModel.setGeoTagApp(false);

                                    lactureListModel.setImageCapturedApp("");


                                    if (lactureListModel.getImgStatus().equals("no"))
                                        lactureListModel.setImageUploaded(false);
                                    else
                                        lactureListModel.setImageUploaded(true);

                                    lactureListModels.add(lactureListModel);
                                }

                                lactureListAdapter = new LactureListAdapter(lactureListModels, context);
                                lactureList.setAdapter(lactureListAdapter);
                            }
                            // Toast.makeText(context,""+msg, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(context, "Some issue in loading" + volleyError, FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("ano_id", anoId);
                params.put("user", "ano");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void selectImage(ArrayList<LactureListModel> lactureListModels, int position) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(LactureListActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                this.lactureListModels = lactureListModels;
                this.position = position;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        FancyToast.makeText(context, "Permission Needed", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA);
                }
            }
        } else {
            this.lactureListModels = lactureListModels;
            this.position = position;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
    }

    ////////////////////////////////////////////////////////
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        lactureListModels.get(position).setImageCaptured(true);
        reloadList();
        return encodedImage;
    }

    public void getLatLng(final ArrayList<LactureListModel> lactureListModels, final int position) {

        this.lactureListModels = lactureListModels;
        this.position = position;

        final double[] latitude = {0};
        final double[] longitude = {0};

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Acquire a reference to the system Location Manager
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                if (location.getLatitude() != 0)
                    locationManager.removeUpdates(this);

                longitude[0] = location.getLongitude();
                latitude[0] = location.getLatitude();
                ////////////////////////////////////////////////////////
                lactureListModels.get(position).setLng("" + longitude[0]);
                lactureListModels.get(position).setLat("" + latitude[0]);
                lactureListModels.get(position).setGeoTagApp(true);
                ////////////////////////////////////////////////////////
                FancyToast.makeText(context, "Your Location is - \nLat: " + latitude[0] + "\nLong: " + longitude[0], FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                reloadList();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        //  Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Missing Location Permission", Toast.LENGTH_SHORT).show();
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } ////////////////////////////////////////////////////////////////////////////////////////////

    }

    public void reloadList() {
        lactureListAdapter = new LactureListAdapter(lactureListModels, context);
        lactureList.setAdapter(lactureListAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, CadetListActivity.class);
        intent.putExtra("instt_id", insttId);
        intent.putExtra("battalion_id", battalionId);
        intent.putExtra("ano_id", anoId);
        intent.putExtra("user_type", userType);
        startActivity(intent);
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    base64Image = getStringImage(bitmap);
                    lactureListModels.get(position).setImageCapturedApp(base64Image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAMERA) {
            if (data != null) {
                thumbnail = (Bitmap) data.getExtras().get("data");
                base64Image = getStringImage(thumbnail);
                lactureListModels.get(position).setImageCapturedApp(base64Image);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakenPictureIntent();
            } else {
                FancyToast.makeText(context, "Permission Needed.", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
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
