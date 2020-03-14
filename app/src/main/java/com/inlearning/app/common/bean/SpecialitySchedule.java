package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class SpecialitySchedule extends BmobObject {

    private Speciality mSpeciality;
    private Course2 mCourse2;

    public Speciality getSpeciality() {
        return mSpeciality;
    }

    public SpecialitySchedule setSpeciality(Speciality speciality) {
        mSpeciality = speciality;
        return this;
    }

    public Course2 getCourse2() {
        return mCourse2;
    }

    public SpecialitySchedule setCourse2(Course2 course2) {
        mCourse2 = course2;
        return this;
    }
}
