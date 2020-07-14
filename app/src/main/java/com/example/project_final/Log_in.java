package com.example.project_final;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class Log_in extends AppCompatActivity {

    EditText login_phone;

    EditText login_pass;
    TextInputLayout login_input_phone;
    TextInputLayout login_input_pass;
    JSONObject  jsonObject=null;
    String phone="";
    String name="";
    int id;
    int response;
    private static final String phonepattern = "[7]"+"[7/3/1/0]"+"[0-9]{7}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        login_phone=(EditText)findViewById(R.id.login_phone);
        login_pass=(EditText)findViewById(R.id.login_pass);
        login_input_phone=(TextInputLayout)findViewById(R.id.login_input_phone);
        login_input_pass=(TextInputLayout)findViewById(R.id.login_input_pass);
    }

    public void forget_pass(View view) {
    }

    public void login(View view) {

       String LogInPhone= login_phone.getText().toString();
       String logInPass= login_pass.getText().toString();

        validate();
        CheckInternetConnection checkInternetConnection =new CheckInternetConnection(getApplicationContext());
        boolean ischeck=checkInternetConnection.isConnectionToInternet();
        if(!validate()) {
            Toast.makeText(this,"تحقق من عدم وجود أي خطأ",Toast.LENGTH_SHORT).show();
        }/*else if(! ischeck)
        {
            Toast.makeText(Log_in.this,"تحقق من إتصالك بالانترنت",Toast.LENGTH_SHORT).show();
        }*/
        else{
            //××××××××يحتاج ادخال الرابط ××××××
            String myurl="http://10.0.2.2/courier_Service_WebService/WebService1.asmx/Login?Phone="+LogInPhone+"&PassWord="+logInPass;
            new AsyncForLogin().execute(myurl);
            }

    }
    public void create_account(View view) {
        Intent intent=new Intent(getApplicationContext(),New_Account.class);
        startActivity(intent);
    }


    //دالة للتحقق من صحة البيانات
    public boolean validate(){
        boolean valid = true;
        if(!login_phone.getText().toString().matches(phonepattern)){
            login_input_phone.setError("فضلاً أدخل رفم الهانف بشكل صحيح");
            valid = false;
        }
        else
            login_input_phone.setErrorEnabled(false);
        if(login_pass.length()<5 || login_pass.length()>32){
            login_input_pass.setError("يجب أن تكون اكثر من 5 أحرف ");
            valid = false;
        }
        else
            login_input_pass.setErrorEnabled(false);
        return valid;
    }
    //__________________________________________________________________
    String Data="";
    public class AsyncForLogin extends AsyncTask<String,String,String> {
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

             JSONArray jphone = jsonObject.getJSONArray("Phone");
             JSONArray jid = jsonObject.getJSONArray("id");
             JSONArray jname = jsonObject.getJSONArray("Name");

       response= jsonObject.getInt("response");
                for (int i = 0; i < jphone.length(); i++) {
                    phone = jphone.getString(i);
                    name = jname.getString(i);
                    id = jid.getInt(i);




                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(String Result2) {
            if (response==1){
                Toast.makeText(Log_in.this,"مرحبا بك تم تسجيل دخولك بنجاح ",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Home.class);
                Bundle b=new Bundle();
                b.putInt("ID",id);
                b.putString("Name",name);
                b.putString("Phone",phone);
                intent.putExtras(b);
                startActivity(intent);

            }
            else
            {
                Toast.makeText(Log_in.this,"معذرة تأكد من صحة ماأدخلت او أنشأ حسابك اولا  ",Toast.LENGTH_SHORT).show();
            }


        }
    }
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
