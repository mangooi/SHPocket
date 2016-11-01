package com.mangooi.shpocket.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mangooi.shpocket.R;
import com.mangooi.shpocket.activity.impl.MainActivity;
import com.mangooi.shpocket.adapter.HPAdapter;
import com.mangooi.shpocket.service.GetDataService;
import com.mangooi.shpocket.util.parse.GsonUtils;
import com.mangooi.shpocket.model.WeiXinHot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;




/**
 * 默认的第一个页面
 * Created by Administrator on 2016/10/16.
 */

public class HomePage extends Fragment{


    @BindView(R.id.id_homepage_et)
    EditText etEntry;

    @BindView(R.id.id_homepage_list)
    RecyclerView list;

    Context mContext;

    HPAdapter mHPAdapter;
    Handler mHandler;
    Intent mIntent;

    List<String> briefList;
    List<WeiXinHot.NewsList> newsList;
    ArrayList<String> urlList;

    List<WeiXinHot> mWeiXinHots;

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
        etEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH) search(getText());
                clearET();
                ((MainActivity)mContext).hintKbTwo();
                return false;
            }
        });


        GetDataService.setOnCallListener(new GetDataService.OnCallListener() {
            @Override
            public void onCall(String content) {
                //将content进行解析
                mWeiXinHots= GsonUtils.parseJsonArrayWithGson(content,WeiXinHot.class);
                newsList=mWeiXinHots.get(0).getNewslist();
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
                        openItem(mWeiXinHots.get(0),position);
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

    private void clearET() {
        etEntry.setText("");
    }

    private void search(String text) {
        startServiceByKey(text);
    }


    private void openItem(WeiXinHot weiXinHot,int position) {
        //可以选择直接加载一个URL
        Toast.makeText(mContext, "打开一个网页", Toast.LENGTH_SHORT).show();
        ((MainActivity)mContext).showHomePageDetails(weiXinHot,position);
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

    }

    @Override
    public void onPause() {
        super.onPause();
        //GetDataService.desListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        //GetDataService.desListener();
    }

    @NonNull
    public String getText(){
        return etEntry.getText().toString();
    }


}
