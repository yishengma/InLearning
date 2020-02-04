package com.inlearning.student.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.student.R;
import com.inlearning.student.adapter.ForumAdapter;
import com.inlearning.student.bean.QuestionBean;
import com.inlearning.student.ui.QuestionActivity;

import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends BaseFragment {
    private RecyclerView mRvForum;
    private ForumAdapter mForumAdapter;
    private List<QuestionBean> mQuestionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        mRvForum = view.findViewById(R.id.rv_forum);
        mRvForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mQuestionList = new ArrayList<>();
        mQuestionList.add(new QuestionBean());
        mQuestionList.add(new QuestionBean());
        mQuestionList.add(new QuestionBean());
        mQuestionList.add(new QuestionBean());
        mQuestionList.add(new QuestionBean());
        mQuestionList.add(new QuestionBean());
        mQuestionList.add(new QuestionBean());
        mQuestionList.add(new QuestionBean());
        mForumAdapter = new ForumAdapter(mQuestionList);
        mRvForum.setAdapter(mForumAdapter);
        mForumAdapter.setClickListener(new ForumAdapter.ClickListener() {
            @Override
            public void onClick(QuestionBean question) {
                QuestionActivity.startActivity(getContext());
            }
        });
        return view;
    }
}
