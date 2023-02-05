package com.example.combine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Test1Activity extends AppCompatActivity implements View.OnClickListener {
    String TAG = Test1Activity.class.getSimpleName()+"My";
    Button btnTest2;
    StringBuffer st1 = new StringBuffer();
    StringBuffer st2 = new StringBuffer();
    StringBuffer st3 = new StringBuffer();
    StringBuffer st4 = new StringBuffer();
    StringBuffer st5 = new StringBuffer();
    StringBuffer st6 = new StringBuffer();
    TextView dt1,dt2,dt3,dt4,dt5,dt6;
    TextView t1,t2,t3,t4,t5,t6;
    LinearLayout lay1,lay2,lay3,lay4,lay5,lay6;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProgressDialog dialog = ProgressDialog.show(this,"讀取中"
                ,"請稍候",true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        btnTest2 = findViewById(R.id.btnTest2);
        btnTest2.setBackgroundColor(getResources().getColor(R.color.teal_700));
        btnTest2.setOnClickListener(this);
        String catchData = "https://data.epa.gov.tw/api/v2/aqx_p_432?api_key=e8dd42e6-9b8b-43f8-991e-b3dee723a52d&limit=1000&sort=ImportDate%20desc&format=JSON";
        t1=findViewById(R.id.title1);
        dt1=findViewById(R.id.detail1);
        lay1=findViewById(R.id.layout1);
        t2=findViewById(R.id.title2);
        dt2=findViewById(R.id.detail2);
        lay2=findViewById(R.id.layout2);
        t3=findViewById(R.id.title3);
        dt3=findViewById(R.id.detail3);
        lay3=findViewById(R.id.layout3);
        t4=findViewById(R.id.title4);
        dt4=findViewById(R.id.detail4);
        lay4=findViewById(R.id.layout4);
        t5=findViewById(R.id.title5);
        dt5=findViewById(R.id.detail5);
        lay5=findViewById(R.id.layout5);
        t6=findViewById(R.id.title6);
        dt6=findViewById(R.id.detail6);
        lay6=findViewById(R.id.layout6);

        new Thread(()->{
            try {
                JSONObject jsonObject = null;
                URL url = new URL(catchData);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String line = in.readLine();
                StringBuffer json = new StringBuffer();
                while (line != null) {
                    json.append(line);
                    line = in.readLine();
                }
                jsonObject = new JSONObject(json.toString());
                JSONArray recordArray = jsonObject.getJSONArray("records");
                for (int i = 0; i < recordArray.length(); i++) {
                    JSONObject object = recordArray.getJSONObject(i);
                    String sitename = object.getString("sitename");
                    String county = object.getString("county");
                    String aqi= object.getString("aqi");
                    String pollutant= object.getString("pollutant");
                    String status = object.getString("status");
                    String so2= object.getString("so2");
                    String co = object.getString("co");
                    String o3 = object.getString("o3");
                    String o3_8hr = object.getString("o3_8hr");
                    String pm10 = object.getString("pm10");
                    String pm25 = object.getString("pm2.5");
                    String no2 = object.getString("no2");
                    String nox = object.getString("nox");
                    String no = object.getString("no");
                    String wind_speed = object.getString("wind_speed");
                    String wind_direc = object.getString("wind_direc");
                    String publishtime = object.getString("publishtime");
                    String co_8hr = object.getString("co_8hr");
                    String pm25_avg = object.getString("pm2.5_avg");
                    String pm10_avg = object.getString("pm10_avg");
                    String so2_avg = object.getString("so2_avg");
                    String latitiude = object.getString("latitude");
                    String longitude = object.getString("longitude");
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("sitename",sitename);
                    hashMap.put("county",county);
                    hashMap.put("aqi",aqi);
                    hashMap.put("pollutant",pollutant);
                    hashMap.put("status",status);
                    hashMap.put("co",co);
                    hashMap.put("o3",o3);
                    hashMap.put("no2",no2);
                    hashMap.put("no",no);
                    hashMap.put("nox",nox);
                    hashMap.put("pm25",pm25);
                    hashMap.put("pm10",pm10);
                    hashMap.put("pm25_avg",pm25_avg);
                    hashMap.put("pm10_avg",pm10_avg);
                    hashMap.put("wind_direc",wind_direc);
                    hashMap.put("wind_speed",wind_speed);
                    hashMap.put("publishtime",publishtime);
                    hashMap.put("longitude",longitude);
                    hashMap.put("latitude",latitiude);
                    arrayList.add(hashMap);

                }

                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i).get("aqi").isEmpty()){
                        arrayList.remove(i);
                    }
                }

                runOnUiThread(()->{
                    HashMap<String, String> temporary = new HashMap<String, String>();
                    for (int c = 0; c < (arrayList.size() - 1); c++) {
                        for (int d = 0; d < (arrayList.size() - c - 1); d++) {

                            if (Integer.parseInt(arrayList.get(d).get("aqi")) > Integer
                                    .parseInt(arrayList.get(d + 1).get("aqi"))) {

                                temporary = arrayList.get(d);
                                arrayList.set(d, arrayList.get(d + 1));
                                arrayList.set(d + 1, temporary);

                            }
                        }
                    }
                    final int num = arrayList.size();
                    t1.append(arrayList.get(0).get("county")+arrayList.get(0).get("sitename"));
                    t2.append(arrayList.get(1).get("county")+arrayList.get(1).get("sitename"));
                    t3.append(arrayList.get(2).get("county")+arrayList.get(2).get("sitename"));
                    t4.append(arrayList.get(num-1).get("county")+arrayList.get(num-1).get("sitename"));
                    t5.append(arrayList.get(num-2).get("county")+arrayList.get(num-2).get("sitename"));
                    t6.append(arrayList.get(num-3).get("county")+arrayList.get(num-3).get("sitename"));

                    st1.append("\n"+"更新時間: "+arrayList.get(0).get("publishtime")+"\n\n"+"狀態 :"
                            +arrayList.get(0).get("status")+"\n\n"+"pm2.5 :"+arrayList.get(0).get("pm25")
                            +" (μg/m3)\n\n" +"pm10 :"+arrayList.get(0).get("pm10")+" (μg/m3)\n\n"+"一氧化碳 :"+arrayList.get(0).get("co")+" (ppm)\n\n"
                            +"臭氧 :"+arrayList.get(0).get("o3")+" (ppb)\n\n" +"二氧化氮 :"+arrayList.get(0).get("no2")+" (ppb)"+"\n\n"+"氮氧化物 :"
                            +arrayList.get(0).get("nox")+" (ppb)"+"\n\n"+"一氧化氮 :" +arrayList.get(0).get("no")+ " (ppb)"+"\n\n"
                            +"風速 :"+arrayList.get(0).get("wind_speed")+" (m/sec)"+"\n\n"+"風向 :"+arrayList.get(0).get("wind_direc")+" (degree)"+"\n\n");

                    st3.append("\n"+"更新時間: "+arrayList.get(2).get("publishtime")+"\n\n"+"狀態 :"
                            +arrayList.get(2).get("status")+"\n\n"+"pm2.5 :"+arrayList.get(2).get("pm25")
                            +" (μg/m3)\n\n" +"pm10 :"+arrayList.get(2).get("pm10")+" (μg/m3)\n\n" +"一氧化碳 :"+arrayList.get(2).get("co")+" (ppm)\n\n"
                            +"臭氧 :"+arrayList.get(2).get("o3")+" (ppb)\n\n" +"二氧化氮 :"+arrayList.get(2).get("no2")+" (ppb)"+"\n\n"+"氮氧化物 :"
                            +arrayList.get(2).get("nox")+" (ppb)"+"\n\n"+"一氧化氮 :" +arrayList.get(2).get("no")+ " (ppb)"+"\n\n"
                            +"風速 :"+arrayList.get(2).get("wind_speed")+" (m/sec)"+"\n\n"+"風向 :"+arrayList.get(2).get("wind_direc")+" (degree)"+"\n\n");

                    st2.append("\n"+"更新時間: "+arrayList.get(1).get("publishtime")+"\n\n"+"狀態 :"
                            +arrayList.get(1).get("status")+"\n\n"+"pm2.5 :"+arrayList.get(1).get("pm25")
                            +" (μg/m3)\n\n" +"pm10 :"+arrayList.get(1).get("pm10")+" (μg/m3)\n\n" +"一氧化碳 :"+arrayList.get(1).get("co")+" (ppm)\n\n"
                            +"臭氧 :"+arrayList.get(1).get("o3")+" (ppb)\n\n" +"二氧化氮 :"+arrayList.get(1).get("no2")+" (ppb)"+"\n\n"+"氮氧化物 :"
                            +arrayList.get(1).get("nox")+" (ppb)"+"\n\n"+"一氧化氮 :" +arrayList.get(1).get("no")+ " (ppb)"+"\n\n"
                            +"風速 :"+arrayList.get(1).get("wind_speed")+" (m/sec)"+"\n\n"+"風向 :"+arrayList.get(1).get("wind_direc")+" (degree)"+"\n\n");

                    st4.append("\n"+"更新時間: "+arrayList.get(num-1).get("publishtime")+"\n\n"+"狀態 :"
                            +arrayList.get(num-1).get("status")+"\n\n"+"pm2.5 :"+arrayList.get(num-1).get("pm25")
                            +" (μg/m3)\n\n" +"pm10 :"+arrayList.get(num-1).get("pm10")+" (μg/m3)\n\n"+"一氧化碳 :"+arrayList.get(num-1).get("co")+" (ppm)\n\n"
                            +"臭氧 :"+arrayList.get(num-1).get("o3")+" (ppb)\n\n" +"二氧化氮 :"+arrayList.get(num-1).get("no2")+" (ppb)"+"\n\n"+"氮氧化物 :"
                            +arrayList.get(num-1).get("nox")+" (ppb)"+"\n\n"+"一氧化氮 :" +arrayList.get(num-1).get("no")+ " (ppb)"+"\n\n"
                            +"風速 :"+arrayList.get(num-1).get("wind_speed")+" (m/sec)"+"\n\n"+"風向 :"+arrayList.get(num-1).get("wind_direc")+" (degree)"+"\n\n");

                    st5.append("\n"+"更新時間: "+arrayList.get(num-2).get("publishtime")+"\n\n"+"狀態 :"
                            +arrayList.get(num-2).get("status")+"\n\n"+"pm2.5 :"+arrayList.get(num-2).get("pm25")
                            +" (μg/m3)\n\n" +"pm10 :"+arrayList.get(num-2).get("pm10")+" (μg/m3)\n\n"+"一氧化碳 :"+arrayList.get(num-2).get("co")+" (ppm)\n\n"
                            +"臭氧 :"+arrayList.get(num-2).get("o3")+" (ppb)\n\n" +"二氧化氮 :"+arrayList.get(num-2).get("no2")+" (ppb)"+"\n\n"+"氮氧化物 :"
                            +arrayList.get(num-2).get("nox")+" (ppb)"+"\n\n"+"一氧化氮 :" +arrayList.get(num-2).get("no")+ " (ppb)"+"\n\n"
                            +"風速 :"+arrayList.get(num-2).get("wind_speed")+" (m/sec)"+"\n\n"+"風向 :"+arrayList.get(num-2).get("wind_direc")+" (degree)"+"\n\n");

                    st6.append("\n"+"更新時間: "+arrayList.get(num-3).get("publishtime")+"\n\n"+"狀態 :"
                            +arrayList.get(num-3).get("status")+"\n\n"+"pm2.5 :"+arrayList.get(num-3).get("pm25")
                            +" (μg/m3)\n\n" +"pm10 :"+arrayList.get(num-3).get("pm10")+" (μg/m3)\n\n"+"一氧化碳 :"+arrayList.get(num-3).get("co")+" (ppm)\n\n"
                            +"臭氧 :"+arrayList.get(num-3).get("o3")+" (ppb)\n\n" +"二氧化氮 :"+arrayList.get(num-3).get("no2")+" (ppb)"+"\n\n"+"氮氧化物 :"
                            +arrayList.get(num-3).get("nox")+" (ppb)"+"\n\n"+"一氧化氮 :" +arrayList.get(num-3).get("no")+ " (ppb)"+"\n\n"
                            +"風速 :"+arrayList.get(num-3).get("wind_speed")+" (m/sec)"+"\n\n"+"風向 :"+arrayList.get(num-3).get("wind_direc")+" (degree)"+"\n\n");
                    dialog.dismiss();

                    //Log.d(TAG, "catchData: "+arrayList);
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        intent = new Intent(this, Test2Activity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("data",arrayList);
        intent.putExtras(extras);
        startActivity(intent);

    }

    public void expand1(View view) {
        int v1=(dt1.getVisibility()==View.GONE)?View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(lay1,new AutoTransition());
        dt1.setVisibility(v1);
        dt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;
                intent = new Intent(Test1Activity.this, dt1Activity.class);
                Bundle extras = new Bundle();
                extras.putString("dt1",st1.toString());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }
    public void expand2(View view) {
        int v2=(dt2.getVisibility()==View.GONE)?View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(lay2,new AutoTransition());
        dt2.setVisibility(v2);
        dt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;
                intent = new Intent(Test1Activity.this, dt2Activity.class);
                Bundle extras = new Bundle();
                extras.putString("dt2",st2.toString());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    public void expand3(View view) {
        int v3=(dt3.getVisibility()==View.GONE)?View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(lay3,new AutoTransition());
        dt3.setVisibility(v3);
        dt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;
                intent = new Intent(Test1Activity.this, dt3Activity.class);
                Bundle extras = new Bundle();
                extras.putString("dt3",st3.toString());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    public void expand4(View view) {
        int v4=(dt4.getVisibility()==View.GONE)?View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(lay4,new AutoTransition());
        dt4.setVisibility(v4);
        dt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;
                intent = new Intent(Test1Activity.this, dt4Activity.class);
                Bundle extras = new Bundle();
                extras.putString("dt4",st4.toString());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    public void expand5(View view) {
        int v5=(dt5.getVisibility()==View.GONE)?View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(lay5,new AutoTransition());
        dt5.setVisibility(v5);
        dt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;
                intent = new Intent(Test1Activity.this, dt5Activity.class);
                Bundle extras = new Bundle();
                extras.putString("dt5",st5.toString());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    public void expand6(View view) {
        int v6=(dt6.getVisibility()==View.GONE)?View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(lay6,new AutoTransition());
        dt6.setVisibility(v6);
        dt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;
                intent = new Intent(Test1Activity.this, dt6Activity.class);
                Bundle extras = new Bundle();
                extras.putString("dt6",st6.toString());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    /*public void put_dt(){

    }*/

}