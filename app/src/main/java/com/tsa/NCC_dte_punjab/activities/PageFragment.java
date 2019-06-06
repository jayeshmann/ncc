package com.tsa.NCC_dte_punjab.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.fragments.AimFragment;
import com.tsa.NCC_dte_punjab.fragments.CoreFragment;
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

// In this case, the fragment displays simple text based on the page
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    //indexes after 7 belogs to UnitsCornerPageAdaptor
     Fragment fragment[]=new Fragment[]{new AimFragment(),new GenesisFragment(),new MottoFragment()
            ,new CoreFragment(),new PledgeFragment(),new NCCFlagFragment(),new NCCSongFragment(),new ShowPDFFragment(),new InstutionalFragment()};

    private int mPage;

    public static Fragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        //PageFragment fragment = new PageFragment();
        Fragment localFragment=null;//fragment[page];
        localFragment.setArguments(args);
        return localFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        return view;
    }
}
