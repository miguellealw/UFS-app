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

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);
        // IMPORTANT: COMMENT AFTER DATA IS GENERATED.
        // This will generate the data for the start of the demo
        // but needs to be commented and re-run the app to avoid wiping out the data
        // that we create during the demo
        //db.generateData();

        //  get logged in user info
        SP_LocalStorage sp = new SP_LocalStorage(MainActivity.this);
        boolean isLoggedIn = sp.getIsLoggedIn();
        boolean isStudent = sp.getIsStudent();
        //Log.i(TAG, "Is User Student " + isStudent);

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

        // Decide which bottom navigation to show based on if user is student
        student_bottom_nav.setVisibility(isStudent ? View.VISIBLE : View.GONE);
        admin_bottom_nav.setVisibility(isStudent ? View.GONE : View.VISIBLE);
        NavigationUI.setupWithNavController(isStudent ? student_bottom_nav : admin_bottom_nav, navController);

    }


}