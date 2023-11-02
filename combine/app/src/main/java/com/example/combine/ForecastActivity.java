package com.example.combine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ForecastActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tvsite;
    List<ForecastData>list=new ArrayList<>();
    ForecastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);
        tvsite=findViewById(R.id.tv_site);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("pos");
        String site =bundle.getString("site");
        tvsite.setText(site+"\n"+"一周逐12小時天氣預報");
        ArrayList<String> start_end = bundle.getStringArrayList("se");
        ArrayList<String> stuation = bundle.getStringArrayList("su");

        List  n_sted = start_end.subList(14*pos,14*pos+14);
        List  n_stu = stuation.subList(14*pos,14*pos+14);

        for(int i=0;i<n_sted.size();i++){
            String c[] = n_stu.get(i).toString().split("。");// 数组
            for (int j = 0;j<c.length;j++){
                //System.out.println(c[j]);
            }
            ForecastData forecastdata = new ForecastData(n_sted.get(i).toString(),c[0],c[c.length-4],c[c.length-3],c[c.length-1]);
            list.add(forecastdata);
        }
        Collections.reverse(list);
        adapter =new ForecastAdapter(list, ForecastActivity.this);
        recyclerView.setAdapter(adapter);
    }


}