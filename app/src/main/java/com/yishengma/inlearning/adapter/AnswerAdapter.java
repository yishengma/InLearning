package com.yishengma.inlearning.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.bean.AnswerBean;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    private List<AnswerBean> mAnswerBeanList;

    public AnswerAdapter(List<AnswerBean> answerBeanList) {
        mAnswerBeanList = answerBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_answer, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mAnswerBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
