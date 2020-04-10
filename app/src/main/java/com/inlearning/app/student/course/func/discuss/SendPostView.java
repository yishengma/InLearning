package com.inlearning.app.student.course.func.discuss;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Post;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.ThreadMgr;

import java.io.File;

import cn.bmob.v3.util.V;

public class SendPostView extends BaseFuncView implements View.OnClickListener {

    public SendPostView(Context context) {
        this(context, null);
    }

    public SendPostView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SendPostView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private ImageView mBackView;
    private TextView mPostView;

    private TextInputEditText mInputTitleText;
    private TextInputEditText mInputContentText;
    private TextView mImageAddView;
    private ImageView mPostImageView;
    private ImageView mPostDeleteView;
    private FrameLayout mPostImageLayout;
    private String mImageFilePath;

    private CourseChapter mChapter;
    private Student mStudent;

    public interface SendPostListener {
        void onSendPost(Post post);

        void onBack();
    }

    private SendPostListener mPostListener;

    public void setPostListener(SendPostListener postListener) {
        mPostListener = postListener;
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_post_detail, this);
        mBackView = view.findViewById(R.id.imv_bar_back);
        mPostView = view.findViewById(R.id.tv_bar_post);
        mInputTitleText = view.findViewById(R.id.et_title);
        mInputContentText = view.findViewById(R.id.et_content);
        mImageAddView = view.findViewById(R.id.tv_add_image);
        mPostImageView = view.findViewById(R.id.imv_post_image);
        mPostDeleteView = view.findViewById(R.id.imv_image_delete);
        mPostImageLayout = view.findViewById(R.id.view_post_image);
        mBackView.setOnClickListener(this);
        mPostView.setOnClickListener(this);
        mImageAddView.setOnClickListener(this);
        mPostDeleteView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                if (mPostListener != null) {
                    mPostListener.onBack();
                }
                break;
            case R.id.tv_bar_post:
                sendPost();
                break;
            case R.id.tv_add_image:
                showPickDialog();
                break;
            case R.id.imv_image_delete:
                setPostImageVisible(false);
                mImageFilePath = "";
                mImageAddView.setVisibility(VISIBLE);
                break;
        }
    }


    @Override
    public Activity getActivity() {
        return (Activity) getContext();
    }

    @Override
    void setQuesImage(File fileCropUri) {
        mPostImageLayout.setVisibility(VISIBLE);
        Glide.with(getContext()).load(fileCropUri).into(mPostImageView);
        mImageFilePath = fileCropUri.getPath();
        mPostImageView.setImageBitmap(BitmapFactory.decodeFile(fileCropUri.getPath()));
        mImageAddView.setVisibility(GONE);
    }

    @Override
    void setQuesImage(Bitmap bitmap) {

    }

    public void setChapter(CourseChapter chapter) {
        mChapter = chapter;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    private void setPostImageVisible(boolean show) {
        mPostImageLayout.setVisibility(show ? VISIBLE : GONE);
    }

    public void show() {
        mImageFilePath = "";
        mInputTitleText.setText("");
        mInputContentText.setText("");
        mImageAddView.setVisibility(VISIBLE);
        setPostImageVisible(false);
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }


    private void sendPost() {
        String title = mInputTitleText.getText().toString();
        String content = mInputContentText.getText().toString();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getContext(), "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "请输入描述", Toast.LENGTH_SHORT).show();
            return;
        }
        Post post = new Post();
        post.setImageUrl(mImageFilePath);
        post.setContent(content);
        post.setTitle(title);
        post.setChapter(mChapter);
        post.setStudent(mStudent);
        ThreadMgr.getInstance().postToSubThread(new Runnable() {
            @Override
            public void run() {
                DiscussModel.sendPost(post, new DiscussModel.Callback<Post>() {
                    @Override
                    public void onResult(Post post) {
                        if (mPostListener != null) {
                            mPostListener.onSendPost(post);
                        }
                    }
                });
            }
        });
    }
}
