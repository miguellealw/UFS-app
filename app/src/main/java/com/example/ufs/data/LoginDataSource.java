package com.example.ufs.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.data.model.LoggedInUser;
import com.example.ufs.ui.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String email, String password, Context cxt) {

        try {
            // TODO: handle loggedInUser authentication
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Jane Doe");

            String fname = "";

            DatabaseHelper dbo = new DatabaseHelper(cxt);
            fname = dbo.getUser(email, password);

            LoggedInUser usr = new LoggedInUser(
                // TODO: change this to user primary key (id)
                java.util.UUID.randomUUID().toString(),
                fname
            );

            if(fname == null) throw new Exception("Invalid login. Try Again.");

            return new Result.Success<>(usr);
        } catch (Exception e) {
            //Toast.makeText(cxt, e.getMessage(), Toast.LENGTH_SHORT).show();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}