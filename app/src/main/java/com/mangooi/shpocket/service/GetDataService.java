package com.mangooi.shpocket.service;

import android.app.IntentService;
import android.content.Intent;

import com.mangooi.shpocket.data.Constant;
import com.mangooi.shpocket.util.Test;

/**
 * Created by mangooi on 2016/10/24 - 9:56
 */

public class GetDataService extends IntentService{

    private static OnCallListener mListener;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public GetDataService() {
        super("123");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String key=intent.getStringExtra("Key");
        switch (key){
            case "WeiXinHot":
                mListener.onCall(Test.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG));
                break;
            default:
                break;
        }
    }

    public interface OnCallListener{
        void onCall(String content);
    }

    public static void setOnCallListener(OnCallListener listener){
        mListener=listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener=null;
    }
}
