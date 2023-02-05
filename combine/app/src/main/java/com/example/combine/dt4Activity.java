package com.example.combine;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class dt4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_dt4);
        Bundle bundle = getIntent().getExtras();
        String dt4 = bundle.getString("dt4");
        System.out.println(dt4);
        TextView t1 = findViewById(R.id.tv4);
        t1.setText(dt4);
    }
}