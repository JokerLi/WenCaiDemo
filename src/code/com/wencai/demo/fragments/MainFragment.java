package com.wencai.demo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.wencai.demo.R;
import com.wencai.demo.fragments.adapters.MainListAdapter;

/**
 * Created by Li Guoqing on 2016/12/6.
 */
public class MainFragment extends Fragment {
    private ExpandableListView mExpandableListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.main_content);
        mExpandableListView.setAdapter(new MainListAdapter(getContext()));
        return view;
    }
}
