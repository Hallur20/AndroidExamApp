package com.example.hvn15.finaleapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private DatabaseReference database;
    private String firebaseUsername;
    private String firbasePassword;
    private ArrayList<Person> pList = new ArrayList<>();
    public ArrayList<String> uNames =  new ArrayList<>();
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else {
            //   gps functions.
        }
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        database = FirebaseDatabase.getInstance().getReference().child("users");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.child("role").getValue().equals("admin")) {
                        pList.add(new Person(
                                child.child("role").getValue().toString(),
                                child.child("username").getValue().toString(),
                                child.child("password").getValue().toString(),
                                Double.parseDouble(child.child("latitude").getValue().toString()),
                                Double.parseDouble(child.child("longitude").getValue().toString()),
                                child.child("title").getValue().toString(),
                                child.child("address").getValue().toString()
                        ));
                        Log.d(TAG, child.toString());
                    } else {
                        pList.add(new Person(
                                child.child("role").getValue().toString(),
                                child.child("username").getValue().toString(),
                                child.child("password").getValue().toString()
                        ));
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void btnClicked(View view) {
        Intent intent = new Intent(this, LoggedIn.class);
        Intent intent2 = new Intent(this, LoggedInAdmin.class);
        //Log.d("love", database.child("username");

        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).getUsername().equals(username.getText().toString())
                    && pList.get(i).getPassword().equals(password.getText().toString())
                    && pList.get(i).getRole().equals("guest")) {
                String name = pList.get(i).getUsername();
                intent.putExtra("hello", name);
                intent.putExtra("users", pList);
                startActivity(intent);


            } else if (pList.get(i).getUsername().equals(username.getText().toString())
                    && pList.get(i).getPassword().equals(password.getText().toString())
                    && pList.get(i).getRole().equals("admin")) {
                String name = pList.get(i).getUsername();
                intent2.putExtra("adminName", name);
                startActivity(intent2);


            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //  gps functionality
        }
    }
}

