package com.tsa.NCC_dte_punjab.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tsa.NCC_dte_punjab.fragments.UnityCornerFragment;
import com.tsa.NCC_dte_punjab.fragments.UnityCornerFragment1;
import com.tsa.NCC_dte_punjab.fragments.UnityCornerFragment2;
import com.tsa.NCC_dte_punjab.fragments.UnityCornerFragment3;
import com.tsa.NCC_dte_punjab.fragments.UnityCornerFragment4;
import com.tsa.NCC_dte_punjab.fragments.UnityCornerFragment5;
import com.tsa.NCC_dte_punjab.utils.GLOBAL;

/**
 * Created by Akhil Tripathi on 16-03-2018.
 */

public class UnitsCornerPageAdaptor extends FragmentPagerAdapter {
    final int PAGE_COUNT = 6;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String tabTitles[] = new String[] { "NCC Act","NCC Rules","Policies & Guidelines", "Annual Trg Directive", "P I Handbook", "Red Book" };
    Fragment fragment[]=new Fragment[]{new UnityCornerFragment(),new UnityCornerFragment1(),new UnityCornerFragment2()
    ,new UnityCornerFragment3(),new UnityCornerFragment4(),new UnityCornerFragment5()};
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////
    public UnitsCornerPageAdaptor(FragmentManager fm) {
        super(fm);
    }
    ///////////////////////////////////////////////////////////////////

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        GLOBAL.tabSelected=position;
        return  fragment[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

