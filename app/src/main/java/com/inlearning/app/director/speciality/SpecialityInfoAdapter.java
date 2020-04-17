package com.inlearning.app.director.speciality;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;

import java.util.List;

public class SpecialityInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ClassInfo> mClassInfoList;
    private ClickListener mClickListener;
    private float mX;
    private float mY;

    public SpecialityInfoAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public interface ClickListener {
        void onClick(ClassInfo classInfo);

        void onLongClick(View view, float x, float y, ClassInfo classInfo);
    }

    public SpecialityInfoAdapter(List<ClassInfo> classInfoList) {
        mClassInfoList = classInfoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case ClassInfo.ITEM_CLASS_INFO:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_director_speciality_info, viewGroup, false);
                return new InfoViewHolder(view);
            case ClassInfo.ITEM_SEPARATE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_separate, viewGroup, false);
                return new SeparateViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final ClassInfo classInfo = mClassInfoList.get(i);
        if (viewHolder instanceof InfoViewHolder) {
            ((InfoViewHolder) viewHolder).mClassInfo.setText(classInfo.getName());
            ((InfoViewHolder) viewHolder).mClassCount.setText(String.format("%s 个学生", classInfo.getCount()));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onClick(classInfo);
                    }
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onLongClick(view, mX, mY, classInfo);
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
            ((InfoViewHolder) viewHolder).mClassImageTextView.setText(classInfo.getName().substring(classInfo.getName().length() - 3));

        }
        if (viewHolder instanceof SeparateViewHolder) {
            ((SeparateViewHolder) viewHolder).mInfo.setText(classInfo.getSpeciality().getShortName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mClassInfoList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mClassInfoList.size();
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {
        private TextView mClassInfo;
        private TextView mClassCount;
        private TextView mClassImageTextView;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mClassInfo = itemView.findViewById(R.id.tv_class_info);
            mClassCount = itemView.findViewById(R.id.tv_class_student_count);
            mClassImageTextView = itemView.findViewById(R.id.imv_class_text);
        }
    }

    class SeparateViewHolder extends RecyclerView.ViewHolder {
        private TextView mInfo;

        public SeparateViewHolder(@NonNull View itemView) {
            super(itemView);
            mInfo = itemView.findViewById(R.id.tv_info);
        }
    }
}
