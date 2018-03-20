package com.example.androidappfirebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by hvn15 on 3/13/2018.
 */

public class FBStorage {
    FBStorage(){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReference();

        File[] files = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera").listFiles();

        for(File file: files){
            Log.d("image",file.getName());
        }

        StorageReference imagesReference = storageReference.child("images/" + files[0].getName());
        Bitmap bitmap = BitmapFactory.decodeFile(files[0].getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesReference.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
            Log.d("image",downloadUrl.toString());
        }).addOnFailureListener(Exception -> {
            Log.d("image", Exception.getMessage());
        });
    }
}
