package com.mangooi.shpocket.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mangooi.shpocket.R;
import com.mangooi.shpocket.util.parse.homepage.WeiXinHot;

import java.util.List;


/**
 * Created by Administrator on 2016/10/21.
 */

public class HPAdapter extends RecyclerView.Adapter<HPAdapter.MyViewHolder>{


    private Context mContext;
    private List<WeiXinHot.NewsList> newsLists;
    private List<Bitmap> bitmaps;
    private List<String> briefs;


    public HPAdapter(Context mContext, List<WeiXinHot.NewsList> newsLists, List<Bitmap> bitmaps, List<String> briefs) {
        this.mContext = mContext;
        this.newsLists = newsLists;
        this.bitmaps = bitmaps;
        this.briefs = briefs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_homepage,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvImage.setBackground(new BitmapDrawable(bitmaps.get(position)));
        holder.tvBrief.setText(briefs.get(position));

    }

    @Override
    public int getItemCount() {
        return newsLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvImage;
        TextView tvBrief;
        MyViewHolder(View itemView) {
            super(itemView);
            tvImage= (TextView) itemView.findViewById(R.id.id_homepage_item_image);
            tvBrief= (TextView) itemView.findViewById(R.id.id_homepage_item_brief);
        }
    }
}
