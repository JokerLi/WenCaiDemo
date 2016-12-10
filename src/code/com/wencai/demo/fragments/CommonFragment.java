package com.wencai.demo.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wencai.demo.R;

/**
 * Created by Li Guoqing on 2016/12/6.
 */
public class CommonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = new FrameLayout(getActivity());
        view.setBackgroundColor(Color.parseColor("#ffffff"));
        return view;
    }
}
