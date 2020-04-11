package com.inlearning.app.teacher.attendclass.func.discuss;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Comment;
import com.inlearning.app.common.bean.Post;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.common.util.PhotoUtils;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.TakePhotoUtil;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.student.StudentRuntime;
import com.inlearning.app.teacher.TeacherRuntime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    private TextView mCommentView;
    private ImageView mFullImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        setContentView(R.layout.activity_discuss_detail);
        getIntentData();
        initView();
        getComments();
        initDialog();
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
        mCommentView = findViewById(R.id.tv_comment);
        mFullImageView = findViewById(R.id.imv_full_image);
        mFullImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFullImageView.setVisibility(GONE);
            }
        });
        mComments = new ArrayList<>();
        mCommentAdapter = new CommentAdapter(mComments);
        mRvComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvComment.setAdapter(mCommentAdapter);
        mCommentAdapter.setClickListener(new CommentAdapter.ClickListener() {
            @Override
            public void onClick(String path) {
                mFullImageView.setVisibility(VISIBLE);
                Glide.with(DiscussDetailActivity.this).load(path).into(mFullImageView);
            }

            @Override
            public void onDelete(Comment comment) {
                showDialog(comment);
            }
        });
        mBackView.setOnClickListener(this);
        mStudentNameView.setText(mPost.getStudent().getName());
        mPostTitleView.setText(mPost.getTitle());
        mPostContentView.setText(mPost.getContent());
        if (!TextUtils.isEmpty(mPost.getImageUrl())) {
            mPostImageView.setVisibility(VISIBLE);
            Glide.with(this).load(mPost.getImageUrl()).into(mPostImageView);
        }
        mCommentView.setOnClickListener(this);
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
            case R.id.tv_comment:
                showCommentDialog();
                break;
        }
    }


    private void showDialog(final Comment comment) {
        final Dialog dialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该评论？删除之后不可恢复！");
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
                deleteComment(comment);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteComment(Comment comment) {
        DiscussModel.deleteComment(comment, new com.inlearning.app.student.course.func.discuss.DiscussModel.Callback<Comment>() {
            @Override
            public void onResult(Comment comment) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mComments.remove(comment);
                        mCommentAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    static class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

        private List<Comment> mComments;

        public CommentAdapter(List<Comment> comments) {
            mComments = comments;
        }

        public interface ClickListener {
            void onClick(String path);

            void onDelete(Comment comment);
        }

        private ClickListener mClickListener;

        public void setClickListener(ClickListener clickListener) {
            mClickListener = clickListener;
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
                Log.e("ethan", comment.getStudent().getName());
                viewHolder.mUserNameView.setText(comment.getStudent().getName());
                if (comment.getStudent().getObjectId() != null && comment.getStudent().getObjectId().equals(StudentRuntime.getStudent().getObjectId())) {
                    viewHolder.mDeleteView.setVisibility(VISIBLE);
                } else {
                    viewHolder.mDeleteView.setVisibility(GONE);
                }
            }
            if (comment.getTeacher() != null) {
                Log.e("ethan", comment.getTeacher().getName());
                viewHolder.mUserNameView.setText(comment.getTeacher().getName());
                if (comment.getTeacher().getObjectId() != null && comment.getTeacher().getObjectId().equals(TeacherRuntime.getCurrentTeacher().getObjectId())) {
                    viewHolder.mDeleteView.setVisibility(VISIBLE);
                } else {
                    viewHolder.mDeleteView.setVisibility(GONE);
                }

            }
            viewHolder.mDeleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onDelete(comment);
                    }
                }
            });

            if (!TextUtils.isEmpty(comment.getImageUrl())) {
                viewHolder.mContentImageView.setVisibility(VISIBLE);
                Glide.with(viewHolder.itemView.getContext()).load(comment.getImageUrl()).into(viewHolder.mContentImageView);
                viewHolder.mContentImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) {
                            mClickListener.onClick(comment.getImageUrl());
                        }
                    }
                });
            } else {
                viewHolder.mContentImageView.setVisibility(GONE);
            }
            if (!TextUtils.isEmpty(comment.getContent())) {
                viewHolder.mCommentTextView.setText(comment.getContent());
                viewHolder.mCommentTextView.setVisibility(VISIBLE);
            } else {
                viewHolder.mCommentTextView.setVisibility(GONE);
            }

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
            private TextView mDeleteView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mUserNameView = itemView.findViewById(R.id.tv_user_name);
                mUserImageView = itemView.findViewById(R.id.imv_user_image);
                mCommentTextView = itemView.findViewById(R.id.tv_comment_content);
                mContentImageView = itemView.findViewById(R.id.imv_content_image);
                mDeleteView = itemView.findViewById(R.id.tv_delete);
            }
        }
    }

    private Dialog mCommentDialog;
    private TextInputEditText mInputCommentText;
    private TextView mImageAddView;
    private ImageView mCommentImageView;
    private ImageView mDeleteImageView;
    private FrameLayout mCommentImageLayout;
    private TextView mSendCommentView;
    private String mImageFilePath;

    private void initDialog() {
        mCommentDialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        mCommentDialog.setContentView(R.layout.dialog_discuss_comment);
        mCommentDialog.setCanceledOnTouchOutside(true);
        mInputCommentText = mCommentDialog.findViewById(R.id.et_content);
        mImageAddView = mCommentDialog.findViewById(R.id.tv_add_image);
        mCommentImageView = mCommentDialog.findViewById(R.id.imv_comment_image);
        mDeleteImageView = mCommentDialog.findViewById(R.id.imv_image_delete);
        mCommentImageLayout = mCommentDialog.findViewById(R.id.view_comment_image);
        mSendCommentView = mCommentDialog.findViewById(R.id.tv_send_comment);
        mCommentDialog.setCanceledOnTouchOutside(true);
        mImageAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickDialog();
            }
        });
        mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPostImageVisible(false);
                mImageFilePath = "";
                mImageAddView.setVisibility(VISIBLE);
            }
        });
        mCommentDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mCommentView.setVisibility(VISIBLE);
            }
        });
        mSendCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mInputCommentText.getText().toString();
                String path = mImageFilePath;
                if (TextUtils.isEmpty(content) && TextUtils.isEmpty(path)) {
                    Toast.makeText(DiscussDetailActivity.this, "请输入内容或图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setImageUrl(path);
                comment.setStudent(StudentRuntime.getStudent());
                comment.setTeacher(TeacherRuntime.getCurrentTeacher());
                comment.setPost(mPost);
                sendComment(comment);
            }
        });
    }

    private void sendComment(Comment comment) {
        DiscussModel.sendComment(comment, new DiscussModel.Callback<Comment>() {
            @Override
            public void onResult(Comment comment) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mComments.add(0, comment);
                        mCommentAdapter.notifyDataSetChanged();
                        mCommentDialog.dismiss();
                    }
                });
            }
        });
    }

    public void showCommentDialog() {
        mCommentDialog.show();
        mCommentView.setVisibility(GONE);
    }

    private void setPostImageVisible(boolean show) {
        mCommentImageLayout.setVisibility(show ? VISIBLE : GONE);
    }

    public void showPickDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        bottomDialog.setContentView(R.layout.dialog_pick_image);
        TextView albumView = bottomDialog.findViewById(R.id.tv_album);
        TextView takePhotoView = bottomDialog.findViewById(R.id.tv_take_photo);
        TextView cancelView = bottomDialog.findViewById(R.id.tv_cancel);
        albumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoObtainStoragePermission();
                bottomDialog.dismiss();
            }
        });
        takePhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoObtainCameraPermission();
                bottomDialog.dismiss();
            }
        });
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }


    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private String mTakePhotoPath = "";

    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                mTakePhotoPath = TakePhotoUtil.takePhoto(this, CODE_CAMERA_REQUEST);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        mTakePhotoPath = TakePhotoUtil.takePhoto(this, CODE_CAMERA_REQUEST);
                    }
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                }
                break;
            default:
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST:
                    Log.e("ethan", mTakePhotoPath);
                    setQuesImage(new File(mTakePhotoPath));
                    break;
                case CODE_GALLERY_REQUEST:
                    String path = FileUtil.getChooseFileResultPath(this, data.getData());
                    Log.e("ethan", path);
                    setQuesImage(new File(path));
                    break;
                default:
            }
        }
    }

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    public boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    public void setQuesImage(File fileCropUri) {
        mCommentImageLayout.setVisibility(VISIBLE);
        Glide.with(this).load(fileCropUri).into(mCommentImageView);
        mImageFilePath = fileCropUri.getPath();
        mCommentImageView.setImageBitmap(BitmapFactory.decodeFile(fileCropUri.getPath()));
        mImageAddView.setVisibility(GONE);
    }

    @Override
    public void onBackPressed() {
        if (mFullImageView.getVisibility() == VISIBLE) {
            mFullImageView.setVisibility(GONE);
            return;
        }
        super.onBackPressed();
    }
}
