package com.yishengma.inlearning.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yishengma.inlearning.R;
import com.yishengma.inlearning.bean.CourseBean;

import java.util.List;

public class RecentCourseAdapter extends RecyclerView.Adapter<RecentCourseAdapter.ViewHolder> {
    private List<CourseBean> mList;
    private OnClickListener mOnClickListener;

    public RecentCourseAdapter(List<CourseBean> list) {
        mList = list;
    }

    public interface OnClickListener {
        void onClick(CourseBean courseBean);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecentCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recent_course, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentCourseAdapter.ViewHolder viewHolder, int i) {
        final CourseBean course = mList.get(i);
        viewHolder.mCourseName.setText(course.getName());
        viewHolder.mCourseTime.setText(course.getTime());
        viewHolder.mCourseType.setText(course.getType());
        viewHolder.mCourseStuCount.setText(String.format("%d学生", course.getStudentCount()));
        //Glide.with(viewHolder.itemView).load(course.getIconUrl()).into(viewHolder.mCourseIcon);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(course);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseName;
        private TextView mCourseTime;
        private TextView mCourseType;
        private TextView mCourseStuCount;
        private ImageView mCourseIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCourseName = itemView.findViewById(R.id.tv_course_name);
            mCourseTime = itemView.findViewById(R.id.tv_course_time);
            mCourseType = itemView.findViewById(R.id.tv_course_type);
            mCourseIcon = itemView.findViewById(R.id.imv_course_icon);
            mCourseStuCount = itemView.findViewById(R.id.tv_student_count);
        }
    }
}
