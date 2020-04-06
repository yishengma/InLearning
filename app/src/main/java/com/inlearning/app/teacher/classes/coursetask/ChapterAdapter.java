package com.inlearning.app.teacher.classes.coursetask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    private List<CourseChapter> mChapters;

    public ChapterAdapter(List<CourseChapter> chapters) {
        mChapters = chapters;
    }

    public interface ClickListener {
        void onClick(CourseChapter courseChapter);
    }

    private ClickListener mListener;

    public ChapterAdapter setListener(ClickListener listener) {
        mListener = listener;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_chapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final CourseChapter chapter = mChapters.get(i);
        viewHolder.mChapterNameView.setText(String.format("第%s节 %s", i + 1, chapter.getChapterName()));
        viewHolder.mChapterInfoView.setText(String.format("时间 %s", chapter.getCreatedAt().substring(0,11)));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(chapter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mChapterNameView;
        private TextView mChapterInfoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mChapterNameView = itemView.findViewById(R.id.tv_chapter_name);
            mChapterInfoView = itemView.findViewById(R.id.tv_chapter_info);
        }
    }
}
