package com.inlearning.app.director.teacher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.Teacher;

import java.util.ArrayList;
import java.util.List;


public class TeacherFragment extends BaseFragment {
    private RecyclerView mRvTeacherInfo;
    private List<Teacher> mTeacherList;
    private TeacherInfoAdapter mTeacherInfoAdapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_teacher, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvTeacherInfo = view.findViewById(R.id.rv_content);
        mRvTeacherInfo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mTeacherList = new ArrayList<>();
        mTeacherList.add(new Teacher());
        mTeacherList.add(new Teacher());
        mTeacherList.add(new Teacher());
        mTeacherList.add(new Teacher());
        mTeacherList.add(new Teacher());
        mTeacherList.add(new Teacher());
        mTeacherList.add(new Teacher());
        mTeacherList.add(new Teacher());
        mTeacherList.add(new Teacher());
        mTeacherInfoAdapter = new TeacherInfoAdapter(mTeacherList);
        mRvTeacherInfo.setAdapter(mTeacherInfoAdapter);
    }
}
