package com.gnarlydinouser.randompicker;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                new add()).commit();
        // Display the default fragment
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId()==R.id.add)
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                new add()).commit();
                    else if(item.getItemId()==R.id.modify)
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                new modify()).commit();
                    else if(item.getItemId()==R.id.delete)
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                new delete()).commit();
                    return true;
                }
            };
}