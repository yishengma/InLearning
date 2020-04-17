package com.inlearning.app.teacher.classes.coursetask.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Student;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoAdapter.ViewHolder> {

    private List<StuListView.StudentProxy> mStudentList;
    private Context mContext;

    public StudentInfoAdapter(List<StuListView.StudentProxy> studentList) {
        mStudentList = studentList;
    }

    private ClickListener mClickListener;

    public StudentInfoAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public interface ClickListener {
        void onClick(Student student);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_teacher_task_stu_info, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        StuListView.StudentProxy proxy = mStudentList.get(i);
        final Student student = proxy.getStudent();
        viewHolder.mNumberView.setText(String.valueOf(student.getAccount()));
        viewHolder.mNameView.setText(String.valueOf(student.getName()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onClick(student);
                }
            }
        });
        if (!TextUtils.isEmpty(student.getProfilePhotoUrl())) {
            viewHolder.mImageTextView.setVisibility(View.GONE);
            Glide.with(mContext).load(student.getProfilePhotoUrl()).into(viewHolder.mImageView);
        } else {
            String name = student.getName();
            if (name.length() >= 2) {
                name = name.substring(name.length() - 2);
            }
            viewHolder.mImageTextView.setText(name);
            viewHolder.mImageTextView.setVisibility(View.VISIBLE);
            viewHolder.mImageView.setImageResource(R.drawable.icon_common_image);
        }
        viewHolder.mVideoStateView.setText(proxy.isVideoDone() ? "已完成" : "未完成");
        viewHolder.mVideoStateView.setTextColor(proxy.isVideoDone() ? mContext.getColor(R.color.app_global_blue) : mContext.getColor(R.color.app_global_red));

        viewHolder.mHomeworkStateView.setText(proxy.isHomeworkDone() ? "已完成" : "未完成");
        viewHolder.mHomeworkStateView.setTextColor(proxy.isHomeworkDone() ? mContext.getColor(R.color.app_global_blue) : mContext.getColor(R.color.app_global_red));

    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImageView;
        private TextView mNameView;
        private TextView mNumberView;
        private TextView mVideoStateView;
        private TextView mHomeworkStateView;
        private TextView mImageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imv_student_image);
            mNameView = itemView.findViewById(R.id.tv_student_name);
            mNumberView = itemView.findViewById(R.id.tv_student_number);
            mVideoStateView = itemView.findViewById(R.id.tv_video_state);
            mHomeworkStateView = itemView.findViewById(R.id.tv_homework_state);
            mImageTextView = itemView.findViewById(R.id.imv_student_text);

        }
    }
}
