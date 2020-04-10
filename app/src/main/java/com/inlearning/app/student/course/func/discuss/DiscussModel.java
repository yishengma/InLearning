package com.inlearning.app.student.course.func.discuss;

import android.text.TextUtils;

import com.inlearning.app.common.bean.Comment;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Post;
import com.inlearning.app.common.bean.Student;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class DiscussModel {

    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getDiscussPost(CourseChapter chapter, Callback<List<Post>> callback) {
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("mChapter", chapter);
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                callback.onResult(list);
            }
        });
    }


    public static void sendPost(Post post, Callback<Post> callback) {
        if (!TextUtils.isEmpty(post.getImageUrl())) {
            BmobFile bmobFile = new BmobFile(new File(post.getImageUrl()));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e != null) {
                        return;
                    }
                    post.setImageUrl(bmobFile.getUrl());
                    post.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            post.setObjectId(s);
                            callback.onResult(post);
                        }
                    });
                }
            });
        } else {
            post.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    post.setObjectId(s);
                    callback.onResult(post);
                }
            });
        }
    }

    public static void deletePost(Post post, Callback<Post> callback) {
        post.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(post);
                }
            }
        });
    }

    public static void getComment(Post post, Callback<List<Comment>> callback) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("mPost", post);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                    callback.onResult(list);
            }
        });
    }


}
