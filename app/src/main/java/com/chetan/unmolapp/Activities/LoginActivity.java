package com.chetan.unmolapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chetan.unmolapp.R;


public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button btnLogin;
    String login_username,login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_username = username.getText().toString();
                login_password = password.getText().toString();

            }
        });
    }
}
