package com.inlearning.app.director.teacher;

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
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.BaseSearchActivity;
import com.inlearning.app.director.DirectorAppRuntime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TeacherSearchActivity extends BaseSearchActivity {

    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, TeacherSearchActivity.class);
        context.startActivity(intent);
    }

    private List<Teacher> mTeacherList;
    private TeacherInfoAdapter mInfoAdapter;
    private PopupWindow mPopupWindow;

    @Override
    protected void initAdapter() {
        mTeacherList = new ArrayList<>();
        mInfoAdapter = new TeacherInfoAdapter(mTeacherList);
        mInfoAdapter.setClickListener(new TeacherInfoAdapter.ClickListener() {
            @Override
            public void onClick(Teacher tea) {
                TeacherEditActivity.startActivity(TeacherSearchActivity.this, tea);
            }

            @Override
            public void onLongClick(View view, float x, float y, Teacher tea) {
                showPopWindow(view, x, y, tea);
            }
        });
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return mInfoAdapter;
    }

    @Override
    protected void doSearch(String key) {
        mTeacherList.clear();
        List<Teacher> teachers = DirectorAppRuntime.getTeachers();
        for (Teacher tea : teachers) {
            if (tea.getName().contains(key) || tea.getJobNumber().contains(key)) {
                mTeacherList.add(tea);
            }
        }
        mInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void resetList() {
        mTeacherList.clear();
        mInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected String editHint() {
        return "搜索教师";
    }


    private void showPopWindow(final View view, float x, float y, final Teacher teacher) {
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
        msgView.setText("删除该教师");
        msgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(teacher);
                mPopupWindow.dismiss();
            }
        });
    }

    private void showDialog(final Teacher teacher) {
        final Dialog dialog = new Dialog(TeacherSearchActivity.this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_excel_import);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该教师信息？删除之后不可恢复！");
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
                deleteTeacher(teacher);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteTeacher(final Teacher teacher2) {
        TeacherModel.deleteTeacher(teacher2, new TeacherModel.Callback<Teacher>() {
            @Override
            public void onResult(boolean suc, Teacher teacher) {
                if (suc) {
                    showToast("删除成功");
                    updateList(teacher2);
                }
            }
        });
    }

    private void showToast(final String msg) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeacherSearchActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateList(final Teacher teacher) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Iterator<Teacher> iterator = mTeacherList.iterator();
                while (iterator.hasNext()) {
                    Teacher teacher1 = iterator.next();
                    if (teacher1 == null) {
                        continue;
                    }
                    if (teacher1.getObjectId().equals(teacher.getObjectId())) {
                        iterator.remove();
                        break;
                    }
                }
                mInfoAdapter.notifyDataSetChanged();
            }
        });
    }
}
