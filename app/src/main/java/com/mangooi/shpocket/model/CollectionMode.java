package com.mangooi.shpocket.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * values.put("pic",data.getPicUrl());
 values.put("brief",data.getDescription());
 values.put("title",data.getTitle());
 values.put("time",data.getCtime());
 * Created by Administrator on 2016/10/31.
 */

public class CollectionMode implements Parcelable{

    private String picUrl;
    private String brief;
    private String title;
    private String time;
    private String url;

    public CollectionMode(){}

    public CollectionMode(String picUrl, String brief, String title, String time, String url) {
        this.picUrl = picUrl;
        this.brief = brief;
        this.title = title;
        this.time = time;
        this.url = url;
    }

    protected CollectionMode(Parcel in) {
        picUrl = in.readString();
        brief = in.readString();
        title = in.readString();
        time = in.readString();
        url = in.readString();
    }

    public static final Creator<CollectionMode> CREATOR = new Creator<CollectionMode>() {
        @Override
        public CollectionMode createFromParcel(Parcel in) {
            return new CollectionMode(in);
        }

        @Override
        public CollectionMode[] newArray(int size) {
            return new CollectionMode[size];
        }
    };

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectionMode that = (CollectionMode) o;

        if (picUrl != null ? !picUrl.equals(that.picUrl) : that.picUrl != null) return false;
        if (brief != null ? !brief.equals(that.brief) : that.brief != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        int result = picUrl != null ? picUrl.hashCode() : 0;
        result = 31 * result + (brief != null ? brief.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picUrl);
        dest.writeString(brief);
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(url);
    }

    @Override
    public String toString() {
        return "CollectionMode{" +
                "picUrl='" + picUrl + '\'' +
                ", brief='" + brief + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
