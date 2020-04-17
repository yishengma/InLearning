package com.inlearning.app.teacher.classes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;

import java.util.List;

public class ClassInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ClassScheduleProxy> mProxies;
    private ClickListener mClickListener;

    public ClassInfoAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public interface ClickListener {
        void onClick(ClassScheduleProxy proxy);
    }

    public ClassInfoAdapter(List<ClassScheduleProxy> proxies) {
        mProxies = proxies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case ClassScheduleProxy.ITEM_CLASS_INFO:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_teacher_class_info, viewGroup, false);
                return new InfoViewHolder(view);
            case ClassScheduleProxy.ITEM_SEPARATE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_separate, viewGroup, false);
                return new SeparateViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final ClassScheduleProxy proxy = mProxies.get(i);
        if (viewHolder instanceof InfoViewHolder) {
            final ClassInfo classInfo = proxy.getSchedule().getClassInfo();
            ((InfoViewHolder) viewHolder).mClassInfo.setText(classInfo.getName());
            ((InfoViewHolder) viewHolder).mClassCount.setText(String.format("%s个学生", classInfo.getCount()));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onClick(proxy);
                    }
                }
            });
            ((InfoViewHolder) viewHolder).mClassIconTextView.setText(classInfo.getName().substring(classInfo.getName().length() - 3));
        }
        if (viewHolder instanceof SeparateViewHolder) {
            ((SeparateViewHolder) viewHolder).mInfo.setText(proxy.getCourse());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mProxies.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mProxies.size();
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {
        private TextView mClassInfo;
        private TextView mClassCount;
        private TextView mClassIconTextView;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mClassInfo = itemView.findViewById(R.id.tv_class_info);
            mClassCount = itemView.findViewById(R.id.tv_class_student_count);
            mClassIconTextView = itemView.findViewById(R.id.imv_class_text);
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
