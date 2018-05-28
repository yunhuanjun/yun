package com.example.a24442.air_conditioner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a24442.air_conditioner.Fragment.Fragment_home;
import com.example.a24442.air_conditioner.Fragment.Fragment_location;
import com.example.a24442.air_conditioner.Fragment.Fragment_people;
import com.example.a24442.air_conditioner.Fragment.Fragment_text;
import com.example.a24442.air_conditioner.Fragment.Fragment_voice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private TextView tv_home;
    private TextView tv_people;
    private TextView tv_voice;
    private TextView tv_location;
    private TextView tv_text;

    private  FrameLayout contrainer;
    private Fragment_home fragment_home = new Fragment_home();
    private Fragment_people fragment_people = new Fragment_people();
    private Fragment_voice fragment_voice = new Fragment_voice();
    private Fragment_location fragment_location = new Fragment_location();
    private Fragment_text fragment_text = new Fragment_text();

    private FragmentManager fManager;

    private ImageView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();
        bindViews();
        tv_home.performClick();

    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_people = (TextView) findViewById(R.id.tv_people);
        tv_voice = (TextView) findViewById(R.id.tv_voice);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_text = (TextView) findViewById(R.id.tv_text);
        contrainer = (FrameLayout) findViewById(R.id.container);

        tv_home.setOnClickListener(this);
        tv_people.setOnClickListener(this);
        tv_voice.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        tv_text.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        tv_home.setSelected(false);
        tv_people.setSelected(false);
        tv_voice.setSelected(false);
        tv_location.setSelected(false);
        tv_text.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fragment_home != null)
            fragmentTransaction.hide(fragment_home);
        if(fragment_people != null)
            fragmentTransaction.hide(fragment_people);
        if(fragment_voice != null)
            fragmentTransaction.hide(fragment_voice);
        if(fragment_location != null)
            fragmentTransaction.hide(fragment_location);
        if(fragment_text != null)
            fragmentTransaction.hide(fragment_text);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()){
            case R.id.tv_home:
                setSelected();
                tv_home.setSelected(true);
                fragment_home = new Fragment_home();
                fTransaction.replace(R.id.container,fragment_home);
                break;

            case R.id.tv_people:
                setSelected();
                tv_people.setSelected(true);

                Intent intent_people = new Intent(MainActivity.this,SituationMode.class);
                startActivity(intent_people);
                break;

            case R.id.tv_voice:
                setSelected();
                tv_voice.setSelected(true);
                fragment_voice = new Fragment_voice();
                fTransaction.replace(R.id.container,fragment_voice);
                break;

            case R.id.tv_location:
                setSelected();
                tv_location.setSelected(true);

                Intent intent_location = new Intent(MainActivity.this,GPSModelActivity.class);
                startActivity(intent_location);
                break;

            case R.id.tv_text:
                setSelected();
                tv_text.setSelected(true);


                Intent intent_text = new Intent(MainActivity.this,ElectrcityList.class);
                startActivity(intent_text);
                break;

            default:
                break;
        }
        fTransaction.commit();
    }
}