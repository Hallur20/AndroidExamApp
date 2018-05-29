package com.example.hvn15.finaleapp.customerFragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hvn15.finaleapp.adapters.CustomAdapter;
import com.example.hvn15.finaleapp.R;
import com.example.hvn15.finaleapp.objectClasses.Shop;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountListFragment extends Fragment {

    public static ArrayList<Shop> allDiscounts = new ArrayList<Shop>();
    private ListView discountsListView;
    private MapFragment mapFragment;
    public static CustomAdapter customAdapter = new CustomAdapter();
    private ArrayList<Shop> deletedItemsFromCategoryMethod = new ArrayList<>(); //removed discounts with categories not wanted
    private ArrayList<Shop> deletedItemsFromNameMethod = new ArrayList<>(); //removed discounts with store name not wanted

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        discountsListView = view.findViewById(R.id.listview);
        mapFragment = new MapFragment();
        //we add our discount list to the the customAdapter.
        customAdapter = new CustomAdapter(allDiscounts, getContext(), 0);
        discountsListView.setAdapter(customAdapter);
        return view;
    }
// we filter the list, by checking if the discount % from the firebase = the seekbar value, then update our customadpter.
    public void filterDiscount(ArrayList<Shop> allDiscounts, int num) {
        ArrayList<Shop> filteredList = new ArrayList<>(); //empty list
        for (int i = 0; i < allDiscounts.size(); i++) {
            if (Integer.parseInt(allDiscounts.get(i).getDiscount()) >= num) {
                filteredList.add(allDiscounts.get(i)); //if discount is bigger or equal to seekbar number add to list
            }
        }
        DiscountListFragment.allDiscounts.clear(); //make copylist empty (for restarting purposes)
        for (int i = 0; i < filteredList.size(); i++) {
            DiscountListFragment.allDiscounts.add(filteredList.get(i)); //add everything to copylist
        }
        customAdapter.notifyDataSetChanged(); //notify customadapter that changes have been made.
    }


    public void checkIfDiscountIsInRadius(int num, HashMap<String, ArrayList<Shop>> allDiscounts) {

        for (int i = 0; i < mapFragment.markersMap.size(); i++) {
            float[] km = new float[1];
            Location.distanceBetween(mapFragment.myMarker.getPosition().latitude, mapFragment.myMarker.getPosition().longitude,
                    mapFragment.markersMap.get(mapFragment.markersMap.keySet().toArray()[i]).getMarker().getPosition().latitude, mapFragment.markersMap.get(mapFragment.markersMap.keySet().toArray()[i]).getMarker().getPosition().longitude,
                    km);

            try {
                if (km[0] / 1000 > num) {
                    //we check if the km in the discounts data from the firebase more then the value of the km seekbar,so we delete to the list and update.
                    ArrayList<Shop> dicsounts = allDiscounts.get(mapFragment.markersMap.keySet().toArray()[i]);
                    for (int j = 0; j < DiscountListFragment.allDiscounts.size(); j++) {
                        for (int k = 0; k < dicsounts.size(); k++) {
                            if (DiscountListFragment.allDiscounts.get(j).getCategory().equals(dicsounts.get(k).getCategory()) &&
                                    DiscountListFragment.allDiscounts.get(j).getDate().equals(dicsounts.get(k).getDate()) &&
                                    DiscountListFragment.allDiscounts.get(j).getDescription().equals(dicsounts.get(k).getDescription()) &&
                                    DiscountListFragment.allDiscounts.get(j).getDiscount().equals(dicsounts.get(k).getDiscount()) &&
                                    DiscountListFragment.allDiscounts.get(j).getPeriod().equals(dicsounts.get(k).getPeriod()) &&
                                    DiscountListFragment.allDiscounts.get(j).getPriceafter().equals(dicsounts.get(k).getPriceafter()) &&
                                    DiscountListFragment.allDiscounts.get(j).getPricebefore().equals(dicsounts.get(k).getPricebefore()) &&
                                    DiscountListFragment.allDiscounts.get(j).getTitle().equals(dicsounts.get(k).getTitle())) {
                                DiscountListFragment.allDiscounts.remove(j);
                            }
                        }
                    }
                }
                //we check if the km in the discounts data from the firebase less then the value of the km seekbar,so we add to the list and update.
                else {
                    ArrayList<Shop> discount = allDiscounts.get(mapFragment.markersMap.keySet().toArray()[i]);
                    for (int k = 0; k < discount.size(); k++) {
                        if (!DiscountListFragment.allDiscounts.contains(discount.get(k))) {
                            DiscountListFragment.allDiscounts.add(discount.get(k));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        customAdapter.notifyDataSetChanged();
    }

    public void filterCategory(String cloth) {
        //if no catgories have been deleted then check if the category isnt correct and add it to the remove-list
        if (deletedItemsFromCategoryMethod.size() == 0) {
            for (int i = 0; i < allDiscounts.size(); i++) {
                if (!allDiscounts.get(i).getCategory().equals(cloth)) {
                    deletedItemsFromCategoryMethod.add(allDiscounts.get(i));
                    allDiscounts.remove(i);
                }
            }
        } else {
            for (int i = 0; i < deletedItemsFromCategoryMethod.size(); i++) {
                if (deletedItemsFromCategoryMethod.get(i).getCategory().equals(cloth)) {
                    //if remove-list now contains the category, add it again to allDiscount
                    allDiscounts.add(deletedItemsFromCategoryMethod.get(i));
                }
            }
            // check if allDiscounts contains cloth, and if not then add it to the remove-list
            for (int i = 0; i < allDiscounts.size(); i++) {
                if (!allDiscounts.get(i).getCategory().equals(cloth)) {
                    if (!deletedItemsFromCategoryMethod.contains(allDiscounts.get(i))) {
                        deletedItemsFromCategoryMethod.add(allDiscounts.get(i));
                    }
                    allDiscounts.remove(i);
                }
            }
        }
        customAdapter.notifyDataSetChanged();
    }
// the same as  filterCategory way.
    public void filterName(String name) {
        if (deletedItemsFromNameMethod.isEmpty()) {
            for (int i = 0; i < allDiscounts.size(); i++) {
                if (!allDiscounts.get(i).getStore().equals(name)) {
                    deletedItemsFromNameMethod.add(allDiscounts.get(i));
                    allDiscounts.remove(i);
                }
            }
        } else {
            for (int i = 0; i < deletedItemsFromNameMethod.size(); i++) {
                if (deletedItemsFromNameMethod.get(i).getStore().equals(name)) {
                    allDiscounts.add(deletedItemsFromNameMethod.get(i));
                }
            }
            for (int i = 0; i < allDiscounts.size(); i++) {
                if (!allDiscounts.get(i).getStore().equals(name)) {

                    if (!deletedItemsFromNameMethod.contains(allDiscounts.get(i))) {
                        deletedItemsFromNameMethod.add(allDiscounts.get(i));
                    }
                    allDiscounts.remove(i);
                }
            } //this last one fixes duplicates in the deleted items lists.
        }
        customAdapter.notifyDataSetChanged();
    }


}
