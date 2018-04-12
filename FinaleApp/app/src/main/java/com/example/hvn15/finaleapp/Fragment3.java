package com.example.hvn15.finaleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class Fragment3 extends Fragment {


    private static final String Tag = "Fragment1";

    private Button btnNavSecondActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3_layout, container, false);

        btnNavSecondActivity = (Button) view.findViewById(R.id.btnNavSecondActivity);


        btnNavSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Second Activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
