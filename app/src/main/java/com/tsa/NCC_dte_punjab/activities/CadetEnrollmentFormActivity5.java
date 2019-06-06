package com.tsa.NCC_dte_punjab.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class CadetEnrollmentFormActivity5 extends CustomActivity {
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private ImageView cadetImage;
    private String imageString;
    private Context context;

    private Button upload;
    private Button nextBT;

    private TextView part1;
    private TextView part2;
    private TextView part3;

    private String partST1;
    private String partST2;
    private String partST3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollment_form5);
        init();
    }

    private void init() {
        if (GLOBAL.cadetFormModel!=null&& GLOBAL.cadetFormModel.getName()!=null)
        {
            partST1 = "1.\tI " + GLOBAL.cadetFormModel.getName() + " solemnly declare that the answers I have given to the questions in this form are true and that no part of them is false, and that I am willing to fulfill the engagement made.";
            partST2 = "2.\tI " + GLOBAL.cadetFormModel.getName() + " promise that I will honestly and faithfully serve my country and abide by the rules and Regulation of the National Cadet Corps that I will, to the best of my ability.";
            partST3 = "3.\tI " + GLOBAL.cadetFormModel.getName() + " further promise that after enrolment,I will have no claim on authorities  for  any  compensation  in the  event  of  injury  due  to  accident  during  training  camps,  courses,  travelling  and while on YEP or any other such NCC events like RDC and IDC. I understand I have no service liability.";
        }
        else
        {
            partST1 = "1.\tI " + GLOBAL.cadetName + " solemnly declare that the answers I have given to the questions in this form are true and that no part of them is false, and that I am willing to fulfill the engagement made.";
            partST2 = "2.\tI " + GLOBAL.cadetName + " promise that I will honestly and faithfully serve my country and abide by the rules and Regulation of the National Cadet Corps that I will, to the best of my ability.";
            partST3 = "3.\tI " + GLOBAL.cadetName + " further promise that after enrolment,I will have no claim on authorities  for  any  compensation  in the  event  of  injury  due  to  accident  during  training  camps,  courses,  travelling  and while on YEP or any other such NCC events like RDC and IDC. I understand I have no service liability.";
        }

        ///////////////////////////////////////////////////
        cadetImage = findViewById(R.id.image_iv);
        upload = findViewById(R.id.upload_bt);
        context = CadetEnrollmentFormActivity5.this;
        ///////////////////////////////////////////////////
        part1 = findViewById(R.id.part1_tv);
        part1.setText(partST1);
        part2 = findViewById(R.id.part2_tv);
        part2.setText(partST2);
        part3 = findViewById(R.id.part3_tv);
        part3.setText(partST3);

        nextBT = findViewById(R.id.next_button);

        nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation())
                    registration5();
                else
                    FancyToast.makeText(context, "Fill All Fields", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    public void registration5() {
        nextBT.setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "cadre_sign_up_login/cadreRegistrationAPIStep-5.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        nextBT.setEnabled(true);
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            if (status.equals("0")) {
                                Intent intent = new Intent(context, HomeActivity.class);
                                createMyDialogue(context, "Enrollment Form Submitted Successfully Please Check Your Mail For Details", true, false, intent);
                                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            } else {
                                FancyToast.makeText(context, "Some Issue in loading Data", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
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

                if (GLOBAL.cadetID!=null)
                params.put("cadet_id", GLOBAL.cadetID);
                else
                    params.put("cadet_id", GLOBAL.cadetFormModel.getCadetId());
                //params.put("cadet_id","260");
              // params.put("signature_image", ""+imageString);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean checkValidation() {
        boolean ret = true;
        //if (imageString.equals("")) ret = false;
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
                                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                    cameraIntent();
                                } else {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
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
                    //  Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
                    cadetImage.setImageBitmap(bitmap);
                    imageString = getStringImage(getResizedBitmap(bitmap, 2000));
                } catch (IOException e) {
                    e.printStackTrace();
                    FancyToast.makeText(context, "Failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            cadetImage.setImageBitmap(thumbnail);
            imageString = getStringImage(getResizedBitmap(thumbnail, 2000));
            Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
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

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    //////////////////////////////////////////////
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

    //////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void createMyDialogue(final Context context, String msg, boolean okButton, boolean canButton, final Intent intent) {
        // TODO Create custom dialog
        final Dialog dialog = new Dialog(context);

        //for no Title bar
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.exit_dialogue);

        //setting layout hight width
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (okButton) {
            Button ok = dialog.findViewById(R.id.ok_button);
            Button cancleButton = dialog.findViewById(R.id.cancle_button);
            final TextView msgBox = dialog.findViewById(R.id.msg_box);
            msgBox.setText(msg);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(intent);
                    finish();
                }
            });

            if (canButton) {
                cancleButton.setVisibility(View.VISIBLE);
            } else {
                cancleButton.setVisibility(View.GONE);
            }

        }

        dialog.show();
    }
}
