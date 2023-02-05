package com.example.combine;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class dt5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_dt5);
        Bundle bundle = getIntent().getExtras();
        String dt5 = bundle.getString("dt5");
        System.out.println(dt5);
        TextView t1 = findViewById(R.id.tv5);
        t1.setText(dt5);
    }
}