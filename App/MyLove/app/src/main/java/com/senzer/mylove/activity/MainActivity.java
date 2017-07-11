package com.senzer.mylove.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.senzer.mylove.R;
import com.senzer.mylove.util.PermissionDetector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionDetector.verifyLocationPermissions(this);

    }
}
