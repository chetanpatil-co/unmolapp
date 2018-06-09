package com.chetan.unmolapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chetan.unmolapp.Pojo.Register;
import com.chetan.unmolapp.R;
import com.chetan.unmolapp.Util.LocalDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText fName, lName, mobile, email, city;
    Button btnRegister;
    LocalDatabase localDatabase;
    String first_name, last_name, mobile_no, email_id, city_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        localDatabase = new LocalDatabase(SignupActivity.this);

        fName = (EditText) findViewById(R.id.fName);
        lName = (EditText) findViewById(R.id.lName);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        city = (EditText) findViewById(R.id.city);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = fName.getText().toString();
                last_name = lName.getText().toString();
                mobile_no = mobile.getText().toString();
                email_id = email.getText().toString();
                city_name = city.getText().toString();

                Register r = new Register();
                r.setFirstName(first_name);
                r.setLastName(last_name);
                r.setMobile(mobile_no);
                r.setEmail(email_id);
                r.setCity(city_name);
                r.setStatus("Active");

                String result = localDatabase.RegisterUser(r);
                if (result.equalsIgnoreCase("Success")) {
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Registration Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
