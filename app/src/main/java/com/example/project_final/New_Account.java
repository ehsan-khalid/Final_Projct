package com.example.project_final;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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
import java.util.regex.Pattern;
public class New_Account extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText phone;
    EditText pass1;
    EditText pass2;
    TextInputLayout input_name;
    TextInputLayout input_email;
    TextInputLayout input_phone;
    TextInputLayout input_pass1;
    TextInputLayout input_pass2;
    //for database
    JSONObject  jsonObject=null;
    String message="";


    private static final Pattern emailpattern = Pattern.compile("" +
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "("+
            "\\."+
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}"+")+");
    private static final String phonepattern = "[7]"+"[7/3/1/0]"+"[0-9]{7}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__account);
        username=(EditText)findViewById(R.id.UserName);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone_number);
        pass1=(EditText)findViewById(R.id.password1);
        pass2=(EditText)findViewById(R.id.password2);
        input_name=(TextInputLayout)findViewById(R.id.input_name);
        input_email=(TextInputLayout)findViewById(R.id.input_email);
        input_phone=(TextInputLayout)findViewById(R.id.phone_input);
        input_pass1=(TextInputLayout)findViewById(R.id.password1_input);
        input_pass2=(TextInputLayout)findViewById(R.id.password2_input);
        //_____________________________________________________________
    }
    public void create(View view) {
        String Name=username.getText().toString();
        String Phone=phone.getText().toString();
        String Email=email.getText().toString();
        String Address=phone.getText().toString();
        String Password=pass2.getText().toString();


        validate();
        CheckInternetConnection checkInternetConnection =new CheckInternetConnection(getApplicationContext());
        boolean ischeck=checkInternetConnection.isConnectionToInternet();
        if(!validate()) {
            Toast.makeText(this,"تحقق من عدم وجود أي خطأ",Toast.LENGTH_SHORT).show();
        }else if(! ischeck)
        {
            Toast.makeText(New_Account.this,"تحقق من إتصالك بالانترنت",Toast.LENGTH_SHORT).show();
        }
        else{
            //××××××××يحتاج ادخال الرابط ××××××
            String myurl="http://10.0.2.2/courier_Service_WebService/WebService1.asmx/" +
                    "AddUser?Name="+Name+"&Phone="+Phone+"&Email="+Email+"&Address="+Address+"&Password="+Password;
            new AsyncTaskToCreateUser().execute(myurl);
           }
    }
    //التحقق من صحة المدخلات
    public boolean validate(){
        String emailinput= input_email.getEditText().getText().toString().trim();
        String etpass1=pass1.getText().toString();
        String etpass2=pass2.getText().toString();
        boolean valid = true;
        if(username.length()<3 || username.length()>32){
            input_name.setError("فضلاً أدخل الاسم بشكل صحيح");
            valid = false;
        }
        else
            input_name.setErrorEnabled(false);
        if(!emailpattern.matcher(emailinput).matches()){
            input_email.setError("فضلاً أدخل الإيميل بشكل صحيح");
            valid = false;
        }
        else
            input_email.setErrorEnabled(false);

        if(!phone.getText().toString().matches(phonepattern)){
            input_phone.setError("فضلاً أدخل رفم الهانف بشكل صحيح");
            valid = false;
        }
        else
            input_phone.setErrorEnabled(false);
        if(pass1.length()<1 || pass1.length()>32){
            input_pass1.setError("فضلاً أدخل كلمة المرور بشكل صحيح");
            valid = false;
        }
        else
            input_pass1.setErrorEnabled(false);
        if(!etpass1.equals(etpass2)){
            input_pass2.setError("ليست متطابقتان !");
            valid = false;
        }
        else
            input_pass2.setErrorEnabled(false);
        return valid;
    }


    //ارسال البيانات الى قاعدة البيانات
    String Data="";
    public class AsyncTaskToCreateUser extends AsyncTask<String,String,String> {
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
                Intent intent=new Intent(getApplicationContext(),Log_in.class);
                startActivity(intent);

                Toast.makeText(New_Account.this, message, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(String Result2) {
            Toast.makeText(New_Account.this,message,Toast.LENGTH_SHORT).show();
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