package com.inlearning.app.director.speciality.classinfo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Student;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoAdapter.ViewHolder> {

    private List<Student> mStudentList;

    public StudentInfoAdapter(List<Student> studentList) {
        mStudentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_director_student_info, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Student student = mStudentList.get(i);
        viewHolder.mNumberView.setText(String.valueOf(student.getAccount()));
        viewHolder.mNameView.setText(String.valueOf(student.getName()));
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImageView;
        private TextView mNameView;
        private TextView mNumberView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imv_student_image);
            mNameView = itemView.findViewById(R.id.tv_student_name);
            mNumberView = itemView.findViewById(R.id.tv_student_number);
        }
    }
}
