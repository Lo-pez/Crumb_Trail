package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;

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



        setUpBottomViewNavigation();
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpBottomViewNavigation() {
        mainBottomNav = binding.mainBottomNav;
        mainBottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.action_profile:
                    fragment = new ProfileFragment();
                    break;
                case R.id.action_camera:
                    fragment = new CameraFragment();
                    break;
                case R.id.action_search:
                default:
                    fragment = new SearchFragment();
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.mainActivityFl, fragment).commit();
            return true;
        });
        mainBottomNav.setSelectedItemId(R.id.action_camera);
    }
}