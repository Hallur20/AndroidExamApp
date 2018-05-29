package com.example.hvn15.finaleapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.hvn15.finaleapp.R;
import com.example.hvn15.finaleapp.objectClasses.Shop;

import java.util.ArrayList;
// customiz the user discount list
public class CustomAdapter extends BaseAdapter {
    private ArrayList<Shop> allDiscounts = new ArrayList<>();
    Context context;
    int resource;

    public CustomAdapter(ArrayList<Shop> allDiscounts, Context context, int resource) {
        super();
        this.allDiscounts = allDiscounts;
        this.context = context;
        this.resource = resource;
    }
    public CustomAdapter() {
    }
    @Override
    public int getCount() {
        return allDiscounts.size();
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
        ImageView imageView = view.findViewById(R.id.image);
        TextView description = view.findViewById(R.id.description);
        TextView title = view.findViewById(R.id.title);
        TextView category = view.findViewById(R.id.category);
        TextView date = view.findViewById(R.id.date);
        TextView discount = view.findViewById(R.id.discount);
        TextView period = view.findViewById(R.id.period);
        TextView priceafter = view.findViewById(R.id.priceafter);
        TextView pricebefore = view.findViewById(R.id.pricebefore);


        String url = "https://firebasestorage.googleapis.com/v0/b/finaleapp-dcad7.appspot.com/o/hm1.png?alt=media&token=b9c680fb-e056-408f-858c-b11d9a2b334e";
        Glide.with(view.getContext()).load(url).into(imageView);
        category.setText(allDiscounts.get(i).getCategory());
        date.setText(allDiscounts.get(i).getDate());
        description.setText(allDiscounts.get(i).getDescription());
        discount.setText(allDiscounts.get(i).getDiscount());
        period.setText(allDiscounts.get(i).getPeriod());
        pricebefore.setText(allDiscounts.get(i).getPricebefore());
        priceafter.setText(allDiscounts.get(i).getPriceafter());
        title.setText(allDiscounts.get(i).getTitle());
        return view;
    }
}
