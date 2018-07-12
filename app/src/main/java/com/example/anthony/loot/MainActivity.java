package com.example.anthony.loot;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    public static MyDBHelper dbHelper;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottomNavBar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(bottomNavBarListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFragment,
                new HomeFragment()).commit();

        dbHelper = new MyDBHelper(this, null, null, 1);

        dbHelper.queryData("CREATE TABLE IF NOT EXISTS THINGS " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR, " +
                "image BLOB, " +
                "description VARCHAR, " +
                "daystokeep INTEGER, " +
                "yearstokeep INTEGER, " +
                "postdate LONG)");

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
