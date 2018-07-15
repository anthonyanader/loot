package com.example.anthony.loot;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottomNavBar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(bottomNavBarListener);

        selectedFragment = new HomeFragment();
        loadCurrentFragment();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavBarListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId()){
                        case R.id.navBarHome:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.navBarPost:
                            selectedFragment = new PostFragment();
                            break;

                        case R.id.navBarSettings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }
                    loadCurrentFragment();
                    return true;
                }
            };

    private void loadCurrentFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFragment,
                selectedFragment).commit();
    }
}
