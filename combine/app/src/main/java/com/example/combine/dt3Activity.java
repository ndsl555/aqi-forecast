package com.example.combine;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class dt3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_dt3);
        Bundle bundle = getIntent().getExtras();
        String dt3 = bundle.getString("dt3");
        System.out.println(dt3);
        TextView t3 = findViewById(R.id.tv3);
        t3.setText(dt3);
    }
}