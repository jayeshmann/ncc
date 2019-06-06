package com.tsa.NCC_dte_punjab.custom;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Akhil Tripathi on 09-01-2018.
 */

public class MyWebViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            // do your handling codes here, which url is the requested url
            // probably you need to open that url rather than redirect:
            // view.loadUrl(url);
            return false; // then it is not handled by default action
        }
}
