package com.tsa.NCC_dte_punjab.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;

public class NCCFlagFragment extends Fragment {

    private View view;
    private TextView itemTV1;
    private ScrollView scrollView;
    private FloatingActionButton floatingActionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup content_frame,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_flag, content_frame, false);

        init();
        return view;

    }

    private void init() {
        itemTV1 = (TextView) view.findViewById(R.id.flag_tv);
        itemTV1.setText(Html.fromHtml(getString(R.string.flag)));

        scrollView=view.findViewById(R.id.scrollView);
    }

}
