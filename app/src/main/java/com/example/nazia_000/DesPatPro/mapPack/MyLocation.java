package com.example.nazia_000.DesPatPro.mapPack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nazia_000.DesPatPro.Adapter.infoWindowAdapter;
import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.example.nazia_000.DesPatPro.homePack.ShowProfileActivity;
import com.example.nazia_000.DesPatPro.homePack.searchActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyLocation extends AppCompatActivity
        implements
        OnMapReadyCallback{

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private DatabaseReference locRef;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private  Marker marker,marker1;
    private Geocoder geocoder;

    private GoogleMap mMap;

    private Spinner searchSpinner;
    private List<Address> addresses;
    private ArrayList<Marker> markerList;

    private ImageView locImg;
    private EditText searchRad;

    LatLng latLngSust = new LatLng(24.9172,91.8319);

    private int radius = 20;

    private TextView nameTxt,nmbrTxt,grpTxt, adrsTxt,statTxt;
    private boolean isCalled = false;

    private  String nam,nmb,grp,adr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locImg = findViewById(R.id.imgLoc);
        searchRad = findViewById(R.id.radSearch);

        locRef = FirebaseDatabase.getInstance().getReference("Users");
        geocoder = new Geocoder(getApplicationContext());
        addresses = new ArrayList<>();
        markerList = new ArrayList<>();

        Handler handler = new Handler();
        searchSpinner = findViewById(R.id.spinnerSearch);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Blood_Group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);
        searchSpinner.setOnItemSelectedListener(handler);

        ClickHandle clickHandle = new ClickHandle();
        locImg.setOnClickListener(clickHandle);

        findViewById(R.id.toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),searchActivity.class));
            }
        });


    }

    double latitude;
    double longitude;
    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else{
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 10);
                        String result = addresses.get(0).getLocality() + ":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        if (marker != null) {
                            marker.remove();
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            mMap.setMaxZoomPreference(20);
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),15f));
                        } else {
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            mMap.setMaxZoomPreference(20);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15f));
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };


            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),15f));
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }



    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        getLocation();
        marker = mMap.addMarker(new MarkerOptions().position(latLngSust).title("Sylhet:Bangladesh").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(24.9172,91.8319), 15f));

    }

    Circle mapCircle = null;
    class ClickHandle implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            try {
                if (v.getId() == R.id.imgLoc) {

                    String s = searchRad.getText().toString().trim();
                    int dis = Integer.parseInt((String) s);

                    if (mapCircle != null)
                        mapCircle.remove();

                    if (!s.isEmpty()) {

                        LatLng latLng = new LatLng(latitude, longitude);

                        CircleOptions circleOptions = new CircleOptions();
                        circleOptions.center(latLngSust);
                        circleOptions.radius(10 * dis);
                        circleOptions.strokeColor(R.color.colorAccent);
                        circleOptions.fillColor(R.color.CadetBlue);
                        mapCircle = mMap.addCircle(circleOptions);

                    } else {
                        Toast.makeText(getApplicationContext(), "Fill search field first", Toast.LENGTH_SHORT).show();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    class Handler implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {

            for(Marker mrkr: markerList)
                mrkr.remove();


            addresses.clear();

            String s = (String) searchSpinner.getSelectedItem();

            Query query = FirebaseDatabase.getInstance().getReference("Users")
                    .orderByChild("blood_group")
                    .equalTo(s);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        final ProfilesClass profilesClass = ds.getValue(ProfilesClass.class);

                        if(profilesClass.getStatus().equals("Yes")) {
                            infoWindowAdapter infoWindowAdapter = new infoWindowAdapter(MyLocation.this);
                            mMap.setInfoWindowAdapter(infoWindowAdapter);

                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    final ProfilesClass pc = (ProfilesClass) marker.getTag();
                                    Intent intent = new Intent(MyLocation.this, ShowProfileActivity.class);
                                    intent.putExtra("object", pc);
                                    startActivity(intent);
                                }
                            });

                            try {
                                addresses = geocoder.getFromLocationName(profilesClass.getaddress().trim(), 10);
                                LatLng latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                                Marker m = mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title("" + profilesClass.getaddress())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                                markerList.add(m);
                                m.setTag(profilesClass);
                                m.showInfoWindow();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}

