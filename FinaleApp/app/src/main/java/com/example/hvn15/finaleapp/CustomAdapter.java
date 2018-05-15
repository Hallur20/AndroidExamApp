package com.example.hvn15.finaleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<Shop> copyList = new ArrayList<>();
    Context context;
    int resource;

    public CustomAdapter(ArrayList<Shop> copyList, Context context, int resource) {
        super();
        this.copyList = copyList;
        this.context = context;
        this.resource = resource;
    }

    public CustomAdapter() {
    }

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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.customlayout, null);
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
