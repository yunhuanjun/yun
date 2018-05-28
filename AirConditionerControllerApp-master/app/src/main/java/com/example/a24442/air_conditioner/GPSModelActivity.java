package com.example.a24442.air_conditioner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GPSModelActivity extends AppCompatActivity {
    TextView homeText;
    TextView openText;
    TextView closeText;
    TextView idealText;
    TextView rangeText;

    ImageView back;
    TextView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsmodel);

        homeText = (TextView) findViewById(R.id.HomeText);
        openText = (TextView) findViewById(R.id.OpenText);
        closeText = (TextView) findViewById(R.id.CloseText);
        idealText = (TextView) findViewById(R.id.IdealText);
        rangeText = (TextView) findViewById(R.id.RangeText);
        back = (ImageView) findViewById(R.id.back_icon);
        edit = (TextView) findViewById(R.id.title_edit);

        edit.setText("设置");

        Intent intent = getIntent();
        String home = intent.getStringExtra("homeText");
        String open = intent.getStringExtra("openText");
        String close = intent.getStringExtra("closeText");
        String ideal = intent.getStringExtra("idealText");
        String range = intent.getStringExtra("rangeText");

        if(!isEmpty(home))
            homeText.setText(home);
        if(!isEmpty(open))
            openText.setText(open);
        if(!isEmpty(close))
            closeText.setText(close);
        if(!isEmpty(ideal))
            idealText.setText(ideal);
        if(!isEmpty(range))
            rangeText.setText(range);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent_gps_back = new Intent(GPSModelActivity.this,MainActivity.class);
            startActivity(intent_gps_back);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(GPSModelActivity.this, GPSModeEdit.class);
                startActivity(editIntent);
            }
        });
    }

    public boolean isEmpty(String attr){
        return attr == null;
    }
}
