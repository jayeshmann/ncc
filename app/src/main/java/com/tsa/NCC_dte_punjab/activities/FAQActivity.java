package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.tsa.NCC_dte_punjab.R;

public class FAQActivity extends CustomActivity {
private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        WebView wv;
        wv = findViewById(R.id.webView1);
        wv.loadUrl("file:///android_asset/faq.html");
        context=FAQActivity.this;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(context,HomeActivity.class));
        finish();
    }
}
