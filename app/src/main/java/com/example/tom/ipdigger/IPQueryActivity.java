package com.example.tom.ipdigger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

import com.example.tom.ipdigger.util.HttpCallbackListener;
import com.example.tom.ipdigger.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tom on 2016/2/11.
 */
public class IPQueryActivity extends Activity implements HttpCallbackListener{

    private String ip;

    private String language;

    private ProgressDialog progressDialog;

    private EditText queryState ;

    protected  void onCreate(Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        language = intent.getStringExtra("lang");
        ip = intent.getStringExtra("ip");

       String address;
        if(ip.equals("currentIP")){
            address = "http://ip-api.com/json/"+"?lang="+language+"&fields=58073";

       }else{
            address = "http://ip-api.com/json/"+ip+"?lang="+language+"&fields=58073";
       }

        showProgressDialog();
        HttpUtil.sendHttpRequest(address,IPQueryActivity.this);
        closeProgressDialog();
    }

    @Override
    public void onFinish(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if(status.equals("fail")){
                String message = jsonObject.getString("message");
                String query =jsonObject.getString("query");
                setContentView(R.layout.activity_ip_query);
                queryState = (EditText) findViewById(R.id.query_state);
                queryState.setText("status:"+status+"\n"+"queried IP:"+query+"\n"+"message:"+message);
            }else{
                String city = jsonObject.getString("city");
                String country = jsonObject.getString("country");
                String isp = jsonObject.getString("isp");
                float latitude = (float) jsonObject.getDouble("lat");
                float longitude = (float) jsonObject.getDouble("lon");
                String query =jsonObject.getString("query");
                String region = jsonObject.getString("regionName");

                Intent intent = new Intent(IPQueryActivity.this,IPInfoActivity.class);
                intent.putExtra("lang",language);
                intent.putExtra("ip",ip);

                intent.putExtra("city",city);
                intent.putExtra("country",country);
                intent.putExtra("isp",isp);
                intent.putExtra("query",query);
                intent.putExtra("region",region);
                intent.putExtra("lat",latitude);
                intent.putExtra("lon",longitude);

                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            setContentView(R.layout.activity_ip_query);
            queryState = (EditText) findViewById(R.id.query_state);
            queryState.setText(e.getMessage().toString());
        }


    }

    @Override
    public void onError(Exception e) {
        queryState.setText(e.getMessage().toString());
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(IPQueryActivity.this);
            progressDialog.setMessage("querying...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
