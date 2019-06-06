package com.tsa.NCC_dte_punjab.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;

public class AimFragment extends Fragment {

    private View view;
    private TextView itemTV1;
    private TextView itemTV2;
    private TextView itemTV3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup content_frame,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aim, content_frame, false);

        init();
        return view;

    }

    ///////////////////////////
    private void init() {
        itemTV1=view.findViewById(R.id.aim1_tv);
        itemTV1.setText(Html.fromHtml(getString(R.string.aim1)));


        itemTV2=view.findViewById(R.id.aim2_tv);
        itemTV2.setText(Html.fromHtml(getString(R.string.aim2)));

        itemTV3=view.findViewById(R.id.aim3_tv);
        itemTV3.setText(Html.fromHtml(getString(R.string.aim3)));
       
    }
    ///////////////////////////

}
