package com.mangooi.shpocket.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mangooi.shpocket.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/16.
 */

public class HomePage extends Fragment{


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_homepage,container,false);
        ButterKnife.bind(this,view);


        return view;
    }
}
