package com.inlearning.app.teacher.attendclass;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;

import java.util.List;

public class CourseInfoAdapter extends  RecyclerView.Adapter<CourseInfoAdapter.ViewHolder>{
    private List<Course2> mCourseList;
    private boolean mIsImport;

    public CourseInfoAdapter(List<Course2> courseList) {
        mCourseList = courseList;
    }

    public CourseInfoAdapter setImport(boolean isImport) {
        mIsImport = isImport;
        return this;
    }

    public interface ClickListener {
        void onClick(Course2 course);
    }

    private ClickListener mClickListener;

    public CourseInfoAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_director_course_info,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Course2 course = mCourseList.get(i);
        viewHolder.mSelectView.setVisibility(mIsImport?View.VISIBLE:View.GONE);
        viewHolder.mSelectView.setChecked(course.isSelected());
        viewHolder.mSelectView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                course.setSelected(b);
            }
        });
        viewHolder.mCourseName.setText(course.getName());
        viewHolder.mCourseTime.setText(course.getTime());
        viewHolder.mCourseScore.setText(course.getScore());
        viewHolder.mCourseType.setText(course.getType());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onClick(course);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseTime;
        private TextView mCourseName;
        private TextView mCourseType;
        private TextView mCourseScore;
        private CheckBox mSelectView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCourseName = itemView.findViewById(R.id.tv_course_name);
            mCourseTime = itemView.findViewById(R.id.tv_course_time);
            mCourseType = itemView.findViewById(R.id.tv_course_type);
            mCourseScore = itemView.findViewById(R.id.tv_course_score);
            mSelectView = itemView.findViewById(R.id.btn_select);
        }
    }
}
