package com.mangooi.shpocket.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mangooi.shpocket.R;
import com.mangooi.shpocket.adapter.HPAdapter;
import com.mangooi.shpocket.service.GetDataService;
import com.mangooi.shpocket.util.parse.GsonUtils;
import com.mangooi.shpocket.util.parse.homepage.WeiXinHot;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/16.
 */

public class HomePage extends Fragment{


    @BindView(R.id.id_homepage_list)
    RecyclerView list;

    Context mContext;

    Intent mIntent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_homepage,container,false);
        ButterKnife.bind(this,view);
        GetDataService.setOnCallListener(new GetDataService.OnCallListener() {
            @Override
            public void onCall(String content) {
                //将content进行解析
                List<WeiXinHot> weiXinHots= GsonUtils.parseJsonArrayWithGson(content,WeiXinHot.class);
                Log.i("Test",weiXinHots.toString());
                /*list.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                list.setAdapter(new HPAdapter(mContext));*/
            }
        });
        mIntent=new Intent(mContext,GetDataService.class);
        mIntent.putExtra("Key","WeiXinHot");
        mContext.startService(mIntent);
        return view;
    }





}
