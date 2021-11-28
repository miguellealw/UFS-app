package com.example.ufs.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.data.model.LoggedInUser;
import com.example.ufs.data.model.UserModel;
import com.example.ufs.ui.login.LoginActivity;
import com.example.ufs.ui.login.LoginViewModelFactory;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String email, String password, Context ctx) {

        try {
            // Get user data from database
            DatabaseHelper dbo = new DatabaseHelper(ctx);
            UserModel db_user = dbo.getUser(email, password);

            // if db_user is null that means user w/ email and password does not exist
            if (db_user == null) throw new Exception("Invalid login. Try Again.");

            // Assign user data to view model. This will contain the data that
            // can be displayed on the app
            LoggedInUser usr = new LoggedInUser(
                    db_user.getId(),
                    db_user.getFirstName(),
                    db_user.getIsStudent()
            );

//            UserModel login_user = new UserModel(db_user.getId(), db_user.getFirstName(), db_user.getIsStudent());
//            LoginViewModelFactory usr = new LoginViewModelFactory();
//            usr.create(login_user);

            // Add user data to shared preferences to access later
            SharedPreferences sharedPref = ctx.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putBoolean("isLoggedIn", true);
            editor.putInt("userId", db_user.getId());
            editor.putString("userFName", db_user.getFirstName());
            editor.putString("userLName", db_user.getLastName());
            editor.putString("userEmail", db_user.getEmail());
            editor.putBoolean("isStudent", db_user.getIsStudent());

            editor.apply();

            return new Result.Success<LoggedInUser>(usr);
        } catch (Exception e) {
            //Toast.makeText(cxt, e.getMessage(), Toast.LENGTH_SHORT).show();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }


    public void logout() {
        // TODO: revoke authentication
    }
}