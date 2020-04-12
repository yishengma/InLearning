package com.inlearning.app.director.person.coursemanager.speciality;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.director.DirectorAppRuntime;

import java.util.ArrayList;
import java.util.List;

public class SpecialityCourseView extends LinearLayout implements View.OnClickListener {

    public SpecialityCourseView(@NonNull Context context) {
        this(context, null);
    }

    public SpecialityCourseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecialityCourseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private RecyclerView mRvSpecialityView;
    private SpecialityCourseAdapter mSpecialityCourseAdapter;
    private List<Speciality> mSpecialities;
    private ImageView mBackView;
    private TextView mEmptyView;

    public interface ClickListener {
        void onBack();
    }


    private ClickListener mClickListener;

    public SpecialityCourseView setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    private void initView() {
        mSpecialities = new ArrayList<>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.laout_speciality_course_manager, this);
        mBackView = view.findViewById(R.id.imv_bar_back);
        mRvSpecialityView = view.findViewById(R.id.rv_speciality);
        mRvSpecialityView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mSpecialityCourseAdapter = new SpecialityCourseAdapter(mSpecialities);
        mRvSpecialityView.setAdapter(mSpecialityCourseAdapter);
        mBackView.setOnClickListener(this);
        mSpecialityCourseAdapter.setClickListener(new SpecialityCourseAdapter.ClickListener() {
            @Override
            public void onClick(Speciality speciality) {
                SpecialityScheduleActivity.startScheduleActivity(getContext(), speciality);
            }
        });
        mEmptyView = view.findViewById(R.id.tv_empty);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_back:
                mClickListener.onBack();
                break;
        }
    }

    public void show() {
        setVisibility(VISIBLE);
        mSpecialities.clear();
        mSpecialities.addAll(DirectorAppRuntime.getSpecialities());
        mSpecialityCourseAdapter.notifyDataSetChanged();
        mEmptyView.setVisibility(mSpecialities.isEmpty() ? VISIBLE : GONE);
    }

    public void hide() {
        setVisibility(GONE);
    }
}
