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
    private LinearLayout mBottomBarView;
    private TextView mEditQuesView;
    private TextView mDeleteView;
    private LinearLayoutManager mLayoutManager;
    private int mCuttentPosition = 0;

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
        mRvHomework.setLayoutManager(mLayoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper(){
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                mCuttentPosition =  super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                return mCuttentPosition;
            }
        };
        snapHelper.attachToRecyclerView(mRvHomework);
        mBackView.setOnClickListener(this);
        mAddView = view.findViewById(R.id.tv_bar_add);
        mAddView.setOnClickListener(this);
        mEmptyView = view.findViewById(R.id.tv_empty);
        mTitleBar = view.findViewById(R.id.fl_title_bar);
        mHomeworkEditView = view.findViewById(R.id.view_homework_edit);
        mBottomBarView = view.findViewById(R.id.view_bottom_bar);
        mEditQuesView = view.findViewById(R.id.tv_edit_ques);
        mDeleteView = view.findViewById(R.id.tv_delete);

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
        mBottomBarView.setVisibility(hasData ? VISIBLE : GONE);
    }

    public void hideFuncView() {
        mTitleBar.setVisibility(GONE);
        findViewById(R.id.split_line).setVisibility(GONE);
        mRvHomework.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        mBottomBarView.setVisibility(GONE);
    }

    public void showFuncView() {
        mTitleBar.setVisibility(VISIBLE);
        findViewById(R.id.split_line).setVisibility(VISIBLE);
        mRvHomework.setVisibility(VISIBLE);
        mEmptyView.setVisibility(VISIBLE);
        mBottomBarView.setVisibility(VISIBLE);
    }

    public HomeworkEditView getHomeworkEditView() {
        return mHomeworkEditView;
    }

    public TextView getEditQuesView() {
        return mEditQuesView;
    }

    public TextView getDeleteView() {
        return mDeleteView;
    }

    public LinearLayout getBottomBarView() {
        return mBottomBarView;
    }

    public int getCurrentPosition() {
        return mCuttentPosition;
    }
}

