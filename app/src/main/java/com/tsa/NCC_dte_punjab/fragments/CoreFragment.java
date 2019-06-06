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

public class CoreFragment extends Fragment {

    private View view;
    private TextView coreTV;

    private ScrollView scrollView;
    private FloatingActionButton floatingActionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup content_frame,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_core, content_frame, false);

        init();
        return view;

    }

    private void init()
    {
        coreTV=view.findViewById(R.id.coreTV);
        coreTV.setText(Html.fromHtml(getString(R.string.core)));

        scrollView=view.findViewById(R.id.scrollView);
    }

}
