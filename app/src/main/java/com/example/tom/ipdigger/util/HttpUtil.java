package com.example.tom.ipdigger.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tom on 2016/2/11.
 */
public class HttpUtil {

    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable(){
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while((line=bfr.readLine())!=null){
                        response.append(line);
                    }
                    if(listener!=null){
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if(listener!=null){
                        listener.onError(e);
                    }
                } finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
