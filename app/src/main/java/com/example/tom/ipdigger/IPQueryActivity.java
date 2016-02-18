package com.example.tom.ipdigger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tom.ipdigger.util.HttpCallbackListener;
import com.example.tom.ipdigger.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tom on 2016/2/11.
 */
public class IPQueryActivity extends Activity implements HttpCallbackListener{

    private String ip ;

    private String language ;

    private String queryFailMessage;

    private ProgressDialog progressDialog;

    private  TextView queryState;


    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_query);
        queryState = (TextView) findViewById(R.id.query_state);

        Intent intent = getIntent();
        if(intent != null){
            language = intent.getStringExtra("lang");
            ip = intent.getStringExtra("ip");
        }

       String address;
        if( ip.equals("currentIP")){
            address = "http://ip-api.com/json/"+"?lang="+language+"&fields=61439";

       }else{
            address = "http://ip-api.com/json/"+ip+"?lang="+language+"&fields=61439";
       }

        showProgressDialog();
        HttpUtil.sendHttpRequest(address,IPQueryActivity.this);
    }

    @Override
    public void onFinish(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if(status.equals("fail")){
                String message = jsonObject.getString("message");
                String query =jsonObject.getString("query");
                queryFailMessage = "status:"+status+"\n"+"queried IP:"+query+"\n"+"message:"+message;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        queryState.setText(queryFailMessage);

                    }
                });
            }else{
                String city = jsonObject.getString("city");
                String country = jsonObject.getString("country");
                String isp = jsonObject.getString("isp");
                float latitude = (float) jsonObject.getDouble("lat");
                float longitude = (float) jsonObject.getDouble("lon");
                String query =jsonObject.getString("query");
                String region = jsonObject.getString("regionName");
                String timezone = jsonObject.getString("timezone");

                Intent intent = new Intent(IPQueryActivity.this,IPInfoActivity.class);
                intent.putExtra("lang",language);
                intent.putExtra("ip",ip);

                intent.putExtra("city",city);
                intent.putExtra("country",country);
                intent.putExtra("isp",isp);
                intent.putExtra("query",query);
                intent.putExtra("region",region);
                intent.putExtra("timezone",timezone);
                intent.putExtra("lat",latitude);
                intent.putExtra("lon",longitude);

                startActivity(intent);
                closeProgressDialog();

                finish();
            }
        } catch (JSONException e) {
            queryFailMessage =e.getMessage().toString();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    closeProgressDialog();
                    queryState.setText(queryFailMessage);
                }
            });
        }


    }

    @Override
    public void onError(Exception e) {
        queryFailMessage =e.getMessage().toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                closeProgressDialog();
                queryState.setText(queryFailMessage);
            }
        });
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
//        Log.d("ipqueryActivity","dialog show");
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
//            Log.d("ipqueryActivity","dialog dismiss");
        }
    }
}
