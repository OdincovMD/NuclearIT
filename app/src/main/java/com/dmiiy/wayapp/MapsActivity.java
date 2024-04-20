package com.dmiiy.wayapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements View.OnClickListener{
    //private GoogleMap mMap;
//    LatLng mLatLng ,mLatl,mLat,mLa,mlaton,mlato,mlatono,mlatonono;
//    String mDescription,mdes,mde,md,mdesco,mdescon,mdescono,mdesconon,act;
    private ImageButton imageButton,imageView;
    private ImageView qr_scan;

    private Button buttonsmoke,buttonnotsmoke;
    private int ACCESS_LOCATION_REQUEST_CODE = 10001;

    private Context ctx;
FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ctx = getApplicationContext();
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
     //   Intent intent = getIntent();
//        mLatLng = intent.getParcelableExtra("latLon");
//        mDescription = intent.getStringExtra("desc");
//        mLatl= intent.getParcelableExtra("latL");
//        mdes = intent.getStringExtra("de");
//        mLat= intent.getParcelableExtra("lat");
//        mde = intent.getStringExtra("d");
//        mLa= intent.getParcelableExtra("la");
//        md = intent.getStringExtra("des");
//        mlato= intent.getParcelableExtra("lant");
//        mdesco = intent.getStringExtra("desca");
//        mlaton= intent.getParcelableExtra("lanta");
//        mdescon = intent.getStringExtra("descan");
//        mlatono= intent.getParcelableExtra("lantano");
//        mdescono = intent.getStringExtra("descara");
//        mlatonono= intent.getParcelableExtra("lantana");
//        mdesconon = intent.getStringExtra("descanra");
//        act=intent.getStringExtra("act");
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        imageView= (ImageButton) findViewById(R.id.imageV);
        imageView.setOnClickListener(this);
        buttonsmoke=(Button)findViewById(R.id.buttonsmoke);
        buttonsmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MapsActivity.this, RecycleViewSmoke.class);
                startActivity(intent1);
            }
        });
        buttonnotsmoke=(Button)findViewById(R.id.buttonnotsmoke);
        buttonnotsmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageV:{
                Intent intent= new Intent(MapsActivity.this, Profile.class);
                startActivity(intent);
                break;
            }
        }
    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        if (mLatLng != null) {
//            // Add a marker for location/description sent from MainActivity
//            mMap.addMarker(new MarkerOptions().position(mLatLng).title(mDescription).icon(BitmapDescriptorFactory.fromResource(R.drawable.m1)));
//
//        }
//        if (mLatl != null) {
//            // Add a marker for location/description sent from MainActivity
//            mMap.addMarker(new MarkerOptions().position(mLatl).title(mdes).icon(BitmapDescriptorFactory.fromResource(R.drawable.m2)));
//
//        }
//        if (mLat != null) {
//            // Add a marker for location/description sent from MainActivity
//            mMap.addMarker(new MarkerOptions().position(mLat).title(mde).icon(BitmapDescriptorFactory.fromResource(R.drawable.m3)));
//
//        }
//        if (mLa != null) {
//            // Add a marker for location/description sent from MainActivity
//            mMap.addMarker(new MarkerOptions().position(mLa).title(md).icon(BitmapDescriptorFactory.fromResource(R.drawable.m4)));
//
//        }
//        if (mlato != null) {
//            // Add a marker for location/description sent from MainActivity
//            mMap.addMarker(new MarkerOptions().position(mlato).title(mdesco).icon(BitmapDescriptorFactory.fromResource(R.drawable.m5)));
//
//        }
//        if (mlaton != null) {
//            // Add a marker for location/description sent from MainActivity
//            mMap.addMarker(new MarkerOptions().position(mlaton).title(mdescon).icon(BitmapDescriptorFactory.fromResource(R.drawable.m6)));
//
//        }
//        if (mlatono != null) {
//            // Add a marker for location/description sent from MainActivity
//            mMap.addMarker(new MarkerOptions().position(mlatono).title(mdescono).icon(BitmapDescriptorFactory.fromResource(R.drawable.m7)));
//
//        }
//        if (mlatonono != null) {
//            // Add a marker for location/description sent from MainActivity
//            mMap.addMarker(new MarkerOptions().position(mlatonono).title(mdesconon).icon(BitmapDescriptorFactory.fromResource(R.drawable.m8)));
//
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            enableUserLocation();
//            //zoomToUserLocation();
//            LatLng center =new LatLng(55.751135,37.617315);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center,9));
//        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                //We can show user a dialog why this permission is necessary
//                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
//            } else  {
//                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
//            }
//
//        }
//    }

//    private void enableUserLocation() {
//        mMap.setMyLocationEnabled(true);
//    }
//    private void zoomToUserLocation(){
//    Task<Location> locationTask= fusedLocationProviderClient.getLastLocation();
//    locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
//        @Override
//        public void onSuccess(Location location) {
//          LatLng latLng= new LatLng(location.getLatitude(),location.getLongitude());
//          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
//
//        }
//    });
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == ACCESS_LOCATION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                enableUserLocation();
//                //zoomToUserLocation();
//                LatLng center =new LatLng(55.751135,37.617315);
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center,9));
//            } else {
//                //We can show a dialog that permission is not granted...
//            }
//        }
//    }
}