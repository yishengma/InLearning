package com.inlearning.common.util;


import okhttp3.Call;

public interface  OkHttpResultCallback {

    void onError(Call call, Exception e);

    void onResponse(byte[] bytes);
}
