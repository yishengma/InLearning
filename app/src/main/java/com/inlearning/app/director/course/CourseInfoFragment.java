package com.inlearning.app.director.course;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.DirectorAppRuntime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourseInfoFragment extends BaseFragment{
    private RecyclerView mRvCourseInfo;
    private List<Course2> mCourseList;
    private CourseInfoAdapter mCourseInfoAdapter;
    private String mFragmentTitle;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_course_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvCourseInfo = view.findViewById(R.id.rv_content);
        mRvCourseInfo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mCourseList = new ArrayList<>();
        mCourseInfoAdapter = new CourseInfoAdapter(mCourseList);
        mRvCourseInfo.setAdapter(mCourseInfoAdapter);
        mCourseInfoAdapter.setClickListener(new CourseInfoAdapter.ClickListener() {
            @Override
            public void onClick(Course2 course) {
                CourseEditActivity.startActivity(getContext(),course);
            }

            @Override
            public void onLongClick(View view, float x, float y, Course2 course) {
                showPopWindow(view, x, y, course);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        CourseModel.getCourseList(new CourseModel.Callback<List<Course2>>() {
            @Override
            public void onResult(boolean suc, List<Course2> course2s) {
                mCourseList.clear();
                for (Course2 c : course2s) {
                    if (mFragmentTitle.contains(c.getType())) {
                        mCourseList.add(c);
                    }
                }
                DirectorAppRuntime.setCourse2s(course2s);
                mCourseInfoAdapter.notifyDataSetChanged();
            }
        });
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
        final Dialog dialog = new Dialog(getContext(), R.style.SimpleDialog);//SimpleDialog
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
                            mCourseInfoAdapter.notifyDataSetChanged();
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
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public CourseInfoFragment setFragmentTitle(String fragmentTitle) {
        mFragmentTitle = fragmentTitle;
        return this;
    }
}


