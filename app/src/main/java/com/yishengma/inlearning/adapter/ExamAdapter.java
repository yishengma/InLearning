package com.yishengma.inlearning.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yishengma.inlearning.R;


import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {

    private List<String> mStringList;

    public ExamAdapter(List<String> stringList) {
        mStringList = stringList;
    }

    @NonNull
    @Override
    public ExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task_homework_view, viewGroup, false);
        return new ExamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}