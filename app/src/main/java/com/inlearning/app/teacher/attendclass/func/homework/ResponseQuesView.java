package com.inlearning.app.teacher.attendclass.func.homework;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Question;

import java.io.File;
import java.util.List;


public class ResponseQuesView extends BaseQuesFunc implements View.OnClickListener {

    public ResponseQuesView(Context context) {
        this(context, null);
    }

    public ResponseQuesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResponseQuesView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mImageFilePath = "";
        mQuesDeleteView.setOnClickListener(this);
        mAddView.setOnClickListener(this);
        mUploadView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
    }

    @Override
    Activity initActivity() {
        return (Activity) getContext();
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
            case R.id.tv_upload:
                if (mClickListener == null) {
                    return;
                }
                Question question = createQuestion();
                if (question == null) {
                    return;
                }
                mClickListener.onUpload(question);
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
        mQuesImageView.setImageBitmap(BitmapFactory.decodeFile(fileCropUri.getPath()));
        mAddView.setVisibility(GONE);
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

    private Question createQuestion() {
        String questionTitle = mInputEditText.getText().toString();
        if (TextUtils.isEmpty(questionTitle) && TextUtils.isEmpty(mImageFilePath)) {
            Toast.makeText(getContext(), "请输入题目或者选择插入图片", Toast.LENGTH_SHORT).show();
            return null;
        }
        Question question = new Question();
        question.setQuestionTitle(questionTitle);
        question.setType(Question.Type.RESPONSE_QUESTION);
        question.setQuestionImage(mImageFilePath);
        return question;
    }

}
