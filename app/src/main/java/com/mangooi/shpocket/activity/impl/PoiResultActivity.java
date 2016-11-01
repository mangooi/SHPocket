package com.mangooi.shpocket.activity.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.mangooi.shpocket.R;
import com.mangooi.shpocket.adapter.PoiResultAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @deprecated
 * Created by Administrator on 2016/10/29.
 */

public class PoiResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    @BindView(R.id.id_poiResult_list1)
    ListView mListView;


    PoiResult mPoiResult;
    List<PoiInfo> poiInfos;

    PoiResultAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData(savedInstanceState);
        setContentView(R.layout.activity_poiresult);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mAdapter=new PoiResultAdapter(this,poiInfos);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }


    public void getData(Bundle data) {
        mPoiResult=data.getParcelable("poiResult");
        if (mPoiResult==null)return;
        poiInfos=mPoiResult.getAllPoi();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
    }
}
