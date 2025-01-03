package com.riyo_juan.mtvtracker.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.riyo_juan.mtvtracker.Fragment.AccountFragment;
import com.riyo_juan.mtvtracker.Fragment.HistoryFragment;
import com.riyo_juan.mtvtracker.Fragment.HomeFragment;
import com.riyo_juan.mtvtracker.R;
import com.riyo_juan.mtvtracker.Fragment.WishlistFragment;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frg_home, fragment);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home) {
            loadFragment(new HomeFragment());
            return true;
        }
        else if(id == R.id.wishlist) {
            loadFragment(new WishlistFragment());
            return true;
        }
        else if(id == R.id.history) {
            loadFragment(new HistoryFragment());
            return true;
        }
        else if(id == R.id.account_settings) {
            SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
            String email = prefs.getString("email", "");

            AccountFragment accountFragment = AccountFragment.newInstance(email);
            loadFragment(accountFragment);
            return true;
        }

        return false;
    }
}