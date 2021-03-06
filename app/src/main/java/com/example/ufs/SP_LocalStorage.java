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
    public SharedPreferences.Editor getEditor() { return this.sp.edit(); }

    public int getLoggedInUserId() { return this.sp.getInt("userId", -1); }
    public boolean getIsLoggedIn() { return this.sp.getBoolean("isLoggedIn", false); }
    public boolean getIsStudent() { return this.sp.getBoolean("isStudent", false); }
    public String getUserFName() { return this.sp.getString("userFName", ""); }
    public String getUserUniId() { return this.sp.getString("universityId", ""); }
    public String getUserLName() { return this.sp.getString("userLName", ""); }
    public String getUserEmail() { return this.sp.getString("userEmail", ""); }

    public boolean getIsEditingRestaurant() {
        return this.sp.getBoolean("isEditingRestaurant", false);
    }
}
