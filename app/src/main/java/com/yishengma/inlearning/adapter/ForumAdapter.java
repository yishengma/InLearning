package com.yishengma.inlearning.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.bean.QuestionBean;

import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
    private List<QuestionBean> mQuestionList;
    private ClickListener mClickListener;
    public ForumAdapter(List<QuestionBean> questionList) {
        mQuestionList = questionList;
    }

    public interface ClickListener {
        void onClick(QuestionBean question);
    }

    public ForumAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_forum, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       final QuestionBean questionBean = mQuestionList.get(i);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onClick(questionBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mQuestionList == null) {
            return 0;
        }
        return mQuestionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
