package com.example.tom.ipdigger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

/**
 * Created by Tom on 2016/2/18.
 */
public class GdMapActivity extends Activity {
    //声明变量
    private MapView mapView;
    private AMap aMap;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //在onCreat方法中给aMap对象赋值
        setContentView(R.layout.activity_gdmap);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 必须要写
        aMap = mapView.getMap();

        Intent intent = getIntent();
        if(intent!= null){
            float lat =intent.getFloatExtra("lat",200);
            float lon = intent.getFloatExtra("lon",200);
            LatLng latLng = new LatLng(lat,lon);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14f);
            aMap.moveCamera(cameraUpdate);

           //添加图标
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map60x60));
            aMap.addMarker(markerOptions);
        }
    }

    protected void onDestory(){
        super.onDestroy();
        mapView.onDestroy();

    }

    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
}
