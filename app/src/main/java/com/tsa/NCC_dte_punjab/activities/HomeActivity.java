package com.tsa.NCC_dte_punjab.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.UnitsCornersActivity;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

public class HomeActivity extends CustomActivity {
    private TextView regTv;
    private TextView lactureTv;
    private Context context;
    LinearLayout mLayout;
    private ImageView button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;
        /**
         * checking fro the permissions
         */
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            int requestCode = 200;
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(HomeActivity.this, button1);
                //Inflating the Popup using xml file
                if (GLOBAL.isRegistered) {
                    popup.getMenuInflater()
                            .inflate(R.menu.cadet_menu, popup.getMenu());
                } else {
                    popup.getMenuInflater()
                            .inflate(R.menu.menu_main, popup.getMenu());
                }

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        Intent intent = new Intent(HomeActivity.this, GPLogin.class);
                        if (id == R.id.adg) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.ADG_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 1;
                        } else if (id == R.id.ddg) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.ADG_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 2;
                        } else if (id == R.id.director) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.ADG_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 3;
                        } else if (id == R.id.ad_trg) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.ADG_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 4;
                        } else if (id == R.id.ad_lgs) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.ADG_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 5;
                        } else if (id == R.id.ad_ms) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.ADG_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 6;
                        } else if (id == R.id.pc) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.ADG_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 15;
                        }
                        //////////////////////////////////////////////////////////////////////////////////
                        else if (id == R.id.gp_cdr) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.GP_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 8;
                        } else if (id == R.id.dy_gp_cdr) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.GP_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 9;
                        } else if (id == R.id.trg_off) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.GP_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 10;
                        }
                        ////////////////////////////////////////////////////////////////////////////////////
                        else if (id == R.id.co) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.CO_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 11;
                        } else if (id == R.id.adm_officer) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.CO_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 12;
                        }
                        ///////////////////////////////////////////////////////////////////////////////////
                        else if (id == R.id.ano_opt) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.ANO_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 13;
                        } else if (id == R.id.principal) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.PRINCIPAL_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 14;
                        } else if (id == R.id.cadet_opt) {
                            GLOBAL.LOGIN_TYPE = GLOBAL.CADET_LOGIN;
                            GLOBAL.LOGIN_TYPE_ID = 16;
                        }
                        else if (id == R.id.logout) {
                            logout();
                        } else if (id == R.id.view_profile) {
                            GLOBAL.LOGIN_TYPE = 8;
                            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                            finish();
                        } else if (id == R.id.upload_doc) {
                            GLOBAL.LOGIN_TYPE = 8;
                            startActivity(new Intent(HomeActivity.this, DocUploadFormCadetActivity.class));
                            finish();
                        }
                        else if (id == R.id.notification) {
                            GLOBAL.LOGIN_TYPE = 8;
                            Intent intent1=new Intent(context,NotificationActivity.class);
                            intent1.putExtra("instt_id",GLOBAL.cadetModel.getInsttId());
                            intent1.putExtra("battalion_id",GLOBAL.cadetModel.getBattalionId());
                            intent1.putExtra("ano_id", GLOBAL.cadetModel.getAnoID());
                            intent1.putExtra("user_type","cadet");
                            intent1.putExtra("go","HomeActivity");
                            startActivity(intent1);
                            finish();
                        }
                        else {
                            GLOBAL.LOGIN_TYPE = 8;
                        }

                        if (!(GLOBAL.LOGIN_TYPE == 8)) {
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method

        mLayout = findViewById(R.id.root1);
        ////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////
        regTv = findViewById(R.id.reg_tv);
        lactureTv = findViewById(R.id.lacture_tv);
        ////////////////////////////////////////////////////////////

        ///////////////////////////////////////////
        if (GLOBAL.cadetModel == null) {
            regTv.setVisibility(View.VISIBLE);
            lactureTv.setVisibility(View.GONE);
        } else {
            if (GLOBAL.cadetModel.getRegistrationNo().equals("")) {
                regTv.setVisibility(View.GONE);
                lactureTv.setVisibility(View.GONE);
            } else {
                regTv.setVisibility(View.GONE);
                lactureTv.setVisibility(View.VISIBLE);
            }
        }

        //////////////////////////////////////////////
    }

    public void goReg(View view) {
        startActivity(new Intent(HomeActivity.this, SelectRegActivity.class));
        finish();
    }

    public void goAboutNcc(View view) {
        startActivity(new Intent(HomeActivity.this, AboutNCCActivity.class));
        finish();
    }

    public void goUnitCorner(View view) {
        startActivity(new Intent(HomeActivity.this, UnitsCornersActivity.class));
        finish();
    }

    public void goInstitutional(View view) {
        startActivity(new Intent(HomeActivity.this, InstutionalCornersActivity.class));
        finish();
    }

    public void goTodayLacture(View view) {
        startActivity(new Intent(HomeActivity.this, TodayLactureActivity.class));
        finish();
    }

    public void goProfile(View view) {
        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        finish();
    }

    public void goDownload(View view) {
        startActivity(new Intent(HomeActivity.this, DownloadsActivity.class));
        finish();
    }

    private void logout() {
        GLOBAL.LOGIN_TYPE = 8;
        GLOBAL.isRegistered = false;
        GLOBAL.cadetModel = null;
        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        createMyDialogue(context, "Do You Want To Logout ?", true, false, new Intent(Intent.ACTION_MAIN));
    }

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
            final TextView msgBox = dialog.findViewById(R.id.msg_box);
            msgBox.setText(msg);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
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

    public void goToStudyMatrial(View v) {
        createMyDialogue(context);
    }

    public void createMyDialogue(final Context context) {
        // TODO Create custom dialog
        final Dialog dialog = new Dialog(context);

        //for no Title bar
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.study_material_dialogue);

        //setting layout hight width
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button ok = dialog.findViewById(R.id.ok_button);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = "com.tsa.nccapp"; // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
