package com.example.hvn15.finaleapp;

import android.app.Activity;
import android.app.FragmentContainer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Fragment1 extends Fragment {

    private static final String Tag = "Fragment1";

    /*private Button btnNavSecondActivity;*/
    public static ArrayList<Shop> copyList = new ArrayList<Shop>();
    private ListView listView;
    public static CustomAdapter customAdapter = new CustomAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        customAdapter = new CustomAdapter(copyList, getContext(), 0);
        listView.setAdapter(customAdapter);
        return view;
    }

    public void updateList(ArrayList<Shop> list, int num) {

        ArrayList<Shop> filteredList = new ArrayList<>(); //empty list
        for (int i = 0; i < list.size(); i++) {
            if (Integer.parseInt(list.get(i).getDiscount()) >= num) {
                filteredList.add(list.get(i)); //if discount is bigger or equal to seekbar number add to list
            }
        }

        copyList.clear(); //make copylist empty (for restarting purposes)
        for (int i = 0; i < filteredList.size(); i++) {
            copyList.add(filteredList.get(i)); //add everything to copylist
        }
        customAdapter.notifyDataSetChanged(); //notify customadapter that changes have been made.
    }
}
