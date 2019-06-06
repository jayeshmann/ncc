package com.tsa.NCC_dte_punjab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsa.NCC_dte_punjab.R;

public class CadetProfileFrg1 extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup content_frame,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.show_enrollment_form1, content_frame, false);
        return view;

    }

    public static  CadetProfileFrg1 getInstance()
    {
        return  new CadetProfileFrg1();
    }

    public void init(View view)
    {

    }
}
