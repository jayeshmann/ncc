package com.tsa.NCC_dte_punjab.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;

class MyDialogue extends Dialog {
    private boolean ok;
    private boolean cancle;
    private String msg;

    //////////////////////////////////////////////////////////////////////////////////////////////////
    public MyDialogue(Context context, DialogOnClickListener myclick, String msg, boolean ok, boolean cancle) {
        super(context);
        this.myListener = myclick;
        this.ok = ok;
        this.cancle = cancle;
        this.msg = msg;
    }


    public DialogOnClickListener myListener;

    // This is my interface //
    public interface DialogOnClickListener {
        void onOkClick();

        void onCancleClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start_trip_dialogue);

        //setting layout hight width
        Window window = getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button ok = findViewById(R.id.ok_button);

        if (!this.ok)
            ok.setVisibility(View.GONE);

        ok.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                myListener.onOkClick();
            }
        });

        Button cancle = findViewById(R.id.cancle_button);
        if (!this.cancle)
            cancle.setVisibility(View.GONE);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onCancleClick();
            }
        });

        TextView msgTV = findViewById(R.id.msg_box);
        msgTV.setText(msg);
    }
}

