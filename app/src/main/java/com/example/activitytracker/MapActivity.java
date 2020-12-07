package com.example.activitytracker;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "testing";
    String start;
    String end;

    GoogleMap mapAPI;
    private Geocoder geocoder;

    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);

        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(this);
    }

    public LatLng getLocationFromAddress(String strAddress){
        List<Address> address;
        LatLng  coordinates = null;

        try {
            address = geocoder.getFromLocationName(strAddress,1);

            if (address==null) {
                return null;
            }

            Address location=address.get(0);
            System.out.println("THE ADDRESSSSS: " + location);

            location.getLatitude();
            System.out.println("Latitutdeeeee" + location.getLatitude());

            location.getLongitude();
            System.out.println("Loooooooooog" + location.getLongitude());

            coordinates = new LatLng ((double) (location.getLatitude()), (double) (location.getLongitude()));
            System.out.println("Coordinatesssssssssssssssssss" + coordinates.latitude);
            System.out.println("Coordinatesssssssssssss222222222" + coordinates.longitude);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return coordinates;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;

        mapAPI.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Bundle bundle = getIntent().getExtras();
        start = bundle.getString("start");
        end = bundle.getString("end");

        System.out.println("Coming?" + start);
        System.out.println("Ya?" + end);
        MainActivity m = new MainActivity();

        LatLng startLocation = getLocationFromAddress(start);
        LatLng endLocation = getLocationFromAddress(end);

//        LatLng start_location = new LatLng(startLocation.latitude, startLocation.longitude);
//        LatLng end_location = new LatLng(endLocation.latitude, endLocation.longitude);

        if(startLocation != null)
            mapAPI.addMarker(new MarkerOptions().position(startLocation).title("Start"));
        else
            Toast.makeText(getApplicationContext(), "Location not loaded properly. Try again later" ,Toast.LENGTH_SHORT).show();

        if(endLocation != null)
            mapAPI.addMarker(new MarkerOptions().position(endLocation).title("End"));
        else
            Toast.makeText(getApplicationContext(), "Location not loaded properly. Try again later" ,Toast.LENGTH_SHORT).show();

        if(startLocation != null)
            mapAPI.moveCamera(CameraUpdateFactory.newLatLng(startLocation));

        if(endLocation != null)
            mapAPI.moveCamera(CameraUpdateFactory.newLatLng(endLocation));
    }
}