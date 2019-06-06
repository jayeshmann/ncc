package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.adaptor.DownloadPageAdaptor;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

public class DownloadsActivity extends CustomActivity {
    private Context context;
    final String BASE_URL_PDF1="http://nccindia.nic.in/sites/default/files/";
    final String BASE_URL_PDF2="https://indianarmy.nic.in/writereaddata/documents/";
    final String BASE_URL_PDF3="http://nccdtepunjab.in/assets/web_documents/Enrollment_Form/";

    private String tabUrls[] = new String[] {
            BASE_URL_PDF3+"Declaration_By_Parents.pdf",  BASE_URL_PDF3+"Medical_Certificate.pdf",  BASE_URL_PDF3+"Nomination_Form.pdf",
            BASE_URL_PDF3+"Membership_and_Regimental_Fee_Receipt.pdf",  BASE_URL_PDF3+"Regimental_Fee_Receipt.pdf",
            BASE_URL_PDF3+"Indemnity_Bond.pdf",
            BASE_URL_PDF1+"Microsoft+Word+-+ACR_FORM__ANOs_.pdf",
            BASE_URL_PDF1+"apply_ano.pdf",
            BASE_URL_PDF1+"CADET+ENROLMENT+FORM(S)_1.pdf",
            BASE_URL_PDF1+"CADET+ENROLMENT+FORM.pdf",
            BASE_URL_PDF1+"APPLICATION+FOR+raising+NCC+in+institute.pdf",
            BASE_URL_PDF1+"MailService_e-mail_Subscription_Form_1.pdf",
            BASE_URL_PDF2+"CommonAppform.pdf",
            BASE_URL_PDF1+"form_ba_no.pdf",
            BASE_URL_PDF1+"form_ba_no.pdf" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_ncc);
        context=DownloadsActivity.this;
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //////////////////////////////////////////////////////////////////
                GLOBAL.docURL=tabUrls[position];
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new DownloadPageAdaptor(getSupportFragmentManager()));
        PagerSlidingTabStrip tabsStrip =findViewById(R.id.tabs);
        tabsStrip.setViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,HomeActivity.class));
        finish();
    }
}
