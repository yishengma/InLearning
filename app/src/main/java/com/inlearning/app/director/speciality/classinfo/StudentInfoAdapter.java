package com.inlearning.app.director.speciality.classinfo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Student;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoAdapter.ViewHolder> {

    private List<Student> mStudentList;
    private boolean mIsImport;

    public StudentInfoAdapter(List<Student> studentList) {
        mStudentList = studentList;
    }

    private ClickListener mClickListener;

    public StudentInfoAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public StudentInfoAdapter setImport(boolean isImport) {
        mIsImport = isImport;
        return this;
    }

    public interface ClickListener {
        void onClick(Student student);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_director_student_info, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Student student = mStudentList.get(i);
        viewHolder.mSelectView.setVisibility(mIsImport ? View.VISIBLE : View.GONE);
        viewHolder.mSelectView.setChecked(student.isSelected());
        viewHolder.mSelectView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                student.setSelected(b);
            }
        });
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
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImageView;
        private TextView mNameView;
        private TextView mNumberView;
        private CheckBox mSelectView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSelectView = itemView.findViewById(R.id.btn_select);
            mImageView = itemView.findViewById(R.id.imv_student_image);
            mNameView = itemView.findViewById(R.id.tv_student_name);
            mNumberView = itemView.findViewById(R.id.tv_student_number);
        }
    }
}
