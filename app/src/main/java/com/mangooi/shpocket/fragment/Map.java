package com.mangooi.shpocket.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.mangooi.shpocket.R;
import com.mangooi.shpocket.activity.impl.MainActivity;
import com.mangooi.shpocket.adapter.PoiResultAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/27.
 */

public class Map extends Fragment implements AdapterView.OnItemClickListener{


    private static final String TAG = "Map";
    @BindView(R.id.id_map_et_entry)
    EditText etEntry;
    @BindView(R.id.id_map_map)
    MapView mMapView;
    @BindView(R.id.id_map_btn_loc)
    Button btn;
    @BindView(R.id.id_poiResult_list)
    ListView mListView;
    @BindView(R.id.id_poiResult_wv)
    WebView mWebView;

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

    PoiSearch mPoiSearch;

    //private Intent mIntent;
    private List<PoiInfo> infos;


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
        mMapView.showZoomControls(false);
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
            //mapView 销毁后不在处理新接收的位置
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

        mPoiSearch=PoiSearch.newInstance();

        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                Toast.makeText(mContext, "onGetPoiResult", Toast.LENGTH_SHORT).show();
                infos=poiResult.getAllPoi();
                mListView.setAdapter(new PoiResultAdapter(mContext,infos));
                mListView.setOnItemClickListener(Map.this);
                btn.setVisibility(View.INVISIBLE);
                mListView.setVisibility(View.VISIBLE);
                /*mIntent=new Intent(getActivity(),PoiResultActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("poiResult",poiResult);
                startActivityForResult(mIntent,0x01,bundle);//这种方式启动后显示不了...???
                Toast.makeText(mContext, "已经启动", Toast.LENGTH_SHORT).show();
                mIntent=null;*/
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                Toast.makeText(mContext, "onGetPoiDetailResult", Toast.LENGTH_SHORT).show();
                GetDetails(poiDetailResult);
                ((MainActivity)mContext).showPoiDetails(poiDetailResult);
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                Toast.makeText(mContext, "onGetPoiIndoorResult", Toast.LENGTH_SHORT).show();
            }
        });

        mPoiSearch.searchInCity(new PoiCitySearchOption().city("上海").keyword(text).pageNum(10));

        //mPoiSearch.destroy();
    }

    private void GetDetails(PoiDetailResult poiDetailResult) {
        Log.i(TAG+"name",poiDetailResult.getName());
        Log.i(TAG+"getLocation",poiDetailResult.getLocation().toString());
        Log.i(TAG+"getAddress",poiDetailResult.getAddress());
        Log.i(TAG+"getTelephone",poiDetailResult.getTelephone());
        Log.i(TAG+"getUid",poiDetailResult.getUid());
        Log.i(TAG+"getTag",poiDetailResult.getTag());
        Log.i(TAG+"getType",poiDetailResult.getType());
        Log.i(TAG+"getDetailUrl",poiDetailResult.getDetailUrl());
        Log.i(TAG+"getType",poiDetailResult.getType()+"");
        Log.i(TAG+"getOverallRating",poiDetailResult.getOverallRating()+"");
        Log.i(TAG+"getTasteRating",poiDetailResult.getTasteRating()+"");
        Log.i(TAG+"getServiceRating",poiDetailResult.getServiceRating()+"");
        Log.i(TAG+"getEnvironmentRating",poiDetailResult.getEnvironmentRating()+"");
        Log.i(TAG+"getFacilityRating",poiDetailResult.getFacilityRating()+"");
        Log.i(TAG+"getTechnologyRating",poiDetailResult.getTechnologyRating()+"");
        Log.i(TAG+"getImageNum",poiDetailResult.getImageNum()+"");
        Log.i(TAG+"getGrouponNum",poiDetailResult.getGrouponNum()+"");
        Log.i(TAG+"getCommentNum",poiDetailResult.getCommentNum()+"");
        Log.i(TAG+"getFavoriteNum",poiDetailResult.getFavoriteNum()+"");
        Log.i(TAG+"getCheckinNum",poiDetailResult.getCheckinNum()+"");
        Log.i(TAG+"getShopHours",poiDetailResult.getShopHours());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(infos.get(position).uid));
        mListView.setVisibility(View.GONE);
        btn.setVisibility(View.VISIBLE);
    }




    @NonNull
    private String getText(){
        return etEntry.getText().toString();
    }



}
