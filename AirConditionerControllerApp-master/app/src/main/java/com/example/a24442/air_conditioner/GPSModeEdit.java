package com.example.a24442.air_conditioner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GPSModeEdit extends AppCompatActivity {
    EditText homeText;
    EditText openText;
    EditText closeText;
    EditText idealText;
    EditText range1;
    EditText range2;
    Spinner openSpinner;
    Spinner closeSpinner;
    ImageView back;
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsmode_edit);

        homeText = (EditText) findViewById(R.id.LocationEdit);
        openText = (EditText) findViewById(R.id.OpenDisEdit);
        closeText = (EditText) findViewById(R.id.CloseDisEdit);
        idealText = (EditText) findViewById(R.id.IdealTempEdit);
        range1 = (EditText) findViewById(R.id.TempRangeEdit1);
        range2 = (EditText) findViewById(R.id.TempRangeEdit2);
        back = (ImageView) findViewById(R.id.back_icon);
        save = (TextView) findViewById(R.id.title_edit);
        openSpinner = (Spinner) findViewById(R.id.openLength);
        closeSpinner = (Spinner) findViewById(R.id.closeLength);

        setHomeText();
        saveGPSInfo();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GPSModeEdit.this, GPSModelActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setHomeText(){
        homeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(GPSModeEdit.this, HomeMapActivity.class);
                startActivity(mapIntent);
            }
        });
        Intent intent = getIntent();
        if(intent.hasExtra("address")){
            homeText.setText(intent.getStringExtra("address"));
        }
    }

    public void saveGPSInfo(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String home = "未设置";
                String open = "未设置";
                String close = "未设置";
                String range = "未设置";
                String ideal = "未设置";
                //String home = "未设置";判断是否设置了家庭信息
                if(isNull(homeText)) {
                    Toast.makeText(GPSModeEdit.this, "请设置家庭信息", Toast.LENGTH_SHORT).show();
                    return ;
                }else{
                    home=(homeText.getText().toString());
                }

                if(!isNull(openText))
                    open = openText.getText().toString() + " " +openSpinner.getSelectedItem().toString();

                if(!isNull(closeText))
                    close = closeText.getText().toString() + " " + closeSpinner.getSelectedItem().toString();


                if(!isNull(idealText))
                    ideal = idealText.getText().toString() + " °C";

                //温度范围必须完整
                if(isNull(range1) && isNull(range2)) {

                }else if(isNull(range1) || isNull(range2)){
                    Toast.makeText(GPSModeEdit.this, "请完善好温度范围", Toast.LENGTH_SHORT).show();
                    return ;
                }else{
                    range = range1.getText().toString()+" °C ———— "+range2.getText().toString()+" °C";
                }

                Intent intent = new Intent(GPSModeEdit.this, GPSModelActivity.class);
                intent.putExtra("homeText", home);
                intent.putExtra("openText", open);
                intent.putExtra("closeText", close);
                intent.putExtra("idealText", ideal);
                intent.putExtra("rangeText", range);
                startActivity(intent);
            }
        });
    }

    public boolean isNull(TextView textView){
        return textView.getText().toString().equals("");
    }
}
