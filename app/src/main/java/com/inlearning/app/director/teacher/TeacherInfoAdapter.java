package com.inlearning.app.director.teacher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Teacher;

import java.util.List;

public class TeacherInfoAdapter extends RecyclerView.Adapter<TeacherInfoAdapter.ViewHolder> {
    private List<Teacher> mTeacherList;

    public TeacherInfoAdapter(List<Teacher> teacherList) {
        mTeacherList = teacherList;
    }

    public interface ClickListener {
        void onClick(Teacher tea);
    }

    ClickListener mClickListener;

    public TeacherInfoAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_director_teacher_info,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Teacher teacher = mTeacherList.get(i);
        viewHolder.mTeaName.setText(teacher.getName());
        viewHolder.mTeaTitle.setText(teacher.getTitle());
        viewHolder.mTeaJobNumber.setText(teacher.getJobNumber());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onClick(teacher);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTeacherList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTeaName;
        private TextView mTeaTitle;
        private TextView mTeaJobNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTeaName = itemView.findViewById(R.id.director_teacher_name);
            mTeaTitle = itemView.findViewById(R.id.director_teacher_title);
            mTeaJobNumber = itemView.findViewById(R.id.director_teacher_job_number);
        }
    }
}
