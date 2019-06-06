package com.tsa.NCC_dte_punjab.adaptor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tsa.NCC_dte_punjab.fragments.InstutionalFragment;
import com.tsa.NCC_dte_punjab.fragments.InstutionalFragment1;
import com.tsa.NCC_dte_punjab.fragments.InstutionalFragment2;
import com.tsa.NCC_dte_punjab.fragments.InstutionalFragment3;
import com.tsa.NCC_dte_punjab.fragments.InstutionalFragment4;

/**
 * Created by Akhil Tripathi on 16-03-2018.
 */

public class InstutionalPageAdaptor extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;

    private String tabTitles[] = new String[] {"Enrollment Procedure" ,"Honorarium/Allowances \n ANO/Caretaker",
            "Promotions of ANOs","QRs for Appointment: ANO","ANO Hand Book"};
    Fragment fragment[]=new Fragment[]{new InstutionalFragment(),new InstutionalFragment1(),new InstutionalFragment2()
            ,new InstutionalFragment3(),new InstutionalFragment4()};

    public InstutionalPageAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        //GLOBAL.tabSelected=position;
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        return  fragment[position];
       // return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

