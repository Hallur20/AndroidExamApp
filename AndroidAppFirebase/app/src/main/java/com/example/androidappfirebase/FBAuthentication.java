package com.example.androidappfirebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by hvn15 on 3/13/2018.
 */

public class FBAuthentication {
    public FBAuthentication(){
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword("hallur@test.dk","123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    Log.d("authentication","Login succesfull");
                } else {
                    Log.d("authentication","Login unsuccesfull");
                }
                //firebaseAuth.signOut();
            }
        });

}}
