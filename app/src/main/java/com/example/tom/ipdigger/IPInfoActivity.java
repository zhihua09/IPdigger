package com.example.tom.ipdigger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class IPInfoActivity extends Activity implements View.OnClickListener{

    private EditText ipOrDomainToQuery ;
    private Button query ;
    private TextView result;
    private TextView ip ;
    private TextView country ;
    private TextView region ;
    private TextView city ;
    private TextView isp ;
    private TextView timezone ;
    private TextView latitude ;
    private TextView longitude;
    private Button locateOnMap;
    private Button langSwitch;

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
            }else if(lang.equals("zh-CN")){
                setContentView(R.layout.activity_ip_info_zh);
            }

            langSwitch = (Button) findViewById(R.id.lang);
            ipOrDomainToQuery = (EditText) findViewById(R.id.ip_domain_to_query);
            query = (Button) findViewById(R.id.qurey);
            result = (TextView) findViewById(R.id.result);
            ip = (TextView) findViewById(R.id.ip);
            country = (TextView) findViewById(R.id.country);
            region = (TextView) findViewById(R.id.region);
            city = (TextView) findViewById(R.id.city);
            isp = (TextView) findViewById(R.id.isp);
            timezone = (TextView) findViewById(R.id.timezone);
            latitude = (TextView) findViewById(R.id.latitude);
            longitude = (TextView) findViewById(R.id.longitude);
            locateOnMap = (Button) findViewById(R.id.locate_on_map);

            query.setOnClickListener(this);
            locateOnMap.setOnClickListener(this);
            langSwitch.setOnClickListener(this);


            if(!intent.getStringExtra("ip").equals("currentIP")){
                if("en".equals(lang)){
                    result.setText("Query result");
                }else if("zh-CN".equals(lang)){
                    result.setText("查询结果");
                }
            }

            ip.setText(intent.getStringExtra("query"));
            country.setText(intent.getStringExtra("country"));
            region.setText(intent.getStringExtra("region"));
            city.setText(intent.getStringExtra("city"));
            isp.setText(intent.getStringExtra("isp"));
            timezone.setText(intent.getStringExtra("timezone"));
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
                String ipOrDomain = ipOrDomainToQuery.getText().toString();
                if(Pattern.matches("^(\\d{1,3})(\\.(\\d{1,3})){3}$",ipOrDomain) || Pattern.matches("^[^\\s]*$",ipOrDomain)){
                    Intent intent = new Intent(IPInfoActivity.this,IPQueryActivity.class);
                    intent.putExtra("lang",lang);
                    intent.putExtra("ip",ipOrDomain);
                    startActivity(intent);
                    finish();
                }else{
                    if(lang.equals("en")){
                        Toast.makeText(IPInfoActivity.this,"ip or domain is invalid.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(IPInfoActivity.this,"请输入正确的域名或IP地址",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.lang:
                Intent intentLang = new Intent(IPInfoActivity.this,IPQueryActivity.class);
                if(lang.equals("en")){
                    intentLang.putExtra("lang","zh-CN");
                }else{
                    intentLang.putExtra("lang","en");
                }
                intentLang.putExtra("ip",ip.getText());
                startActivity(intentLang);
                finish();
                break;
            case R.id.locate_on_map:
                Intent mapIntent = new Intent(IPInfoActivity.this,GdMapActivity.class);
                mapIntent.putExtra("lat",lat);
                mapIntent.putExtra("lon",lon);
                startActivity(mapIntent);
                break;
        }

    }
}
