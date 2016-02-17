package com.example.tom.ipdigger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private BaiduMap baiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.map_view);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        Intent intent = getIntent();
        float lat =intent.getFloatExtra("lat",200);
        float lon = intent.getFloatExtra("lon",200);
        LatLng latLng = new LatLng(lat,lon);
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(update);
        update = MapStatusUpdateFactory.zoomTo(14f);
        baiduMap.animateMapStatus(update);

        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(lat);
        builder.longitude(lon);
        MyLocationData locationData = builder.build();
        baiduMap.setMyLocationData(locationData);



    }

    protected void onDestroy(){
        super.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
    }

    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    protected void  onResume(){
        super.onResume();
        mapView.onResume();
    }
}
