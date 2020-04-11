package com.inlearning.app.student.person;

import com.inlearning.app.common.bean.Post;
import com.inlearning.app.common.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MineDiscussModel {

    public interface Callback<T> {
        void onResult(T t);
    }


    public static void getMinePost(Student student, Callback<List<Post>> callback) {
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("mStudent", student);
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });
    }

    public static void deletePost(Post post,Callback<Boolean> callback) {
        post.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                callback.onResult(e == null);
            }
        });
    }
}
