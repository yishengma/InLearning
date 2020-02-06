package com.inlearning.app.student.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.app.R;

import java.util.List;
import android.widget.TextView;
import com.inlearning.app.student.bean.ChapterBean;
import com.inlearning.app.student.bean.CourseBean;

public class NoteInfoAdapter extends RecyclerView.Adapter<NoteInfoAdapter.CourseViewHolder> {
    private List<CourseBean> mCourseList;

    public NoteInfoAdapter(List<CourseBean> courseList) {
        mCourseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task_note_view, viewGroup, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder viewHolder, int i) {
        CourseBean bean = mCourseList.get(i);
        viewHolder.mCourseName.setText(bean.getName());
        CourseViewHolder.Adapter adapter = new CourseViewHolder.Adapter(bean.getChapters());
        viewHolder.mRvCourseChapter.setLayoutManager(new LinearLayoutManager(viewHolder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        viewHolder.mRvCourseChapter.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRvCourseChapter;
        private TextView mCourseName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            mRvCourseChapter = itemView.findViewById(R.id.rv_course_chapter);
            mCourseName = itemView.findViewById(R.id.tv_course_name);
        }

        public static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
            private List<ChapterBean> mChapterList;

            public Adapter(List<ChapterBean> mChapterList) {
                this.mChapterList = mChapterList;
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_note, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            }

            @Override
            public int getItemCount() {
                return mChapterList.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                }
            }
        }

    }
}
