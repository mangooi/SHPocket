package com.mangooi.shpocket.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.mangooi.shpocket.data.Constant;
import com.mangooi.shpocket.util.HexadecimalConver;
import com.mangooi.shpocket.util.PictureUtils;
import com.mangooi.shpocket.util.NetUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

        if (mListener==null||mGetBitMap==null)return;//避免用户按键太快,会报空指针

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
                if (mGetBitMap==null)break;
                mGetBitMap.onCall(bitmaps);
                break;
            default:
                String temp=null;
                Log.i("Test",key);
                try {
                    temp=URLEncoder.encode(key,"UTF-8");
                    Log.i("Test",HexadecimalConver.encode(key)+"   /   "+ URLEncoder.encode(key,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("Test",HexadecimalConver.encode(key));
                mListener.onCall(NetUtils.request(Constant.WEIXIN_HOT_URL,Constant.WEIXIN_HOT_ARG_HEAD+temp+Constant.WEIXIN_HOT_ARG_END));
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
