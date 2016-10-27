package com.mangooi.shpocket.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.mangooi.shpocket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/27.
 */

public class Map extends Fragment{


    @BindView(R.id.id_map_et_entry)
    EditText etEntry;
    @BindView(R.id.id_map_map)
    MapView mMapView;

    private BaiduMap mBaiduMap;
    private Context mContext;
    //定位图层显示模式 (普通-跟随-罗盘)
    private MyLocationConfiguration.LocationMode mCurrentMode;
    //当前位置经纬度
    private double latitude;
    private double longitude;
    //是否首次定位
    private boolean isFirstLoc = true;
    //定位SDK的核心类
    private LocationClient mLocClient;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_map,container,false);
        ButterKnife.bind(this,view);
        init();

        return view;
    }

    private void init() {
        //设置回车键的样式及响应
        etEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH) search(getText());
                return false;
            }
        });
        //设置地图缩放级别16 类型普通地图
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定位初始化
        //注意: 实例化定位服务 LocationClient类必须在主线程中声明 并注册定位监听接口
        mLocClient = new LocationClient(mContext);
        mLocClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);              //打开GPS
        option.setCoorType("bd09ll");        //设置坐标类型
        option.setScanSpan(5000);            //设置发起定位请求的间隔时间为5000ms
        mLocClient.setLocOption(option);     //设置定位参数
        mLocClient.start();                  //调用此方法开始定位
    }

    @Override
    public void onDestroy() {
        mLocClient.stop();                       //退出时销毁定位
        mBaiduMap.setMyLocationEnabled(false);   //关闭定位图层
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        isFirstLoc=true;
    }


    /*----------------------华丽的分割线------------------*/


    @OnClick(R.id.id_map_btn_loc)
    void goMyLocation(){
        LatLng loc = new LatLng(latitude,longitude);
        //MapStatusUpdate描述地图将要发生的变化
        //MapStatusUpdateFactory生成地图将要反生的变化
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(loc);
        mBaiduMap.animateMapStatus(msu);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapview 销毁后不在处理新接收的位置
            if (location == null || mBaiduMap == null) {
                return;
            }
            //MyLocationData.Builder定位数据建造器
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            //设置定位数据
            mBaiduMap.setMyLocationData(locData);
            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
            //获取经纬度
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //Toast.makeText(getApplicationContext(), String.valueOf(latitude), Toast.LENGTH_SHORT).show();
            //第一次定位的时候，那地图中心点显示为定位到的位置
            if (isFirstLoc) {
                isFirstLoc = false;
                //地理坐标基本数据结构
                LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
                //MapStatusUpdate描述地图将要发生的变化
                //MapStatusUpdateFactory生成地图将要反生的变化
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(loc);
                mBaiduMap.animateMapStatus(msu);
                Toast.makeText(mContext, location.getAddrStr(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 用户输入搜索的KeyWord确认后进行搜索操作
     * @param text keyWords
     */
    private void search(String text) {



    }




    @NonNull
    private String getText(){
        return etEntry.getText().toString();
    }


}
