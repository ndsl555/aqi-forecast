package com.example.combine;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

public class AqiFragment extends Fragment implements View.OnClickListener {
    String TAG = AqiFragment.class.getSimpleName() + "My";
    Button btnTest2;

    TextView t1, t2, t3, t4, t5, t6;
    LinearLayout lay1, lay2, lay3, lay4, lay5, lay6;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aqi, container, false);

        btnTest2 = view.findViewById(R.id.btnTest2);
        btnTest2.setBackgroundColor(getResources().getColor(R.color.teal_700));
        btnTest2.setOnClickListener(this);
        String catchData = "https://data.epa.gov.tw/api/v2/aqx_p_432?api_key=e8dd42e6-9b8b-43f8-991e-b3dee723a52d&limit=1000&sort=ImportDate%20desc&format=JSON";
        t1 = view.findViewById(R.id.title1);
        lay1 = view.findViewById(R.id.layout1);
        t2 = view.findViewById(R.id.title2);
        lay2 = view.findViewById(R.id.layout2);
        t3 = view.findViewById(R.id.title3);
        lay3 = view.findViewById(R.id.layout3);
        t4 = view.findViewById(R.id.title4);
        lay4 = view.findViewById(R.id.layout4);
        t5 = view.findViewById(R.id.title5);
        lay5 = view.findViewById(R.id.layout5);
        t6 = view.findViewById(R.id.title6);
        lay6 = view.findViewById(R.id.layout6);

        new Thread(() -> {
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
                    String aqi = object.getString("aqi");
                    String pollutant = object.getString("pollutant");
                    String status = object.getString("status");
                    String so2 = object.getString("so2");
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
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("sitename", sitename);
                    hashMap.put("county", county);
                    hashMap.put("aqi", aqi);
                    hashMap.put("pollutant", pollutant);
                    hashMap.put("status", status);
                    hashMap.put("co", co);
                    hashMap.put("o3", o3);
                    hashMap.put("no2", no2);
                    hashMap.put("no", no);
                    hashMap.put("nox", nox);
                    hashMap.put("pm25", pm25);
                    hashMap.put("pm10", pm10);
                    hashMap.put("pm25_avg", pm25_avg);
                    hashMap.put("pm10_avg", pm10_avg);
                    hashMap.put("wind_direc", wind_direc);
                    hashMap.put("wind_speed", wind_speed);
                    hashMap.put("publishtime", publishtime);
                    hashMap.put("longitude", longitude);
                    hashMap.put("latitude", latitiude);
                    arrayList.add(hashMap);

                }

                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).get("aqi").isEmpty()) {
                        arrayList.remove(i);
                    }
                }

                requireActivity().runOnUiThread(() -> {
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
                    t1.append(arrayList.get(0).get("county") + arrayList.get(0).get("sitename"));
                    t2.append(arrayList.get(1).get("county") + arrayList.get(1).get("sitename"));
                    t3.append(arrayList.get(2).get("county") + arrayList.get(2).get("sitename"));
                    t4.append(arrayList.get(num - 1).get("county") + arrayList.get(num - 1).get("sitename"));
                    t5.append(arrayList.get(num - 2).get("county") + arrayList.get(num - 2).get("sitename"));
                    t6.append(arrayList.get(num - 3).get("county") + arrayList.get(num - 3).get("sitename"));


                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        intent = new Intent(requireActivity(), Test2Activity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("data", arrayList);
        intent.putExtras(extras);
        startActivity(intent);
    }


}
