package com.inlearning.app.student.course.func.discuss;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.util.L;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Comment;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Post;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.student.course.func.ChapterFunctionActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.util.V;

public class DiscussDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static void startActivity(Context context, Post post) {
        Intent intent = new Intent(context, DiscussDetailActivity.class);
        intent.putExtra("post", post);
        context.startActivity(intent);
    }

    private ImageView mBackView;
    private TextView mPostTitleView;
    private ImageView mStudentImageView;
    private TextView mStudentNameView;
    private TextView mPostContentView;
    private ImageView mPostImageView;
    private RecyclerView mRvComment;
    private Post mPost;
    private CommentAdapter mCommentAdapter;
    private List<Comment> mComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        setContentView(R.layout.activity_discuss_detail);
        getIntentData();
        initView();
        getComments();
    }

    private void getIntentData() {
        mPost = (Post) getIntent().getSerializableExtra("post");
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_edit_back);
        mPostTitleView = findViewById(R.id.tv_post_title);
        mStudentImageView = findViewById(R.id.imv_student_image);
        mStudentNameView = findViewById(R.id.tv_student_name);
        mPostContentView = findViewById(R.id.tv_post_content);
        mPostImageView = findViewById(R.id.imv_post_image);
        mRvComment = findViewById(R.id.rv_comment);
        mComments = new ArrayList<>();
        mCommentAdapter = new CommentAdapter(mComments);
        mRvComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvComment.setAdapter(mCommentAdapter);

        mBackView.setOnClickListener(this);
        mStudentNameView.setText(mPost.getStudent().getName());
        mPostTitleView.setText(mPost.getTitle());
        mPostContentView.setText(mPost.getContent());
        if (!TextUtils.isEmpty(mPost.getImageUrl())) {
            mPostImageView.setVisibility(View.VISIBLE);
            Glide.with(this).load(mPost.getImageUrl()).into(mPostImageView);
        }
    }

    public void getComments() {
        DiscussModel.getComment(mPost, new DiscussModel.Callback<List<Comment>>() {
            @Override
            public void onResult(List<Comment> comments) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mComments.clear();
                        mComments.addAll(comments);
                        mCommentAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_edit_back:
                finish();
                break;
        }
    }

    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

        private List<Comment> mComments;

        public CommentAdapter(List<Comment> comments) {
            mComments = comments;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_discuss_comment, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Comment comment = mComments.get(i);
            if (comment.getStudent() != null) {
                viewHolder.mUserNameView.setText(comment.getStudent().getName());
            }
            if (comment.getTeacher() != null) {
                viewHolder.mUserNameView.setText(comment.getTeacher().getName());
            }
            if (!TextUtils.isEmpty(comment.getImageUrl())) {
                Glide.with(viewHolder.itemView.getContext()).load(comment.getImageUrl()).into(viewHolder.mContentImageView);
            }
            viewHolder.mCommentTextView.setText(comment.getContent());
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView mUserImageView;
            private TextView mUserNameView;
            private TextView mCommentTextView;
            private ImageView mContentImageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mUserNameView = itemView.findViewById(R.id.tv_user_name);
                mUserImageView = itemView.findViewById(R.id.imv_user_image);
                mCommentTextView = itemView.findViewById(R.id.tv_comment_content);
                mContentImageView = itemView.findViewById(R.id.imv_content_image);
            }
        }
    }

}
