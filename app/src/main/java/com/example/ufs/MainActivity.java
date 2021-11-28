package com.example.ufs;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.ufs.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "===== MAIN ACTIVITY";
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  get logged in user info
        SP_LocalStorage sp = new SP_LocalStorage(MainActivity.this);
        boolean isLoggedIn = sp.getIsLoggedIn();
        boolean isStudent = sp.isStudent();
        Log.i(TAG, "Is User Student " + isStudent);

        // If not logged in redirect to login screen
        if(!isLoggedIn) {
            Toast.makeText(getApplicationContext(), "Log in to continue", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }

        // Navigation logic
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment =
                (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        BottomNavigationView student_bottom_nav = findViewById(R.id.student_bottom_nav);
        BottomNavigationView admin_bottom_nav = findViewById(R.id.admin_bottom_nav);

        student_bottom_nav.setVisibility(isStudent ? View.VISIBLE : View.GONE);
        admin_bottom_nav.setVisibility(isStudent ? View.GONE : View.VISIBLE);
        NavigationUI.setupWithNavController(isStudent ? student_bottom_nav : admin_bottom_nav, navController);

    }


}