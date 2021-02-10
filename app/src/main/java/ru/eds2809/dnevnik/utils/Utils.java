package ru.eds2809.dnevnik.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;

public class Utils {

    private static final String APP_PREFERENCES = "mySettings";
    private static final Gson mGson = new Gson();

    private Utils() {

    }

    public static <T> String toJson(T t){
        return mGson.toJson(t);
    }

    public static <T> T fromJson(String json, Class<T> tClass){
        return mGson.fromJson(json,tClass);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void saveToSP(Context context, String key, String userJson) {
        SharedPreferences mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        editor.putString(key, userJson);
        editor.apply();
    }

    public static boolean containSharedPreference(Context context, String key) {
        SharedPreferences mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return mSettings.contains(key);
    }

    public static String getFromSharedPreference(Context context, String key) {
        SharedPreferences mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return mSettings.getString(key, "");
    }
}
