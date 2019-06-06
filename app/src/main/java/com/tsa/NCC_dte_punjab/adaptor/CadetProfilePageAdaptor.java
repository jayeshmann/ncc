package com.tsa.NCC_dte_punjab.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tsa.NCC_dte_punjab.fragments.CadetProfileFrg1;
import com.tsa.NCC_dte_punjab.fragments.CadetProfileFrg2;
import com.tsa.NCC_dte_punjab.fragments.CadetProfileFrg3;
import com.tsa.NCC_dte_punjab.fragments.CadetProfileFrg4;

/**
 * Created by Akhil Tripathi on 16-03-2018.
 */

public class CadetProfilePageAdaptor extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;

    private String tabTitles[] = new String[] { "Personal", "Educational","NCC Details","Bank Details"};
    Fragment fragment[]=new Fragment[]{CadetProfileFrg1.getInstance(), CadetProfileFrg2.getInstance(), CadetProfileFrg3.getInstance(), CadetProfileFrg4.getInstance()};

    public CadetProfilePageAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

