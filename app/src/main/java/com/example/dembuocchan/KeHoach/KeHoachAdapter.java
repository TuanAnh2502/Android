package com.example.dembuocchan.KeHoach;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dembuocchan.ChiTietKeHoachActivity;
import com.example.dembuocchan.DanhgiaActivity;
import com.example.dembuocchan.KeHoachHelper;
import com.example.dembuocchan.R;

import java.util.List;

public class KeHoachAdapter extends RecyclerView.Adapter<KeHoachAdapter.KeHoachViewHolder> {

    private List<KeHoach> list;
    private OnClickKehoachListener onClickKehoachListener;

    public KeHoachAdapter(List<KeHoach> list,OnClickKehoachListener onClickKehoachListener) {
        this.list = list;
        this.onClickKehoachListener = onClickKehoachListener;
    }

    public void setData(List<KeHoach> list) {
        this.list = list;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public KeHoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kehoach, parent, false);
        return new KeHoachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeHoachViewHolder holder, int position) {
        KeHoach planner = list.get(position);
        if (planner == null) {
            return;
        }
        holder.imgPlanner.setImageResource(planner.getResourceId());
        holder.tvTitle.setText(planner.getTitle());
        holder.tvLevel.setText(planner.getLevel());
        holder.tvTime.setText(planner.getTime());

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 onClickKehoachListener.onClick(planner);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class KeHoachViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPlanner;
        private TextView tvTitle;
        private TextView tvLevel;
        private TextView tvTime;
        private CardView layout_item;

        public KeHoachViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_item = itemView.findViewById(R.id.layout_item);
            imgPlanner = itemView.findViewById(R.id.image_planner);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvLevel = itemView.findViewById(R.id.tv_level);
            tvTime = itemView.findViewById(R.id.tv_time);
        }


    }
}