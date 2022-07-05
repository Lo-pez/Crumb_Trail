package com.example.crumbtrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.crumbtrail.adapters.FragmentPageAdapter;
import com.example.crumbtrail.databinding.ActivityMainBinding;
import com.example.crumbtrail.fragments.CameraFragment;
import com.example.crumbtrail.fragments.HomeFragment;
import com.example.crumbtrail.fragments.ProfileFragment;
import com.example.crumbtrail.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final int requestCode = 100;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private ViewPager viewPager;
    private BottomNavigationView mainBottomNav;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, requestCode);
        }

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setCurrentItem(1); //Setting first screen to camera

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mainBottomNav.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        mainBottomNav.getMenu().findItem(R.id.action_camera).setChecked(true);
                        break;
                    case 2:
                        mainBottomNav.getMenu().findItem(R.id.action_search).setChecked(true);
                        break;
                    case 3:
                        mainBottomNav.getMenu().findItem(R.id.action_profile).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Create an adapter that
        // knows which fragment should
        // be shown on each page
        FragmentPageAdapter adapter = new FragmentPageAdapter(getSupportFragmentManager());

        // Set the adapter onto
        // the view pager
        viewPager.setAdapter(adapter);

        setUpBottomViewNavigation();
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpBottomViewNavigation() {
        mainBottomNav = binding.mainBottomNav;
        mainBottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_camera:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_search:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.action_profile:
                    viewPager.setCurrentItem(3);
            }
            return true;
        });
        mainBottomNav.setSelectedItemId(R.id.action_camera);
    }
}