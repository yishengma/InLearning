package com.inlearning.app.student.course;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Teacher;

import java.util.List;

public class CourseInfoAdapter extends RecyclerView.Adapter<CourseInfoAdapter.ViewHolder> {
    private List<ClassSchedule> mClassSchedules;

    public CourseInfoAdapter(List<ClassSchedule> classSchedules) {
        mClassSchedules = classSchedules;
    }

    public interface ClickListener {
        void onClick(ClassSchedule schedule);
    }

    private ClickListener mClickListener;

    public CourseInfoAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student_course_info, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ClassSchedule schedule = mClassSchedules.get(i);
        Course2 course = schedule.getCourse2();
        Teacher teacher = schedule.getTeacher();
        viewHolder.mCourseNameView.setText(course.getName());
        viewHolder.mCourseTimeView.setText(course.getTime());
        viewHolder.mCourseTypeView.setText(course.getType());
        viewHolder.mTeaNameView.setText(teacher.getName());
        viewHolder.mTeaTitleView.setText(teacher.getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick(schedule);
                }
            }
        });
        if (TextUtils.isEmpty(teacher.getProfilePhotoUrl())) {
            String name = teacher.getName();
            if (name.length() >= 2) {
                name = name.substring(name.length() - 2);
            }
            viewHolder.mTeaIconTextView.setText(name);
            viewHolder.mTeaIconTextView.setVisibility(View.VISIBLE);
            viewHolder.mTeaIconView.setImageResource(R.drawable.icon_common_image);
        } else {
            viewHolder.mTeaIconTextView.setVisibility(View.GONE);
            Glide.with(viewHolder.itemView.getContext()).load(teacher.getProfilePhotoUrl()).into(viewHolder.mTeaIconView);
        }
    }

    @Override
    public int getItemCount() {
        return mClassSchedules.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseNameView;
        private TextView mCourseTimeView;
        private TextView mCourseTypeView;
        private ImageView mTeaIconView;
        private TextView mTeaNameView;
        private TextView mTeaTitleView;
        private TextView mStuCountView;
        private ImageView mCourseIconView;
        private TextView mTeaIconTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCourseNameView = itemView.findViewById(R.id.tv_course_name);
            mCourseTimeView = itemView.findViewById(R.id.tv_course_time);
            mCourseTypeView = itemView.findViewById(R.id.tv_course_type);
            mTeaIconView = itemView.findViewById(R.id.imv_teacher_icon);
            mTeaNameView = itemView.findViewById(R.id.tv_teacher_name);
            mTeaTitleView = itemView.findViewById(R.id.tv_teacher_title);
            mStuCountView = itemView.findViewById(R.id.tv_student_count);
            mCourseIconView = itemView.findViewById(R.id.imv_course_icon);
            mTeaIconTextView = itemView.findViewById(R.id.imv_teacher_text);
        }
    }
}
