package com.mangooi.shpocket.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mangooi.shpocket.R;
import com.mangooi.shpocket.model.CollectionMode;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */

public class CollectionAdapter extends BaseAdapter{


    private Context mContext;
    private List<CollectionMode> lists;
    private List<Bitmap> bitmaps;
    private LayoutInflater inflater;

    public CollectionAdapter(Context mContext, List<CollectionMode> lists , List<Bitmap> bitmaps) {
        this.mContext = mContext;
        this.lists = lists;
        this.bitmaps=bitmaps;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.item_collection,null);

        CollectionMode mode= (CollectionMode) getItem(position);

        TextView tvImage= (TextView) view.findViewById(R.id.id_collection_item_tv_image);
        tvImage.setBackground(new BitmapDrawable(bitmaps.get(position)));

        TextView tvTitle= (TextView) view.findViewById(R.id.id_collection_item_tv_title);
        tvTitle.setText(mode.getTitle());

        TextView tvBrief= (TextView) view.findViewById(R.id.id_collection_item_tv_brief);
        tvBrief.setText(mode.getBrief());

        TextView tvTime = (TextView) view.findViewById(R.id.id_collection_item_tv_time);
        tvTime.setText(mode.getTime());
        return view;
    }
}
