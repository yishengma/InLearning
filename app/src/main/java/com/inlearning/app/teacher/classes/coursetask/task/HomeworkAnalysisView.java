package com.inlearning.app.teacher.classes.coursetask.task;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Answer;
import com.inlearning.app.common.bean.Question;
import com.openxu.cview.chart.ProgressPieChart;
import com.openxu.cview.chart.bean.ChartLable;
import com.openxu.cview.chart.bean.PieChartBean;
import com.openxu.cview.chart.piechart.PieChartLayout;
import com.openxu.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class HomeworkAnalysisView extends LinearLayout {

    public HomeworkAnalysisView(Context context) {
        this(context, null);
    }

    public HomeworkAnalysisView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeworkAnalysisView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initPieChartView();
        initPieChart();
    }

    private PieChartLayout mPieChartLayout;
    private ProgressPieChart mPieChart;
    private RecyclerView mRvStuAnswer;
    private AnswerAdapter mAnswerAdapter;
    private List<Answer> mAnswers;


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_homework_analysis, this);
        mPieChartLayout = view.findViewById(R.id.view_pie_chart);
        mPieChart = view.findViewById(R.id.pie_chart_view);
        mRvStuAnswer = view.findViewById(R.id.rv_content);
        mRvStuAnswer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    public void setAnswerAdapter(List<Answer> answers, Question question) {
        mAnswers = new ArrayList<>();
        mAnswers.addAll(answers);
        mAnswerAdapter = new AnswerAdapter(mAnswers, question);
        mRvStuAnswer.setAdapter(mAnswerAdapter);
    }

    private void initPieChart() {
        mPieChart.setProSize(DensityUtil.dip2px(getContext(), 5));  //圆环宽度
        mPieChart.setDebug(false);
        mPieChart.setLoading(false);
        mPieChart.setProColor(getResources().getColor(R.color.app_global_blue));  //进度颜色
    }

    private void initPieChartView() {

        /*
         * 圆环宽度
         * ringWidth > 0 :空心圆环，内环为白色，可以在内环中绘制字
         * ringWidth <=0 :实心
         */
        mPieChartLayout.setRingWidth(DensityUtil.dip2px(getContext(), 15));
        mPieChartLayout.setLineLenth(DensityUtil.dip2px(getContext(), 8)); // //指示线长度
        mPieChartLayout.setTagModul(PieChartLayout.TAG_MODUL.MODUL_CHART);       //在扇形图上显示tag
        mPieChartLayout.setDebug(false);
        mPieChartLayout.setLoading(true);

    }


    public void setData(List<Answer> answers, Question question) {
        HashMap<String, Integer> answerMap = new HashMap<>();
        int total = answers.size();
        int progress = 0;
        String ques = Arrays.toString(question.getChoiceAnswers().toArray());
        for (Answer answer : answers) {
            String an = Arrays.toString(answer.getChoiceAnswers().toArray());
            if (answerMap.get(an) != null) {
                answerMap.put(an, answerMap.get(an) + 1);
            } else {
                answerMap.put(an, new Integer(1));
            }
            progress += ques.equals(an) ? 1 : 0;
        }
        setPieChartData(answerMap);
        setPieChartData(total, progress);
        setAnswerAdapter(answers, question);
    }

    private void setPieChartData(HashMap<String, Integer> map) {
//请求数据
        List<PieChartBean> datalist = new ArrayList<>();
        for (String s : map.keySet()) {
            datalist.add(new PieChartBean(map.get(s), s));
        }
//显示在中间的lable
        List<ChartLable> tableList = new ArrayList<>();
        tableList.add(new ChartLable("答案", DensityUtil.sp2px(getContext(), 12), getResources().getColor(R.color.text_color_light_gray)));
        tableList.add(new ChartLable("分布", DensityUtil.sp2px(getContext(), 12), getResources().getColor(R.color.text_color_light_gray)));
        mPieChartLayout.setLoading(false);
//参数1：数据类型   参数2：数量字段名称   参数3：名称字段   参数4：数据集合   参数5:lable集合
        mPieChartLayout.setChartData(PieChartBean.class, "num", "name", datalist, tableList);
    }

    public void setPieChartData(int total, int progress) {

        List<ChartLable> lables = new ArrayList<>();
        lables.add(new ChartLable(String.valueOf(progress * 100.0 / total) + " %",
                DensityUtil.sp2px(getContext(), 18), getResources().getColor(R.color.app_global_blue)));
        lables.add(new ChartLable("正确率",
                DensityUtil.sp2px(getContext(), 12), getResources().getColor(R.color.text_color_light_gray)));
        mPieChart.setData(total, progress, lables);
    }

    public static class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
        private List<Answer> mAdapterAnswers;
        private Question mQuestion;

        public AnswerAdapter(List<Answer> adapterAnswers, Question question) {
            mAdapterAnswers = adapterAnswers;
            mQuestion = question;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homework_choice_answer, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Answer answer = mAdapterAnswers.get(i);
            viewHolder.mStuNameView.setText(answer.getStudent().getName());
            viewHolder.mAnswerView.setText(Arrays.toString(answer.getChoiceAnswers().toArray()));
            String an = Arrays.toString(answer.getChoiceAnswers().toArray());
            String ques = Arrays.toString(mQuestion.getChoiceAnswers().toArray());
            viewHolder.mResultView.setText(ques.equals(an) ? "正确" : "错误");
            viewHolder.mResultView.setTextColor(ques.equals(an) ? Color.parseColor("#FF2196F3") : Color.parseColor("#FFFF0031"));
        }

        @Override
        public int getItemCount() {
            return mAdapterAnswers.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mStuNameView;
            private TextView mAnswerView;
            private TextView mResultView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mStuNameView = itemView.findViewById(R.id.tv_stu_name);
                mAnswerView = itemView.findViewById(R.id.tv_choice);
                mResultView = itemView.findViewById(R.id.tv_result);
            }
        }
    }
}
