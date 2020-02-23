package com.inlearning.app.director.speciality;

import android.util.Log;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Speciality;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SpecialityModel {
    private static final String TAG = "SpecialityModel";

    public interface Callback<T> {
        void onResult(boolean suc, T t);
    }

    public static void addSpeciality(String name, final String shortName,int count, final Callback<Speciality> callback) {
        final Speciality speciality = new Speciality();
        speciality.setName(name);
        speciality.setShortName(shortName);
        speciality.setClassCount(count);
        List<ClassInfo> classInfos = new ArrayList<>(count);
        for (int i = 1; i <= count ; i++) {
            classInfos.add(new ClassInfo().setName(String.format(name+" %s 班",i)));
        }
        speciality.setClassInfoList(classInfos);
        speciality.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                Log.i(TAG, "done: %s", e);
                if (e == null) {
                    speciality.setObjectId(objectId);
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

    public static void changeSpeciality(final Speciality speciality, final Callback<Speciality> callback) {
        speciality.update(new UpdateListener() {
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
        BmobQuery<Speciality> specialityBmobQuery = new BmobQuery<>();
        specialityBmobQuery.findObjects(new FindListener<Speciality>() {
            @Override
            public void done(List<Speciality> list, BmobException e) {
                Log.i(TAG, "done: list " + (list == null ? 0 : list.size()));
                if (e == null) {
                    callback.onResult(true, list);
                }
            }
        });
    }
}
