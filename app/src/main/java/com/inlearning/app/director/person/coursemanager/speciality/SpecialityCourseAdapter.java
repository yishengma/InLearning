package com.inlearning.app.director.person.coursemanager.speciality;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Speciality;

import java.util.List;

public class SpecialityCourseAdapter extends RecyclerView.Adapter<SpecialityCourseAdapter.ViewHolder> {

    private List<Speciality> mSpecialities;

    public SpecialityCourseAdapter(List<Speciality> specialities) {
        mSpecialities = specialities;
    }

    public interface ClickListener {
        void onClick(Speciality speciality);
    }

    private ClickListener mClickListener;

    public SpecialityCourseAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_speciality_manager, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Speciality speciality = mSpecialities.get(i);
        viewHolder.mSpecialityName.setText(speciality.getName());
        viewHolder.mShortName.setText(String.format("简称：%s", speciality.getShortName()));
        viewHolder.mClassInfo.setText(String.format("班级：%s 个班级", speciality.getClassCount()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onClick(speciality);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpecialities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSpecialityName;
        private TextView mShortName;
        private TextView mClassInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSpecialityName = itemView.findViewById(R.id.tv_speciality_name);
            mShortName = itemView.findViewById(R.id.tv_short_name);
            mClassInfo = itemView.findViewById(R.id.tv_class_count);
        }
    }
}
