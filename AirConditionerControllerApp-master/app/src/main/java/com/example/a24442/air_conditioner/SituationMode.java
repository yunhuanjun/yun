package com.example.a24442.air_conditioner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SituationMode extends AppCompatActivity {

    private ListView listView;
    private TextView add;
    private ImageView back;
    private ArrayList<HashMap<String, String>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation_mode);

        initViewAndData();
    }

    private void initViewAndData(){
        listView = (ListView) findViewById(R.id.situationList);
        add = (TextView) findViewById(R.id.title_edit);
        back = (ImageView) findViewById(R.id.back_icon);
        add.setText("");
        add.setBackgroundResource(R.drawable.add);

        setAddStep();//    添加模块的步骤

        setBackStep();//    添加返回的步骤

        data = new ArrayList<>();

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String item1 = intent.getStringExtra("first");
        String item2 = intent.getStringExtra("second");
        int type = intent.getIntExtra("type", 0);
        //获取修改的信息
        if(title != null) {
            HashMap<String, String> map = new HashMap<>();
            map.put("item1", item1);
            map.put("item2", item2);
            map.put("title", title);
            map.put("type", type + "");
            data.add(map);
        }



        SituationAdapter situationAdapter = new SituationAdapter(SituationMode.this, data);
        listView.setAdapter(situationAdapter);
    }

    //    添加模块的步骤
    public void setAddStep(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SituationMode.this, SituationEdit.class);
                startActivity(intent);
            }
        });
    }
    //    添加返回的步骤
    public void setBackStep(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SituationMode.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    static class SituationAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;

        public SituationAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
                //绑定item模块
                convertView = LayoutInflater.from(context).inflate(R.layout.situation_item, parent, false);
                holder = new ViewHolder();
                //设置模块中的数
                holder.titleText = (TextView) convertView.findViewById(R.id.theText1);
                holder.text1 = (TextView) convertView.findViewById(R.id.theText2);
                holder.text2 = (TextView) convertView.findViewById(R.id.theText3);
                holder.switchCompat = convertView.findViewById(R.id.switch1);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, String> modeData = list.get(position);
            holder.titleText.setText(modeData.get("title"));
            holder.text1.setText(modeData.get("item1"));
            if(modeData.get("item2") == null)
                holder.text2.setVisibility(View.GONE);
            else{
                holder.text2.setText(modeData.get("item2"));
            }


            holder.switchCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return convertView;
        }

        private static class ViewHolder {
            private TextView titleText;
            private TextView text1;
            private TextView text2;
            private SwitchCompat switchCompat;
        }
    }
}
