package com.tsa.NCC_dte_punjab.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tsa.NCC_dte_punjab.fragments.AimFragment;
import com.tsa.NCC_dte_punjab.fragments.CoreFragment;
import com.tsa.NCC_dte_punjab.fragments.FAQFragment;
import com.tsa.NCC_dte_punjab.fragments.GenesisFragment;
import com.tsa.NCC_dte_punjab.fragments.InstutionalFragment;
import com.tsa.NCC_dte_punjab.fragments.MottoFragment;
import com.tsa.NCC_dte_punjab.fragments.NCCFlagFragment;
import com.tsa.NCC_dte_punjab.fragments.NCCSongFragment;
import com.tsa.NCC_dte_punjab.fragments.PledgeFragment;
import com.tsa.NCC_dte_punjab.fragments.ShowPDFFragment;

/**
 * Created by Akhil Tripathi on 16-03-2018.
 */

public class AboutNCCPageAdaptor extends FragmentPagerAdapter {
    final int PAGE_COUNT = 8;

    private String tabTitles[] = new String[] { "Aim","Genesis","Motto Of NCC", "Core Values", "Pledge", "NCC Flag","NCC Song","FAQ" };
    Fragment fragment[]=new Fragment[]{new AimFragment(),new GenesisFragment(),new MottoFragment()
            ,new CoreFragment(),new PledgeFragment(),new NCCFlagFragment(),new NCCSongFragment(),new ShowPDFFragment(),new InstutionalFragment(),new FAQFragment()};

    public AboutNCCPageAdaptor(FragmentManager fm) {
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

