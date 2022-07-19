package com.example.crumbtrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
    public static ViewPager viewPager;
    private BottomNavigationView mainBottomNav;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        viewPager = findViewById(R.id.viewPager);
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
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        FragmentPageAdapter adapter = new FragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.75f;

            public void transformPage(@NonNull View page, float position ) {
                int pageWidth = page.getWidth();

                if ( position < -1 ) { // [ -Infinity,-1 )
                    // This page is way off-screen to the left.
                    page.setAlpha( 0 );

                } else if ( position <= 0 ) { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    page.setAlpha( 1 );
                    page.setTranslationX( 0 );
                    page.setScaleX( 1 );
                    page.setScaleY( 1 );

                } else if ( position <= 1 ) { // (0,1]
                    // Fade the page out.
                    page.setAlpha( 1 - position );

                    // Counteract the default slide transition
                    page.setTranslationX( pageWidth * -position );

                    // Scale the page down ( between MIN_SCALE and 1 )
                    float scaleFactor = MIN_SCALE
                            + ( 1 - MIN_SCALE ) * ( 1 - Math.abs( position ) );
                    page.setScaleX( scaleFactor );
                    page.setScaleY( scaleFactor );

                } else { // ( 1, +Infinity ]
                    // This page is way off-screen to the right.
                    page.setAlpha( 0 );
                }
            }
        });
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
            }
            return true;
        });
        mainBottomNav.setSelectedItemId(R.id.action_camera);
    }
}