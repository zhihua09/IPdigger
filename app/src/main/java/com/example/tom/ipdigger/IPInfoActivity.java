package com.example.tom.ipdigger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;

public class IPInfoActivity extends Activity implements View.OnClickListener{

    private EditText ipOrDomainToQuery ;
    private Button query ;
    private TextView result;
    private TextView ip ;
    private TextView country ;
    private TextView region ;
    private TextView city ;
    private TextView isp ;
    private TextView latitude ;
    private TextView longitude;
    private Button locateOnMap;

    private String lang;
    private float lat;
    private float lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent intent = getIntent();
        if(intent != null){
            lang = intent.getStringExtra("lang");
            if(lang.equals("en")){
                setContentView(R.layout.activity_ip_info_en);
            }else if(lang.equals("ja")){
            }else if(lang.equals("zh-CN")){
                setContentView(R.layout.activity_ip_info_zh);
            }

            ipOrDomainToQuery = (EditText) findViewById(R.id.ip_domain_to_query);
            query = (Button) findViewById(R.id.qurey);
            result = (TextView) findViewById(R.id.result);
            ip = (TextView) findViewById(R.id.ip);
            country = (TextView) findViewById(R.id.country);
            region = (TextView) findViewById(R.id.region);
            city = (TextView) findViewById(R.id.city);
            isp = (TextView) findViewById(R.id.isp);
            latitude = (TextView) findViewById(R.id.latitude);
            longitude = (TextView) findViewById(R.id.longitude);
            locateOnMap = (Button) findViewById(R.id.locate_on_map);

            query.setOnClickListener(this);
            locateOnMap.setOnClickListener(this);

//            ipOrDomainToQuery.setText(intent.getStringExtra("query"));

            if(!intent.getStringExtra("ip").equals("currentIP")){
                if("en".equals(lang)){
                    result.setText("Query result");
                }else if("ja".equals(lang)){
                    result.setText("クエリ結果");
                }else if("zh-CN".equals(lang)){
                    result.setText("查询结果");
                }else if("es".equals(lang)){

                }
            }

            ip.setText(intent.getStringExtra("query"));
            country.setText(intent.getStringExtra("country"));
            region.setText(intent.getStringExtra("region"));
            city.setText(intent.getStringExtra("city"));
            isp.setText(intent.getStringExtra("isp"));
            lat = intent.getFloatExtra("lat",200);
            lon = intent.getFloatExtra("lon",200);
            if(lat<200){
                latitude.setText(lat+"");
            }
            if(lon<200){
                longitude.setText(lon+"");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.qurey:
                Intent intent = new Intent(IPInfoActivity.this,IPQueryActivity.class);
                intent.putExtra("lang",lang);
                intent.putExtra("ip",ipOrDomainToQuery.getText().toString());
                startActivity(intent);
                finish();
                break;
            case R.id.locate_on_map:
                Intent mapIntent = new Intent(IPInfoActivity.this,MapsActivity.class);
                mapIntent.putExtra("lat",lat);
                mapIntent.putExtra("lon",lon);
                startActivity(mapIntent);
                break;
        }

    }
}
