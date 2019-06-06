package com.tsa.NCC_dte_punjab.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;

public class MottoFragment extends Fragment {

    private View view;
    private TextView itemTV1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup content_frame,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_moto, content_frame, false);

        init();
        return view;

    }

    private void init() {
        itemTV1=view.findViewById(R.id.mottoTv);
        itemTV1.setText(Html.fromHtml(getString(R.string.moto)));
       }

}
