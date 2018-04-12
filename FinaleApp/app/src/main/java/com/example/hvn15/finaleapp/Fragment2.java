package com.example.hvn15.finaleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.ContentValues.TAG;

public class Fragment2 extends Fragment implements OnMapReadyCallback {

    public Fragment2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    private static final String Tag = "Fragment1";

    private Button btnNavSecondActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment2_layout, container, false);
        btnNavSecondActivity = (Button) mView.findViewById(R.id.btnNavSecondActivity);


        btnNavSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Second Activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent);
            }
        });
        return mView;
    }
    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;

        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(55.676098, 12.568337)).title("Dk").snippet("I Love Dk"));
        CameraPosition DK= CameraPosition.builder().target(new LatLng(55.676098, 12.568337)).zoom(5).bearing(0).tilt(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(DK));
    }

}
