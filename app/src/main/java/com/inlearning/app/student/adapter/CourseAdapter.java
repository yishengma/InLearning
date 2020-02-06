package com.inlearning.app.student.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.inlearning.app.R;
import com.inlearning.app.student.bean.CourseBean;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<CourseBean> mCourseTasks;
    private OnClickListener mOnClickListener;

    public CourseAdapter(List<CourseBean> lessonTasks) {
        mCourseTasks = lessonTasks;
    }

    public interface OnClickListener {
        void onClick(CourseBean task);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_class_lession, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(mCourseTasks.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCourseTasks == null) {
            return 0;
        }
        return mCourseTasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseNameView;
        private TextView mCourseIconView;
        private TextView mCourseStateView;
        private ImageView mTeacherIconView;
        private TextView mTeacherNameView;
        private TextView mProgressTextView;
        private ProgressBar mCourseProgressView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCourseNameView = itemView.findViewById(R.id.course_name);
            mCourseIconView = itemView.findViewById(R.id.course_icon);
            mCourseStateView = itemView.findViewById(R.id.course_state);
            mTeacherIconView = itemView.findViewById(R.id.course_teacher_icon);
            mTeacherNameView = itemView.findViewById(R.id.course_teacher_name);
            mProgressTextView = itemView.findViewById(R.id.course_progress_text);
            mCourseProgressView = itemView.findViewById(R.id.course_study_progress);

        }
    }
}
