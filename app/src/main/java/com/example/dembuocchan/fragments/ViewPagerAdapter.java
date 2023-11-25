package com.example.dembuocchan.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new KeHoachFragment();
            case 1:
                return new TienDoFragment();
            case 2:
                return new BatDauFragment();
            case 3:
                return new BuocFragment();
            case 4:
                return new CaiDatFragment();
            default:
                return new KeHoachFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
