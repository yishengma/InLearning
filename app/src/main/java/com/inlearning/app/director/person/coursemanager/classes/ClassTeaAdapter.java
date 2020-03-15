package com.inlearning.app.director.person.coursemanager.classes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Teacher;

import java.util.List;

public class ClassTeaAdapter extends RecyclerView.Adapter<ClassTeaAdapter.ViewHolder> {

    private List<ClassScheduletBean> mScheduletBeans;

    public interface ClickListener {
        void onClick(Course2 course);

    }

    private ClickListener mClickListener;

    public ClassTeaAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public ClassTeaAdapter(List<ClassScheduletBean> scheduletBeans) {
        mScheduletBeans = scheduletBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_teacher, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ClassScheduletBean bean = mScheduletBeans.get(i);
        final Course2 course = bean.getClassSchedule().getCourse2();
        viewHolder.mCourseName.setText(course.getName());
        viewHolder.mCourseTime.setText(course.getTime());
        viewHolder.mCourseScore.setText(course.getScore());
        viewHolder.mCourseType.setText(course.getType());

        Teacher teacher = bean.getClassSchedule().getTeacher();
        if (teacher == null) {
            viewHolder.mEmptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onClick(course);
                    }
                }
            });

            return;
        }
        viewHolder.mTeaName.setText(teacher.getName());
        viewHolder.mTeaTitle.setText(teacher.getTitle());
        viewHolder.mTeaJobNumber.setText(teacher.getJobNumber());
    }

    @Override
    public int getItemCount() {
        return mScheduletBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseTime;
        private TextView mCourseName;
        private TextView mCourseType;
        private TextView mCourseScore;
        private TextView mTeaName;
        private TextView mTeaTitle;
        private TextView mTeaJobNumber;
        private TextView mEmptyView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCourseName = itemView.findViewById(R.id.tv_course_name);
            mCourseTime = itemView.findViewById(R.id.tv_course_time);
            mCourseType = itemView.findViewById(R.id.tv_course_type);
            mCourseScore = itemView.findViewById(R.id.tv_course_score);
            mTeaName = itemView.findViewById(R.id.director_teacher_name);
            mTeaTitle = itemView.findViewById(R.id.director_teacher_title);
            mTeaJobNumber = itemView.findViewById(R.id.director_teacher_job_number);
            mEmptyView = itemView.findViewById(R.id.tv_empty);
        }
    }
}
