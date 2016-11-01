package com.mangooi.shpocket.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 解析微信热门API返回的Json数据的JavaBean
 * Created by mangooi on 2016/10/22 - 14:57
 */

public class WeiXinHot implements Parcelable{
    private String code;
    private String msg;
    private List<NewsList> newslist;

    protected WeiXinHot(Parcel in) {
        code = in.readString();
        msg = in.readString();
    }

    public static final Creator<WeiXinHot> CREATOR = new Creator<WeiXinHot>() {
        @Override
        public WeiXinHot createFromParcel(Parcel in) {
            return new WeiXinHot(in);
        }

        @Override
        public WeiXinHot[] newArray(int size) {
            return new WeiXinHot[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(msg);
    }


    public class NewsList {
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;


        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewsList> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewsList> newslist) {
        this.newslist = newslist;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeiXinHot weiXinHot = (WeiXinHot) o;

        if (code != null ? !code.equals(weiXinHot.code) : weiXinHot.code != null) return false;
        if (msg != null ? !msg.equals(weiXinHot.msg) : weiXinHot.msg != null) return false;
        return newslist != null ? newslist.equals(weiXinHot.newslist) : weiXinHot.newslist == null;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (newslist != null ? newslist.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeiXinHot{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", newslist=" + newslist +
                '}';
    }


}
