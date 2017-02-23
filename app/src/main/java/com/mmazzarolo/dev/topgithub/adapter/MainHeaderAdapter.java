package com.mmazzarolo.dev.topgithub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmazzarolo.dev.topgithub.Navigator;
import com.mmazzarolo.dev.topgithub.R;
import com.mmazzarolo.dev.topgithub.model.MainHeaderItem;

import java.util.ArrayList;
import java.util.List;

/**
  * @desc:离线功能-列表头布局-表格适配器
  * @author：Arison on 2017/2/23
  */
public class MainHeaderAdapter extends BaseAdapter {

    private Context mContext;
    private List<MainHeaderItem> mDatas;

    public MainHeaderAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item_main_header, viewGroup, false);
        bindView(mDatas.get(i), view);
        bindClick(view, mDatas.get(i), i);
        return view;
    }

    private void bindClick(View view, MainHeaderItem item, int i) {
        view.setOnClickListener(view1 -> {
            switch (i) {
                case 0:
                    //Navigator.startSearchActivity(mContext);
                    break;
                case 1:
                    Navigator.startWebActivity(mContext, item.link);
                    break;
            }
        });
    }

    private void bindView(MainHeaderItem item, View view) {
        TextView textView = (TextView) view.findViewById(R.id.text_grid_item);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_grid_item);
        textView.setText(item.name);
        imageView.setImageResource(item.icon);
    }

    public void updateData(List<MainHeaderItem> items) {
        setData(items);
        notifyDataSetChanged();
    }

    public void setData(List<MainHeaderItem> items) {
        mDatas.clear();
        mDatas.addAll(items);
    }
}
