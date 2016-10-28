package com.mangooi.shpocket.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;

import com.mangooi.shpocket.data.Constant;
import com.mangooi.shpocket.util.PictureUtils;
import com.mangooi.shpocket.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mangooi on 2016/10/24 - 9:56
 */

public class GetDataService extends IntentService{

    private static OnCallListener mListener;
    private static OnGetBitMap mGetBitMap;
    private List<Bitmap> bitmaps;
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
                mListener.onCall(NetUtils.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG));
                break;
            case "WeiXinHotFood":
                mListener.onCall(NetUtils.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG_FOOD));
                break;
            case "WeiXinHotShop":
                mListener.onCall(NetUtils.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG_SHOPPING));
                break;
            case "WeiXinHotFilm":
                mListener.onCall(NetUtils.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG_FILM));
                break;
            case "WeiXinHotSpot":
                mListener.onCall(NetUtils.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG_SPOT));
                break;
            case "WeiXinHotExhibition":
                mListener.onCall(NetUtils.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG_EXHIBITION));
                break;
            case "WeiXinHotActivity":
                mListener.onCall(NetUtils.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG_ACTIVITY));
                break;
            case "BitMap":
                bitmaps=new ArrayList<>();
                ArrayList<String> urls=intent.getStringArrayListExtra("array");
                for (String url : urls) {
                    bitmaps.add(PictureUtils.getBitmap(url));
                }
                mGetBitMap.onCall(bitmaps);
            default:
                break;
        }
    }

    public interface OnCallListener{
        void onCall(String content);
    }

    public interface OnGetBitMap{
        void onCall(List<Bitmap> bitmaps);
    }

    public static void setOnCallListener(OnCallListener listener){
        mListener=listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



    public static void setOnCallListener(OnGetBitMap listener){mGetBitMap=listener;}

    public static void desListener(){
        mListener=null;
        mGetBitMap=null;
    }
}
