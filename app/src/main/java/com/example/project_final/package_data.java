package com.example.project_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class package_data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_data);
    }

    public void receiver_data(View view) {
        Intent intent=new Intent(getApplicationContext(),Receiver_data.class);
        RadioGroup rg=(RadioGroup)findViewById(R.id.type);
        String valueselected1=((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        RadioGroup rg2=(RadioGroup)findViewById(R.id.size);
        String valueselected2=((RadioButton)findViewById(rg2.getCheckedRadioButtonId())).getText().toString();
        EditText package_desc=(EditText)findViewById(R.id.desc_et);
        String  description=package_desc.getText().toString();


        intent.putExtra("rg1",valueselected1);
        intent.putExtra("rg2",valueselected2);
        intent.putExtra("package_desc",description);
        startActivity(intent);


    }
}
