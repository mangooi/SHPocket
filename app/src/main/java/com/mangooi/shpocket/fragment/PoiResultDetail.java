package com.mangooi.shpocket.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.mangooi.shpocket.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/30.
 */

public class PoiResultDetail extends Fragment{

    Context mContext;

    private PoiDetailResult poiDetailResult;

    OverlayOptions overlayOptions;

    @BindView(R.id.id_detail_tv_name)
    TextView tvName;
    @BindView(R.id.id_detail_tv_address)
    TextView tvAddress;
    @BindView(R.id.id_detail_tv_phone)
    TextView tvPhone;
    @BindView(R.id.id_detail_tv_price)
    TextView tvPrice;
    @BindView(R.id.id_detail_tv_time)
    TextView tvTime;
    @BindView(R.id.id_detail_map)
    MapView mMapView;

    private BaiduMap mBaiduMap;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        poiDetailResult=getArguments().getParcelable("PoiDetailResult");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_poi_result_detail,container,false);
        ButterKnife.bind(this,view);
        tvName.setText(poiDetailResult.getName());
        tvAddress.setText(poiDetailResult.getAddress());
        tvPhone.setText(poiDetailResult.getTelephone());
        tvPrice.setText(poiDetailResult.getPrice()+"");
        tvTime.setText(poiDetailResult.getShopHours());
        mBaiduMap=mMapView.getMap();

        MapStatusUpdate msu1 = MapStatusUpdateFactory.zoomTo(32.0f);
        mBaiduMap.setMapStatus(msu1);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(poiDetailResult.getLocation());
        mBaiduMap.animateMapStatus(msu);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(poiDetailResult.getLocation())
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        return view;
    }


}
