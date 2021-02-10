package ru.eds2809.dnevnik.service.common;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpResponse<T> implements Callback<T> {

    HttpCallBack<T> httpCallBack;
    public HttpResponse(HttpCallBack<T> httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        httpCallBack.call(response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d("HTTP_ERROR", t.getMessage());
        httpCallBack.call(null);
    }

    public interface HttpCallBack<T> {
        void call(T body);
    }
}
