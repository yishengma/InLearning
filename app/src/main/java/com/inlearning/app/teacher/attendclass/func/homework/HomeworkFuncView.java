package com.inlearning.app.teacher.attendclass.func.homework;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.inlearning.app.R;

public class HomeworkFuncView extends LinearLayout implements View.OnClickListener {

    public HomeworkFuncView(Context context) {
        this(context, null);
    }

    public HomeworkFuncView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeworkFuncView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private ResponseQuesView mResponseQuesView;
    private ChooseQuesView mChooseQuesView;
    private HomeworkEditView mHomeworkEditView;
    private ImageView mBackView;
    private TextView mTitleView;
    private RecyclerView mRvHomework;
    private TextView mAddView;
    private TextView mEmptyView;
    private FrameLayout mTitleBar;

    public interface ClickListener {
        void onBack();

        void onAddChoiceQues();

        void onAddResponseQues();
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_homework_function, this);
        mResponseQuesView = view.findViewById(R.id.view_response_ques);
        mChooseQuesView = view.findViewById(R.id.view_choose_ques);
        mBackView = view.findViewById(R.id.imv_bar_back);
        mTitleView = view.findViewById(R.id.tv_bar_title);
        mRvHomework = view.findViewById(R.id.rv_homework);
        mRvHomework.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRvHomework);
        mBackView.setOnClickListener(this);
        mAddView = view.findViewById(R.id.tv_bar_add);
        mAddView.setOnClickListener(this);
        mEmptyView = view.findViewById(R.id.tv_empty);
        mTitleBar = view.findViewById(R.id.fl_title_bar);
        mHomeworkEditView = view.findViewById(R.id.view_homework_edit);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                if (mClickListener != null) {
                    mClickListener.onBack();
                }
                break;
            case R.id.tv_bar_add:
                showDialog();
                break;
        }
    }

    public RecyclerView getRvHomework() {
        return mRvHomework;
    }

    public void setTitleMsg(String msg) {
        mTitleView.setText(msg);
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext(), R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_add_question);
        dialog.setCanceledOnTouchOutside(true);
        TextView choiceView = dialog.findViewById(R.id.tv_choice_question);
        TextView responseView = dialog.findViewById(R.id.tv_response_question);
        choiceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onAddChoiceQues();
                }
                dialog.dismiss();
            }
        });
        responseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onAddResponseQues();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public ResponseQuesView getResponseQuesView() {
        return mResponseQuesView;
    }

    public ChooseQuesView getChooseQuesView() {
        return mChooseQuesView;
    }

    public void refreshUI(boolean hasData) {
        mRvHomework.setVisibility(hasData ? VISIBLE : GONE);
        mEmptyView.setVisibility(hasData ? GONE : VISIBLE);
    }

    public void hideFuncView() {
        mTitleBar.setVisibility(GONE);
        findViewById(R.id.split_line).setVisibility(GONE);
        mRvHomework.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
    }

    public void showFuncView() {
        mTitleBar.setVisibility(VISIBLE);
        findViewById(R.id.split_line).setVisibility(VISIBLE);
        mRvHomework.setVisibility(VISIBLE);
        mEmptyView.setVisibility(VISIBLE);
    }

    public HomeworkEditView getHomeworkEditView() {
        return mHomeworkEditView;
    }
}

