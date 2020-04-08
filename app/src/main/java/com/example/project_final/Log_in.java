package com.example.project_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Log_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void forget_pass(View view) {
    }

    public void login(View view) {

        Intent intent=new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
    }

    public void create_account(View view) {
        Intent intent=new Intent(getApplicationContext(),New_Account.class);
        startActivity(intent);
    }
}
