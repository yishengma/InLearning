package com.yishengma.inlearning.classroom.lessontask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yishengma.inlearning.R;

import java.util.List;

public class LessonTaskAdapter extends RecyclerView.Adapter<LessonTaskAdapter.ViewHolder> {
    private List<ChapterTask> mLessonTasks;

    public LessonTaskAdapter(List<ChapterTask> lessonTasks) {
        mLessonTasks = lessonTasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lesson_task, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        if (mLessonTasks == null) {
            return 0;
        }
        return mLessonTasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
