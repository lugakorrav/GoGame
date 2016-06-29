package com.lugakorrav.gogame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        textView = (TextView)findViewById(R.id.textView_info);
        String str = getIntent().getExtras().getString("info");
        textView.setText(str);
    }
}
