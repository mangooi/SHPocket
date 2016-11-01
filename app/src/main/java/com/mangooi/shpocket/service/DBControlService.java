package com.mangooi.shpocket.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mangooi.shpocket.data.Constant;
import com.mangooi.shpocket.model.CollectionDao;
import com.mangooi.shpocket.model.CollectionMode;
import com.mangooi.shpocket.model.WeiXinHot;
import com.mangooi.shpocket.util.DBHelper;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class DBControlService extends IntentService{

    private static final String TAG = "DBControlService";
    private static OnResultListener mListener;

    private DBHelper dbHelper;
    private CollectionDao mCollectionDao;
    private List<WeiXinHot> mWeiXinHotList;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public DBControlService() {
        super("DBControl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        dbHelper=new DBHelper(getApplicationContext(), Constant.DATABASE_NAME,null,1);
        mCollectionDao=new CollectionDao(dbHelper);

        String dataBase=intent.getStringExtra("DataBase");
        switch (dataBase){
            case "Collection":
                String type=intent.getStringExtra("Type");

                switch (type){
                    case "Save":
                        Bundle bundle=intent.getBundleExtra("WeiXinHot");
                        CollectionMode mode=bundle.getParcelable("CollectionMode");
                        Log.i(TAG,mode.toString());
                        if (mCollectionDao.saveCollection(mode))
                            mListener.success();
                        else
                            mListener.fail();
                        break;
                    case "Delete":
                        String title=intent.getStringExtra("Title");
                        if (mCollectionDao.deleteCollection(title))mListener.success();

                        break;
                    case "Find":
                        List<CollectionMode> list=mCollectionDao.findCollection();
                        if (list!=null)
                            mListener.result(list);
                        else
                            mListener.fail();
                        break;
                }
                break;
            case "Pocket":


                break;
            default:
                break;
        }


    }

    public interface OnResultListener{
        void success();
        void fail();
        void result(List<CollectionMode> list);
    }

    public static void setOnResultListener(OnResultListener listener){
        mListener=listener;
    }

    public static void UnResultListener(){
        mListener=null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
