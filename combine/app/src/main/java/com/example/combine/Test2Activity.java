package com.example.combine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class Test2Activity extends MapActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {


    @Override
    protected void onMapReady() {
        setMap();
        addMarker();
    }
    private void setMap() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnInfoWindowClickListener(this);
        moveCamera();
    }
    public void addMarker() {

        ArrayList<HashMap<String,String>> brrayList = location();

        for (int i = 0; i < brrayList.size(); i++) {
            Double la = Double.parseDouble(brrayList.get(i).get("latitude"));
            Double ln = Double.parseDouble(brrayList.get(i).get("longitude"));

            LatLng latLng = new LatLng(la,ln);
            System.out.println(latLng);
            MarkerOptions options = new MarkerOptions();

            options.position(latLng);
            options.title(brrayList.get(i).get("county")+" "+brrayList.get(i).get("sitename"));
            options.snippet("AQI:" + brrayList.get(i).get("aqi")+"  "+"pm2.5:"+brrayList.get(i).get("pm25")+
                    " (μg/m3) "+brrayList.get(i).get("status"));
            options.alpha(0.9f);
            options.anchor(0.5f, 0.5f);
            options.draggable(false);
            options.flat(false);

            if(brrayList.get(i).get("status").equals("對敏感族群不健康")){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

            }
            else if(brrayList.get(i).get("status").equals("對所有族群不健康")){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            }
            else if(brrayList.get(i).get("status").equals("非常不健康")){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            else if(brrayList.get(i).get("status").equals("危害")){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }
            else {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }

            getMap().addMarker(options);
        }
    }
    public void moveCamera() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        ArrayList<HashMap<String,String>> crrayList = location();
        //
        for (int i = 0; i < crrayList.size(); i++) {
            Double la = Double.parseDouble(crrayList.get(i).get("latitude"));
            Double ln = Double.parseDouble(crrayList.get(i).get("longitude"));
            LatLng p = new LatLng(la,ln);
            System.out.println(p);
            builder.include(p);
        }
        //
        LatLngBounds bounds = builder.build();
        //
        getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50), 1000, null);
    }

    public ArrayList<HashMap<String,String>> location () {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<HashMap<String,String>> arrayList =(ArrayList<HashMap<String,String>>) bundle.getSerializable("data");
        return arrayList;
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
