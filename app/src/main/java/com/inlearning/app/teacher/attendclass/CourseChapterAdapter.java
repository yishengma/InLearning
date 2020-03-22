package com.inlearning.app.teacher.attendclass;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;

import java.util.List;

public class CourseChapterAdapter extends RecyclerView.Adapter<CourseChapterAdapter.ViewHolder> {
    private List<CourseChapter> mCourseChapters;

    public CourseChapterAdapter(List<CourseChapter> lessonTasks) {
        mCourseChapters = lessonTasks;
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(CourseChapter chapter);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_teacher_course_chapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CourseChapter chapter = mCourseChapters.get(i);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(chapter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCourseChapters == null) {
            return 0;
        }
        return mCourseChapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
