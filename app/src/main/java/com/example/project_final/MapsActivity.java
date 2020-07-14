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
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    int ID;
    String Uname,Uphone;
    private GoogleMap mMap;
    double lg,lat;
    Marker marker;
    Geocoder gc;
    List<Address>addresses;
    String fullAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle b=getIntent().getExtras();

        Uname= b.getString("Name");
        Uphone= b.getString("Phone");
        ID=b.getInt("ID");

        Toast.makeText(MapsActivity.this,""+ID,Toast.LENGTH_SHORT).show();

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
        LatLng sydney = new LatLng(33, 0.2);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Yemen"));
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
final GoogleMap mp =mMap;
    //    mMap.setMyLocationEnabled(true);
        /*

        String  locality;

       ////////////////////////////
*/

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (marker != null) {
                    marker.remove();
                    mp.clear();

                }
                  marker=  mp.addMarker(new MarkerOptions()
                            .position(latLng)
                          .title("Yemen")
                            .draggable(true)
                            .visible(true));
                lat=latLng.latitude;
                lg=latLng.longitude;

                gc=new Geocoder(MapsActivity.this);
                try {

                    addresses = gc.getFromLocation(lat,lg,1);
                    String adreess=addresses.get(0).getAddressLine(0);
                    String area=addresses.get(0).getLocality();
                    String city=addresses.get(0).getAdminArea();
                    String country=addresses.get(0).getCountryName();
                    fullAdress=adreess+" ,"+area+" ,"+city+" ,"+country;


                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MapsActivity.this, fullAdress, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(getApplicationContext(),Receiver_data.class);

                String type=getIntent().getStringExtra("rg1");
                String size=getIntent().getStringExtra("rg2");
                String name=getIntent().getStringExtra("reciever_name");
                String phone=getIntent().getStringExtra("reciever_phone");
                String describe=getIntent().getStringExtra("package_desc");
                intent.putExtra("reciever_name",name);
                intent.putExtra("reciever_phone",phone);
                intent.putExtra("lg",lg);
                intent.putExtra("lat",lat);
                Bundle b=new Bundle();
                b.putInt("ID",ID);
                b.putString("Name",Uname);
                b.putString("Phone",Uphone);
                b.putString("type",type);
                b.putString("size",size);
                b.putString("package_desc",describe);
                intent.putExtras(b);
startActivity(intent);

            }
        });

    }

}
