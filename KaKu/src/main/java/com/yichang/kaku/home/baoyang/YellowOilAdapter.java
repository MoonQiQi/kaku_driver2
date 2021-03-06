package com.yichang.kaku.home.baoyang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.YellowOilObj;

import java.util.List;

public class YellowOilAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<YellowOilObj> list;
    private Context mContext;

    public YellowOilAdapter(Context context, List<YellowOilObj> list) {
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
        YellowOilObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_yellowoil, null);
            holder.iv_yellowitem_nike = (ImageView) convertView.findViewById(R.id.iv_yellowitem_nike);
            holder.tv_yellowitem_1 = (TextView) convertView.findViewById(R.id.tv_yellowitem_1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ("Y".equals(obj.getFlag_check())) {
            holder.iv_yellowitem_nike.setImageResource(R.drawable.check_yuan);
        } else {
            holder.iv_yellowitem_nike.setImageResource(R.drawable.uncheck_yuan);
        }
        holder.tv_yellowitem_1.setText(obj.getName_service());

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_yellowitem_nike;
        private TextView tv_yellowitem_1;

    }
}