package com.example.ufs;

import android.content.Context;
import android.content.SharedPreferences;

// Helper class for SharePreferences to easily get helpful information like
// the currently logged in user id.
public class SP_LocalStorage {
    private SharedPreferences sp;

    public SP_LocalStorage(Context ctx) {
        sp = ctx.getSharedPreferences("sharedPrefs", ctx.MODE_PRIVATE);
    }

    public SharedPreferences getSP() { return this.sp; }
    public int getLoggedInUserId() { return this.sp.getInt("userId", -1); }
    public boolean getIsLoggedIn() { return this.sp.getBoolean("isLoggedIn", false); }
    public boolean isStudent() { return this.sp.getBoolean("isStudent", false); }
}
