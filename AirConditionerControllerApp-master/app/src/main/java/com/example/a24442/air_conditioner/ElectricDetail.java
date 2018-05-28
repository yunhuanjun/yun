package com.example.a24442.air_conditioner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ElectricDetail extends AppCompatActivity {

        ImageView back;
        TextView title;
        Spinner monthSpinner;
        TextView time;
        TextView electric;
        TextView price;
        TextView cost;
        RelativeLayout costDetail;
        RelativeLayout chartSet;
        LineChartView chartView;
        ImageView detailView;
        static boolean chart = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_electric_detail);

            initViewsAndDatas();
            initChartViews();
            //点击展示日耗电趋势图
            detailView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (chartSet.getVisibility()) {
                        // 若显示，则隐藏
                        case 0:
                            chartSet.setVisibility(View.GONE);
                            detailView.setImageResource(R.drawable.detail);
                            break;
                        // 若隐藏，则显示
                        case 8:
                            chartSet.setVisibility(View.VISIBLE);
                            detailView.setImageResource(R.drawable.less);
                            break;
                        default:
                            break;
                    }

                }
            });
        }

        public void initViewsAndDatas(){
            monthSpinner = (Spinner) findViewById(R.id.monthChoose);
            time = (TextView) findViewById(R.id.timeSet);
            electric = (TextView) findViewById(R.id.electricSet);
            price = (TextView) findViewById(R.id.priceSet);
            cost = (TextView) findViewById(R.id.costSet);
            chartView = (LineChartView) findViewById(R.id.costChart);
            costDetail = (RelativeLayout) findViewById(R.id.costDetail);

            title = (TextView) findViewById(R.id.title_edit);
            back = (ImageView) findViewById(R.id.back_icon);
            detailView = (ImageView) findViewById(R.id.detailImage);
            chartSet = (RelativeLayout) findViewById(R.id.chart);

            title.setVisibility(View.GONE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ElectricDetail.this, ElectrcityList.class);
                    startActivity(intent);
                }
            });

            Intent intent = getIntent();
            electric.setText(intent.getStringExtra("electric"));
            cost.setText(intent.getStringExtra("cost"));
            String temp = intent.getStringExtra("month");

            int index = Integer.valueOf(temp.substring(0, temp.length()-1));
            monthSpinner.setSelection(index - 1, true);


        }

        public void initChartViews(){


            //定义线条
            List<Line> lines = new ArrayList<>();

            List<String> dateList = new ArrayList<>();
            List<Integer> dushu = new ArrayList<>();
            for(int i = 0; i < 5; i++){
                dateList.add(i*5+"日");
                dushu.add(i);
            }
            //设置x，y轴数据
            List<AxisValue> axisXValueList = new ArrayList<>();
            List<PointValue> pointValueList = new ArrayList<>();

            for (int i = 0; i < dateList.size(); i++) {
                axisXValueList.add(new AxisValue(i).setLabel(dateList.get(i)));
                pointValueList.add(new PointValue(i, dushu.get(i)));
            }

            Axis axisX = new Axis(axisXValueList);//x轴
            Axis axisY = new Axis();//y轴
            axisY.setName("度数");//设置Y轴显示名称
            axisX.setName("日期");//设置X轴显示名称

            Line line = new Line(pointValueList);
            line.setHasLabels(true);
            //折线的颜色
            line.setColor(ChartUtils.DEFAULT_COLOR);
            //折线图上每个数据点的形状
            //line.setShape(ValueShape.CIRCLE);
            //设置数据点颜色
            line.setPointColor(ChartUtils.COLOR_RED);
            //是否用线显示。如果为false 则没有曲线只有点显示
            line.setHasLines(true);
            lines.add(line);
            //图形数据加载
            LineChartData lineChartData = new LineChartData(lines);

            lineChartData.setAxisXBottom(axisX);
            lineChartData.setAxisYLeft(axisY);

            //把数据放到控件中
            chartView.setLineChartData(lineChartData);

            chartView.setInteractive(true);
            chartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
            chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
            chartView.getAxesRenderer();
        }
    }
