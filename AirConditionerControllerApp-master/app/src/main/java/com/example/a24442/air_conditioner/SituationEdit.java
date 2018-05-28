package com.example.a24442.air_conditioner;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class SituationEdit extends AppCompatActivity {
    private Calendar mCalendar;

    //模式的压缩界面
    RelativeLayout leave;
    RelativeLayout people;
    RelativeLayout temp;

    //各个模块的修改内容
    LinearLayout leaveHomeEdit;
    LinearLayout peopleEdit;
    LinearLayout tempEdit;
    LinearLayout customizeEdit;

    TextView leaveStart;
    TextView leaveEnd;
    TextView peopleStart;
    TextView peopleEnd;
    Spinner leaveDate;

    //确认按钮
    Button btnLeaveSure;
    Button btnPeopleSure;
    Button btnTempSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation_edit);

        setViewAndData();
        HashMap<RelativeLayout,LinearLayout> editMode = new HashMap<>();
        editMode.put(leave, leaveHomeEdit);
        editMode.put(people, peopleEdit);
        editMode.put(temp, tempEdit);

        setBtnLeaveSure();
        setBtnPeopleSure();
        setBtnTempSure();

        setModeState(editMode);

        setModeTimePicker();
    }

//    设置视图内的控件初始化
    private void setViewAndData(){

        leaveHomeEdit = (LinearLayout) findViewById(R.id.leaveEditMode);
        peopleEdit = (LinearLayout) findViewById(R.id.peopleEditMode);
        tempEdit = (LinearLayout) findViewById(R.id.tempEditMode);

        leave = (RelativeLayout) findViewById(R.id.leaveMode);
        people = (RelativeLayout) findViewById(R.id.peopleMode);
        temp  = (RelativeLayout) findViewById(R.id.temperatureMode);

        btnLeaveSure = (Button) findViewById(R.id.btLeave);
        btnPeopleSure = (Button) findViewById(R.id.btPeople);
        btnTempSure = (Button) findViewById(R.id.btTemp);
        
    }
    //设置模块的隐藏显示
    private void setModeState(final HashMap<RelativeLayout, LinearLayout> editMode){
        Set<RelativeLayout> keys = editMode.keySet();
        for(RelativeLayout key : keys ){
            final RelativeLayout temp = key;
            key.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout edit = editMode.get(temp);
                    switch(edit.getVisibility()){
                        // 若显示，则隐藏
                        case 0:
                            edit.setVisibility(View.GONE);
                            break;
                        // 若隐藏，则显示
                        case 8:
                            edit.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

    }

    //设置各个模块内部的时间控件
    private void setModeTimePicker(){
        leaveStart = (TextView) findViewById(R.id.leaveTimeStart);
        leaveEnd = (TextView) findViewById(R.id.leaveTimeEnd);
        peopleStart = (TextView) findViewById(R.id.peopleTimeStart1);
        peopleEnd = (TextView) findViewById(R.id.peopleTimeEnd1);
        leaveDate = (Spinner) findViewById(R.id.leaveDate);

        getTimePicker(leaveStart);
        getTimePicker(leaveEnd);
        getTimePicker(peopleStart);
        getTimePicker(peopleEnd);
    }

    //获得时间信息
    private void getTimePicker(final TextView time) {
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendar = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(SituationEdit.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        mCalendar.set(Calendar.HOUR, i);
                        mCalendar.set(Calendar.MINUTE, i1);

                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        time.setText(format.format(mCalendar.getTime()));

                    }
                }, mCalendar.get(Calendar.HOUR), mCalendar.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

    }

    void setBtnLeaveSure(){
        btnLeaveSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> views = new ArrayList<>();
                views.add(leaveEnd.getText().toString());
                views.add(leaveStart.getText().toString());
                if(isNull(views)){
                    Toast.makeText(SituationEdit.this, "请完善信息", Toast.LENGTH_SHORT).show();
                }
                else{
                    String title = ((TextView)findViewById(R.id.leaveText)).getText().toString();
                    String firstItem = leaveDate.getSelectedItem().toString() + "       "
                            +leaveStart.getText().toString()+" - "+leaveEnd.getText().toString();
                    String secondItem = ((TextView)findViewById(R.id.leave)).getText().toString();
                    int type = 1;

                    startIntent(title, firstItem, secondItem, type);
                }

            }
        });
    }
    void setBtnPeopleSure(){
        btnPeopleSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> views = new ArrayList<>();
                views.add(peopleStart.getText().toString());
                views.add(peopleEnd.getText().toString());

                EditText tempSet = (EditText) findViewById(R.id.peopleTemp1);
                views.add(tempSet.getText().toString());

                if(isNull(views)){
                    Toast.makeText(SituationEdit.this, "请完善信息", Toast.LENGTH_SHORT).show();
                }
                else{
                    String title = ((TextView)findViewById(R.id.peopleText)).getText().toString();
                    String firstItem = "   "+peopleStart.getText().toString()+" - "+peopleEnd.getText().toString()
                            +"      "+ tempSet.getText().toString() + " °C";
                    String secondItem = null;
                    int type = 2;

                    startIntent(title, firstItem, secondItem, type);
                }
            }
        });
    }
    void setBtnTempSure(){
        btnTempSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tempSet = (EditText) findViewById(R.id.temp1);
                ArrayList<String> views = new ArrayList<>();
                views.add(tempSet.getText().toString());
                Log.e(tempSet.getText().toString(), "ss");
                if(isNull(views)){
                    Toast.makeText(SituationEdit.this, "请完善信息", Toast.LENGTH_SHORT).show();
                }
                else{
                    String title = ((TextView)findViewById(R.id.temperatureText)).getText().toString();
                    String item = ((TextView)findViewById(R.id.tempText)).getText().toString();
                    String than = ((Spinner) findViewById(R.id.than)).getSelectedItem().toString();
                    String firstItem = item +  than + tempSet.getText().toString()+((TextView)findViewById(R.id.temp_unit1)).getText().toString();;
                    String secondItem = null;
                    int type = 3;

                    startIntent(title, firstItem, secondItem, type);
                }
            }
        });
    }

    private  boolean isNull(ArrayList<String> textViews){
        for(String view : textViews){
            if(view.equals("")) {
                return true;
            }
        }
        return false;
    }

    public void startIntent(String title, String item1, String item2, int type){
        Intent intent = new Intent(SituationEdit.this, SituationMode.class);

        intent.putExtra("title", title);
        intent.putExtra("first", item1);
        intent.putExtra("second", item2);
        intent.putExtra("type", type);

        startActivity(intent);
    }
}
