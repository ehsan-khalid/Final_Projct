package com.example.project_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class summery_request extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summery_request);
        TextView rec_location=(TextView)findViewById(R.id.rec_location);
        TextView describe=(TextView)findViewById(R.id.desc_tv);

        ///////////////////// passing Name and Phone ////////////////////////////
        TextView rec_name=(TextView)findViewById(R.id.receiver_name);
        TextView rec_phone=(TextView)findViewById(R.id.receiver_phone);
        ///////////////////////////////////////////////////////

        TextView package_type=(TextView)findViewById(R.id.package_type);
        TextView package_size=(TextView)findViewById(R.id.size);

        Bundle bundle =getIntent().getExtras();
        String name=  bundle.getString("reciever_name");
        String phone=  bundle.getString("reciever_phone");
        String package_description=  bundle.getString("package_desc");


        rec_name.setText(name);
        rec_phone.setText(phone);
        describe.setText(package_description);

        String type=getIntent().getStringExtra("type");
        String size=getIntent().getStringExtra("size");
        package_type.setText(type);
        package_size.setText(size);

        ///////////////////////////////////////
        double lat=bundle.getDouble("lat");
        double lon=bundle.getDouble("lg");

        rec_location.setText(""+lat+","+lon);
       /////////////////////////////////////





    }

    public void confirm(View view) {
        Intent intent=new Intent(getApplicationContext(),send_finished_content.class);
        startActivity(intent);
    }
}
