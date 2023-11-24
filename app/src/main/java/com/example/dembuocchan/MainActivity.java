package com.example.dembuocchan;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dembuocchan.fragments.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);




        // xu ly su kien
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Sử dụng if thay vì switch
                if (position == 0) {
                    bottomNavigationView.getMenu().findItem(R.id.KeHoach).setChecked(true);
                } else if (position == 1) {
                    bottomNavigationView.getMenu().findItem(R.id.TienDo).setChecked(true);
                } else if (position == 2) {
                    bottomNavigationView.getMenu().findItem(R.id.BatDau).setChecked(true);
                } else if (position == 3) {
                    bottomNavigationView.getMenu().findItem(R.id.Buoc).setChecked(true);
                } else if (position == 4) {
                    bottomNavigationView.getMenu().findItem(R.id.CaiDat).setChecked(true);
                }
            }
        });

        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                // Sử dụng if thay vì switch
                if (item.getItemId() == R.id.KeHoach) {
                    viewPager.setCurrentItem(0);
                } else if (item.getItemId() == R.id.TienDo) {
                    viewPager.setCurrentItem(1);
                } else if (item.getItemId() == R.id.BatDau) {
                    viewPager.setCurrentItem(2);
                } else if (item.getItemId() == R.id.Buoc) {
                    viewPager.setCurrentItem(3);
                } else if (item.getItemId() == R.id.CaiDat) {
                    viewPager.setCurrentItem(4);
                }
            }
        });
    }
}