package com.tsa.NCC_dte_punjab.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.pdf.FileUtils;
import com.tsa.NCC_dte_punjab.pdf.permission.PermissionsActivity;
import com.tsa.NCC_dte_punjab.pdf.permission.PermissionsChecker;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.tsa.NCC_dte_punjab.pdf.LogUtils.LOGE;
import static com.tsa.NCC_dte_punjab.pdf.permission.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.tsa.NCC_dte_punjab.pdf.permission.PermissionsChecker.REQUIRED_PERMISSION;

public class InstituteProfileActivity extends CustomActivity {

    private TextView instituteTv;

    private TextView nameTv;
    private TextView emailTv;
    private TextView mobileTv;

    private TextView aadhaarTv;

    private TextView DOBTv;
    private TextView regNoTv;

    private Context context;
    private Bundle bundle;
    private String goTo = "";

    ////////////////////////////////////////////
    private String hqRegID;
    private String battalionId;
    Context mContext;
    PermissionsChecker checker;
    ////////////////////////////////////////////

    Chunk mTitleChunk[] = new Chunk[12];
    Paragraph mTitleParagraph[] = new Paragraph[12];

    String titles[] = new String[]{
            "InsttName",
            "mName",
            "Email",
            "Mobile",
            "Aadhaar No",
            "Registration Number"};

    String titleValus[] = new String[]{
            GLOBAL.instituteanoModel.getInsttName(), GLOBAL.instituteanoModel.getName(), GLOBAL.instituteanoModel.getEmail(),
            GLOBAL.instituteanoModel.getMobile(), GLOBAL.instituteanoModel.getAadhaarNo(), GLOBAL.instituteanoModel.getRegistrationNo(),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_profile);

        mContext = getApplicationContext();

        checker = new PermissionsChecker(this);
        init();
    }

    private void init() {
        bundle = getIntent().getExtras();

        if (bundle != null) {
            hqRegID = bundle.getString("hq_reg_id");
            battalionId=bundle.getString("battalion_id");
            goTo=bundle.getString("go_to");
        }

        context = InstituteProfileActivity.this;
        instituteTv = findViewById(R.id.institute_tv);
        nameTv = findViewById(R.id.name_tv);
        emailTv = findViewById(R.id.email_tv);
        mobileTv = findViewById(R.id.mobile_tv);
        aadhaarTv = findViewById(R.id.aadhar_tv);
        regNoTv = findViewById(R.id.reg_tv);

        if (GLOBAL.instituteanoModel != null) {
            instituteTv.setText(GLOBAL.instituteanoModel.getInsttName());
            nameTv.setText(GLOBAL.instituteanoModel.getName());
            emailTv.setText(GLOBAL.instituteanoModel.getEmail());
            mobileTv.setText(GLOBAL.instituteanoModel.getMobile());
            aadhaarTv.setText(GLOBAL.instituteanoModel.getAadhaarNo());
            regNoTv.setText(GLOBAL.instituteanoModel.getRegistrationNo());
        }

        com.github.clans.fab.FloatingActionButton fab = findViewById(R.id.pdf_feb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {
                    PermissionsActivity.startActivityForResult(InstituteProfileActivity.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);
                } else {
                    createPdf(FileUtils.getAppPath(mContext) + nameTv.getText() + ".pdf");
                }
            }
        });
    }


    public void createPdf(String dest) {

        if (new File(dest).exists()) {
            new File(dest).delete();
        }

        try {
            /**
             * Creating Document
             */
            Document document = new Document();

            // Location to save
            PdfWriter.getInstance(document, new FileOutputStream(dest));

            // Open to write
            document.open();

            // Document Settings
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("TSA");
            document.addCreator("Akhil Tripathi");

            /***
             * Variables for further use....
             */
            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 26.0f;

            /**
             * How to USE FONT....
             */
            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);

            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            // Title Order Details...
            // Adding Title....
            Font mOrderDetailsTitleFont = new Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderDetailsTitleChunk = new Chunk("Form Details", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            int j=0;
            int k=0;
            // Adding Chunks for Title and value
            Font mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Font mOrderIdValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);

            for (int i = 0; i < 12; i++) {

                if (i % 2 == 0) {
                    mTitleChunk[i] = new Chunk(titles[j], mOrderIdFont);
                    j++;
                } else {
                    if (titleValus[k]==null||titleValus[k].equals(""))
                        titleValus[k]="Not Available";

                    mTitleChunk[i] = new Chunk(""+titleValus[k], mOrderIdValueFont);
                    k++;
                }
                mTitleParagraph[i] = new Paragraph(mTitleChunk[i]);
                document.add(mTitleParagraph[i]);
            }

            document.close();

            Toast.makeText(mContext, "Downloaded on Path "+dest, Toast.LENGTH_LONG).show();

            showPDF(dest);

        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(mContext, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

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
        Intent intent;
        if (goTo.equals("1"))
        {
            intent = new Intent(context, COManageActivity.class);
        }
        else
        {
            intent= new Intent(context, Manage2Activity.class);
        }
        intent.putExtra("hq_reg_id", hqRegID);
        intent.putExtra("battalion_id",battalionId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ////////////////////////////////////////////////////////////////////////////////////////
        startActivity(intent);
        finish();
    }

    public void showPDF(String path) {
        File file = new File(path);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }

}
