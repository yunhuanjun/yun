package com.example.a24442.air_conditioner.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a24442.air_conditioner.DatePickerDialog;
import com.example.a24442.air_conditioner.ElectrcityList;
import com.example.a24442.air_conditioner.ElectricDetail;
import com.example.a24442.air_conditioner.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by 24442 on 2018/5/6.
 */

public class Fragment_text extends Fragment{
    ListView listView;
    TextView yearChoose;
    Button btDate;
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_electrcity_list,container,false);
        listView = (ListView) view.findViewById(R.id.electricity_list);
        title = (TextView) view.findViewById(R.id.title_edit);
        yearChoose = (TextView) view.findViewById(R.id.yearChoose);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}

