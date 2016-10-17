package com.mangooi.shpocket.activity.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.mangooi.shpocket.R;
import com.mangooi.shpocket.TestActivity;
import com.mangooi.shpocket.fragment.HomePage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/16.
 */

public class MainActivity extends AppCompatActivity{

    Fragment mHomePage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        init();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.id_main_fl,mHomePage);
        ft.commit();
    }

    private void init() {
        mHomePage=new HomePage();
    }

    @OnClick(R.id.id_main_homepage)
    void homePage(){
        Toast.makeText(this, "homePage", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.id_main_find)
    void find(){
        Toast.makeText(this, "find", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.id_main_collection)
    void collection(){
        Toast.makeText(this, "collection", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.id_main_pocket)
    void pocket(){
        Toast.makeText(this, "pocket", Toast.LENGTH_SHORT).show();
        //ForTest
        startActivity(new Intent(this, TestActivity.class));
    }



}
