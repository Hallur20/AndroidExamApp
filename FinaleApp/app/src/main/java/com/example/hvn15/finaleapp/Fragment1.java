package com.example.hvn15.finaleapp;

import android.app.Activity;
import android.app.FragmentContainer;
import android.content.Context;
import android.location.Location;
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
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class Fragment1 extends Fragment {

    private static final String Tag = "Fragment1";

    /*private Button btnNavSecondActivity;*/
    public static ArrayList<Shop> copyList = new ArrayList<Shop>();
    private ListView listView;
    private Fragment2 fragment2;
    private static ArrayList<Shop> save = new ArrayList<>();
    public static CustomAdapter customAdapter = new CustomAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        fragment2 = new Fragment2();
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

    public void updateListTest(int num, HashMap<String, ArrayList<Shop>> test1) {

        for (int i = 0; i < fragment2.markersMap.size(); i++) {
            ArrayList<String> filterTheseArticles = new ArrayList<>();
            float[] km = new float[1];
            Location.distanceBetween(fragment2.myMarker.getPosition().latitude, fragment2.myMarker.getPosition().longitude,
                    fragment2.markersMap.get(fragment2.markersMap.keySet().toArray()[i]).getPosition().latitude, fragment2.markersMap.get(fragment2.markersMap.keySet().toArray()[i]).getPosition().longitude,
                    km);
            try {
                if (km[0] / 1000 > num) {

                    ArrayList<Shop> dicsounts = test1.get(fragment2.markersMap.keySet().toArray()[i]);
                    for (int j = 0; j < copyList.size(); j++) {
                        for (int k = 0; k < dicsounts.size(); k++) {
                            if (copyList.get(j).getCategory().equals(dicsounts.get(k).getCategory()) &&
                                    copyList.get(j).getDate().equals(dicsounts.get(k).getDate()) &&
                                    copyList.get(j).getDescription().equals(dicsounts.get(k).getDescription()) &&
                                    copyList.get(j).getDiscount().equals(dicsounts.get(k).getDiscount()) &&
                                    copyList.get(j).getPeriod().equals(dicsounts.get(k).getPeriod()) &&
                                    copyList.get(j).getPriceafter().equals(dicsounts.get(k).getPriceafter()) &&
                                    copyList.get(j).getPricebefore().equals(dicsounts.get(k).getPricebefore()) &&
                                    copyList.get(j).getTitle().equals(dicsounts.get(k).getTitle())) {
                                copyList.remove(j);
                            }
                        }
                    }
                } else {
                    Log.d("faen", "kommer vi her?");
                    ArrayList<Shop> dicsounts = test1.get(fragment2.markersMap.keySet().toArray()[i]);
                    Log.d("faen", dicsounts.toString());
                    for (int k = 0; k < dicsounts.size(); k++) {
                        if (!copyList.contains(dicsounts.get(k))) {
                            copyList.add(dicsounts.get(k));
                        }
                    }

                }
            } catch (Exception e) {
            }
        }

        customAdapter.notifyDataSetChanged();
    }

    public void filterCategory(String cloth) {
        Log.d("tissemand", cloth);
        if (deletedItems.size() == 0) {
            for (int i = 0; i < copyList.size(); i++) {
                if (!copyList.get(i).getCategory().equals(cloth)) {
                    deletedItems.add(copyList.get(i));
                    copyList.remove(i);
                }
            }
        } else {
            for (int i = 0; i < deletedItems.size(); i++) {
                if (deletedItems.get(i).getCategory().equals(cloth)) {
                    copyList.add(deletedItems.get(i));
                }
            }
            for (int i = 0; i < copyList.size(); i++) {
                if (!copyList.get(i).getCategory().equals(cloth)) {

                    if(!deletedItems.contains(copyList.get(i))){
                        deletedItems.add(copyList.get(i));
                    }
                    copyList.remove(i);
                }
            }

        }
        customAdapter.notifyDataSetChanged();
    }
    public void filterName(String name){
        Log.d("tissemand", name.toString());
        if(deletedItems2.isEmpty()){
            for(int i = 0; i < copyList.size(); i++){
                if(!copyList.get(i).getStore().equals(name)){
                    deletedItems2.add(copyList.get(i));
                    copyList.remove(i);
                }
            }
        } else {
            for (int i = 0; i < deletedItems2.size(); i++) {
                if (deletedItems2.get(i).getStore().equals(name)) {
                    copyList.add(deletedItems2.get(i));
                }
            }
            for (int i = 0; i < copyList.size(); i++) {
                if (!copyList.get(i).getStore().equals(name)) {

                    if(!deletedItems2.contains(copyList.get(i))){
                        deletedItems2.add(copyList.get(i));
                    }
                    copyList.remove(i);
                }
            } //this last one fixes duplicates in the deleted items lists.
        }
        customAdapter.notifyDataSetChanged();
    }

    ArrayList<Shop> deletedItems = new ArrayList<>(); //removed discounts with categories not wanted
    ArrayList<Shop> deletedItems2 = new ArrayList<>(); //removed discounts with store name not wanted
}
