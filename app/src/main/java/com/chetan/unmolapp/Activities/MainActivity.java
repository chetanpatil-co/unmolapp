package com.chetan.unmolapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chetan.unmolapp.R;
import com.chetan.unmolapp.Util.Commonutils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Commonutils.backupdata("UNMOLAPP");

        startActivity(new Intent(MainActivity.this,SignupActivity.class));
    }
}
