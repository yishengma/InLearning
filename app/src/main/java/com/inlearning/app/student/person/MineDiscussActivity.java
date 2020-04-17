package com.inlearning.app.student.person;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Post;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.student.StudentHomeActivity;
import com.inlearning.app.student.StudentRuntime;
import com.inlearning.app.student.course.func.discuss.DiscussDetailActivity;
import com.inlearning.app.student.course.func.discuss.DiscussFuncView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MineDiscussActivity extends AppCompatActivity implements View.OnClickListener {


    public static void startMineDiscussActivity(Context context) {
        Intent intent = new Intent(context, MineDiscussActivity.class);
        context.startActivity(intent);
    }


    private RecyclerView mRvPost;
    private List<Post> mPosts;
    private PostAdapter mPostAdapter;
    private ImageView mEditPostView;
    private ImageView mBackView;
    private ImageView mFullImageView;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_discuss);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initView();
        initData();
    }

    private void initView() {
        mRvPost = findViewById(R.id.rv_post);
        mRvPost.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPosts = new ArrayList<>();
        mPostAdapter = new PostAdapter(mPosts);
        mRvPost.setAdapter(mPostAdapter);
        mEditPostView = findViewById(R.id.imv_bar_edit);
        mBackView = findViewById(R.id.imv_bar_back);
        mEditPostView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mFullImageView = findViewById(R.id.imv_full_image);
        mFullImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFullImageView.setVisibility(GONE);
            }
        });
        mPostAdapter.setClickListener(new PostAdapter.ClickListener() {
            @Override
            public void onClick(String path) {
                mFullImageView.setVisibility(VISIBLE);
                Glide.with(MineDiscussActivity.this).load(path).into(mFullImageView);
            }

            @Override
            public void onDelete(Post post) {
                showDialog(post);
            }
        });
        mEmptyView = findViewById(R.id.tv_empty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
            case R.id.imv_bar_edit:
                break;
        }
    }

    public void initData() {
        MineDiscussModel.getMinePost(StudentRuntime.getStudent(), new MineDiscussModel.Callback<List<Post>>() {
            @Override
            public void onResult(List<Post> posts) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        setPosts(posts);
                    }
                });
            }
        });
    }

    public void setPosts(List<Post> posts) {
        mPosts.clear();
        mPosts.addAll(posts);
        mPostAdapter.notifyDataSetChanged();
        mEmptyView.setVisibility(mPosts.isEmpty() ? VISIBLE : GONE);
    }

    public void update(Post posts) {
        mPosts.add(0, posts);
        mPostAdapter.notifyDataSetChanged();
        mEmptyView.setVisibility(mPosts.isEmpty() ? VISIBLE : GONE);
    }


    private void showDialog(Post post) {
        final Dialog dialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该提问？删除之后不可恢复！");
        TextView cancelView = dialog.findViewById(R.id.tv_cancel);
        TextView confirmView = dialog.findViewById(R.id.tv_confirm);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                deletePost(post);
            }
        });
        dialog.show();
    }

    private void deletePost(Post post) {
        MineDiscussModel.deletePost(post, new MineDiscussModel.Callback<Boolean>() {
            @Override
            public void onResult(Boolean aBoolean) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mPosts.remove(post);
                        mPostAdapter.notifyDataSetChanged();
                        mEmptyView.setVisibility(mPosts.isEmpty() ? VISIBLE : GONE);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mFullImageView.getVisibility() == VISIBLE) {
            mFullImageView.setVisibility(GONE);
            return;
        }
        super.onBackPressed();
    }


    public static class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
        private List<Post> mPosts;
        private Context mContext;

        public PostAdapter(List<Post> posts) {
            mPosts = posts;
        }


        public interface ClickListener {
            void onClick(String path);

            void onDelete(Post post);
        }

        private ClickListener mClickListener;

        public void setClickListener(ClickListener clickListener) {
            mClickListener = clickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            mContext = viewGroup.getContext();
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_func_discuss, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Post post = mPosts.get(i);
//            Glide.with(viewHolder.itemView.getContext()).load(post.getStudent().getProfilePhotoUrl()).into(viewHolder.mUserImageView);
            if (!TextUtils.isEmpty(post.getImageUrl())) {
                Glide.with(viewHolder.itemView.getContext()).load(post.getImageUrl()).into(viewHolder.mContentImageView);
                viewHolder.mContentImageView.setVisibility(VISIBLE);
                viewHolder.mContentImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) {
                            mClickListener.onClick(post.getImageUrl());
                        }
                    }
                });
            } else {
                viewHolder.mContentImageView.setVisibility(GONE);
            }
            viewHolder.mDeleteView.setVisibility(VISIBLE);
            viewHolder.mDeleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onDelete(post);
                    }
                }
            });
            viewHolder.mPostTitleView.setText(post.getTitle());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiscussDetailActivity.startActivity(viewHolder.itemView.getContext(), post);
                }
            });
            if (post.getStudent() != null && !TextUtils.isEmpty(post.getStudent().getProfilePhotoUrl())) {
                Glide.with(mContext).load(post.getStudent().getProfilePhotoUrl()).into(viewHolder.mUserImageView);
            } else {
                viewHolder.mUserImageView.setBackground(mContext.getDrawable(R.drawable.viewpage_guide_3));
            }
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView mUserImageView;
            private TextView mPostTitleView;
            private ImageView mContentImageView;
            private TextView mDeleteView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mUserImageView = itemView.findViewById(R.id.imv_user_image);
                mPostTitleView = itemView.findViewById(R.id.tv_post_title);
                mContentImageView = itemView.findViewById(R.id.imv_content_image);
                mDeleteView = itemView.findViewById(R.id.tv_delete);
            }
        }

    }
}