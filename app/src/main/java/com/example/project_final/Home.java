package com.example.project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView nametv;
    TextView navName;
    TextView navPhone;
    int ID=0;
    String name="";
    String phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
       nametv=(TextView)findViewById(R.id.user_name);


       //كود لإستدعاء navNameوnavPhone
         NavigationView navigationView1=(NavigationView)findViewById(R.id.nav_view);
         View view=navigationView.getHeaderView(0);
         navName=(TextView) view.findViewById(R.id.nav_username);
         navPhone=(TextView) view.findViewById(R.id.nav_userphone);
//_________________________________________________________________

        Bundle b=getIntent().getExtras();
        ID= b.getInt("ID");
        name= b.getString("Name");
        phone= b.getString("Phone");

        nametv.setText(name);
        navName.setText(name);
        navPhone.setText(phone);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent=new Intent(getApplicationContext(),Home.class);
            Bundle b=new Bundle();
            b.putInt("ID",ID);

            b.putString("Name",name);
            b.putString("Phone",phone);
            intent.putExtras(b);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.UserProfile) {
            Intent intent=new Intent(getApplicationContext(),User_Profile.class);
            startActivity(intent);

        } else if (id == R.id.send_post) {
            Intent intent=new Intent(getApplicationContext(),AllPostActivity.class);
            Bundle b=new Bundle();
            b.putInt("ID",ID);

            b.putString("Name",name);
            b.putString("Phone",phone);
            intent.putExtras(b);
            startActivity(intent);

        } else if (id == R.id.AboutApp) {
            Intent intent=new Intent(getApplicationContext(),AboutApp.class);
            startActivity(intent);

        }else if (id == R.id.Share) {
            Intent shareintent=new Intent();
            shareintent.setType("text/plain");
            String shareBody="https://play.google.com/store/apps";
            String shareSub="Track It App";
            shareintent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            shareintent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(shareintent,"مشاركة عبر "));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void send(View view) {

            Intent intent=new Intent(getApplicationContext(),package_data.class);
        Bundle b=new Bundle();
        b.putInt("ID",ID);

        b.putString("Name",name);
        b.putString("Phone",phone);
            intent.putExtras(b);
            startActivity(intent);

    }

    public void track_post(View view) {
        Intent intent=new Intent(getApplicationContext(),TrackPost.class);
        startActivity(intent);
    }



}
