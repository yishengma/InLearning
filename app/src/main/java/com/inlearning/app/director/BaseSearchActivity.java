package com.inlearning.app.director;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.util.InputMethodUtil;
import com.inlearning.app.common.util.StatusBar;

public abstract class BaseSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mCancelView;
    private RecyclerView mResultRecyclerView;
    private EditText mEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_search);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initAdapter();
        initView();
    }

    private void initView() {
        mResultRecyclerView = findViewById(R.id.rv_search_result);
        mResultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mResultRecyclerView.setAdapter(getAdapter());
        mCancelView = findViewById(R.id.tv_search_cancel);
        mCancelView.setOnClickListener(this);
        mEditView = findViewById(R.id.search_bar_edit_text);
        mEditView.setHint(editHint());
        mEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String key = mEditView.getText().toString().trim();
                    if (TextUtils.isEmpty(key)) {
                        return true;
                    }
                    doSearch(key);
                    InputMethodUtil.forceToHideSoftInput(mEditView, BaseSearchActivity.this);
                    return true;
                }
                return false;
            }
        });
        mEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key = mEditView.getText().toString().trim();
                if (TextUtils.isEmpty(key)) {
                    resetList();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_cancel:
                finish();
                break;
        }
    }

    protected abstract void initAdapter();

    protected abstract RecyclerView.Adapter getAdapter();

    protected abstract void doSearch(String key);

    protected abstract void resetList();

    protected abstract String editHint();

}
