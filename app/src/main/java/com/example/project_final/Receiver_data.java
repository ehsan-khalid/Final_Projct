package com.example.project_final;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Receiver_data extends AppCompatActivity {
    EditText rePhone,reName;
    String r_address;
    TextInputLayout reNameInput,rePhoneInput;
    JSONObject jsonObject;
    String message;
    String Rname,Rphone;
    String type,size,describe;
    int UID;
    String reciever_name,reciever_phone;
    String Uname,Uphone;
    String ad;
    private static final String phonepattern = "[7]"+"[7/3/1/0]"+"[0-9]{7}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_data);
        reName =(EditText)findViewById(R.id.ReciverName);
        rePhone=(EditText)findViewById(R.id.ReciverPhone);
        reNameInput=(TextInputLayout)findViewById(R.id.ReciverNameInput);
        rePhoneInput=(TextInputLayout)findViewById(R.id.Reciver_phone_Input);
        TextView receiver_location=(TextView)findViewById(R.id.receiver_location);

        Bundle b=getIntent().getExtras();

        Uname= b.getString("Name");
        Uphone= b.getString("Phone");
         UID=b.getInt("ID");
         type=b.getString("type");
         size=b.getString("size");
         describe=b.getString("package_desc");
        Bundle bundle =getIntent().getExtras();
        double lat=bundle.getDouble("lat");
        double lg=bundle.getDouble("lg");
        Rname=  bundle.getString("reciever_name");
         Rphone=  bundle.getString("reciever_phone");


        //................................
        reName.setText(Rname);
        rePhone.setText(Rphone);
    r_address   = (lat+","+lg);
            receiver_location.setText(r_address);
      ad=receiver_location.getText().toString();

    }

    public void show_details(View view) {
        validate();
        if(!validate()) {
            Toast.makeText(this, "تحقق من عدم وجود أي خطأ", Toast.LENGTH_SHORT).show();
        }
        else{
               String myurl="http://10.0.2.2/courier_Service_WebService/WebService1.asmx/SendPost?description="+describe +
            "&weight="+size+"&type="+type+"&r_name="+reName.getText().toString()+"&r_phone="+rePhone.getText().toString()+"&r_addressr="+ad+"&id_customer="+UID;
            new AsyncTaskToSendPost().execute(myurl);

        }

           }
    public void maps(View view) {
        String type=getIntent().getStringExtra("rg1");
        String size=getIntent().getStringExtra("rg2");
        String describe=getIntent().getStringExtra("package_desc");
       reciever_name=getIntent().getStringExtra("reciever_name");
       reciever_phone=getIntent().getStringExtra("reciever_phone");

        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        Bundle b=new Bundle();

        b.putInt("ID",UID);

        b.putString("Name",Uname);
        b.putString("Phone",Uphone);
        intent.putExtras(b);
        intent.putExtra("type",type);
        intent.putExtra("size",size);
        intent.putExtra("package_desc",describe);
        intent.putExtra("reciever_phone",reciever_phone);
        intent.putExtra("reciever_name",reciever_name);
        Bundle bundle =getIntent().getExtras();
//intent=getIntent();

        double lat=bundle.getDouble("lat");
        double lg=bundle.getDouble("lg");

        intent.putExtra("reciever_name",reName.getText().toString());
        intent.putExtra("reciever_phone",rePhone.getText().toString());



        intent.putExtra("latLng",lg);
        intent.putExtra("lat",lat);



        intent.putExtras(bundle);
        startActivity(intent);

    }


    //دالة للتحقق من صحة البيانات
    public boolean validate(){
        boolean valid = true;
        if(reName.length()<5 || reName.length()>32){
            reNameInput.setError("يجب أن يكون اكثر من 3 أحرف ");
            valid = false;
        }
        else
            reNameInput.setErrorEnabled(false);
        if(!rePhone.getText().toString().matches(phonepattern)){
            rePhoneInput.setError("فضلاً أدخل رفم الهاتف بشكل صحيح");
            valid = false;
        }
        else
            rePhoneInput.setErrorEnabled(false);
        return valid;
    }


    //ارسال البيانات الى قاعدة البيانات
    String Data="";
    public class AsyncTaskToSendPost extends AsyncTask<String,String,String> {
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
                Toast.makeText(Receiver_data.this, message, Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(String Result2) {

            Toast.makeText(Receiver_data.this, message, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),summery_request.class);


            intent.putExtra("type",type);
            intent.putExtra("size",size);
            intent.putExtra("package_desc",describe);
            intent.putExtra("reciever_phone",Rphone);
            intent.putExtra("reciever_name",Rname);
            intent.putExtra("r_address",r_address);
            Bundle b=new Bundle();

            b.putInt("ID",UID);

            b.putString("Name",Uname);
            b.putString("Phone",Uphone);
            intent.putExtras(b);

            startActivity(intent);        }
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