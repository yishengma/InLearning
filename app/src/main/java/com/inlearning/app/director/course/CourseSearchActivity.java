package com.inlearning.app.director.course;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.BaseSearchActivity;
import com.inlearning.app.director.DirectorAppRuntime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourseSearchActivity extends BaseSearchActivity {

    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, CourseSearchActivity.class);
        context.startActivity(intent);
    }

    private List<Course2> mCourseList;
    private CourseInfoAdapter mInfoAdapter;

    @Override
    protected void initAdapter() {
        mCourseList = new ArrayList<>();
        mInfoAdapter = new CourseInfoAdapter(mCourseList);
        mInfoAdapter.setClickListener(new CourseInfoAdapter.ClickListener() {
            @Override
            public void onClick(Course2 course) {
                CourseEditActivity.startActivity(CourseSearchActivity.this, course);
            }

            @Override
            public void onLongClick(View view, float x, float y, Course2 course) {
                showPopWindow(view, x, y, course);
            }
        });
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return mInfoAdapter;
    }

    @Override
    protected void doSearch(String key) {
        mCourseList.clear();
        List<Course2> course2s = DirectorAppRuntime.getCourse2s();
        for (Course2 course2 : course2s) {
            if (course2.getName().contains(key)) {
                mCourseList.add(course2);
            }
        }
        mInfoAdapter.notifyDataSetChanged();
        setEmptyView(mCourseList);
    }

    @Override
    protected void resetList() {
        mCourseList.clear();
        mInfoAdapter.notifyDataSetChanged();
        setEmptyView(mCourseList);
    }

    @Override
    protected String editHint() {
        return "搜索课程";
    }


    private PopupWindow mPopupWindow;

    private void showPopWindow(final View view, float x, float y, final Course2 course2) {
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
        msgView.setText("删除该课程");
        msgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(course2);
                mPopupWindow.dismiss();
            }
        });
    }

    private void showDialog(final Course2 course2) {
        final Dialog dialog = new Dialog(CourseSearchActivity.this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该专业信息？删除之后不可恢复！");
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
                deleteCourse(course2);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void deleteCourse(final Course2 course2) {
        CourseModel.deleteCourse(course2, new CourseModel.Callback<Course2>() {
            @Override
            public void onResult(boolean suc, final Course2 course) {
                if (suc) {
                    showToast("删除成功");
                    ThreadMgr.getInstance().postToUIThread(new Runnable() {
                        @Override
                        public void run() {
                            Iterator<Course2> iterator = mCourseList.iterator();
                            while (iterator.hasNext()) {
                                Course2 course = iterator.next();
                                if (course == null) {
                                    continue;
                                }
                                if (course.getObjectId().equals(course2.getObjectId())) {
                                    iterator.remove();
                                    break;
                                }
                            }
                            mInfoAdapter.notifyDataSetChanged();
                            setEmptyView(mCourseList);
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
                Toast.makeText(CourseSearchActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
