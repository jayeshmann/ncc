package com.tsa.NCC_dte_punjab.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.utils.CustomVolleyRequest;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class DocUploadFormCadetActivity extends CustomActivity {

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private NetworkImageView cadetImage[]=new NetworkImageView[9];
    private String imageString[]=new String[9];
    private Context context;
    private int docNo=0;
    private Button submit;

    private Button upload[]=new Button[9];
    private Button choose[]=new Button[9];
    private String titlesString[]=new String[]{"Aadhaar Card",
            "Pan Card",
            "DOB Certificate",
            "Declaration By Parents",
            "Medical Certificate",
            "Nomination Form",
            "Membership & Regimental Fee Receipt",
            "Regimental Fee Receipt",
            "Indemnity Bond"};
    private String paramString[]=new String[]{"adhaar_card_image",
"pan_card_image", "dob_cert_image", "parent_dec_image",
"medical_cert_image", "nomination_form_image", "member_regiment_fee_receipt_image",
"regiment_fee_receipt_image", "indemnity_bond_image"};

    private TextView titles[]=new TextView[9];

    String imageNames[] = new String[]{GLOBAL.cadetModel.getAdhaarCardImage(),GLOBAL.cadetModel.getPanCardImage(),GLOBAL.cadetModel.getDobCertImage(),
            GLOBAL.cadetModel.getParentDecImage(),GLOBAL.cadetModel.getMedicalCertImage(),GLOBAL.cadetModel.getNominationFormImage(),
            GLOBAL.cadetModel.getMemberRegimentFeeReceiptImage(),GLOBAL.cadetModel.getRegimentFeeReceiptImage(),GLOBAL.cadetModel.getIndemnityBondImage()};

    private int docsView[]=new int[]{R.id.doc1,R.id.doc2,R.id.doc3,R.id.doc4,R.id.doc5,R.id.doc6,R.id.doc7,R.id.doc8,R.id.doc9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_upload_form_cadet);
        init();
    }

    private void init() {
        submit=findViewById(R.id.submit_button);
        for (int i=0;i<docsView.length;i++)
        {
            View view=findViewById(docsView[i]);
            upload[i]=view.findViewById(R.id.upload_bt);
            choose[i]=view.findViewById(R.id.choose_bt);
            cadetImage[i]=view.findViewById(R.id.doc_iv2);
            titles[i]=view.findViewById(R.id.title1);
            titles[i].setText(titlesString[i]);

            String src="http://nccdtepunjab.in/API/cadre/documents/"+GLOBAL.cadetModel.getEmail()+"/"+imageNames[i];
            loadImage(src,cadetImage[i]);

            final int finalI = i;
            choose[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    docNo= finalI;
                    if(imageNames[finalI].equals(""))
                    showPictureDialog();
                    else
                        Toast.makeText(context, "Already Uploaded", Toast.LENGTH_SHORT).show();
                }
            });

            upload[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageNames[finalI].equals(""))
                        registration5( finalI);
                    else
                        Toast.makeText(context, "Already Uploaded", Toast.LENGTH_SHORT).show();

                }
            });
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ///////////////////////////////////////////////////
        context=DocUploadFormCadetActivity.this;
        ///////////////////////////////////////////////////


    }
    public void registration5(final int docNo) {
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "uploadCadreDocument.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                               // GLOBAL.cadetID = json.getString("cadet_id");
                            }

                                Toast.makeText(context, ""+json.getString("msg"), Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        FancyToast.makeText(context,"Some issue in loading", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();

                params.put("document_image_name",imageString[docNo]);
                params.put("cadet_id", GLOBAL.cadetModel.getCadetId());
                params.put("document_name",paramString[docNo]);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (imageString.equals(""))ret=false;

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
                            case 1:
                                if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                    cameraIntent();
                                }
                                else{
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                                            Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
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
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
                    cadetImage[docNo].setImageBitmap(bitmap);
                    imageString[docNo]=getStringImage(getResizedBitmap(bitmap,2000));

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            cadetImage[docNo].setImageBitmap(thumbnail);
            imageString[docNo]=getStringImage(getResizedBitmap(thumbnail,2000));
            //saveImage(thumbnail);
           // Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
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

        float bitmapRatio = (float)width / (float) height;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void loadImage(String url,NetworkImageView imageView1){
        if(url.equals("")){
            Toast.makeText(this,"Please enter a URL",Toast.LENGTH_LONG).show();
            return;
        }

        ImageLoader imageLoader = CustomVolleyRequest.getInstance(DocUploadFormCadetActivity.this).getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView1,
                R.drawable.logo, android.R.drawable
                        .ic_dialog_alert));
        if (imageView1!=null)
            imageView1.setImageUrl(url, imageLoader);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    //////////////////////////////////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                dispatchTakenPictureIntent();
            }
            else{
                Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void dispatchTakenPictureIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAMERA);
        }
    }
}
