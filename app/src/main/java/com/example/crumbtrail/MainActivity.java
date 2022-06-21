package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;

import com.example.crumbtrail.databinding.ActivityMainBinding;
import com.example.crumbtrail.fragments.CameraFragment;
import com.example.crumbtrail.fragments.HomeFragment;
import com.example.crumbtrail.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUpBottomViewNavigation();
    }

    private void setUpBottomViewNavigation() {
        bottomNavigationView = binding.bottomNavigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.action_profile:
                    fragment = new ProfileFragment();
                    break;
                case R.id.action_camera:
                default:
                    fragment = new CameraFragment();
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.action_camera);
    }
}