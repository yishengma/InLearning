package com.inlearning.app.teacher.classes.coursetask.task;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ChapterProgress;
import com.openxu.cview.chart.ProgressPieChart;
import com.openxu.cview.chart.barchart.BarHorizontalChart;
import com.openxu.cview.chart.bean.ChartLable;
import com.openxu.cview.chart.bean.TimeBarBean;
import com.openxu.utils.DensityUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LearnTimeView extends LinearLayout {

    public LearnTimeView(Context context) {
        this(context, null);
    }

    public LearnTimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LearnTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initPieChart();
        initHorizontalChart();
    }

    private ProgressPieChart mPieChart;
    private BarHorizontalChart mHorizontalChart;
    private TextView mEmptyView;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tea_course_task_learn_time, this);
        mPieChart = view.findViewById(R.id.pie_chart_view);
        mHorizontalChart = view.findViewById(R.id.bar_horizontal_chart_view);
        mEmptyView = view.findViewById(R.id.tv_empty);
    }


    private void initPieChart() {
        mPieChart.setProSize(DensityUtil.dip2px(getContext(), 5));  //圆环宽度
        mPieChart.setDebug(false);
        mPieChart.setLoading(false);
        mPieChart.setProColor(getResources().getColor(R.color.app_global_blue));  //进度颜色
    }

    private void setPieChartData(int size ,List<ChapterProgress> progresses) {
        if (progresses == null || progresses.isEmpty()) {
            List<ChartLable> lables = new ArrayList<>();
            lables.add(new ChartLable(String.valueOf(0 * 100.0 / 100) + " %",
                    DensityUtil.sp2px(getContext(), 18), getResources().getColor(R.color.app_global_blue)));
            lables.add(new ChartLable("完成率",
                    DensityUtil.sp2px(getContext(), 12), getResources().getColor(R.color.text_color_light_gray)));
            mPieChart.setData(100, 0, lables);
            return;
        }
        int total = size;
        int progress = 0;
        for (ChapterProgress s : progresses) {
            progress += s.isDone() ? 1 : 0;
        }

        List<ChartLable> lables = new ArrayList<>();
        lables.add(new ChartLable(new DecimalFormat("#0.00").format(progress * 100 / total) + " %",
                DensityUtil.sp2px(getContext(), 18), getResources().getColor(R.color.app_global_blue)));
        lables.add(new ChartLable("完成率",
                DensityUtil.sp2px(getContext(), 12), getResources().getColor(R.color.text_color_light_gray)));
        mPieChart.setData(total, progress, lables);
    }

    private void initHorizontalChart() {
        mHorizontalChart.setBarSpace(DensityUtil.dip2px(getContext(), 1));  //双柱间距
        mHorizontalChart.setBarItemSpace(DensityUtil.dip2px(getContext(), 5));  //柱间距
        mHorizontalChart.setDebug(false);
        mHorizontalChart.setBarNum(3);
        mHorizontalChart.setBarColor(new int[]{Color.parseColor("#5F93E7"), Color.parseColor("#F28D02")});
    }

    private void setBarChartData(List<ChapterProgress> progresses) {
        if (progresses == null || progresses.isEmpty()) {
            mEmptyView.setVisibility(VISIBLE);
            return;
        }
        mEmptyView.setVisibility(GONE);
        List<String> strXList = new ArrayList<>();
        List<List<TimeBarBean>> dataList = new ArrayList<>();
        for (ChapterProgress progress : progresses) {
            strXList.add(progress.getStudent().getName());
            dataList.add(Collections.singletonList(new TimeBarBean((int) (progress.getStudyDuration() / 1000), "学习时长")));
        }
        mHorizontalChart.setLoading(false);
        mHorizontalChart.setData(dataList, strXList);
    }

    public void setData(int size,List<ChapterProgress> progresses) {
//        List<ChapterProgress> progresses1 = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            progresses1.add(new ChapterProgress().setDone(i%2==0).setStudyDuration(58000+i*10000).setStudent((Student) new Student().setName("马小"+i)));
//        }
        setBarChartData(progresses);
        setPieChartData(size,progresses);
    }

}
