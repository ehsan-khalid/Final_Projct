package com.example.project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class All_post extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_post_send);
    }
    public void go_to_Details_order(View view) {
        Intent intent=new Intent(getApplicationContext(),details_order.class);
        startActivity(intent);
    }
}