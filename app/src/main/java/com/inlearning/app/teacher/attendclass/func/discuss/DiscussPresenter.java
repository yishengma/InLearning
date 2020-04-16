package com.inlearning.app.teacher.attendclass.func.discuss;

import android.app.Activity;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Post;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.List;


public class DiscussPresenter {
    private Activity mActivity;
    private DiscussFuncView mDiscussFuncView;
    private CourseChapter mChapter;

    public DiscussPresenter(Activity activity, CourseChapter chapter) {
        mActivity = activity;
        mDiscussFuncView = mActivity.findViewById(R.id.view_discuss_func);
        mDiscussFuncView.setClickListener(new DiscussFuncView.ClickListener() {
            @Override
            public void onBack() {
                mActivity.finish();
            }

            @Override
            public void onEditClick() {

            }
        });
        mChapter = chapter;
        mDiscussFuncView.setTitle(mChapter.getChapterName()+"/讨论");
        initData();
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

    public boolean onBackPressed() {
        return mDiscussFuncView.onBackPressed();
    }

}
