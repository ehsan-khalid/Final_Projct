package com.example.project_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Receiver_data extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_data);
        TextView receiver_location=(TextView)findViewById(R.id.receiver_location);
        Bundle bundle =getIntent().getExtras();
////////////////
        double lat=bundle.getDouble("lat");
        double lg=bundle.getDouble("lg");
        receiver_location.setText(""+lat+","+lg);

    }

    public void show_details(View view) {
        Intent intent=new Intent(getApplicationContext(),summery_request.class);



        String type=getIntent().getStringExtra("rg1");
        String size=getIntent().getStringExtra("rg2");
        String describe=getIntent().getStringExtra("package_desc");


        intent.putExtra("type",type);
        intent.putExtra("size",size);
        intent.putExtra("package_desc",describe);
        Bundle bundle =getIntent().getExtras();
//intent=getIntent();





        String name=  bundle.getString("reciever_name");
        String phone=  bundle.getString("reciever_phone");







        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void maps(View view) {


        String type=getIntent().getStringExtra("rg1");
        String size=getIntent().getStringExtra("rg2");
        String describe=getIntent().getStringExtra("package_desc");
        String reciever_name=getIntent().getStringExtra("reciever_name");
        String reciever_phone=getIntent().getStringExtra("reciever_phone");

        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);

        intent.putExtra("type",type);
        intent.putExtra("size",size);
        intent.putExtra("package_desc",describe);
        intent.putExtra("reciever_phone",reciever_phone);
        intent.putExtra("reciever_name",reciever_name);
        Bundle bundle =getIntent().getExtras();
//intent=getIntent();

        double lat=bundle.getDouble("lat");
        double lg=bundle.getDouble("lg");



        EditText et_name=(EditText)findViewById(R.id.receiver_name_et);
        EditText et_phone=(EditText)findViewById(R.id.receiver_phone_et);

        intent.putExtra("reciever_name",et_name.getText().toString());
        intent.putExtra("reciever_phone",et_phone.getText().toString());



        intent.putExtra("latLng",lg);
        intent.putExtra("lat",lat);



        intent.putExtras(bundle);
        startActivity(intent);

    }
}
