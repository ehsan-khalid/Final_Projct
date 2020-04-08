package com.example.project_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Receiver_data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_data);
    }

    public void show_details(View view) {
        EditText et_name=(EditText)findViewById(R.id.receiver_name_et);
        EditText et_phone=(EditText)findViewById(R.id.receiver_phone_et);
        Intent intent=new Intent(getApplicationContext(),summery_request.class);


        //intent=getIntent();
        String type=getIntent().getStringExtra("rg1");
        String size=getIntent().getStringExtra("rg2");
        String describe=getIntent().getStringExtra("package_desc");
        intent.putExtra("type",type);
        intent.putExtra("size",size);
        intent.putExtra("package_desc",describe);




        Bundle bundle=new Bundle();
        bundle.putString("reciever_name",et_name.getText().toString());
        bundle.putString("reciever_phone",et_phone.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void maps(View view) {
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));

    }
}
