package com.example.anthony.loot;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottomNavBar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(bottomNavBarListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFragment,
                new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavBarListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch(item.getItemId()){
                        case R.id.navBarHome:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.navBarPost:
                            selectedFragment = new PostFragment();
                            break;

                        case R.id.navBarLoot:
                            selectedFragment = new LootFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFragment,
                            selectedFragment).commit();

                    return true;
                }
            };
}
