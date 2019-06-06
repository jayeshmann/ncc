package com.tsa.NCC_dte_punjab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.custom.MyWebViewClient;

public class DGNCC2Fragment extends Fragment {
    private View view;
    private WebView webView;
    private String urls[]=new String[]{"http://nccindia.nic.in/sites/default/files/ADG(B)+Bio+Data+New.pdf"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            content_frame, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_pdf, content_frame, false);
        init();
        return view;
    }

    private void init() {

        //////////////////////////////////////////////////////////////////////////
        webView=view.findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());
        //////////////////////////////////////////////////////////////////////////
        String url = "http://docs.google.com/gview?embedded=true&url=" + urls[0];
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        ///////////////////////////////////////////////////////////////////////////
    }
}