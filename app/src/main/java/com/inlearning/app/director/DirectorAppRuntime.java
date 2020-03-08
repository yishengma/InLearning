package com.inlearning.app.director;

import android.content.Context;

import com.inlearning.app.common.bean.Speciality;

import java.util.ArrayList;
import java.util.List;

public class DirectorAppRuntime {
    private static Context sContext;
    private static List<Speciality> sSpecialities;

    public static Context getApplicationContext() {
        return sContext;
    }

    public static void setApplicationContext(Context mContext) {
        DirectorAppRuntime.sContext = mContext;
    }

    public static List<Speciality> getSpecialities() {
        if (sSpecialities == null) {
            return new ArrayList<>();
        }
        return sSpecialities;
    }

    public static void setSpecialities(List<Speciality> specialities) {
        sSpecialities = specialities;
    }
}
