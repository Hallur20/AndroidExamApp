package com.example.androidappfirebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by hvn15 on 3/13/2018.
 */

public class FBDatabase {
    public FBDatabase(){
        //Database Reference
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        //Insert
        databaseReference.push().setValue("hej");
        databaseReference.child("someText").setValue("something");

        databaseReference.child("someText").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("sometext",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("someText").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("sometext",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        HashMap<String, User> users = new HashMap();
        users.put("Hallur", new User("Hallur vid Neyst", 27));
        users.put("Karl", new User("Karl Peter", 12));
        users.put("Per", new User("Per Lars", 13));
        users.put("NullUser", null);
        users.put("NullUser", new User(null, 35));
        users.put("NullUser", new User());

        User u = new User("Bla blabla", 12);

        databaseReference.child("users").setValue(users);
        databaseReference.child("users").child("Bla").setValue(u);

        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("user", dataSnapshot.toString());
                HashMap<String, User> hm = new HashMap();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("users", ds.getKey() + " " + ds.getValue());
                    String name = ds.child("name").getValue(String.class);
                    int age = ds.child("age").getValue(int.class);

                    Log.d("users", "User" + name + " " + age);

                    hm.put(ds.getKey(), ds.getValue(User.class));
                }
                Log.d("user", hm.get("Bla").toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.child("users").orderByChild("name").startAt("C").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("findData", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.child("Bla").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
