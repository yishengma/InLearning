package com.inlearning.app.director.teacher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Teacher;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherInfoAdapter extends RecyclerView.Adapter<TeacherInfoAdapter.ViewHolder> {
    private List<Teacher> mTeacherList;
    private boolean isImport = false;
    private Context mContext;

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
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_director_teacher_info, viewGroup, false);
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
                teacher.setSelected(b);
            }
        });
        viewHolder.mTeaName.setText(teacher.getName());
        viewHolder.mTeaTitle.setText(teacher.getTitle());
        viewHolder.mTeaJobNumber.setText(teacher.getAccount());
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
        if (!TextUtils.isEmpty(teacher.getProfilePhotoUrl())) {
            Glide.with(mContext).load(teacher.getProfilePhotoUrl()).into(viewHolder.mTeaIcon);
        } else {
            viewHolder.mTeaIcon.setBackgroundDrawable(mContext.getDrawable(R.drawable.viewpage_guide_3));
        }
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
        private CircleImageView mTeaIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSelectView = itemView.findViewById(R.id.btn_select);
            mTeaName = itemView.findViewById(R.id.director_teacher_name);
            mTeaTitle = itemView.findViewById(R.id.director_teacher_title);
            mTeaJobNumber = itemView.findViewById(R.id.director_teacher_job_number);
            mTeaIcon = itemView.findViewById(R.id.course_teacher_icon);
        }
    }
}
