package com.example.hvn15.finaleapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AdminFragment3 extends Fragment {

    private static final String Tag = "AdminFragment1";

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminfragment3_layout, container, false);
        textView = (TextView) view.findViewById(R.id.textview_fragment3);
        return view;
    }
}
