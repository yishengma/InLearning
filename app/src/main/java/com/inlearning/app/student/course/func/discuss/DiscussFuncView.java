package com.inlearning.app.student.course.func.discuss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Post;

import java.util.List;

public class DiscussFuncView extends RelativeLayout {

    public DiscussFuncView(Context context) {
        this(context, null);
    }

    public DiscussFuncView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscussFuncView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private RecyclerView mRvPost;
    private List<Post> mPosts;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_student_discuss_function, this);
        mRvPost = view.findViewById(R.id.rv_post);
        mRvPost.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }


    public static class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
        private List<Post> mPosts;

        public PostAdapter(List<Post> posts) {
            mPosts = posts;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(0,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
