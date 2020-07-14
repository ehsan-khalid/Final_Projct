package com.example.project_final;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class summery_request extends AppCompatActivity {

    String message;
    JSONObject jsonObject;
    int post_id;
    int UID;
    String Uname;
    String Uphone;
    String response;
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



        Bundle b=getIntent().getExtras();

        Uname= b.getString("Name");
        Uphone= b.getString("Phone");
        UID=b.getInt("ID");
        String type=getIntent().getStringExtra("type");
        String size=getIntent().getStringExtra("size");
        String reciever_name=getIntent().getStringExtra("reciever_name");
        String reciever_phone=getIntent().getStringExtra("reciever_phone");
        String r_address=getIntent().getStringExtra("r_address");
        String package_desc=getIntent().getStringExtra("package_desc");
        package_type.setText(type);
        package_size.setText(size);
        rec_name.setText(reciever_name);
        rec_phone.setText(reciever_phone);
        rec_location.setText(r_address);
        describe.setText(package_desc);

        String myurl="http://10.0.2.2/courier_Service_WebService/WebService1.asmx/GetPostInformation?ID_Customer="+UID;
        new AsyncForGetPostData().execute(myurl);
    }

    public void confirm(View view) {
        Intent intent=new Intent(getApplicationContext(),send_finished_content.class);
        startActivity(intent);
    }

    public void Cancel(View view) {


     String myurl="http://10.0.2.2/courier_Service_WebService/WebService1.asmx/canceled_post_by_user?post_status=canceled&id_post="+post_id;
   new AsyncTaskToCanceledRequest().execute(myurl);
    }



//جلب بيانات الطرد


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


                if (response!=null

                ){
                    Toast.makeText(summery_request.this," "+post_id+response,Toast.LENGTH_SHORT).show();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(String Result2) {
            // tv.setText(null);
            Toast.makeText(summery_request.this," "+post_id+response,Toast.LENGTH_SHORT).show();


        }
    }

    //الغاء الطلب في إرسال الطرد

    public class AsyncTaskToCanceledRequest extends AsyncTask<String,String,String> {
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
                publishProgress("لايوجد إتصال");
            }
            return null;
        }
        protected void onProgressUpdate(String... progress) {
            try {
                jsonObject = new JSONObject(progress[0]);
                message=jsonObject.getString("Message");
                Toast.makeText(summery_request.this, message +Uname+Uphone+UID, Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(String Result2) {
            AlertDialog.Builder builder=new AlertDialog.Builder(summery_request.this);
            builder.setTitle("هل أنت متأكد من الغاء الطلب");
            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent(getApplicationContext(),Home.class);
                    Bundle b=new Bundle();
                    b.putInt("ID",UID);
                    b.putString("Name",Uname);
                    b.putString("Phone",Uphone);
                    intent.putExtras(b);

                    startActivity(intent);
                }
            });
            builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alert=builder.create();
            alert.show();
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
