package com.mangooi.shpocket.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.mangooi.shpocket.R;
import com.mangooi.shpocket.model.CollectionMode;
import com.mangooi.shpocket.model.WeiXinHot;
import com.mangooi.shpocket.service.DBControlService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/31.
 */

public class HomePageDetail extends Fragment{

    @BindView(R.id.id_homepage_detail_webView)
    WebView mWebView;
    @BindView(R.id.id_homepage_detail_btn)
    Button mButton;

    private Intent mIntent;
    private Context mContext;
    private WeiXinHot mWeiXinHot;
    private int position;
    private CollectionMode collectionMode;
    private Handler mHandler;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        mWeiXinHot=getArguments().getParcelable("WeiXinHot");
        position=getArguments().getInt("position");
        setMode();
    }

    private void setMode() {
        WeiXinHot.NewsList mode=mWeiXinHot.getNewslist().get(position);
        //String picUrl, String brief, String title, String time
        collectionMode=new CollectionMode(mode.getPicUrl(),mode.getDescription(),mode.getTitle(),mode.getCtime(),mode.getUrl());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_homepage_detail,container,false);
        ButterKnife.bind(this,view);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //mWebView.loadUrl(mWeiXinHot.getNewslist().get(position).getUrl());
        mWebView.loadUrl(collectionMode.getUrl());
        DBControlService.setOnResultListener(new DBControlService.OnResultListener() {
            @Override
            public void success() {
                mButton.setClickable(false);
                //Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                Log.i("Test","添加成功");
            }

            @Override
            public void fail() {
                Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
                Log.i("Test","添加失败");
            }

            @Override
            public void result(List<CollectionMode> list) {
            }
        });
        return view;
    }

    @OnClick(R.id.id_homepage_detail_btn)
    void joinToCollection(){
        mIntent=new Intent(mContext, DBControlService.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable("CollectionMode",collectionMode);
        mIntent.putExtra("WeiXinHot",bundle);
        mIntent.putExtra("DataBase","Collection");
        mIntent.putExtra("Type","Save");
        mContext.startService(mIntent);
        mIntent=null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DBControlService.UnResultListener();
    }
}
