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
import com.inlearning.app.common.bean.ClassInfo;

import java.util.ArrayList;
import java.util.List;

public class ClassInfoFragment extends BaseFragment {
    private RecyclerView mRvClass;
    private List<ClassInfo> mClassList = new ArrayList<>();
    private ClassInfoAdapter mClassInfoAdapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_class_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvClass = view.findViewById(R.id.rv_content);
        mRvClass.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mClassInfoAdapter = new ClassInfoAdapter(mClassList);
        mRvClass.setAdapter(mClassInfoAdapter);
        mClassInfoAdapter.setClickListener(new ClassInfoAdapter.ClickListener() {
            @Override
            public void onClick(ClassInfo classInfo) {

            }
        });
    }
}
