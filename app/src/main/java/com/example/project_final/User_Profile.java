package com.example.project_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class User_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);
    }

    public void Update_User_Account(View view) {
        Intent intent=new Intent(getApplicationContext(),UpdateUserAccount.class);
        startActivity(intent);
    }
}
