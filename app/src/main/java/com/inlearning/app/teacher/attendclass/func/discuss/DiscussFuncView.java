package com.inlearning.app.teacher.attendclass.func.discuss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Post;

import java.util.ArrayList;
import java.util.List;

public class DiscussFuncView extends RelativeLayout implements View.OnClickListener {

    public DiscussFuncView(Context context) {
        this(context, null);
    }

    public DiscussFuncView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscussFuncView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface ClickListener {
        void onBack();

        void onEditClick();
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    private RecyclerView mRvPost;
    private List<Post> mPosts;
    private PostAdapter mPostAdapter;
    private ImageView mEditPostView;
    private ImageView mBackView;
    private ImageView mFullImageView;
    private TextView mTitleView;
    private TextView mEmptyView;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_student_discuss_function, this);
        mRvPost = view.findViewById(R.id.rv_post);
        mRvPost.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mPosts = new ArrayList<>();
        mPostAdapter = new PostAdapter(mPosts);
        mRvPost.setAdapter(mPostAdapter);
        mEditPostView = view.findViewById(R.id.imv_bar_edit);
        mBackView = view.findViewById(R.id.imv_bar_back);
        mEditPostView.setOnClickListener(this);
        mEditPostView.setVisibility(GONE);
        mEditPostView.setEnabled(false);
        mBackView.setOnClickListener(this);
        mFullImageView = view.findViewById(R.id.imv_full_image);
        mTitleView = view.findViewById(R.id.tv_bar_title);
        mFullImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mFullImageView.setVisibility(GONE);
            }
        });
        mPostAdapter.setClickListener(new PostAdapter.ClickListener() {
            @Override
            public void onClicl(String path) {
                mFullImageView.setVisibility(VISIBLE);
                Glide.with(getContext()).load(path).into(mFullImageView);
            }
        });
        mEmptyView = view.findViewById(R.id.tv_empty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                if (mClickListener != null) {
                    mClickListener.onBack();
                }
                break;
            case R.id.imv_bar_edit:
                setVisibility(GONE);
                if (mClickListener != null) {
                    mClickListener.onEditClick();
                }
                break;
        }
    }

    public void setPosts(List<Post> posts) {
        Log.e("ethan", posts.size() + "");
        mPosts.clear();
        mPosts.addAll(posts);
        mPostAdapter.notifyDataSetChanged();
        mEmptyView.setVisibility(posts.isEmpty() ? VISIBLE : GONE);
    }

    public void update(Post posts) {
        mPosts.add(0, posts);
        mPostAdapter.notifyDataSetChanged();
    }

    public void setTitle(String msg) {
        mTitleView.setText(msg);
    }

    public static class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
        private List<Post> mPosts;

        public PostAdapter(List<Post> posts) {
            mPosts = posts;
        }


        public interface ClickListener {
            void onClicl(String path);
        }

        private ClickListener mClickListener;

        public void setClickListener(ClickListener clickListener) {
            mClickListener = clickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_func_discuss, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Post post = mPosts.get(i);
//            Glide.with(viewHolder.itemView.getContext()).load(post.getStudent().getProfilePhotoUrl()).into(viewHolder.mUserImageView);
            if (!TextUtils.isEmpty(post.getImageUrl())) {
                Glide.with(viewHolder.itemView.getContext()).load(post.getImageUrl()).into(viewHolder.mContentImageView);
                viewHolder.mContentImageView.setVisibility(VISIBLE);
                viewHolder.mContentImageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) {
                            mClickListener.onClicl(post.getImageUrl());
                        }
                    }
                });
            } else {
                viewHolder.mContentImageView.setVisibility(GONE);
            }
            viewHolder.mPostTitleView.setText(post.getTitle());
            viewHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiscussDetailActivity.startActivity(viewHolder.itemView.getContext(), post);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView mUserImageView;
            private TextView mPostTitleView;
            private ImageView mContentImageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mUserImageView = itemView.findViewById(R.id.imv_user_image);
                mPostTitleView = itemView.findViewById(R.id.tv_post_title);
                mContentImageView = itemView.findViewById(R.id.imv_content_image);
            }
        }
    }

    public boolean onBackPressed() {
        if (mFullImageView.getVisibility() == VISIBLE) {
            mFullImageView.setVisibility(GONE);
            return true;
        }
        return false;
    }
}
