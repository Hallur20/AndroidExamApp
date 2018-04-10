package com.example.hvn15.finaleapp;

import android.content.Intent;
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
    private  EditText password;
    private DatabaseReference database;
    private String firebaseUsername;
    private String firbasePassword;
    private ArrayList<Person> pList = new ArrayList<>();
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        database = FirebaseDatabase.getInstance().getReference().child("users");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                pList.add(new Person(
                        child.child("role").getValue().toString(),
                        child.child("username").getValue().toString(),
                        child.child("password").getValue().toString()
                ));
                }
                Log.d(TAG, pList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void btnClicked(View view){
        Intent intent = new Intent(this, LoggedIn.class);
        //Log.d("love", database.child("username");

        for (int i = 0; i < pList.size(); i++) {
            if(pList.get(i).getUsername().equals(username.getText().toString())
                    && pList.get(i).getPassword().equals(password.getText().toString())
                    && pList.get(i).getRole().equals("admin")){
                String name = pList.get(i).getUsername();
            intent.putExtra("hello", name);
                startActivity(intent);


            }
        }

    }
}
