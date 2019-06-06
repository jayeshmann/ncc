package com.tsa.NCC_dte_punjab.adaptor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tsa.NCC_dte_punjab.fragments.DownloadFragment;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

/**
 * Created by Akhil Tripathi on 16-03-2018.
 */
public class DownloadPageAdaptor extends FragmentPagerAdapter {
    final int PAGE_COUNT = 15;


    private String tabTitles[] = new String[] {
            "Declaration By Candidate","Medical Certificate","Nomination Form"
            ,"Membership & Regimental Fee Receipt","Regimental Fee Receipt","Indemnity Bond",
            "ACR form for ANOs",
            "Application for appointment of ANO",
            "Cadets Enrolment form : SD/SW",
            "Cadets Enrolment form : JD/JW",
            "Application Form: Raising NCC in Institutes",
            "Application form for NIC E-mail",
            "Special Entry Scheme - Army",
            "Form for Allotment of BA number",
            "Sample LDC Exam papers" };



    public DownloadPageAdaptor(FragmentManager fm) {
        super(fm);
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        // set Fragmentclass Arguments
        DownloadFragment fragobj = new DownloadFragment();
        fragobj.setArguments(bundle);
        return fragobj;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}

