package com.inlearning.app.student.course.func.discuss;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Post;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.student.StudentRuntime;

import java.util.List;


public class DiscussPresenter {
    private Activity mActivity;
    private DiscussFuncView mDiscussFuncView;
    private SendPostView mSendPostView;
    private CourseChapter mChapter;

    public DiscussPresenter(Activity activity, CourseChapter chapter) {
        mActivity = activity;
        mDiscussFuncView = mActivity.findViewById(R.id.view_discuss_func);
        mSendPostView = mActivity.findViewById(R.id.view_send_post);
        mDiscussFuncView.setClickListener(new DiscussFuncView.ClickListener() {
            @Override
            public void onBack() {
                mActivity.finish();
            }

            @Override
            public void onEditClick() {
                mSendPostView.show();
            }
        });
        mSendPostView.setChapter(mChapter);
        mSendPostView.setStudent(StudentRuntime.getStudent());
        mSendPostView.setPostListener(new SendPostView.SendPostListener() {
            @Override
            public void onSendPost(Post post) {
                updateDiscussFuncView(post);
            }

            @Override
            public void onBack() {
                mSendPostView.hide();
                mDiscussFuncView.setVisibility(View.VISIBLE);
            }
        });
        mChapter = chapter;
        initData();
    }

    private void updateDiscussFuncView(Post post) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mSendPostView.hide();
                mDiscussFuncView.setVisibility(View.VISIBLE);
                mDiscussFuncView.update(post);
            }
        });
    }

    private void initData() {
        DiscussModel.getDiscussPost(mChapter, new DiscussModel.Callback<List<Post>>() {
            @Override
            public void onResult(List<Post> posts) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mDiscussFuncView.setPosts(posts);
                    }
                });
            }
        });
    }


    public void show() {
        mDiscussFuncView.setVisibility(View.VISIBLE);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mSendPostView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSendPostView.onActivityResult(requestCode, resultCode, data);
    }

}
