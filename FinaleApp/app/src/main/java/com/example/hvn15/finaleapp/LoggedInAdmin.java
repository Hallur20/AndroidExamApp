package com.example.hvn15.finaleapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoggedInAdmin extends AppCompatActivity {

    private static final String TAG = "AdminLoggedIn";
    private AdminSectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;
    private EditText input_period;
    private EditText input_title;
    private EditText input_description;
    private Button add_discount;
    public String adminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_admin);
        Log.d(TAG, "onCreate: Started.");
        input_period = (EditText) findViewById(R.id.input_period);
        input_title = (EditText) findViewById(R.id.input_title);
        input_description = (EditText) findViewById(R.id.input_description);
        add_discount = (Button) findViewById(R.id.add_discount);
        Intent intent = getIntent();
        adminName = intent.getStringExtra("adminName");

        mSectionsStatePagerAdapter = new AdminSectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container_admin);
        //Setup the pager
        setupViewPager(mViewPager);


    }
    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AdminFragment1(), "AdminFragment1");
        adapter.addFragment(new AdminFragment2(), "AdminFragment2");
        adapter.addFragment(new AdminFragment3(), "AdminFragment3");
        viewPager.setAdapter(adapter);
    }
    public void setViewPager (int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }
}
