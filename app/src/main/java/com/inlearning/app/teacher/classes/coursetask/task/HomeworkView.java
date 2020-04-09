package com.inlearning.app.teacher.classes.coursetask.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Question;
import com.openxu.cview.chart.ProgressPieChart;
import com.openxu.cview.chart.bean.ChartLable;
import com.openxu.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeworkView extends LinearLayout {

    public HomeworkView(Context context) {
        this(context, null);
    }

    public HomeworkView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeworkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initPieChart();
    }

    private ProgressPieChart mPieChart;
    private RecyclerView mRvHomeWork;
    private HomeworkAdapter mHomeworkAdapter;
    private List<Question> mQuestions;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tea_course_task_homework, this);
        mPieChart = view.findViewById(R.id.pie_chart_view);
        mRvHomeWork = view.findViewById(R.id.rv_homework);
        mRvHomeWork.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mQuestions = new ArrayList<>();
        mHomeworkAdapter = new HomeworkAdapter(mQuestions);
        mRvHomeWork.setAdapter(mHomeworkAdapter);
    }

    private void initPieChart() {
        mPieChart.setProSize(DensityUtil.dip2px(getContext(), 5));  //圆环宽度
        mPieChart.setDebug(false);
        mPieChart.setLoading(false);
        mPieChart.setProColor(getResources().getColor(R.color.app_global_blue));  //进度颜色
    }

    public void setPieChartData(int questionCount, List<Integer> answerCountPerStu) {
        int total = answerCountPerStu.size();
        int progress = 0;
        for (Integer i : answerCountPerStu) {
            progress += i >= questionCount ? 1 : 0;
        }
        List<ChartLable> lables = new ArrayList<>();
        lables.add(new ChartLable(String.valueOf(progress * 100.0 / total) + " %",
                DensityUtil.sp2px(getContext(), 18), getResources().getColor(R.color.app_global_blue)));
        lables.add(new ChartLable("完成率",
                DensityUtil.sp2px(getContext(), 12), getResources().getColor(R.color.text_color_light_gray)));
        mPieChart.setData(total, progress, lables);
    }


    public void setQuestionData(List<Question> questions) {
        mQuestions.clear();
        mQuestions.addAll(questions);
        mHomeworkAdapter.notifyDataSetChanged();
    }

    public HomeworkAdapter getHomeworkAdapter() {
        return mHomeworkAdapter;
    }

    public static class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {

        private List<Question> mAdapterQuestions;

        public HomeworkAdapter(List<Question> questions) {
            mAdapterQuestions = questions;
        }

        public interface ClickListener {
            void onAnalysis(int position, Question question);

            void onDetail(int position, Question question);
        }

        private ClickListener mClickListener;

        public void setClickListener(ClickListener clickListener) {
            mClickListener = clickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tea_course_task_homework, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Question question = mAdapterQuestions.get(i);
            viewHolder.mInfoView.setText(String.format("第%s道 [%s]: %s", i + 1, question.getType() == Question.Type.CHOICE_QUESTION ? "选择题" : "问答题", TextUtils.isEmpty(question.getQuestionTitle()) ? "[图片]" : question.getQuestionTitle()));
            if (question.getType() == Question.Type.CHOICE_QUESTION) {
                viewHolder.mDetailView.setText("详情");
                viewHolder.mDetailView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) {
                            mClickListener.onAnalysis(i + 1, question);
                        }
                    }
                });
            }
            if (question.getType() == Question.Type.RESPONSE_QUESTION) {
                viewHolder.mDetailView.setText("详情");
                viewHolder.mDetailView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) {
                            mClickListener.onDetail(i + 1, question);
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mAdapterQuestions.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mInfoView;
            private TextView mDetailView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mInfoView = itemView.findViewById(R.id.tv_question_info);
                mDetailView = itemView.findViewById(R.id.tv_detail);

            }
        }
    }
}
