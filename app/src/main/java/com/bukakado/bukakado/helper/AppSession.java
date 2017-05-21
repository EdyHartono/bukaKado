package com.bukakado.bukakado.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by edy.h on 5/20/2017.
 */

public class AppSession {
    private SharedPreferences prefs;
    private static final String TOKEN_VAR = "token";

    public AppSession(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setBukaLapakToken(String token) {
        prefs.edit().putString(TOKEN_VAR, token).apply();
    }

    public String getBukaLapakToken() {
        return prefs.getString(TOKEN_VAR,"");
    }
}
