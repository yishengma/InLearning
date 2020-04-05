package com.inlearning.app.director.person.coursemanager.classes.organize;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Teacher;

import java.util.List;

public class TeacherInfoAdapter extends RecyclerView.Adapter<TeacherInfoAdapter.ViewHolder> {
    private List<Teacher> mTeacherList;
    private boolean isImport = false;

    public TeacherInfoAdapter(List<Teacher> teacherList) {
        mTeacherList = teacherList;
    }

    public interface ClickListener {
        void onClick(Teacher tea);

        void onLongClick(View view, float x, float y, Teacher tea);

    }

    ClickListener mClickListener;
    private float mX;
    private float mY;

    public TeacherInfoAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public TeacherInfoAdapter setImport(boolean isImport) {
        this.isImport = isImport;
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
        viewHolder.mSelectView.setVisibility(isImport ? View.VISIBLE : View.GONE);
        viewHolder.mSelectView.setChecked(teacher.isSelected());
        viewHolder.mSelectView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                teacher.setSeleted(b);
            }
        });
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
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onLongClick(view, mX, mY, teacher);
                }
                return false;
            }
        });
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mX = motionEvent.getX();
                mY = motionEvent.getY();
                return false;
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
        private CheckBox mSelectView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSelectView = itemView.findViewById(R.id.btn_select);
            mTeaName = itemView.findViewById(R.id.director_teacher_name);
            mTeaTitle = itemView.findViewById(R.id.director_teacher_title);
            mTeaJobNumber = itemView.findViewById(R.id.director_teacher_job_number);
        }
    }
}