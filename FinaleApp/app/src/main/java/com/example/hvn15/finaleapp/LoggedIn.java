package com.example.hvn15.finaleapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class
LoggedIn extends AppCompatActivity {

    private static final String TAG = "LoggedIn";
    public ArrayList<Shop> shopList = new ArrayList<>();
    public ArrayList<Person> users = new ArrayList<>();
    private DatabaseReference database;
    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;
    public String hej;
    private static final int LOCATION_REQUEST_CODE = 101;
    private Button btnNavSecondActivity;
    public Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container_admin);
        //Setup the pager
        setupViewPager(mViewPager);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return;
        }

        Log.d("loggedinTest", location.toString());



        Log.d(TAG, "onCreate: Started.");
        Bundle extras = getIntent().getExtras();
        hej = extras.getString("hello");
        database = FirebaseDatabase.getInstance().getReference().child("data");
        users = (ArrayList<Person>) getIntent().getSerializableExtra("users");


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
            return;
        }

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.getChildren()){
                    for(DataSnapshot child2 : child.getChildren()){
                        //loop gennnem tilbud her
                        // Log.d(TAG, "test" + child2.child("discount").getValue().toString());
                        shopList.add(new Shop(
                                child2.child("category").getValue().toString(),
                                child2.child("date").getValue().toString(),
                                child2.child("description").getValue().toString(),
                                child2.child("discount").getValue().toString(),
                                child2.child("period").getValue().toString(),
                                child2.child("price_after").getValue().toString(),
                                child2.child("price_before").getValue().toString(),
                                child2.child("title").getValue().toString()
                        ));

                    }
                }
                Log.d(TAG, shopList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d(TAG, hej.toString());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //btnNavSecondActivity = (Button) findViewById(R.id.btnNavSecondActivity);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_list:
                        setViewPager(0);
                        break;
                    case R.id.action_map:
                        setViewPager(1);
                        break;
                    case R.id.action_account:
                        setViewPager(2);
                        break;
                }
                return true;
            }
        });




    }
    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(), "Fragment1");
        adapter.addFragment(new Fragment2(), "Fragment2");
        adapter.addFragment(new Fragment3(), "Fragment3");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager (int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

}