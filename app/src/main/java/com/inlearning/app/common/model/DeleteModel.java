package com.inlearning.app.common.model;

import com.inlearning.app.common.bean.Answer;
import com.inlearning.app.common.bean.ChapterProgress;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Comment;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.HomeworkProgress;
import com.inlearning.app.common.bean.Materials;
import com.inlearning.app.common.bean.Post;
import com.inlearning.app.common.bean.Question;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.bean.SpecialitySchedule;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.bean.TeacherCourse;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;

public class DeleteModel {

    public static void deleteAnswer(String key, BmobObject object) {
        BmobQuery<Answer> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteChapterProgress(String key, BmobObject object) {
        BmobQuery<ChapterProgress> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<ChapterProgress>() {
            @Override
            public void done(List<ChapterProgress> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteMaterial(String key, BmobObject object) {
        BmobQuery<Materials> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<Materials>() {
            @Override
            public void done(List<Materials> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteClassInfo(String key, BmobObject object) {
        BmobQuery<ClassInfo> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<ClassInfo>() {
            @Override
            public void done(List<ClassInfo> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteClassSchedule(String key, BmobObject object) {
        BmobQuery<ClassSchedule> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<ClassSchedule>() {
            @Override
            public void done(List<ClassSchedule> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteComment(String key, BmobObject object) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteChapter(String key, BmobObject object) {
        BmobQuery<CourseChapter> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<CourseChapter>() {
            @Override
            public void done(List<CourseChapter> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteStudent(String key, BmobObject object) {
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteTeacher(String key, BmobObject object) {
        BmobQuery<Teacher> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<Teacher>() {
            @Override
            public void done(List<Teacher> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }


    public static void deleteCourse(String key, BmobObject object) {
        BmobQuery<Course2> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<Course2>() {
            @Override
            public void done(List<Course2> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }


    public static void deletePost(String key, BmobObject object) {
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteQuestion(String key, BmobObject object) {
        BmobQuery<Question> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteSpeciality(String key, BmobObject object) {
        BmobQuery<Speciality> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<Speciality>() {
            @Override
            public void done(List<Speciality> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteSpecialitySchedule(String key, BmobObject object) {
        BmobQuery<SpecialitySchedule> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<SpecialitySchedule>() {
            @Override
            public void done(List<SpecialitySchedule> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

    public static void deleteTeacherCourse(String key, BmobObject object) {
        BmobQuery<TeacherCourse> query = new BmobQuery<>();
        query.addWhereEqualTo(key, object);
        query.findObjects(new FindListener<TeacherCourse>() {
            @Override
            public void done(List<TeacherCourse> list, BmobException e) {
                new BmobBatch().deleteBatch(new ArrayList<>(list)).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {

                    }
                });
            }
        });
    }

}
