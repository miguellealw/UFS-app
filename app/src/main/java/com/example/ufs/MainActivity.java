package com.example.ufs;

import static androidx.navigation.ui.NavigationUI.setupWithNavController;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ufs.ui.FavoritesFragment;
import com.example.ufs.ui.OrdersFragment;
import com.example.ufs.ui.RestaurantsFragment;
import com.example.ufs.ui.ReviewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    Button Signin;
    Button Register;
    EditText ed1,ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment =
                (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        BottomNavigationView bottom_nav = findViewById(R.id.bottom_nav);
        setupWithNavController(bottom_nav, navController);
        //bottom_nav.setupWithController(navController);

        // deprecated, but could not find better way
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i("======== Bottom nav", "nav clicked");
                Fragment selectedFragment = null;

                switch(item.getItemId()) {
                    case R.id.restaurantsFragment:
                        //openFragment(new RestaurantsFragment());
                        selectedFragment = new RestaurantsFragment();
                        break;
                    case R.id.ordersFragment:
                        selectedFragment = new OrdersFragment();
                        break;
                    case R.id.favoritesFragment:
                        selectedFragment = new FavoritesFragment();
                        break;
                    case R.id.reviewsFragment:
                        selectedFragment = new ReviewsFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                return true;
            }
        });



    }

//    void openFragment(Fragment fragment){
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//
//    }

}