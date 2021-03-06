package com.yichang.kaku.home.baoyang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.BrandBigOilObj;

import java.util.List;

public class BaoYangHuoDongHotAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BrandBigOilObj> list;
    private Context mContext;

    public BaoYangHuoDongHotAdapter(Context context, List<BrandBigOilObj> list) {
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
        BrandBigOilObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_sousuohot, null);
            holder.tv_item_sousuohot = (TextView) convertView.findViewById(R.id.tv_item_sousuohot);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_item_sousuohot.setText(obj.getName_brand());

        return convertView;
    }

    class ViewHolder {

        private TextView tv_item_sousuohot;

    }
}