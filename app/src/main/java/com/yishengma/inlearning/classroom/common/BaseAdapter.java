package com.yishengma.inlearning.classroom.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yishengma.inlearning.R;

public abstract class BaseAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task_common_view, viewGroup, false);
        return new BaseAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mTaskContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTaskContainer = itemView.findViewById(R.id.container_view);
        }
    }
}
