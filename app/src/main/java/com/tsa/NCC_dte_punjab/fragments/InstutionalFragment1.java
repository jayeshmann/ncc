package com.tsa.NCC_dte_punjab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.tsa.NCC_dte_punjab.R;

public class InstutionalFragment1 extends Fragment {

    private View view;
    private String htmlPages[]=new String[]{"honorarium.html"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup content_frame,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_test, content_frame, false);
        WebView wv;
        wv = view.findViewById(R.id.webView1);
        wv.loadUrl("file:///android_asset/"+htmlPages[0]);
        return view;

    }
}
