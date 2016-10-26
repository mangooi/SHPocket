package com.mangooi.shpocket.activity.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.mangooi.shpocket.R;
import com.mangooi.shpocket.activity.IMainActivity;
import com.mangooi.shpocket.data.Constant;
import com.mangooi.shpocket.fragment.HomePage;
import com.mangooi.shpocket.util.Test;
import com.mangooi.shpocket.util.parse.GsonUtils;
import com.mangooi.shpocket.util.parse.homepage.WeiXinHot;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/16.
 */

public class MainActivity extends AppCompatActivity implements IMainActivity{

    Fragment mHomePage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.id_main_fl,mHomePage);
        ft.commit();
    }

    private void init() {
        mHomePage=new HomePage();
    }



    @Override
    public void toHomePage() {
        Toast.makeText(this, "homePage", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toFind() {
        Toast.makeText(this, "find", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void toCollection() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<WeiXinHot> list= GsonUtils
                .parseJsonArrayWithGson(Test.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG), WeiXinHot.class);
                Log.i("Test",list.toString());
            }
        }.start();



        Toast.makeText(this, "collection", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void toPocket() {
        Toast.makeText(this, "pocket", Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.id_main_homepage)
    void homePage(){
        toHomePage();
    }
    @OnClick(R.id.id_main_find)
    void find(){
        toFind();
    }
    @OnClick(R.id.id_main_collection)
    void collection(){
        toCollection();
    }
    @OnClick(R.id.id_main_pocket)
    void pocket(){
        toPocket();
    }



}
