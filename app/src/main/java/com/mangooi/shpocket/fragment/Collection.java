package com.mangooi.shpocket.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mangooi.shpocket.R;
import com.mangooi.shpocket.activity.impl.MainActivity;
import com.mangooi.shpocket.adapter.CollectionAdapter;
import com.mangooi.shpocket.model.CollectionMode;
import com.mangooi.shpocket.service.DBControlService;
import com.mangooi.shpocket.service.GetDataService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by Administrator on 2016/10/31.
 */

public class Collection extends Fragment implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener{

    Context mContext;
    List<CollectionMode> modes;

    @BindView(R.id.id_collection_lv)
    ListView mListView;

    CollectionAdapter mAdapter;
    Handler mHandler;

    private int positionSelected;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        mHandler=new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_collection,container,false);
        ButterKnife.bind(this,view);
        getDataFromDB();
        return view;
    }

    public void getDataFromDB() {
        DBControlService.setOnResultListener(new DBControlService.OnResultListener() {
            @Override
            public void success() {
                getDataFromDB();
            }
            @Override
            public void fail() {

            }
            @Override
            public void result(List<CollectionMode> list) {
                Log.i("Test","返回List"+list.toString());
                modes = list;
                getBitMapFromNet();
            }
        });

        Intent intent=new Intent(mContext,DBControlService.class);
        intent.putExtra("DataBase","Collection");
        intent.putExtra("Type","Find");
        mContext.startService(intent);

    }

    public void getBitMapFromNet() {

        //图片资源获取回调
        GetDataService.setOnCallListener(new GetDataService.OnGetBitMap() {
            @Override
            public void onCall(List<Bitmap> bitmaps) {
                showList(bitmaps);
            }
        });

        //启动一个服务 --- 获取图片资源
        Intent intent=new Intent(mContext,GetDataService.class);
        intent.putExtra("Key","BitMap");
        ArrayList<String> picUrls=new ArrayList<>();
        for (CollectionMode mode : modes) {
            picUrls.add(mode.getPicUrl());
        }
        intent.putStringArrayListExtra("array",picUrls);
        mContext.startService(intent);
        Log.i("Test","服务已经启动");
    }




    private void showList(List<Bitmap> bitmaps) {
        mAdapter=new CollectionAdapter(mContext,modes,bitmaps);
        //写一个适配器..
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListView.setAdapter(mAdapter);
                mListView.setOnItemLongClickListener(Collection.this);
                mListView.setOnItemClickListener(Collection.this);
                registerForContextMenu(mListView);
                Log.i("Test","设置完毕");
            }
        });
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((MainActivity)mContext).showCollectionDetails(modes.get(position).getUrl());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        mListView.showContextMenu();
        positionSelected=position;
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,1, Menu.NONE,"删除");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        deleteDataFromDB();
        return super.onContextItemSelected(item);
    }

    private void deleteDataFromDB() {
        Intent intent=new Intent(mContext,DBControlService.class);
        intent.putExtra("DataBase","Collection");
        intent.putExtra("Type","Delete");
        intent.putExtra("Title",modes.get(positionSelected).getTitle());
        mContext.startService(intent);
    }
}
