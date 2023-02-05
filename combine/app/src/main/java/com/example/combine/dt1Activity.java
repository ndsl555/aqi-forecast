package com.example.combine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class dt1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_dt);
        Bundle bundle = getIntent().getExtras();
        String dt1 = bundle.getString("dt1");
        System.out.println(dt1);
        TextView t1 = findViewById(R.id.tv1);
        t1.setText(dt1);
    }
}