package com.example.combine;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class dt6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_dt6);
        Bundle bundle = getIntent().getExtras();
        String dt6 = bundle.getString("dt6");
        System.out.println(dt6);
        TextView t1 = findViewById(R.id.tv6);
        t1.setText(dt6);
    }
}