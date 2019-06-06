package com.tsa.NCC_dte_punjab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.tsa.NCC_dte_punjab.R;


public class FAQFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            content_frame, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_test, content_frame, false);
        WebView wv;
        wv = view.findViewById(R.id.webView1);
        wv.loadUrl("file:///android_asset/faq.html");
        return view;
    }
}