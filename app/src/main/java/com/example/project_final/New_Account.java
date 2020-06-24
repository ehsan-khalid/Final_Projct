package com.example.project_final;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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




    }


    public void create(View view) {
        validate();
        if(!validate()) {
            Toast.makeText(this,"Signup has Failed",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(getApplicationContext(),New_Account.class);


        startActivity(intent);}
    }
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
            input_pass2.setError("Passwords don't match!");
            valid = false;
        }
        else
            input_pass2.setErrorEnabled(false);

        return valid;
    }
}
