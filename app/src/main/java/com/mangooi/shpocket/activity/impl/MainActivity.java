package com.mangooi.shpocket.activity.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.mangooi.shpocket.R;
import com.mangooi.shpocket.activity.IMainActivity;
import com.mangooi.shpocket.data.Constant;
import com.mangooi.shpocket.fragment.Collection;
import com.mangooi.shpocket.fragment.CollectionDetail;
import com.mangooi.shpocket.fragment.HomePage;
import com.mangooi.shpocket.fragment.HomePageDetail;
import com.mangooi.shpocket.fragment.Map;
import com.mangooi.shpocket.fragment.PoiResultDetail;
import com.mangooi.shpocket.util.NetUtils;
import com.mangooi.shpocket.util.parse.GsonUtils;
import com.mangooi.shpocket.model.WeiXinHot;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/16.
 */

public class MainActivity extends AppCompatActivity implements IMainActivity{

    Fragment mHomePage;
    Fragment mMap;
    Fragment mCollection;
    Fragment mPoiResultDetail;
    Fragment mHomePageDetail;
    Fragment mCollectionDetail;
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
        openFragment(mHomePage);
        Toast.makeText(this, "homePage", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toFind() {
        if (mMap==null)mMap=new Map();
        openFragment(mMap);
        Toast.makeText(this, "find", Toast.LENGTH_SHORT).show();
    }

    /**
     * 打开一个新的Fragment界面
     * @param fragment fragment
     */
    private void openFragment(Fragment fragment) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.id_main_fl,fragment);
        ft.commit();
    }

    @Override
    public void toCollection() {
        /*new Thread(){
            @Override
            public void run() {
                super.run();
                List<WeiXinHot> list= GsonUtils
                .parseJsonArrayWithGson(NetUtils.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG), WeiXinHot.class);
                Log.i("NetUtils",list.toString());
            }
        }.start();*/
        if (mCollection==null)mCollection=new Collection();
        openFragment(mCollection);
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


    public void showPoiDetails(PoiDetailResult result){
        Bundle bundle=new Bundle();
        bundle.putParcelable("PoiDetailResult",result);
        mPoiResultDetail=new PoiResultDetail();
        mPoiResultDetail.setArguments(bundle);
        openFragment(mPoiResultDetail);
    }
    public void showHomePageDetails(WeiXinHot weiXinHot,int position){
        Bundle bundle=new Bundle();
        bundle.putParcelable("WeiXinHot",weiXinHot);
        bundle.putInt("position",position);
        mHomePageDetail=new HomePageDetail();
        mHomePageDetail.setArguments(bundle);
        openFragment(mHomePageDetail);
    }

    public void showCollectionDetails(String url){
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        mCollectionDetail=new CollectionDetail();
        mCollectionDetail.setArguments(bundle);
        openFragment(mCollectionDetail);
    }

    public void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }



}
