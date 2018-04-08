package com.example.hvn15.finaleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private  EditText password;
    private DatabaseReference database;
    private DatabaseReference database2;
    private String firebaseUsername;
    private String firbasePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        database = FirebaseDatabase.getInstance().getReference().child("username");
        database2 = FirebaseDatabase.getInstance().getReference().child("password");
        database2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firbasePassword = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseUsername = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void btnClicked(View view){
        Intent intent = new Intent(this, LoggedIn.class);
        //Log.d("love", database.child("username");
        if(firebaseUsername.equals(username.getText().toString()) && firbasePassword.equals(password.getText().toString())) {
            startActivity(intent);

        }
    }
}
