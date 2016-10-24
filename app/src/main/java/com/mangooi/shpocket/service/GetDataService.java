package com.mangooi.shpocket.service;

import android.app.IntentService;
import android.content.Intent;

import com.mangooi.shpocket.data.Constant;
import com.mangooi.shpocket.util.Test;

/**
 * Created by mangooi on 2016/10/24 - 9:56
 */

public class GetDataService extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GetDataService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String key=intent.getStringExtra("Key");
        switch (key){
            case "WeiXinHot":
                Test.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG);
                break;
            default:
                break;
        }
    }


}
