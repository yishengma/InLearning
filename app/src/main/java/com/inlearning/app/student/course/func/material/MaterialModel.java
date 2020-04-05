package com.inlearning.app.student.course.func.material;



import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Materials;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MaterialModel {

    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getMaterialByChapter(CourseChapter chapter, final Callback<List<Materials>> callback) {
        BmobQuery<Materials> query = new BmobQuery<>();
        query.addWhereEqualTo("mChapter", chapter);
        query.findObjects(new FindListener<Materials>() {
            @Override
            public void done(List<Materials> list, BmobException e) {
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });
    }

    public static void deleteMaterial(final Materials materials, final Callback<Boolean> callback) {
        materials.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                callback.onResult(e == null);
                materials.getChapter().increment("mMaterialCount", -1);
                materials.getChapter().update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
        });
    }

    public static void addMaterial(final CourseChapter chapter, final Materials materials, final Callback<Materials> callback) {
        materials.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    materials.setObjectId(s);
                    chapter.increment("mMaterialCount", 1);
                    chapter.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            callback.onResult(materials);
                        }
                    });
                }
            }
        });
    }
}
