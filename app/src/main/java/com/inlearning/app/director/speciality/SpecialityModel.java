package com.inlearning.app.director.speciality;

import android.util.Log;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.bean.SpecialitySchedule;
import com.inlearning.app.common.model.DeleteModel;
import com.inlearning.app.director.DirectorAppRuntime;

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

public class SpecialityModel {
    private static final String TAG = "SpecialityModel";

    public interface Callback<T> {
        void onResult(boolean suc, T t);
    }

    public static void addSpeciality(final Speciality speciality, final Callback<Speciality> callback) {
        speciality.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
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
                if (e == null) {
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
                if (e == null) {
                    callback.onResult(true, speciality);
                    speciality.increment("mClassCount", classInfos.size());
                    speciality.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            speciality.setClassCount(speciality.getClassCount() + classInfos.size());
                        }
                    });
                }
            }
        });

    }

    public static void deleteSpeciality(final Speciality speciality, final Callback<Speciality> callback) {
        speciality.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(true, speciality);
                    DeleteModel.deleteSpecialitySchedule("mSpeciality", speciality);
                    DeleteModel.deleteClassInfo("mSpeciality", speciality);
                }
            }
        });
    }

    public static void updateSpeciality(final Speciality speciality, final Callback<Speciality> callback) {
        Speciality update = new Speciality();
        update.setName(speciality.getName())
                .setShortName(speciality.getShortName())
                .setClassCount(speciality.getClassCount());
        update.update(speciality.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(true, speciality);
                }
            }
        });
    }


    public static void getSpeciality(final Callback<List<Speciality>> callback) {
        final BmobQuery<Speciality> specialityBmobQuery = new BmobQuery<>();
        specialityBmobQuery.findObjects(new FindListener<Speciality>() {
            @Override
            public void done(final List<Speciality> list, BmobException e) {
                if (e != null) {
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (Speciality s : list) {

                            BmobQuery<ClassInfo> classInfoBmobQuery = new BmobQuery<>();
                            classInfoBmobQuery.addWhereEqualTo("mSpeciality", s);
                            List<ClassInfo> classInfos = classInfoBmobQuery.findObjectsSync(ClassInfo.class);
                            DirectorAppRuntime.setsClassInfo(s, classInfos);
                        }
                        callback.onResult(true, list);
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
                classInfo.getSpeciality().increment("mClassCount", -1);
                classInfo.getSpeciality().update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            classInfo.getSpeciality().setClassCount(classInfo.getSpeciality().getClassCount() - 1);
                        }
                    }
                });
                DeleteModel.deleteAnswer("mClassInfo", classInfo);
                DeleteModel.deleteChapterProgress("mClassInfo", classInfo);
                DeleteModel.deleteClassSchedule("mClassInfo", classInfo);
            }
        });
    }
}
