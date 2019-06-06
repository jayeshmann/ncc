package com.tsa.NCC_dte_punjab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.tsa.NCC_dte_punjab.activities.CustomActivity;
import com.tsa.NCC_dte_punjab.activities.HomeActivity;
import com.tsa.NCC_dte_punjab.adaptor.UnitsCornerPageAdaptor;

public class UnitsCornersActivity extends CustomActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_ncc);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new UnitsCornerPageAdaptor(getSupportFragmentManager()));

        context=UnitsCornersActivity.this;
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip =findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(context,HomeActivity.class));
        finish();
    }
}
