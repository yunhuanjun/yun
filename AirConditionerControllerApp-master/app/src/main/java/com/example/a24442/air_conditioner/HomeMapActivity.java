package com.example.a24442.air_conditioner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;

public class HomeMapActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {
    private MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private MyLocationStyle myLocationStyle;
    private String address;

    TextView title;
    Button sureLocation;
    TextView locationText;
    ImageView back;

    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_map);

        title = (TextView) findViewById(R.id.title_edit);
        sureLocation = (Button) findViewById(R.id.sureLocation);
        locationText = (TextView) findViewById(R.id.homeText);
        back = (ImageView) findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeMapActivity.this, GPSModelActivity.class);
                startActivity(intent);
            }
        });
        title.setVisibility(View.GONE);

        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);

        //初始化地图控制器对象aMap
        if (aMap == null) {
            aMap = mapView.getMap(); //将mapView交给地图控制器管理
            // 设置定位监听
            aMap.setLocationSource(this);
            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
            // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
            MyLocationStyle myLocationStyle;

            myLocationStyle = new MyLocationStyle();
            myLocationStyle.interval(2000);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.setMyLocationEnabled(true);
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        }

        setMap();


        sureLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeLocation();
            }
        });

    }

    public void setMap(){

        mUiSettings = aMap.getUiSettings();

        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。
        aMap.showIndoorMap(true);

        /**
         * 设置地图是否可以手势滑动
         */
        mUiSettings.setScrollGesturesEnabled(true);

        /**
         * 设置地图是否可以手势缩放大小
         */
        mUiSettings.setZoomGesturesEnabled(true);
        /**
         * 设置地图是否可以倾斜
         */
        mUiSettings.setTiltGesturesEnabled(true);
        /**
         * 设置地图是否可以旋转
         */
        mUiSettings.setRotateGesturesEnabled(true);
        mUiSettings.setScaleControlsEnabled(true);//控制比例尺控件是否显示
        mUiSettings.setLogoBottomMargin(-50);//隐藏logo

    }

    public void makeLocation(){
        Intent intent = new Intent(HomeMapActivity.this, GPSModeEdit.class);
        intent.putExtra("address",locationText.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();

        if (mlocationClient != null) {
            mlocationClient.onDestroy();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }

    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    //定位回调  在回调方法中调用“mListener.onLocationChanged(amapLocation);”可以在地图上显示系统小蓝点。
    //从location对象中获取经纬度信息，地址描述信息
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                address = aMapLocation.getAddress().toString();
                locationText.setText(address);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("定位AmapErr", errText);
            }
        }
    }
}
