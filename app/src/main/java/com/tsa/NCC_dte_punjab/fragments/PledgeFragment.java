package com.tsa.NCC_dte_punjab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;

public class PledgeFragment extends Fragment {

    private View view;
    private TextView coreTV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup content_frame,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pledge_fragment, content_frame, false);

        init();
        return view;

    }

    private void init() {
        coreTV=view.findViewById(R.id.pledgeTV);
        coreTV.setText(Html.fromHtml(getString(R.string.pledge)));
       }

}
