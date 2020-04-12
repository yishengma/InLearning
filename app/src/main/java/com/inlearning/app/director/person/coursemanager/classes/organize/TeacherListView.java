package com.inlearning.app.director.person.coursemanager.classes.organize;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.director.teacher.TeacherInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeacherListView extends RelativeLayout {

    public TeacherListView(Context context) {
        this(context, null);
    }

    public TeacherListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TeacherListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(LayoutInflater.from(getContext()).inflate(R.layout.view_tea_list, this));
    }

    private RecyclerView mRvTeacherInfo;
    private List<Teacher> mTeacherList;
    private TeacherInfoAdapter mTeacherInfoAdapter;
    private ImageView mBackView;
    private TextView mEmptyView;

    public interface ClickListener {
        void onClick(Teacher teacher);
        void onBack();
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    private void initView(View view) {
        mBackView = view.findViewById(R.id.imv_bar_back);
        mRvTeacherInfo = view.findViewById(R.id.rv_content);
        mRvTeacherInfo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mTeacherList = new ArrayList<>();
        mTeacherInfoAdapter = new com.inlearning.app.director.teacher.TeacherInfoAdapter(mTeacherList);
        mRvTeacherInfo.setAdapter(mTeacherInfoAdapter);
        mTeacherInfoAdapter.setClickListener(new TeacherInfoAdapter.ClickListener() {
            @Override
            public void onClick(Teacher tea) {
                if (mClickListener != null) {
                    mClickListener.onClick(tea);
                }
            }

            @Override
            public void onLongClick(View view, float x, float y, Teacher tea) {

            }
        });
        mBackView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onBack();
                }
            }
        });
        mEmptyView = view.findViewById(R.id.tv_empty);

    }

    public void updateList(List<Teacher> teachers) {
        mTeacherList.clear();
        mTeacherList.addAll(teachers);
        mTeacherInfoAdapter.notifyDataSetChanged();
        mEmptyView.setVisibility(mTeacherList.isEmpty()?VISIBLE:GONE);
    }
}