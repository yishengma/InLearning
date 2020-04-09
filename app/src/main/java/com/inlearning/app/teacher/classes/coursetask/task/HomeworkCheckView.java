package com.inlearning.app.teacher.classes.coursetask.task;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Question;
import com.inlearning.app.teacher.attendclass.func.homework.BaseQuesFunc;


public class HomeworkCheckView extends RelativeLayout implements View.OnClickListener {

    public HomeworkCheckView(Context context) {
        this(context, null);
    }

    public HomeworkCheckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeworkCheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public interface ClickListener {
        void onBack();
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }
    private TextInputEditText mInputEditText;
    private TextView mAddView;
    private ImageView mQuesImageView;
    private ImageView mQuesDeleteView;
    private FrameLayout mQuesImageLayout;
    private TextView mUploadView;
    private ImageView mBackView;
    private TextView mTitleView;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_homework_response_question, this);
        mInputEditText = view.findViewById(R.id.et_question_input);
        mAddView = view.findViewById(R.id.tv_add_image);
        mQuesImageView = view.findViewById(R.id.imv_question_image);
        mQuesDeleteView = view.findViewById(R.id.imv_image_delete);
        mQuesImageLayout = view.findViewById(R.id.view_question_image);
        mUploadView = view.findViewById(R.id.tv_upload);
        mBackView = view.findViewById(R.id.imv_bar_back);
        mTitleView = view.findViewById(R.id.tv_bar_title);
        mQuesDeleteView.setVisibility(GONE);
        mAddView.setVisibility(GONE);
        mUploadView.setVisibility(GONE);
        mBackView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                setVisibility(GONE);
                if (mClickListener != null) {
                    mClickListener.onBack();
                }
                break;
        }
    }

    public void show(Question question) {
        Log.e("ethan", "title" + question.getQuestionTitle());
        if (TextUtils.isEmpty(question.getQuestionTitle())) {
            mInputEditText.setText("如图");
        } else {
            mInputEditText.setText(question.getQuestionTitle());
        }
        if (!TextUtils.isEmpty(question.getQuestionImage())) {
            setQuesImage(question.getQuestionImage());
            mAddView.setVisibility(GONE);
        } else {
            mAddView.setVisibility(VISIBLE);
        }
        setVisibility(VISIBLE);

    }

    void setQuesImage(String path) {
        mQuesImageLayout.setVisibility(VISIBLE);
        Glide.with(getContext()).load(path).into(mQuesImageView);
    }

}
