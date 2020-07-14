package com.example.project_final;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AllPostActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    int ID,post_id;
    String name,phone,response;

    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Bundle b=getIntent().getExtras();
        ID= b.getInt("ID");
        name= b.getString("Name");
        phone= b.getString("Phone");


        String myurl="http://10.0.2.2/courier_Service_WebService/WebService1.asmx/GetPostInformation?ID_Customer="+ID;
        new AsyncForGetPostData().execute(myurl);
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

        }  else if (id == R.id.AboutApp) {
            Intent intent=new Intent(getApplicationContext(),AboutApp.class);
            startActivity(intent);
        }  else if (id == R.id.Share) {
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


    public void go_to_Details_order(View view) {
        Intent intent=new Intent(getApplicationContext(),details_order.class);
        startActivity(intent);
    }



    String Data="";
    public class AsyncForGetPostData extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {

            publishProgress("open conn");

            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                publishProgress("start read buffer");

                Data = Stream2String(in);
                in.close();
                publishProgress(Data);

            } catch (Exception e) {
                publishProgress("can not  connect");
            }
            return null;
        }
        protected void onProgressUpdate(String... progress) {
            try {


                jsonObject = new JSONObject(progress[0]);

                JSONArray Post_Id = jsonObject.getJSONArray("id_post");
                JSONArray Response = jsonObject.getJSONArray("r_name");

                for (int i = 0; i < Post_Id.length(); i++) {
                    post_id = Post_Id.getInt(i);
                    response= Response.getString(i);
                }


                if (response!=null ){
                    Toast.makeText(AllPostActivity.this," "+post_id+response,Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(String Result2) {
            // tv.setText(null);
            Toast.makeText(AllPostActivity.this," "+post_id+response,Toast.LENGTH_SHORT).show();


        }
    }

    //  Convert the stream to string
    public String Stream2String(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String text = "";
        int linenumber = 0;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (linenumber != 0)
                    text += line;
                linenumber++;
            }
            inputStream.close();
            text = text.substring(36, text.length());
            return text.substring(0, text.length() - 9);
        } catch (Exception e) {
            e.getMessage();
        }
        return text;
    }



}
