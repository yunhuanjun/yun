package com.example.a24442.air_conditioner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ElectrcityList extends AppCompatActivity {

    ListView listView;
    TextView yearChoose;
    Button btDate;
    TextView title;
    ImageView list_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrcity_list);

        listView = (ListView) findViewById(R.id.electricity_list);
        title = (TextView) findViewById(R.id.title_edit);
        yearChoose = (TextView) findViewById(R.id.yearChoose);
        list_back = (ImageView) findViewById(R.id.back_icon);

        list_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_list_back = new Intent(ElectrcityList.this,MainActivity.class);
                startActivity(intent_list_back);

            }
        });
        chooseYear();
        title.setVisibility(View.GONE);
        setListView();

    }

    public void chooseYear(){
        yearChoose.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();

            @Override
            public void onClick(View view) {
                new DatePickerDialog(ElectrcityList.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth) {
                        String textString = String.format("%d 年", startYear);
                        yearChoose.setText(textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)).show();
            }
        });
    }
    public void setListView(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        for(int i = 0; i < 12; i++){
            map.put("monthMoney","100");
            map.put("month",i+"月");
            map.put("electric",i+"00");
            list.add(map);
            map = new HashMap<>();
        }

        EletricAdapter eletricAdapter = new EletricAdapter(ElectrcityList.this, list);

        listView.setAdapter(eletricAdapter);
    }

    static class EletricAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;

        public EletricAdapter(Context context, ArrayList<HashMap<String, String>> list) {
            this.context = context;
            this.list = list;
        }

        public Context getContext() {
            return context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.month_item, parent, false);
                holder = new ViewHolder();
                holder.monthText = (TextView) convertView.findViewById(R.id.monthText);
                holder.electricity = (TextView) convertView.findViewById(R.id.electricity);
                holder.monthMoney = (TextView) convertView.findViewById(R.id.monthMoney);
                holder.month = convertView.findViewById(R.id.month);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, String> monthElectric = list.get(position);
            holder.monthMoney.setText(monthElectric.get("monthMoney"));
            holder.monthText.setText(monthElectric.get("month"));
            holder.electricity.setText(monthElectric.get("electric"));

            holder.month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> map = list.get(position);

                    Intent intent = new Intent(getContext(), ElectricDetail.class);

                    intent.putExtra("electric", map.get("electric"));
                    intent.putExtra("cost",map.get("monthMoney"));
                    intent.putExtra("month",map.get("month"));

                    getContext().startActivity(intent);
                }
            });

            return convertView;
        }

        private static class ViewHolder {
            private TextView monthText;
            private TextView electricity;
            private TextView monthMoney;
            private RelativeLayout month;
        }
    }
}

