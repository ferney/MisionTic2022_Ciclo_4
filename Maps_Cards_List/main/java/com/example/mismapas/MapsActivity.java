package com.example.mismapas;




import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String nombre;
    private double Lat,Lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle extras=getIntent().getExtras();
        nombre=extras.getString("nombre");
        Lat=extras.getDouble("1");
        Lon=extras.getDouble("2");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nombre);
        actionBar.setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

        LatLng lugar=new LatLng(Lat,Lon);
        mMap.addMarker(
                new MarkerOptions().position(lugar).title(nombre)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.xx)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar,16));

    }



}
