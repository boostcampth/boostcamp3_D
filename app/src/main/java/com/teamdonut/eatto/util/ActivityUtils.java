package com.teamdonut.eatto.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.teamdonut.eatto.ui.login.LoginActivity;
import com.teamdonut.eatto.ui.main.MainActivity;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.util.Preconditions.checkNotNull;


public class ActivityUtils {
    @SuppressLint("RestrictedApi")
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                            @NonNull Fragment fragment, int frameId) {

        checkNotNull(fragmentManager);
        checkNotNull(fragment);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void redirectLoginActivity(Context context) {
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void redirectMainActivity(Context context) {
        final Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void saveStrValueSharedPreferences(Context context, String name,String key, String value){
        SharedPreferences pref = context.getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStrValueSharedPreferences(Context context, String name, String Key){
        SharedPreferences pref = context.getSharedPreferences(name, MODE_PRIVATE);
        return pref.getString(Key, "");
    }
}
