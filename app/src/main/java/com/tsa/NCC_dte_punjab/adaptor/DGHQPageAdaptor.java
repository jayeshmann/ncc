package com.tsa.NCC_dte_punjab.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tsa.NCC_dte_punjab.fragments.DGNCC1Fragment;
import com.tsa.NCC_dte_punjab.fragments.DGNCC3Fragment;

/**
 * Created by Akhil Tripathi on 16-03-2018.
 */

public class DGHQPageAdaptor extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;

    private String tabTitles[] = new String[] { "DG NCC", "Organization"};
    Fragment fragment[]=new Fragment[]{new DGNCC1Fragment(),new DGNCC3Fragment()};

    public DGHQPageAdaptor(FragmentManager fm) {
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
        // Generate title based on item position
        return tabTitles[position];
    }
}

