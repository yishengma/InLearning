package com.inlearning.app.director.teacher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.ArrayList;
import java.util.List;


public class TeacherFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView mRvTeacherInfo;
    private List<Teacher> mTeacherList;
    private TeacherInfoAdapter mTeacherInfoAdapter;
    private ImageView mAddView;
    private ImageView mSearchView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_teacher, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvTeacherInfo = view.findViewById(R.id.rv_content);
        mSearchView = view.findViewById(R.id.imv_search);
        mAddView = view.findViewById(R.id.imv_add);
        mRvTeacherInfo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mTeacherList = new ArrayList<>();
        mTeacherInfoAdapter = new TeacherInfoAdapter(mTeacherList);
        mRvTeacherInfo.setAdapter(mTeacherInfoAdapter);
        mTeacherInfoAdapter.setClickListener(new TeacherInfoAdapter.ClickListener() {
            @Override
            public void onClick(Teacher tea) {

            }
        });
        mAddView.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_search:
                break;
            case R.id.imv_add:
                TeacherEditActivity.startTeacherEditActivity(getContext());
                break;
            default:
                break;

        }
    }

    private void initData() {
        TeacherModel.getTeacherList(new TeacherModel.Callback<List<Teacher>>() {
            @Override
            public void onResult(boolean suc, List<Teacher> teachers) {
                if (suc) {
                    updateList(teachers);
                }
            }
        });
    }

    private void updateList(final List<Teacher> teachers) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mTeacherList.clear();
                mTeacherList.addAll(teachers);
                mTeacherInfoAdapter.notifyDataSetChanged();
            }
        });
    }
}
