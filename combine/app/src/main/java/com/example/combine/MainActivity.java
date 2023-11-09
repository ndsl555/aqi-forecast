package com.example.combine;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        container = findViewById(R.id.container);

        // 設置初始選中的項目
        bottomNavigationView.setSelectedItemId(R.id.navigation_aqi);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_aqi:
                        // 切換到 AQI 內容
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new AqiFragment())
                                .commit();
                        return true;
                    case R.id.navigation_weather:
                        // 切換到 Weather 內容
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new WeatherFragment())
                                .commit();
                        return true;
                    case R.id.navigation_oil:
                        // 切換到 Gas 內容
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new OilFragment())
                                .commit();
                        return true;

                }
                return false;
            }
        });
    }


}
