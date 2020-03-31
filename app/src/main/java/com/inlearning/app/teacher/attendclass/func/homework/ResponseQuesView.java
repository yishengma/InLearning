package com.inlearning.app.teacher.attendclass.func.homework;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;

public class ResponseQuesView extends RelativeLayout {

    public ResponseQuesView(Context context) {
        this(context, null);
    }

    public ResponseQuesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResponseQuesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private TextInputEditText mInputEditText;
    private TextView mAddView;
    private ImageView mQuesImageView;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_homework_response_question, this);
        mInputEditText = view.findViewById(R.id.et_question_input);
        mAddView = view.findViewById(R.id.tv_add_image);
        mQuesImageView = view.findViewById(R.id.imv_question_image);

    }
}
