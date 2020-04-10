package com.example.project_final;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lg,lat;
    Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
final GoogleMap mp =mMap;
    //    mMap.setMyLocationEnabled(true);






     /*   Geocoder gc=new Geocoder(this);
        List<Address>list= null;
        String  locality;
        try {
            list = gc.getFromLocation(lat,lg,1);
            Address address=list.get(0);
              locality=address.getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
       ////////////////////////////

        Geocoder gc=new Geocoder(MapsActivity.this);

 */
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {



                if (marker != null) {
                    marker.remove();
                }
                  marker=  mp.addMarker(new MarkerOptions()
                            .position(latLng)
                          .title("Yemen")
                            .draggable(true)
                            .visible(true));
                lat=latLng.latitude;
                lg=latLng.longitude;

                Toast.makeText(MapsActivity.this, lat+"--"+lg, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(getApplicationContext(),Receiver_data.class);

                String type=getIntent().getStringExtra("rg1");
                String size=getIntent().getStringExtra("rg2");
                String name=getIntent().getStringExtra("reciever_name");
                String phone=getIntent().getStringExtra("reciever_phone");
                String describe=getIntent().getStringExtra("package_desc");
                intent.putExtra("type",type);
                intent.putExtra("size",size);
                intent.putExtra("reciever_name",name);
                intent.putExtra("reciever_phone",phone);
                intent.putExtra("package_desc",describe);
                intent.putExtra("lg",lg);
                intent.putExtra("lat",lat);
                startActivity(intent);

            }
        });

    }

}
