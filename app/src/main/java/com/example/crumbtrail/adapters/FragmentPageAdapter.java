package com.example.crumbtrail.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.crumbtrail.MainActivity;
import com.example.crumbtrail.fragments.CameraFragment;
import com.example.crumbtrail.fragments.HomeFragment;
import com.example.crumbtrail.fragments.SearchFragment;

/**
 * Handles the logic for switching through fragments using a simple
 * switch case statement.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {

    public FragmentPageAdapter(
            FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CameraFragment();
            case 2:
                return new SearchFragment();
        }
        return new Fragment();
    }

    @Override
    public int getCount()
    {
        return 3;
    }
}
