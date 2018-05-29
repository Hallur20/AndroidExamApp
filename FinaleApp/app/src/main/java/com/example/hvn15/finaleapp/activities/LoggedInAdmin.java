package com.example.hvn15.finaleapp.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.hvn15.finaleapp.adapters.AdminSectionsStatePagerAdapter;
import com.example.hvn15.finaleapp.R;
import com.example.hvn15.finaleapp.adapters.SectionsStatePagerAdapter;
import com.example.hvn15.finaleapp.adminFragments.AccountFragment;
import com.example.hvn15.finaleapp.adminFragments.CreateDiscountFragment;
import com.example.hvn15.finaleapp.adminFragments.MapFragment;

public class LoggedInAdmin extends AppCompatActivity {
    private ViewPager mViewPager;
    public String adminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_admin);
        Intent intent = getIntent();
        adminName = intent.getStringExtra("adminName");
        mViewPager = findViewById(R.id.container_admin);
        //Setup the pager
        setupViewPager(mViewPager);
    }
    //we add the fragments to the adpater, so we can swipe left to right
    private void setupViewPager(ViewPager viewPager) {
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CreateDiscountFragment(), "CreateDiscountFragment");
        adapter.addFragment(new MapFragment(), "MapFragment");
        adapter.addFragment(new AccountFragment(), "AccountFragment");
        viewPager.setAdapter(adapter);
    }

}
