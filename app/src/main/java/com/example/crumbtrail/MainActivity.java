package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.crumbtrail.adapters.FragmentPageAdapter;
import com.example.crumbtrail.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// import statements

/**
 * @author      Diego López Ramos diegolopezramos@fb.com
 * @version     0.1
 * @since       0.1
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final int requestCode = 100;
    private ViewPager viewPager;
    private BottomNavigationView mainBottomNav;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getCameraPermission();
        setUpViewPager();
        setUpBottomViewNavigation();
    }

    /**
     * Gets permission to use the camera from the user if
     * the permission is not already given. This is necessary
     * for the camera fragment.
     */
    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, requestCode);
        }
    }
    /**
     * Sets up the viewpager and connects it to the bottom view navigation.
     */
    private void setUpViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
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
        FragmentPageAdapter adapter = new FragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    /**
     * Sets up bottom view navigation. When user clicks a menu item
     * the viewPager's current item is changed to that item.
     */
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