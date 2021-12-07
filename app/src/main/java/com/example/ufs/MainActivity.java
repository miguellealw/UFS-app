package com.example.ufs;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import android.content.Intent;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.ufs.data.model.AdModel;
import com.example.ufs.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "===== MAIN ACTIVITY";
    NavController navController;

    private List<AdModel> ads = new ArrayList<AdModel>();

    public MainActivity() {

    }

    void generateAdData() {
        // Add advertisement to ads list
        ads.add(new AdModel(
                "Panda Express",
                "https://www.getrealphilippines.com/wp-content/uploads/2018/09/panda_express_philippines.jpg"
        ));
        ads.add(new AdModel(
                "Chick-fil-A",
                "http://d1fd34dzzl09j.cloudfront.net/Images/CFACOM/Stories%20Images/2018/11/door%20dash/2web.jpg"
        ));

        // TODO: imgs
        ads.add(new AdModel(
                "Subway",
                "https://www.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/766x415_The_Nutrition_Facts_for_Subway_Restaurant_Meals.jpg"
        ));
        ads.add(new AdModel(
                "KFC",
                "https://countryrebel.com/wp-content/uploads/2019/05/kfcimposter.jpg"
        ));
        ads.add(new AdModel(
                "Wendy's",
                "http://www.fastfoodfixation.com/wp-content/uploads/2016/12/wendys.jpg"
        ));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  get logged in user info
        SP_LocalStorage sp = new SP_LocalStorage(MainActivity.this);
        boolean isLoggedIn = sp.getIsLoggedIn();
        //Log.i(TAG, "Is User Student " + isStudent);
        TextView tv_ad_text = findViewById(R.id.tv_ad_text);
        ImageView ad_banner = findViewById(R.id.ad_banner_image);
        RelativeLayout darkOverlay = findViewById(R.id.darkOverlay);

        // If not logged in redirect to login screen
        if(!isLoggedIn) {
            Toast.makeText(getApplicationContext(), "Log in to continue", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }

        boolean isStudent = sp.getIsStudent();

        tv_ad_text.setVisibility(isStudent ? View.VISIBLE : View.GONE);
        ad_banner.setVisibility(isStudent ? View.VISIBLE : View.GONE);
        darkOverlay.setVisibility(isStudent ? View.VISIBLE : View.GONE);

        DatabaseHelper db = new DatabaseHelper(this);

        // This will generate the data for the start of the demo
        // but needs to be commented and re-run the app to avoid wiping out the data
        // that we create during the demo
        // ====== IMPORTANT: COMMENT AFTER DATA IS GENERATED. =====
        //db.generateData();

        // If student run ad code
        if(isStudent) {
            generateAdData();

            // Print first ad image
            tv_ad_text.setText(ads.get(0).getRestaurantName());
            Glide
                    .with(MainActivity.this)
                    .load(ads.get(0).getRestaurantImage())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .centerCrop()
                    .into(ad_banner);

            new CountDownTimer(10000, 1000) {
                private int img_counter = 1;

                @Override
                public void onTick(long millisUntilFinished) { }

                @Override
                public void onFinish() {
                    tv_ad_text.setText(ads.get(img_counter).getRestaurantName());
                    Glide
                            .with(MainActivity.this)
                            .load(ads.get(img_counter).getRestaurantImage())
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .centerCrop()
                            .into(ad_banner);

                    img_counter++;
                    if(img_counter > ads.size() - 1) {
                        Log.i("== counter resets at:  ", "img counter" + img_counter);
                        img_counter = 0;
                    }

                    start();
                }
            }.start();
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