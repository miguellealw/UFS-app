package com.example.ufs.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.data.model.LoggedInUser;
import com.example.ufs.data.model.UserModel;
import com.example.ufs.ui.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String email, String password, Context cxt) {

        try {
            // Get user data from database
            DatabaseHelper dbo = new DatabaseHelper(cxt);
            UserModel db_user = dbo.getUser(email, password);

            // Assign user data to view model. This will contain the data that
            // can be displayed on the app
            LoggedInUser usr = new LoggedInUser(
                    db_user.getId(),
                    db_user.getFirstName(),
                    db_user.getIsStudent()
            );

            // if db_user is null that means user w/ email and password does not exist
            if (db_user == null) throw new Exception("Invalid login. Try Again.");

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