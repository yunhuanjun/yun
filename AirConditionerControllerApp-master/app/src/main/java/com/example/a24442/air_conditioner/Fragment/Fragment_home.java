package com.example.a24442.air_conditioner.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a24442.air_conditioner.CircleImageView;
import com.example.a24442.air_conditioner.EditActivity;
import com.example.a24442.air_conditioner.HeadPortraitActivity;
import com.example.a24442.air_conditioner.HelpActivity;
import com.example.a24442.air_conditioner.Login_Activity;
import com.example.a24442.air_conditioner.R;
import com.example.a24442.air_conditioner.RegisterActivity;
import com.example.a24442.air_conditioner.SettingActivity;

/**
 * Created by 24442 on 2018/5/5.
 */

public class Fragment_home extends Fragment implements View.OnClickListener{

   private ImageView user,iv_setting,iv_help,iv_edit;//设置与帮助及编辑
   private TextView tv_login,tv_register,tv_setting,tv_help;//登录等功能
   private CircleImageView head_portrait;//圆形头像

    public static Fragment_home newInstantce(String tittle){
        Fragment_home fragment_home = new Fragment_home();
        Bundle bundle = new Bundle();
        bundle.putString("tittle",tittle);
        fragment_home.setArguments(bundle);
        return  fragment_home;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        tv_login = (TextView)view.findViewById(R.id.tv_login);
        tv_register =(TextView) view.findViewById(R.id.tv_register);
        tv_setting =(TextView) view.findViewById(R.id.tv_setting);
        tv_help =(TextView) view.findViewById(R.id.tv_help);
        iv_setting =(ImageView) view.findViewById(R.id.iv_setting);
        iv_help =(ImageView) view.findViewById(R.id.iv_help);
        iv_edit =(ImageView) view.findViewById(R.id.iv_edit);
        head_portrait = (CircleImageView) view.findViewById(R.id.head_portrait);

        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        tv_help.setOnClickListener(this);
        iv_edit.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        iv_help.setOnClickListener(this);
        head_portrait.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                Intent intent_tv_login  = new Intent(getActivity(),Login_Activity.class);
                getActivity().startActivity(intent_tv_login);
                break;
            case R.id.tv_register:
                Intent intent_tv_register  = new Intent(getActivity(),RegisterActivity.class);
                getActivity().startActivity(intent_tv_register);
                break;
            case R.id.tv_setting:
                Intent intent_tv_setting  = new Intent(getActivity(),SettingActivity.class);
                getActivity().startActivity(intent_tv_setting);
                break;
            case R.id.tv_help:
                Intent intent_tv_help  = new Intent(getActivity(),HelpActivity.class);
                getActivity().startActivity(intent_tv_help);
                break;
            case R.id.iv_edit:
                Intent intent_iv_edit  = new Intent(getActivity(),EditActivity.class);
                getActivity().startActivity(intent_iv_edit);
                break;
            case R.id.iv_setting:
                Intent intent_iv_setting  = new Intent(getActivity(),SettingActivity.class);
                getActivity().startActivity(intent_iv_setting);
                break;
            case R.id.iv_help:
                Intent intent_iv_help  = new Intent(getActivity(),HelpActivity.class);
                getActivity().startActivity(intent_iv_help);
                break;
            case R.id.head_portrait:
                Intent intent_head_portrait = new Intent(getActivity(),HeadPortraitActivity.class);
                getActivity().startActivity(intent_head_portrait);
                break;
            case R.id.ib_switch:

                break;
            case R.id.ib_cold:

                break;
            case R.id.ib_hot:

                break;
            default:
                    break;
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
