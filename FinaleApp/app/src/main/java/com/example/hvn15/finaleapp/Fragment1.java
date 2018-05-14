package com.example.hvn15.finaleapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class Fragment1 extends Fragment {

    private static final String Tag = "Fragment1";

    /*private Button btnNavSecondActivity;*/
    private ArrayList<Shop> copyList = new ArrayList<Shop>();
    private ListView listView;
    private CustomAdapter customAdapter = new CustomAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        /*btnNavSecondActivity = (Button) view.findViewById(R.id.btnNavSecondActivity);*/
        listView = (ListView) view.findViewById(R.id.listview);
        copyList = ((LoggedIn) getActivity()).shopList;
                customAdapter = new CustomAdapter();
                listView.setAdapter(customAdapter);
        return view;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return copyList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            TextView description = (TextView) view.findViewById(R.id.description);
            TextView title = (TextView) view.findViewById(R.id.title);
          TextView category = (TextView) view.findViewById(R.id.category);
            TextView date = (TextView) view.findViewById(R.id.date);
            TextView discount = (TextView) view.findViewById(R.id.discount);
            TextView period = (TextView) view.findViewById(R.id.period);
            TextView priceafter = (TextView) view.findViewById(R.id.priceafter);
            TextView pricebefore = (TextView) view.findViewById(R.id.pricebefore);



            imageView.setImageResource(R.drawable.test);
            category.setText(copyList.get(i).getCategory());
            date.setText(copyList.get(i).getDate());
            description.setText(copyList.get(i).getDescription());
            discount.setText(copyList.get(i).getDiscount());
            period.setText(copyList.get(i).getPeriod());
            pricebefore.setText(copyList.get(i).getPricebefore());
            priceafter.setText(copyList.get(i).getPriceafter());
            title.setText(copyList.get(i).getTitle());

            return view;
        }
    }

    public void updateList(int discountNum , ListView lw, ArrayList<Shop> fromLoggedIn){
        Log.d("hello", "hello world");
        copyList = fromLoggedIn;
        Log.d("halluryo",copyList.toString());


        customAdapter = new CustomAdapter();
        lw.setAdapter(customAdapter);
    }
}
