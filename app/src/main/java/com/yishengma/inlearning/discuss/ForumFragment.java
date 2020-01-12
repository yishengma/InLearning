package com.yishengma.inlearning.discuss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.home.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends BaseFragment {
    private RecyclerView mRvForum;
    private ForumAdapter mForumAdapter;
    private List<String> mStringList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        mRvForum = view.findViewById(R.id.rv_forum);
        mRvForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mStringList = new ArrayList<>();
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mStringList.add("");
        mForumAdapter = new ForumAdapter(mStringList);
        mRvForum.setAdapter(mForumAdapter);
        return view;
    }
}
