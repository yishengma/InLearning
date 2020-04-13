package com.inlearning.app.director.speciality.classinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mStudentInfoRecyclerView;
    private StudentInfoAdapter mStudentInfoAdapter;
    private ClassInfo mClassInfo;
    private List<Student> mStudentList;
    private TextView mClassInfoView;
    private ImageView mAddView;
    private ImageView mSearchView;
    private TextView mEmptyView;

    public static void startActivity(Context context,ClassInfo classInfo) {
        Intent intent = new Intent(context, ClassInfoActivity.class);
        intent.putExtra("classInfo",classInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_info);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        mStudentInfoRecyclerView = findViewById(R.id.rv_student_info);
        mClassInfoView = findViewById(R.id.bar_title);
        mStudentList = new ArrayList<>();
        mStudentInfoAdapter = new StudentInfoAdapter(mStudentList);
        mStudentInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mStudentInfoRecyclerView.setAdapter(mStudentInfoAdapter);
        mAddView = findViewById(R.id.imv_bar_add);
        mSearchView = findViewById(R.id.imv_bar_search);
        mAddView.setOnClickListener(this);
        mSearchView.setOnClickListener(this);
        mStudentInfoAdapter.setClickListener(new StudentInfoAdapter.ClickListener() {
            @Override
            public void onClick(Student student) {
                StudentEditActivity.startActivity(ClassInfoActivity.this, student);

            }

            @Override
            public void onLongClick(View view, float x, float y, Student student) {
                showPopWindow(view, x, y, student);
            }
        });
        mEmptyView = findViewById(R.id.tv_empty);

    }

    private void getIntentData() {
        Intent intent = getIntent();
        mClassInfo = (ClassInfo) intent.getSerializableExtra("classInfo");
        mClassInfoView.setText(mClassInfo.getName());
    }

    private void initData() {
        getIntentData();
        ClassInfoModel.getStudents(mClassInfo, new ClassInfoModel.Callback<List<Student>>() {
            @Override
            public void onResult(boolean suc, List<Student> students) {
                if (suc) {
                    updateList(students);
                }
            }
        });
    }

    private void updateList(final List<Student> students) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                mStudentList.clear();
                mStudentList.addAll(students);
                mStudentInfoAdapter.notifyDataSetChanged();
                mEmptyView.setVisibility(mStudentList.isEmpty()?View.VISIBLE:View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_search:
                StudentSearchActivity.startSearchActivity(this, (ArrayList<Student>) mStudentList);
                break;
            case R.id.imv_bar_add:
                showDialog();
                break;
            default:
                break;
        }
    }


    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_import_way);
        TextView singleView = dialog.findViewById(R.id.tv_single_import);
        TextView excelView = dialog.findViewById(R.id.tv_excel_import);
        singleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentSingleImportActivity.startSingleImportActivity(ClassInfoActivity.this, mClassInfo);
                dialog.dismiss();
            }
        });
        excelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentExcelImportActivity.startExcelImportActivity(ClassInfoActivity.this, mClassInfo);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private PopupWindow mPopupWindow;

    private void showPopWindow(final View view, float x, float y, final Student student) {
        //获取PopWindow宽和高
        mPopupWindow = new PopupWindow(getLayoutInflater().inflate(R.layout.view_menu_window, null), 300, 100, true);
        mPopupWindow.setOutsideTouchable(true);//设置外部可点击取消
        mPopupWindow.setElevation(5);//设置阴影
//        mAddView.setOnClickListener(this);
        view.setBackgroundColor(getResources().getColor(R.color.app_global_simple_gray));
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int xoff = (int) x;
        int yoff = 0 - (view.getHeight() - (int) y) - mPopupWindow.getHeight() - 50;
        if (x > width / 2) {
            xoff = (int) x - width / 2 + 10;
            mPopupWindow.setAnimationStyle(R.style.AnimationRight);
        } else {
            mPopupWindow.setAnimationStyle(R.style.AnimationLeft);
        }
        mPopupWindow.showAsDropDown(view, xoff, yoff, Gravity.TOP);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                view.setBackgroundResource(R.drawable.bg_item_click);
            }
        });
        TextView msgView = mPopupWindow.getContentView().findViewById(R.id.tv_menu_msg);
        msgView.setText("删除该学生");
        msgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(student);
                mPopupWindow.dismiss();
            }
        });
    }

    private void showDialog(final Student student) {
        final Dialog dialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该学生信息？删除之后不可恢复！");
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
                deleteStudent(student);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void deleteStudent(final Student student) {
        ClassInfoModel.deleteStudent(student, new ClassInfoModel.Callback<Student>() {
            @Override
            public void onResult(boolean suc,final Student s) {
                if (suc) {
                    showToast("删除成功");
                    ThreadMgr.getInstance().postToUIThread(new Runnable() {
                        @Override
                        public void run() {
                            Iterator<Student> iterator = mStudentList.iterator();
                            while (iterator.hasNext()) {
                                Student student1 = iterator.next();
                                if (student1 == null) {
                                    continue;
                                }
                                if (student1.getObjectId().equals(student.getObjectId())) {
                                    iterator.remove();
                                    break;
                                }
                            }
                            mStudentInfoAdapter.notifyDataSetChanged();
                            mEmptyView.setVisibility(mStudentList.isEmpty()?View.VISIBLE:View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void showToast(final String msg) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ClassInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
