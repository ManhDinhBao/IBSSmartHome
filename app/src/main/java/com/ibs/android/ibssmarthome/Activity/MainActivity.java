package com.ibs.android.ibssmarthome.Activity;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v8.renderscript.RenderScript;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ibs.android.ibssmarthome.Fragment.HomeFragment;
import com.ibs.android.ibssmarthome.Fragment.NewFeedFragment;
import com.ibs.android.ibssmarthome.Fragment.NewsFragment;
import com.ibs.android.ibssmarthome.Fragment.RequestFragment;
import com.ibs.android.ibssmarthome.Fragment.UtilitiesFragment;
import com.ibs.android.ibssmarthome.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBnvMain;
    private FragmentManager mFrMngMain;
    private DrawerLayout mDlMain;
    View imgBg;
    private RenderScript rs = null;
    Bitmap mBlurBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define controls
        mFrMngMain = getSupportFragmentManager();
        mDlMain = findViewById(R.id.drawer_layout_main);
        mBnvMain = findViewById(R.id.bottomNavigationView_main_menu);
        NavigationView nvMainRight = findViewById(R.id.navigationView_main_right);

        //Right menu event
        mDlMain.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        //Add default fragment
        AddFragment(new HomeFragment());


        mBnvMain.setItemIconTintList(null);
        mBnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Menu menu = mBnvMain.getMenu();

                //Reset item bottom navigation view to default
                menu.findItem(R.id.item_main_newfeed).setIcon(R.drawable.main_newfeeditemdefault_48px);
                menu.findItem(R.id.item_main_home).setIcon(R.drawable.main_homeitemdefault_48px);
                menu.findItem(R.id.item_main_request).setIcon(R.drawable.main_requestitemdefault_48px);
                menu.findItem(R.id.item_main_utilities).setIcon(R.drawable.main_utilityitemdefault_48px);
                menu.findItem(R.id.item_main_notification).setIcon(R.drawable.main_notificationitemdefault_48px);

                switch (menuItem.getItemId()) {
                    case R.id.item_main_newfeed:
                        menu.findItem(R.id.item_main_newfeed).setIcon(R.drawable.main_newfeeditemselected_48px);
                        LoadFragment(new NewFeedFragment());
                        break;
                    case R.id.item_main_home:
                        menu.findItem(R.id.item_main_home).setIcon(R.drawable.main_homeitemselected_48px);
                        LoadFragment(new HomeFragment());
                        break;
                    case R.id.item_main_request:
                        menu.findItem(R.id.item_main_request).setIcon(R.drawable.main_requestitemselected_48px);
                        LoadFragment(new RequestFragment());
                        break;
                    case R.id.item_main_utilities:
                        menu.findItem(R.id.item_main_utilities).setIcon(R.drawable.main_utilitiesitemselected_48px);
                        LoadFragment(new UtilitiesFragment());
                        break;
                    case R.id.item_main_notification:
                        menu.findItem(R.id.item_main_notification).setIcon(R.drawable.main_notificationitemselected_48px);
                        LoadFragment(new NewsFragment());
                        break;
                }
                return true;
            }
        });
    }

    private void LoadFragment(Fragment fragment) {
        FragmentTransaction ft_rep = mFrMngMain.beginTransaction();
        ft_rep.replace(R.id.relativelayout_main, fragment);
        //  t_rep.addToBackStack(null);
        ft_rep.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft_rep.commit();
    }

    private void AddFragment(Fragment fragment) {
        FragmentTransaction ft_add = mFrMngMain.beginTransaction();
        ft_add.add(R.id.relativelayout_main, fragment);
        //ft_rep.addToBackStack(null);
        ft_add.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft_add.commit();
    }

}
