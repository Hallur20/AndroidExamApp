package com.example.hvn15.finaleapp.customerFragments;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hvn15.finaleapp.activities.LoggedIn;
import com.example.hvn15.finaleapp.objectClasses.Person;
import com.example.hvn15.finaleapp.R;
import com.example.hvn15.finaleapp.objectClasses.Shop;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapFragment extends Fragment implements OnMapReadyCallback {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Location location;
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mapView;
    ArrayList<Person> companies = new ArrayList<>();
    ArrayList<String> userNamesInRadius = new ArrayList<>();
    public static HashMap<String, MarkerRules> markersMap = new HashMap<>();
    static ArrayList<Marker> markers = new ArrayList<>();
    static Marker myMarker;
    int minDiscount = 100;
    int maxDiscount = 0;
    public static int maxKm = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mapView = inflater.inflate(R.layout.fragment2_layout, container, false);
        companies = ((LoggedIn) getActivity()).users;
        return mapView;
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        mMapView = mapView.findViewById(R.id.map);
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

        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        location = ((LoggedIn) getActivity()).location;

        Marker marker1 = googleMap.addMarker(new MarkerOptions().position(new LatLng(/*location.getLatitude()*/55.957738, /*location.getLongitude()*/12.260400)).title("you").snippet("you are here"));
        myMarker = marker1;

        for (Person p : companies) {
            if (p.getRole().equals("admin")) {
                HashMap<String, ArrayList<Shop>> test1 = (((LoggedIn) getActivity()).adminDiscountsMap);
                ifAdminHasDiscountsCreateMarker(p, test1, marker1);
            }
        }
        for (int i = 0; i < ((LoggedIn) getActivity()).adminDiscountsMap.size(); i++) {
            for (int j = 0; j < userNamesInRadius.size(); j++) {
                if (((LoggedIn) getActivity()).adminDiscountsMap.containsKey(userNamesInRadius.get(j))) {
                    for (int k = 0; k < ((LoggedIn) getActivity()).adminDiscountsMap.get(userNamesInRadius.get(j)).size(); k++) {
                        if (Integer.parseInt(((LoggedIn) getActivity()).adminDiscountsMap.get(userNamesInRadius.get(j)).get(k).getDiscount()) < minDiscount) {
                            minDiscount = Integer.parseInt(((LoggedIn) getActivity()).adminDiscountsMap.get(userNamesInRadius.get(j)).get(k).getDiscount());
                        }
                        if (Integer.parseInt(((LoggedIn) getActivity()).adminDiscountsMap.get(userNamesInRadius.get(j)).get(k).getDiscount()) > maxDiscount) {
                            maxDiscount = Integer.parseInt(((LoggedIn) getActivity()).adminDiscountsMap.get(userNamesInRadius.get(j)).get(k).getDiscount());
                        }
                    }
                }
            }

        }
        if (userNamesInRadius.size() > 0) {

            ((LoggedIn) getActivity()).vibrator.vibrate(400);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "2"); //helps constructing the typical notification layouts
            builder.setContentTitle("There are: " + userNamesInRadius.size() + " stores near you! (in a " + ((LoggedIn) getActivity()).maxKm + " km radius)"); //sets the string to be shown as a title for the notification
            builder.setContentText("discounts go from: " + minDiscount + "% to " + maxDiscount + "%"); //sets the string to be shown as the context text for the notification
            builder.setSmallIcon(R.mipmap.ic_launcher); //sets an image as an icon for the notification
            Notification notification = builder.build(); //we are done and we parse the build to be of the type 'Notification'
            NotificationManager nm = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE); //has some tools for notifications, in this case we are looking for 'notify'
            nm.notify(3, notification); //notify sends the notification
            MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.ding);
            mp.start();
        }


        CameraPosition DK = CameraPosition.builder().target(new LatLng(/*location.getLatitude()*/55.957738, /*location.getLongitude()*/12.260400)).zoom(5).bearing(0).tilt(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(DK));
    }
    int counter = 0;

    public void ifAdminHasDiscountsCreateMarker(Person p, HashMap<String, ArrayList<Shop>> test1, Marker marker1) {
        if (test1.containsKey(p.getUsername())) {
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(p.getLatitude(), p.getLongitude())).title(p.getTitle()).snippet(p.getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            markers.add(marker);
            markersMap.put(p.getUsername(), new MarkerRules(marker, true, true, true, true));
            float[] results = new float[1];
            Location.distanceBetween(marker1.getPosition().latitude, marker1.getPosition().longitude,
                    marker.getPosition().latitude, marker.getPosition().longitude,
                    results);
            if (results[0] / 1000 > maxKm) {
                maxKm = Math.round(results[0] / 1000);
                ((LoggedIn) getActivity()).setMaxKmOnSeekBar(maxKm); //also add the marker that is furthest away to maximum on seekbar
            }
            ((LoggedIn) getActivity()).seekbarDistance.setProgress((((LoggedIn) getActivity()).maxKm));
            if (results[0] / 1000 < ((LoggedIn) getActivity()).distanceNumOnSeekbar) {
                userNamesInRadius.add(p.getUsername()); // also add the markers that are in a radius of 500 km (this is hardcoded) to a list, this is for the notification showing nearby stores
            }
        }
    }

    public void sortByDiscount(int num, HashMap<String, ArrayList<Shop>> test1) {
        HashMap<String, ArrayList<Shop>> areDiscountsLegit = new HashMap<>(); //empty hashmap containing user key and their discounts

        for (int x = 0; x < test1.size(); x++) {
            areDiscountsLegit.put(test1.keySet().toArray()[x].toString(), test1.get(test1.keySet().toArray()[x])); //add the user keys and discounts from test1
        }
        for (int a = 0; a < areDiscountsLegit.size(); a++) { //then we loop through size of the list containing user key and their discounts

            for (int u = 0; u < areDiscountsLegit.get(areDiscountsLegit.keySet().toArray()[a].toString()).size(); u++) {
                currentDiscounts.add(areDiscountsLegit.get(areDiscountsLegit.keySet().toArray()[a].toString()).get(u)); //add the discounts from the corresponding key
            }
            String userKey = areDiscountsLegit.keySet().toArray()[a].toString(); //save current user key from current element in hash map
            for (int b = 0; b < currentDiscounts.size(); b++) { //loop through discounts belonging to a user
                if (Integer.parseInt(currentDiscounts.get(b).getDiscount()) <= num) {
                    counter++; //if seekbar number is less or same as the current element, add 1 to counter(because it's too low compared to the seekbar number)
                    Log.d("fuck", "counter: " + counter + " current listSize: " + currentDiscounts.size());
                }
                if (counter == currentDiscounts.size()) { // this happens if the counter is equal to size, meaning all the discounts were too low.
                    if (deletedMarkersWithCompanies.containsKey(userKey)) { //if the hash map that saves not visible markers/userkeys does not contain the userkey, then just add marker and user key to the hash map
                        deletedMarkersWithCompanies.get(userKey).add(new Shop(currentDiscounts.get(b).getDiscount(), markersMap.get(userKey).getMarker())); //add to this hash map so we remember which markers are not visible
                    } else { //else we make a new user key
                        ArrayList<Shop> saveMarkers = new ArrayList<>();
                        saveMarkers.add(new Shop(currentDiscounts.get(b).getDiscount(), markersMap.get(userKey).getMarker())); //discount and marker gets saved in arraylist (so we can compare with seek number later)
                        deletedMarkersWithCompanies.put(userKey, saveMarkers); //add the list to hash map with belonging user key
                    }
                    areWeAllowed("discount", false, userKey);
                    counter = 0; //and then we restart counter for next time we loop through
                }
            }
            counter = 0;
            currentDiscounts.clear();
            if (!deletedMarkersWithCompanies.isEmpty() && deletedMarkersWithCompanies.containsKey(userKey)) { //if saved markers hash map is not empty and contains the current user key
                for (int b = 0; b < deletedMarkersWithCompanies.get(userKey).size(); b++) { //loop through hash map
                    if (Integer.parseInt(deletedMarkersWithCompanies.get(userKey).get(b).getDiscount()) >= num) { //if discount is higher or same as num
                        areWeAllowed("discount", true, userKey);
                    }
                }
            }
        }
    }

    private ArrayList<Shop> currentDiscounts = new ArrayList<>(); //empty list containing discounts belonging to a user
    private HashMap<String, ArrayList<Shop>> deletedMarkersWithCompanies = new HashMap<>(); //empty hash map containing user key and highest discount beloning to marker (invisible markers will be saved here)

    public void sortByKm(int num) {

        for (int i = 0; i < markersMap.size(); i++) {
            float[] distance = new float[1];
            Location.distanceBetween(myMarker.getPosition().latitude, myMarker.getPosition().longitude,
                    markersMap.get(markersMap.keySet().toArray()[i]).getMarker().getPosition().latitude, markersMap.get(markersMap.keySet().toArray()[i]).getMarker().getPosition().longitude,
                    distance
            );
            if ((distance[0] / 1000) > num) {
                areWeAllowed("km", false, markersMap.keySet().toArray()[i].toString());
            } else {
                areWeAllowed("km", true, markersMap.keySet().toArray()[i].toString());
            }
        }
    }

    public void sortByCategory(String category, HashMap<String, ArrayList<Shop>> test1) {

        for (int i = 0; i < test1.size(); i++) {

            String userKey = test1.keySet().toArray()[i].toString();


            ArrayList<Shop> innerArray = new ArrayList<>();

            innerArray = test1.get(test1.keySet().toArray()[i]);
            boolean weCanShowMarker = false;
            for (int j = 0; j < innerArray.size(); j++) {
                if (innerArray.get(j).getCategory().contains(category)) {
                    weCanShowMarker = true;
                    break;
                }
            }
            if (weCanShowMarker == true) {
                areWeAllowed("category", true, userKey);
            } else {
                areWeAllowed("category", false, userKey);
            }
        }
    }

    public void areWeAllowed(String filterCategory, boolean setRule, String userKey) {
        if (filterCategory.equals("discount")) {
            if (setRule == true) {
                markersMap.get(userKey).setDiscount(true);
                if (markersMap.get(userKey).isKm() == true && markersMap.get(userKey).isCategory() == true && markersMap.get(userKey).isName() == true) {
                    markersMap.get(userKey).getMarker().setVisible(true);
                }
            } else {
                markersMap.get(userKey).setDiscount(false);
                markersMap.get(userKey).getMarker().setVisible(false);
            }

        }
        if (filterCategory.equals("km")) {
            if (setRule == true) {
                markersMap.get(userKey).setKm(true);
                if (markersMap.get(userKey).isDiscount() == true && markersMap.get(userKey).isCategory() == true && markersMap.get(userKey).isName() == true) {
                    markersMap.get(userKey).getMarker().setVisible(true);
                }
            } else {
                markersMap.get(userKey).setKm(false);
                markersMap.get(userKey).getMarker().setVisible(false);
            }
        }
        if (filterCategory.equals("category")) {
            if (setRule == true) {
                markersMap.get(userKey).setCategory(true);
                if (markersMap.get(userKey).isDiscount() == true && markersMap.get(userKey).isKm() == true && markersMap.get(userKey).isName() == true) {
                    markersMap.get(userKey).getMarker().setVisible(true);
                }
            } else {
                markersMap.get(userKey).setCategory(false);
                markersMap.get(userKey).getMarker().setVisible(false);
            }
        }
        if (filterCategory.equals("name")) {
            if (setRule == true) {
                markersMap.get(userKey).setName(true);
                if (markersMap.get(userKey).isDiscount() == true && markersMap.get(userKey).isKm() == true && markersMap.get(userKey).isCategory() == true) {
                    markersMap.get(userKey).getMarker().setVisible(true);
                }
            } else {
                markersMap.get(userKey).setName(false);
                markersMap.get(userKey).getMarker().setVisible(false);
            }
        }

    }
    public void sortByName(String name, HashMap<String, ArrayList<Shop>> test1) {
        for (int i = 0; i < test1.size(); i++) {
            String userKey = test1.keySet().toArray()[i].toString();
            ArrayList<Shop> checkStoreArray = new ArrayList<>();
            checkStoreArray = test1.get(test1.keySet().toArray()[i]);
            boolean checkStore = false;
            for (int j = 0; j < checkStoreArray.size(); j++) {
                if (checkStoreArray.get(j).getStore().contains(name)) {
                    checkStore = true;
                }
            }
            if (checkStore == true) {
                areWeAllowed("name", true, userKey);
            } else {

                areWeAllowed("name", false, userKey);
            }
        }
    }
    public class MarkerRules {
        private Marker marker;
        private boolean discount;
        private boolean km;
        private boolean category;
        private boolean name;

        public MarkerRules(Marker marker, boolean discount, boolean km, boolean category, boolean name) {
            this.marker = marker;
            this.discount = discount;
            this.km = km;
            this.category = category;
            this.name = name;
        }

        public Marker getMarker() {
            return marker;
        }

        public boolean isDiscount() {
            return discount;
        }

        public void setDiscount(boolean discount) {
            this.discount = discount;
        }

        public boolean isKm() {
            return km;
        }

        public void setKm(boolean km) {
            this.km = km;
        }

        public boolean isCategory() {
            return category;
        }

        public void setCategory(boolean category) {
            this.category = category;
        }

        public boolean isName() {
            return name;
        }

        public void setName(boolean name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MarkerRules{" +
                    "marker=" + marker +
                    ", discount=" + discount +
                    ", km=" + km +
                    ", category=" + category +
                    ", name=" + name +
                    '}';
        }
    }

}