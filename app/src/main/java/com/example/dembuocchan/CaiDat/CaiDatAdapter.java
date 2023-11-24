package com.example.dembuocchan.CaiDat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dembuocchan.R;

import java.util.List;

public class CaiDatAdapter extends BaseAdapter {
    private List<CaiDat> caiDatList;
    private Context context;

    public CaiDatAdapter(List<CaiDat> caiDatList, Context context) {
        this.caiDatList = caiDatList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return caiDatList.size();
    }

    @Override
    public Object getItem(int position) {
        return caiDatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_caidat, parent, false);

            holder = new ViewHolder();
            holder.iconImageView = convertView.findViewById(R.id.iconImageView);
            holder.titleTextView = convertView.findViewById(R.id.titleTextView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CaiDat item = caiDatList.get(position);

        holder.iconImageView.setImageResource(item.getIcon());
        holder.titleTextView.setText(item.getTitle());
        return convertView;
    }
    private static class ViewHolder {
        ImageView iconImageView;
        TextView titleTextView;
    }
}
