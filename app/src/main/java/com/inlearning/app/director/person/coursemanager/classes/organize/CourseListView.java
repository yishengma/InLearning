package com.inlearning.app.director.person.coursemanager.classes.organize;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

public class CourseListView extends RelativeLayout {

    public CourseListView(Context context) {
        this(context, null);
    }

    public CourseListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(LayoutInflater.from(getContext()).inflate(R.layout.view_course_list, this));
    }

    private RecyclerView mRvCourseInfo;
    private List<Course2> mCourseList;
    private CourseInfoAdapter mCourseInfoAdapter;
    private ImageView mBackView;

    public interface ClickListener {
        void onClick(Course2 course);
        void onBack();
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    private void initView(View view) {
        mBackView = view.findViewById(R.id.imv_bar_back);
        mRvCourseInfo = view.findViewById(R.id.rv_content);
        mRvCourseInfo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mCourseList = new ArrayList<>();
        mCourseInfoAdapter = new CourseInfoAdapter(mCourseList);
        mRvCourseInfo.setAdapter(mCourseInfoAdapter);
        mCourseInfoAdapter.setClickListener(new CourseInfoAdapter.ClickListener() {
            @Override
            public void onClick(Course2 course) {
                if (mClickListener != null) {
                    mClickListener.onClick(course);
                }
            }

            @Override
            public void onLongClick(View view, float x, float y, Course2 course) {

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
    }

    public void updateList(List<Course2> course2s) {
        mCourseList.clear();
        mCourseList.addAll(course2s);
        mCourseInfoAdapter.notifyDataSetChanged();
    }

}
