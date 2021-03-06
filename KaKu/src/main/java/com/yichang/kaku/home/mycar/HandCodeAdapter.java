package com.yichang.kaku.home.mycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;

import java.util.List;

public class HandCodeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> list;
    private Context mContext;

    public HandCodeAdapter(Context context, List<String> list) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.mContext = context;
        if (mInflater == null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list != null && !list.isEmpty() ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        String obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_step4, null);

            holder.tv_item_chexi = (TextView) convertView.findViewById(R.id.tv_item_chexi);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_item_chexi.setText(obj);

        return convertView;
    }

    class ViewHolder {

        TextView tv_item_chexi;
    }
}