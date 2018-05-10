package com.example.hvn15.finaleapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Fragment2 extends Fragment implements OnMapReadyCallback{

    public Fragment2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

Location location;
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    ArrayList<Person> companies = new ArrayList<>();

    private static final String Tag = "Fragment2";

    private Button showMarkers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment2_layout, container, false);
        companies = ((LoggedIn) getActivity()).users;

        Log.d(Tag, companies.toString());
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        location = ((LoggedIn)getActivity()).location;
        Log.d("mapTest", location.toString());

        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        Marker marker1 = googleMap.addMarker(new MarkerOptions().position(new LatLng(/*location.getLatitude()*/55.957738, /*location.getLongitude()*/12.260400)).title("you").snippet("you are here"));

        Marker marker2 = googleMap.addMarker(new MarkerOptions().position(new LatLng(55.933770,12.286840)).title("firma titel").snippet("firma addresse").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));



        float[] results = new float[1];
        Location.distanceBetween(marker1.getPosition().latitude, marker1.getPosition().longitude,
                marker2.getPosition().latitude, marker2.getPosition().longitude,
                results);

        Log.d("mapTest", ( String.valueOf(results[0]/1000)));

       /* if(results[0]/1000 < 5){

            ((LoggedIn)getActivity()).vibrator.vibrate(400);

        }
*/


        for(Person p : companies){
            if(p.getRole().equals("admin")){
                googleMap.addMarker(new MarkerOptions().position(new LatLng(p.getLatitude(),p.getLongitude())).title(p.getTitle()).snippet(p.getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        }


        CameraPosition DK= CameraPosition.builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(5).bearing(0).tilt(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(DK));
    }

}