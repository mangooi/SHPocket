package com.mangooi.shpocket.data;

/**
 *
 * Created by Administrator on 2016/10/21.
 */

public final class Constant {
    public static final String BAIDU_API_KEY="6231fdbc8bffad4948b409034ddc500c";
    public static final String WEIXIN_HOT_URL = "http://apis.baidu.com/txapi/weixin/wxhot";
    public static final String WEIXIN_HOT_ARG = "num=10&rand=1&page=1&src=%E4%BA%BA%E6%B0%91%E6%97%A5%E6%8A%A5";
    public static final String WEIXIN_HOT_ARG_FOOD="num=10&rand=1&page=1&word=%e7%be%8e%e9%a3%9f&src=%E4%BA%BA%E6%B0%91%E6%97%A5%E6%8A%A5";
    public static final String WEIXIN_HOT_ARG_SHOPPING="num=10&rand=1&page=1&word=%e8%b4%ad%e7%89%a9&src=%E4%BA%BA%E6%B0%91%E6%97%A5%E6%8A%A5";
    public static final String WEIXIN_HOT_ARG_FILM = "num=10&rand=1&page=1&word=%e7%94%b5%e5%bd%b1&src=%E4%BA%BA%E6%B0%91%E6%97%A5%E6%8A%A5";
    public static final String WEIXIN_HOT_ARG_SPOT = "num=10&rand=1&page=1&word=%e6%97%85%e6%b8%b8&src=%E4%BA%BA%E6%B0%91%E6%97%A5%E6%8A%A5";
    public static final String WEIXIN_HOT_ARG_EXHIBITION = "num=10&rand=1&page=1&word=%e5%b1%95%e8%a7%88&src=%E4%BA%BA%E6%B0%91%E6%97%A5%E6%8A%A5";
    public static final String WEIXIN_HOT_ARG_ACTIVITY = "num=10&rand=1&page=1&word=%e6%b4%bb%e5%8a%a8&src=%E4%BA%BA%E6%B0%91%E6%97%A5%E6%8A%A5";
    public static final String WEIXIN_HOT_ARG_HEAD="num=10&rand=1&page=1&word=";
    public static final String WEIXIN_HOT_ARG_END = "&src=%E4%BA%BA%E6%B0%91%E6%97%A5%E6%8A%A5";
    public static final String CREATE_TAL_COLLECTION ="create table collection(_id integer primary key autoincrement, pic text, title text , brief text, time text ,url text )";
    public static final String CREATE_TAL_POCKET ="create table pocket(_id integer primary key autoincrement, name text, address text, time text , price text ,uid text )";
    public static final String DATABASE_NAME = "SHPocketTest";
    public static final String COLLECTION_COLUMN_PIC="pic";
    public static final String COLLECTION_COLUMN_TITLE="title";
    public static final String COLLECTION_COLUMN_BRIEF="brief";
    public static final String COLLECTION_COLUMN_TIME="time";
    public static final String COLLECTION_COLUMN_URL="url";
}
