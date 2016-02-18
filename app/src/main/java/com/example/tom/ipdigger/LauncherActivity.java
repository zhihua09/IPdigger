package com.example.tom.ipdigger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Tom on 2016/2/18.
 */
public class LauncherActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(LauncherActivity.this, IPQueryActivity.class);
        intent.putExtra("lang","zh-CN");
        intent.putExtra("ip","currentIP");
        startActivity(intent);
        finish();
    }
}
