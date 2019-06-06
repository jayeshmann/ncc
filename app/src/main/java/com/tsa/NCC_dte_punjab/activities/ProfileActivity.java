package com.tsa.NCC_dte_punjab.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.tsa.NCC_dte_punjab.custom.TouchImageView;
import com.tsa.NCC_dte_punjab.databinding.ActivityProfileBinding;
import com.tsa.NCC_dte_punjab.pdf.permission.PermissionsActivity;
import com.tsa.NCC_dte_punjab.pdf.permission.PermissionsChecker;
import com.tsa.NCC_dte_punjab.utils.CustomVolleyRequest;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static com.tsa.NCC_dte_punjab.pdf.permission.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.tsa.NCC_dte_punjab.pdf.permission.PermissionsChecker.REQUIRED_PERMISSION;

public class ProfileActivity extends CustomActivity {
    private TouchImageView cadetImage[] = new TouchImageView[9];
    private String titlesString[] = new String[]{"Aadhaar Card",
            "Pan Card",
            "DOB Certificate",
            "Declaration By Parents",
            "Medical Certificate",
            "Nomination Form",
            "Membership & Regimental Fee Receipt",
            "Regimental Fee Receipt",
            "Indemnity Bond"};

    private TextView titlesTv[] = new TextView[9];

    private int docsView[] = new int[]{R.id.doc1, R.id.doc2, R.id.doc3, R.id.doc4, R.id.doc5, R.id.doc6, R.id.doc7, R.id.doc8, R.id.doc9};


    private NetworkImageView cadetIV;

    private Bundle bundle;
    private String goTo = "";
    ////////////////////////////////////////////
    private String hqRegID;
    private String cadetsOnBatallion;
    private String insttId;
    private String battalionId;
    Context mContext;
    PermissionsChecker checker;
    ////////////////////////////////////////////
    public static String anoId;
    public static String userType;
    ActivityProfileBinding activityProfileBinding;

    /////////////////////////////////////////////

    String imageNames[] = new String[]{GLOBAL.cadetModel.getAdhaarCardImage(), GLOBAL.cadetModel.getPanCardImage(), GLOBAL.cadetModel.getDobCertImage(),
            GLOBAL.cadetModel.getParentDecImage(), GLOBAL.cadetModel.getMedicalCertImage(), GLOBAL.cadetModel.getNominationFormImage(),
            GLOBAL.cadetModel.getMemberRegimentFeeReceiptImage(), GLOBAL.cadetModel.getRegimentFeeReceiptImage(), GLOBAL.cadetModel.getIndemnityBondImage()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        mContext = ProfileActivity.this;
        checker = new PermissionsChecker(this);

        if (GLOBAL.LOGIN_TYPE == GLOBAL.CADET_LOGIN && GLOBAL.cadetModel.getAdhaarCardImage().equals(""))
            createMyDialogue(mContext, "Please upload the scaned copy required documents", true, false, new Intent(mContext, DocUploadFormCadetActivity.class));

        init();
    }

    private void init() {
        bundle = getIntent().getExtras();
        activityProfileBinding.setCadetDetailsModels(GLOBAL.cadetModel);
        if (bundle != null) {
            goTo = bundle.getString("goTo");
            hqRegID = bundle.getString("hq_reg_id");
            cadetsOnBatallion = bundle.getString("cadets_on_batallion");
            insttId = bundle.getString("instt_id");
            battalionId = bundle.getString("battalion_id");
            anoId = bundle.getString("ano_id");
            userType = bundle.getString("user_type");
        }
        if (GLOBAL.LOGIN_TYPE == GLOBAL.ANO_LOGIN) {
            activityProfileBinding.physicalIv.setVisibility(View.VISIBLE);
        } else {
            activityProfileBinding.physicalIv.setVisibility(View.GONE);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < docsView.length; i++) {
            View view = findViewById(docsView[i]);
            cadetImage[i] = view.findViewById(R.id.doc_iv1);
            titlesTv[i] = view.findViewById(R.id.title1);
            titlesTv[i].setText(titlesString[i]);
            final String src = "http://nccdtepunjab.in/API/cadre/documents/" + GLOBAL.cadetModel.getEmail() + "/" + imageNames[i];
            cadetImage[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog settingsDialog = new Dialog(ProfileActivity.this);
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout, null));
                    NetworkImageView popupImageView = settingsDialog.findViewById(R.id.popup_imageview);
                    ImageView cross = settingsDialog.findViewById(R.id.cross_iv);
                    cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            settingsDialog.dismiss();
                        }
                    });
                    loadImage(src, popupImageView);
                    settingsDialog.show();
                }
            });
            loadImage(src, cadetImage[i]);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        cadetIV = findViewById(R.id.cadet_iv1);
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (GLOBAL.cadetModel != null) {
            String url = "http://nccdtepunjab.in/API/cadre/profile_images/" + GLOBAL.cadetModel.getEmail() + "/" + GLOBAL.cadetModel.getProfileImage();
            loadImage(url, cadetIV);
        }

        com.github.clans.fab.FloatingActionButton fab = findViewById(R.id.pdf_feb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {
                    PermissionsActivity.startActivityForResult(ProfileActivity.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);
                } else {
                    downloadPdf();
                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void loadImage(String url, NetworkImageView imageView1) {
        if (url.equals("")) {
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_LONG).show();
            return;
        }

        ImageLoader imageLoader = CustomVolleyRequest.getInstance(ProfileActivity.this).getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView1,
                R.drawable.logo, android.R.drawable.ic_dialog_alert));
        if (imageView1 != null)
            imageView1.setImageUrl(url, imageLoader);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            Toast.makeText(mContext, "Permission Granted to Save", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Permission not granted, Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            Class<?> act = Class.forName(goTo);
            Intent intent = new Intent(mContext, act);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ////////////////////////////////////////////////////////////////////////////////////////
            intent.putExtra("hq_reg_id", hqRegID);
            intent.putExtra("cadets_on_batallion", cadetsOnBatallion);
            intent.putExtra("instt_id", insttId);
            intent.putExtra("battalion_id", battalionId);
            ////////////////////////////////////////////////////////////////////////////////////////
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ////////////////////////////////////////////////////////////////////////////////////////
            Intent intent = new Intent(mContext, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ////////////////////////////////////////////////////////////////////////////////////////
            startActivity(intent);
            finish();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void createMyDialogue(final Context context, String msg, boolean okButton, final boolean canButton, final Intent intent) {
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
            final TextView msgBox = dialog.findViewById(R.id.msg_box);
            msgBox.setText(msg);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (canButton) {
                        sendMail();
                        dialog.dismiss();
                    } else {
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

        Button cancle = dialog.findViewById(R.id.cancle_button);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void sendPhysicalMail(View view) {
        if (GLOBAL.cadetModel.getPhysicalMailStatus().equals("1")) {
            sendMail();
        } else {
            createMyDialogue(mContext, "Mail Is Already Sent Click Ok Button To Send Again", true, true, new Intent(mContext, DocUploadFormCadetActivity.class));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void downloadPdf() {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse("http://nccdtepunjab.in/admin/download_cadet_detail.php?id=" + GLOBAL.cadetModel.getRegisterId()));
        startActivity(viewIntent);
    }

    public void sendMail()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GLOBAL.BASE_URL + "physicalTestApproval.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String status = json.getString("status");
                            String msg = json.getString("msg");
                            if (status.equals("0")) {
                                FancyToast.makeText(mContext, msg, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        FancyToast.makeText(mContext, "Some issue in loading" + volleyError, FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("cadet_id", "" + GLOBAL.cadetModel.getCadetId());
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}


