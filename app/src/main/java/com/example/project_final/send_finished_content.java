package com.example.project_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class send_finished_content extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_send_finished);
    }

    public void track_post(View view) {
        Intent intent=new Intent(getApplicationContext(),TrackPost.class);
        startActivity(intent);
    }

}
