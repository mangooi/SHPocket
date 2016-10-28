package com.mangooi.shpocket.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.mangooi.shpocket.R;
import com.mangooi.shpocket.adapter.HPAdapter;
import com.mangooi.shpocket.service.GetDataService;
import com.mangooi.shpocket.util.parse.GsonUtils;
import com.mangooi.shpocket.util.parse.homepage.WeiXinHot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/16.
 */

public class HomePage extends Fragment{


    @BindView(R.id.id_homepage_list)
    RecyclerView list;

    Context mContext;

    HPAdapter mHPAdapter;
    Handler mHandler;
    Intent mIntent;

    List<String> briefList;
    List<WeiXinHot.NewsList> newsList;
    ArrayList<String> urlList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        mHandler=new Handler();
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
                newsList=weiXinHots.get(0).getNewslist();
                briefList=new ArrayList<String>();
                urlList=new ArrayList<String>();
                for (WeiXinHot.NewsList newsList1 : newsList) {
                    briefList.add(newsList1.getTitle());
                    urlList.add(newsList1.getPicUrl());
                }
                mIntent=new Intent(mContext,GetDataService.class);
                mIntent.putExtra("Key","BitMap");
                mIntent.putStringArrayListExtra("array",urlList);
                mContext.startService(mIntent);
                mIntent=null;
                /*mHPAdapter=new HPAdapter(mContext,weiXinHots.get(0).getNewslist());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        list.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        list.setAdapter(mHPAdapter);
                    }
                });*/

            }
        });

        GetDataService.setOnCallListener(new GetDataService.OnGetBitMap() {
            @Override
            public void onCall(List<Bitmap> bitmaps) {
                mHPAdapter=new HPAdapter(mContext,newsList,bitmaps,briefList);
                mHPAdapter.setListener(new HPAdapter.HPItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        openItem(newsList.get(position));
                    }
                });
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        list.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        list.setAdapter(mHPAdapter);
                    }
                });
            }
        });
        startServiceByKey("WeiXinHot");
        return view;
    }

    /**
     * 单击Item时打开内容
     * @param openContent 打开内容的信息
     */
    private void openItem(WeiXinHot.NewsList openContent) {
        //可以选择直接加载一个URL
        Toast.makeText(mContext, "打开一个网页", Toast.LENGTH_SHORT).show();
    }

    private void startServiceByKey(String key) {
        mIntent=new Intent(mContext,GetDataService.class);
        mIntent.putExtra("Key",key);
        mContext.startService(mIntent);
        mIntent=null;
    }

    @OnClick(R.id.id_homepage_tv_shopping)
    void shopping(){
        startServiceByKey("WeiXinHotShop");
    }
    @OnClick(R.id.id_homepage_tv_exhibition)
    void exhibition(){
        startServiceByKey("WeiXinHotExhibition");
    }
    @OnClick(R.id.id_homepage_tv_activity)
    void activity(){
        startServiceByKey("WeiXinHotActivity");
    }
    @OnClick(R.id.id_homepage_tv_film)
    void film(){
        startServiceByKey("WeiXinHotFilm");
    }
    @OnClick(R.id.id_homepage_tv_food)
    void food(){
        startServiceByKey("WeiXinHotFood");
    }
    @OnClick(R.id.id_homepage_tv_spot)
    void spot(){
        startServiceByKey("WeiXinHotSpot");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GetDataService.desListener();
    }
}
