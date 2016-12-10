package com.wencai.demo.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wencai.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Li Guoqing on 2016/12/10.
 */
public class MainListAdapter extends BaseExpandableListAdapter{
    private static final int INDEX_SCENE = 0;
    private static final int INDEX_DIVIDE = 1;
    private static final int INDEX_LISTVIEW = 2;

    private Context mContext;

    public MainListAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition == 0){
            return 1;
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //listview 复用,没写
        switch (groupPosition){
            case INDEX_SCENE:{
                //尽量使用X自定义控件
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.fragment_home_item_scene, null);
                ImageView imageViewRight = (ImageView)convertView.findViewById(R.id.item_scene_img_right);
                if(isExpanded){
                    //改变箭头样式的时候可以加动画,一个旋转消失,一个旋转出现
                    imageViewRight.setImageResource(R.mipmap.less);
                }else{
                    imageViewRight.setImageResource(R.mipmap.more);
                }
                break;
            }
            case INDEX_DIVIDE:{
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.fragment_home_item_divide, null);
                break;
            }
            case INDEX_LISTVIEW:{
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = getListView(inflater);
                break;
            }
        }
        return convertView;
    }

    private View getListView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home_item_list, null);
        ListView listView = (ListView)view.findViewById(R.id.item_list_lv);
        listView.setAdapter(new ListViewAdapter(mContext, getTestList()));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //listview复用没写,出来回去的动画
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.fragment_home_item_scene_child, null);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<ListViewDataBean> getTestList() {
        List<ListViewDataBean> list = new ArrayList<ListViewDataBean>();
        ListViewDataBean bean = new ListViewDataBean();
        bean.mType = ListViewAdapter.TYPE_TITLE;
        ListViewDataBean bean1 = new ListViewDataBean();
        bean1.mIconId = R.mipmap.bulldog;
        bean1.mTitle = "多功能网关";
        bean1.mDesc = "设备离线";
        ListViewDataBean bean2 = new ListViewDataBean();
        bean2.mIconId = R.mipmap.bulldog;
        bean2.mTitle = "多功能网关";
        bean2.mDesc = "设备离线";
        ListViewDataBean bean3 = new ListViewDataBean();
        bean3.mIconId = R.mipmap.bulldog;
        bean3.mTitle = "多功能网关";
        bean3.mDesc = "设备离线";
        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        return list;
    }

    public static class ListViewAdapter extends BaseAdapter{
        public static final int TYPE_TITLE = 0;
        public static final int TYPE_COMMONITEM = 1;

        private Context mContext;
        private List<ListViewDataBean> mDataList;
        public ListViewAdapter(Context context, List<ListViewDataBean> list) {
            mContext = context;
            mDataList = new ArrayList<ListViewDataBean>();
            if(list != null && !list.isEmpty()){
                mDataList.addAll(list);
            }
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //listview 复用,没写
            ListViewDataBean bean = mDataList.get(position);
            switch (bean.mType){
                case TYPE_TITLE:{
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    convertView = inflater.inflate(R.layout.fragment_home_item_list_title, null);
                    break;
                }
                case TYPE_COMMONITEM:{
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    convertView = inflater.inflate(R.layout.fragment_home_item_list_common, null);
                    TextView title = (TextView)convertView.findViewById(R.id.item_list_common_tv_title);
                    TextView desc = (TextView)convertView.findViewById(R.id.item_list_common_tv_desc);
                    ImageView image = (ImageView)convertView.findViewById(R.id.item_list_common_img);
                    title.setText(bean.mTitle);
                    desc.setText(bean.mDesc);
                    image.setImageResource(bean.mIconId);
                    break;
                }
            }
            return convertView;
        }
    }

    class ListViewDataBean{
        private int mType = ListViewAdapter.TYPE_COMMONITEM;
        private int mIconId;
        private String mTitle;
        private String mDesc;
    }
}
