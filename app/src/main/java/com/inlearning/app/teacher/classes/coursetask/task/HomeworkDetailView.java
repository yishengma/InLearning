package com.inlearning.app.teacher.classes.coursetask.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Answer;

import java.util.ArrayList;
import java.util.List;

public class HomeworkDetailView extends LinearLayout {

    public HomeworkDetailView(Context context) {
        this(context, null);
    }

    public HomeworkDetailView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeworkDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private RecyclerView mRvAnswer;
    private List<Answer> mAnswers;
    private AnswerAdapter mAnswerAdapter;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_homework_detail, this);
        mRvAnswer = view.findViewById(R.id.rv_content);
        mRvAnswer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAnswers = new ArrayList<>();
        mAnswerAdapter = new AnswerAdapter(mAnswers);
        mRvAnswer.setAdapter(mAnswerAdapter);
    }

    public void setAnswers(List<Answer> list) {
        setVisibility(VISIBLE);
        mAnswers.clear();
        mAnswers.addAll(list);
        mAnswerAdapter.notifyDataSetChanged();
        Log.e("ethan", "suzeL" + list.size());
    }


    public static class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
        private List<Answer> mAdapterAnswers;

        public AnswerAdapter(List<Answer> answers) {
            mAdapterAnswers = answers;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homework_response_answer, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Answer answer = mAdapterAnswers.get(i);
            Log.e("ethan", "" + answer.getImageUrl());
            Glide.with(viewHolder.itemView.getContext()).load(answer.getImageUrl()).into(viewHolder.mAnswerView);
        }

        @Override
        public int getItemCount() {
            return mAdapterAnswers.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView mAnswerView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mAnswerView = itemView.findViewById(R.id.imv_image);
            }
        }
    }
}
