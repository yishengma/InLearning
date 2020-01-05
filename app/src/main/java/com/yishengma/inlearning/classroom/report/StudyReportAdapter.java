package com.yishengma.inlearning.classroom.report;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yishengma.inlearning.R;
import java.util.List;

public class StudyReportAdapter extends RecyclerView.Adapter<StudyReportAdapter.ViewHolder> {

    private List<String> mStringList;

    public StudyReportAdapter(List<String> stringList) {
        mStringList = stringList;
    }

    @NonNull
    @Override
    public StudyReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task_report_view, viewGroup, false);
        return new StudyReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyReportAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}