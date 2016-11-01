package com.mangooi.shpocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.mangooi.shpocket.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 */

public class PoiResultAdapter extends BaseAdapter{

    private Context mContext;
    private List<PoiInfo> infos;
    private LayoutInflater inflater;


    public PoiResultAdapter(Context mContext, List<PoiInfo> infos) {
        this.mContext = mContext;
        this.infos = infos;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PoiInfo info= (PoiInfo) getItem(position);
        View view = inflater.inflate(R.layout.item_poi_result,null);
        TextView tvName= (TextView) view.findViewById(R.id.id_poiResult_tv_name);
        tvName.setText(info.name);
        TextView tvAddress= (TextView) view.findViewById(R.id.id_poiResult_tv_address);
        tvAddress.setText(info.address);
        return view;
    }




}
