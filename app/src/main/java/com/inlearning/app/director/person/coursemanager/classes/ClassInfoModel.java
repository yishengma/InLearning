package com.inlearning.app.director.person.coursemanager.classes;

import android.util.Log;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Speciality;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ClassInfoModel {
    private static final String TAG = "ClassInfoModel";

    public interface Callback<T> {
        void onResult(boolean suc, T t);
    }

    public static void addSpeciality(final Speciality speciality,final Callback<Speciality> callback) {
        speciality.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                Log.i(TAG, "done: %s", e);
                if (e == null) {
                    speciality.setObjectId(objectId);
                    addClassInfo(speciality, callback);
                }
            }
        });
    }

    private static void addClassInfo(final Speciality speciality, final Callback<Speciality> callback) {
        final List<ClassInfo> classInfos = new ArrayList<>(speciality.getClassCount());
        List<BmobObject> bmobObjects = new ArrayList<>(speciality.getClassCount());
        for (int i = 1; i <= speciality.getClassCount(); i++) {
            ClassInfo classInfo = new ClassInfo().setName(String.format(speciality.getName() + " %s 班", i)).setSpeciality(speciality);
            classInfos.add(classInfo);
            bmobObjects.add(classInfo);
        }
        new BmobBatch().insertBatch(bmobObjects).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                Log.i(TAG, "done: %s", e);
                if (e == null) {
                    speciality.setClassInfoList(classInfos);
                    callback.onResult(true, speciality);
                }
            }
        });

    }

    public static void addClasses(final Speciality speciality, int count, final Callback<Speciality> callback) {
        final List<ClassInfo> classInfos = new ArrayList<>(count);
        List<BmobObject> bmobObjects = new ArrayList<>(count);
        Speciality bmobNewSpeciality = new Speciality();
        bmobNewSpeciality.setClassCount(speciality.getClassCount())
                .setName(speciality.getName())
                .setShortName(speciality.getShortName())
                .setObjectId(speciality.getObjectId());
        for (int i = speciality.getClassCount() + 1; i <= speciality.getClassCount() + count; i++) {
            ClassInfo classInfo = new ClassInfo().setName(String.format(speciality.getName() + " %s 班", i)).setSpeciality(bmobNewSpeciality);
            classInfos.add(classInfo);
            bmobObjects.add(classInfo);
        }
        new BmobBatch().insertBatch(bmobObjects).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                Log.i(TAG, "done: %s", e);
                if (e == null) {
                    speciality.addClassInfoList(classInfos);
                    callback.onResult(true, speciality);
                }
            }
        });

    }

    public static void deleteSpeciality(final Speciality speciality, final Callback<Speciality> callback) {
        speciality.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Log.i(TAG, "done: %s", e);
                if (e == null) {
                    callback.onResult(true, speciality);
                }
            }
        });
    }

    public static void updateSpeciality(final Speciality speciality, final Callback<Speciality> callback) {
        Speciality update = new Speciality();
        update.setName(speciality.getName())
                .setShortName(speciality.getShortName())
                .setClassCount(speciality.getClassCount());
        update.update(speciality.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Log.i(TAG, "done: %s", e);
                if (e == null) {
                    callback.onResult(true, speciality);
                }
            }
        });
    }


    public static void getSpeciality(final Callback<List<Speciality>> callback) {
        Log.i(TAG, "getSpeciality done: list getSpeciality");

        final BmobQuery<Speciality> specialityBmobQuery = new BmobQuery<>();
        specialityBmobQuery.findObjects(new FindListener<Speciality>() {
            @Override
            public void done(final List<Speciality> list, BmobException e) {
                Log.i(TAG, "getSpeciality done: list " + (list == null ? 0 : list.size()));
                if (e != null) {
                    Log.i(TAG, "getSpeciality done: list "+e );
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (Speciality s:list) {

                            BmobQuery<ClassInfo> classInfoBmobQuery = new BmobQuery<>();
                            classInfoBmobQuery.addWhereEqualTo("mSpeciality",s);
                            List<ClassInfo> classInfos = classInfoBmobQuery.findObjectsSync(ClassInfo.class);
                            Log.i(TAG, "ClassInfo done: list "+classInfos.size()+s.getObjectId());
                            s.setClassInfoList(classInfos);
                        }
                        callback.onResult(true,list);
                    }
                }).start();
            }
        });
    }

    public static void deleteClass(ClassInfo classInfo, final Callback<ClassInfo> callback) {
        classInfo.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(true, null);
                }
            }
        });
    }
}
