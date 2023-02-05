package com.example.combine;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class dt2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_dt2);
        Bundle bundle = getIntent().getExtras();
        String dt2 = bundle.getString("dt2");
        System.out.println(dt2);
        TextView t1 = findViewById(R.id.tv2);
        t1.setText(dt2);
    }
}