package com.example.tom.ipdigger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class LanguageSelectActivity extends Activity implements View.OnClickListener {

    private Button btnEn ;
    private Button btnZh;
    private Button btnDe;
    private Button btnRu;
    private Button btnEs ;
    private Button btnJa;
    private Button btnFr ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_language_select);

        btnEn = (Button) findViewById(R.id.btn_en);
        btnZh = (Button) findViewById(R.id.btn_zh_CN);
        btnDe = (Button) findViewById(R.id.btn_de);
        btnRu = (Button) findViewById(R.id.btn_ru);
        btnEs = (Button) findViewById(R.id.btn_es);
        btnJa = (Button) findViewById(R.id.btn_ja);
        btnFr = (Button) findViewById(R.id.btn_fr);

        btnEn.setOnClickListener(this);
        btnZh.setOnClickListener(this);
        btnDe.setOnClickListener(this);
        btnRu.setOnClickListener(this);
        btnEs.setOnClickListener(this);
        btnJa.setOnClickListener(this);
        btnFr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_en:
                Intent intent = new Intent(LanguageSelectActivity.this,IPQueryActivity.class);
                intent.putExtra("lang","en");
                intent.putExtra("ip","currentIP");
                startActivity(intent);
                finish();
                break;
        }

    }
}
