package com.inlearning.app.director.person.coursemanager.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Teacher;

import java.util.List;

public class ClassTeaAdapter extends RecyclerView.Adapter<ClassTeaAdapter.ViewHolder> {

    private List<ClassSchedule> mClassSchedules;
    private Context mContext;
    public interface ClickListener {
        void onClick(ClassSchedule schedule);

    }

    private ClickListener mClickListener;

    public ClassTeaAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public ClassTeaAdapter(List<ClassSchedule> courseTeaProxies) {
        mClassSchedules = courseTeaProxies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_teacher, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ClassSchedule bean = mClassSchedules.get(i);
        final Course2 course = bean.getCourse2();
        viewHolder.mCourseName.setText(course.getName());
        viewHolder.mCourseTime.setText(String.format("学时:%s",course.getTime()));
        viewHolder.mCourseScore.setText(String.format("学分:%s",course.getScore()));
        viewHolder.mCourseType.setText(course.getType());

        Teacher teacher = bean.getTeacher();
        viewHolder.mTeaName.setText(teacher.getName());
        viewHolder.mTeaTitle.setText(teacher.getTitle());
        viewHolder.mTeaJobNumber.setText(teacher.getAccount());
        if (TextUtils.isEmpty(teacher.getProfilePhotoUrl())) {
            viewHolder.mTeaIconView.setBackgroundDrawable(mContext.getDrawable(R.drawable.viewpage_guide_3));
        } else {
            Glide.with(viewHolder.itemView.getContext()).load(teacher.getProfilePhotoUrl()).into(viewHolder.mTeaIconView);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick(bean);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mClassSchedules.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseTime;
        private TextView mCourseName;
        private TextView mCourseType;
        private TextView mCourseScore;
        private TextView mTeaName;
        private TextView mTeaTitle;
        private TextView mTeaJobNumber;
        private ImageView mTeaIconView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCourseName = itemView.findViewById(R.id.tv_course_name);
            mCourseTime = itemView.findViewById(R.id.tv_course_time);
            mCourseType = itemView.findViewById(R.id.tv_course_type);
            mCourseScore = itemView.findViewById(R.id.tv_course_score);
            mTeaName = itemView.findViewById(R.id.director_teacher_name);
            mTeaTitle = itemView.findViewById(R.id.director_teacher_title);
            mTeaJobNumber = itemView.findViewById(R.id.director_teacher_job_number);
            mTeaIconView = itemView.findViewById(R.id.course_teacher_icon);
        }
    }
}
