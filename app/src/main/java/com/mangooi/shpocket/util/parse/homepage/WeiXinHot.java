package com.mangooi.shpocket.util.parse.homepage;

import java.util.List;

/**
 * 解析微信热门API返回的Json数据的JavaBean
 * Created by mangooi on 2016/10/22 - 14:57
 */

public class WeiXinHot {
    private String code;
    private String msg;
    private List<NewsList> newslist;

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
}
