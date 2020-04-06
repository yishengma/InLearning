package com.inlearning.app.teacher.classes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.teacher.classes.coursetask.ChapterActivity;

import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends BaseFragment {

    private RecyclerView mRvClassView;
    private List<ClassScheduleProxy> mProxies;
    private ClassInfoAdapter mClassInfoAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_class, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mRvClassView = view.findViewById(R.id.rv_class);
        mRvClassView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mProxies = new ArrayList<>();
        mClassInfoAdapter = new ClassInfoAdapter(mProxies);
        mRvClassView.setAdapter(mClassInfoAdapter);
        mClassInfoAdapter.setClickListener(new ClassInfoAdapter.ClickListener() {
            @Override
            public void onClick(ClassScheduleProxy proxy) {
                ChapterActivity.startActivity(getContext(),proxy.getSchedule());
            }
        });
    }

    private void initData() {
        ClassModel.getClassSchedule(new ClassModel.Callback<List<ClassScheduleProxy>>() {
            @Override
            public void onResult(final List<ClassScheduleProxy> proxies) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mProxies.clear();
                        mProxies.addAll(proxies);
                        mClassInfoAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
