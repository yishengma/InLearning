package com.inlearning.app.teacher.attendclass.func.homework;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Question;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.util.V;

public class HomeworkEditView extends BaseQuesFunc implements View.OnClickListener {


    public HomeworkEditView(Context context) {
        this(context, null);
    }

    public HomeworkEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeworkEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private TextInputEditText mInputEditText;
    private TextView mAddView;
    private ImageView mQuesImageView;
    private ImageView mQuesDeleteView;
    private FrameLayout mQuesImageLayout;
    private TextView mUploadView;
    private String mImageFilePath;
    private ImageView mBackView;
    private TextView mTitleView;
    private LinearLayout mCheckBoxLayout;
    private Question mQuestion;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_homework_question_edit, this);
        mInputEditText = view.findViewById(R.id.et_question_input);
        mAddView = view.findViewById(R.id.tv_add_image);
        mQuesImageView = view.findViewById(R.id.imv_question_image);
        mQuesDeleteView = view.findViewById(R.id.imv_image_delete);
        mQuesImageLayout = view.findViewById(R.id.view_question_image);
        mUploadView = view.findViewById(R.id.tv_update);
        mBackView = view.findViewById(R.id.imv_bar_back);
        mTitleView = view.findViewById(R.id.tv_bar_title);
        mCheckBoxLayout = view.findViewById(R.id.view_checkbox);
        mImageFilePath = "";
        mQuesDeleteView.setOnClickListener(this);
        mAddView.setOnClickListener(this);
        mUploadView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_image:
                showPickDialog();
                break;
            case R.id.imv_image_delete:
                setAddViewVisible(true);
                setQuesImageVisible(false);
                mImageFilePath = "";
                break;
            case R.id.tv_update:
                if (mClickListener == null) {
                    return;
                }
                Question question = createQuestion();
                if (question == null) {
                    return;
                }
                mClickListener.onUpdate(question);
                break;
            case R.id.imv_bar_back:
                if (mClickListener != null) {
                    mClickListener.onBack();
                }
                break;
        }
    }

    @Override
    void setQuesImage(File fileCropUri) {
        mQuesImageLayout.setVisibility(VISIBLE);
        Glide.with(getContext()).load(fileCropUri).into(mQuesImageView);
        mImageFilePath = fileCropUri.getPath();
        mAddView.setVisibility(GONE);
    }

    void setQuesImage(String path) {
        mQuesImageLayout.setVisibility(VISIBLE);
        Glide.with(getContext()).load(path).into(mQuesImageView);
        mImageFilePath = path;
    }

    @Override
    void setQuesImage(Bitmap bitmap) {
        mQuesImageLayout.setVisibility(VISIBLE);
        mQuesImageView.setImageBitmap(bitmap);
    }

    public String getQuesContent() {
        return mInputEditText.getText().toString();
    }

    public void setAddViewVisible(boolean show) {
        mAddView.setVisibility(show ? VISIBLE : GONE);
    }

    public void setQuesImageVisible(boolean show) {
        mQuesImageLayout.setVisibility(show ? VISIBLE : GONE);
    }

    public String getQuesImage() {
        return mImageFilePath;
    }


    public void setTitleMsg(String msg) {
        mTitleView.setText(msg);
    }


    private List<String> getCheckBoxAnswer() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < mCheckBoxLayout.getChildCount(); i++) {
            CheckBox box = (CheckBox) mCheckBoxLayout.getChildAt(i);
            if (box.isChecked()) {
                list.add(box.getText().toString());
            }
        }
        return list;
    }

    @Override
    Activity initActivity() {
        return (Activity) getContext();
    }

    private Question createQuestion() {
        String questionTitle = mInputEditText.getText().toString();
        if (TextUtils.isEmpty(questionTitle) && TextUtils.isEmpty(mImageFilePath)) {
            Toast.makeText(getContext(), "请输入题目或者选择插入图片", Toast.LENGTH_SHORT).show();
            return null;
        }
        List<String> answerList = getCheckBoxAnswer();
        if (answerList.isEmpty() && mQuestion.getType() == Question.Type.CHOICE_QUESTION) {
            Toast.makeText(getContext(), "请输入答案", Toast.LENGTH_SHORT).show();
            return null;
        }
        mQuestion.setQuestionTitle(questionTitle);
        mQuestion.setChoiceAnswers(answerList);
        mQuestion.setType(mQuestion.getType());
        mQuestion.setQuestionImage(mImageFilePath);
        return mQuestion;
    }

    public void show(Question question) {
        mQuestion = question;
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
        if (question.getType() == Question.Type.CHOICE_QUESTION) {
            mCheckBoxLayout.setVisibility(VISIBLE);
            List<String> list = question.getChoiceAnswers();
            for (int i = 0; i < mCheckBoxLayout.getChildCount(); i++) {
                CheckBox box = (CheckBox) mCheckBoxLayout.getChildAt(i);
                box.setChecked(list.contains(box.getText()));
            }
        } else {
            mCheckBoxLayout.setVisibility(GONE);
        }

        setVisibility(VISIBLE);

    }

    public void hide() {
        mImageFilePath = "";
        mInputEditText.setText("");
        mQuestion = null;
        setVisibility(GONE);
    }
}
