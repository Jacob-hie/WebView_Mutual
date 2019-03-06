package com.hie2j.webview_mutual;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("查看商品详情");

        txt = findViewById(R.id.txt);
        String id = getIntent().getStringExtra("id").toString();
        Log.e("DetailActivity","id = " + id);
        txt.setText("商品id为"+id);
    }
}
