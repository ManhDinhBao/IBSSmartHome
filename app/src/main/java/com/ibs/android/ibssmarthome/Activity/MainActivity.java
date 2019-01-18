package com.ibs.android.ibssmarthome.Activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ibs.android.ibssmarthome.Fragment.HomeFragment;
import com.ibs.android.ibssmarthome.Fragment.NewFeedFragment;
import com.ibs.android.ibssmarthome.Fragment.NewsFragment;
import com.ibs.android.ibssmarthome.Fragment.RequestFragment;
import com.ibs.android.ibssmarthome.Fragment.UtilitiesFragment;
import com.ibs.android.ibssmarthome.R;

import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomMenu;
    private FragmentManager fm;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setupWindowAnimations();


        fm = getSupportFragmentManager();
        mDrawerLayout = findViewById(R.id.drawer_layout_main);


        NavigationView navigationView = findViewById(R.id.navigationView_main_right);


        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
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

        AddFragment(new HomeFragment());

        bottomMenu = findViewById(R.id.bottomNavigationView_main_menu);
        bottomMenu.setItemIconTintList(null);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Menu menu = bottomMenu.getMenu();
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
        FragmentTransaction ft_rep = fm.beginTransaction();
        ft_rep.replace(R.id.relativelayout_main, fragment);
        //  t_rep.addToBackStack(null);
        ft_rep.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft_rep.commit();
    }

    private void AddFragment(Fragment fragment) {
        FragmentTransaction ft_add = fm.beginTransaction();
        ft_add.add(R.id.relativelayout_main, fragment);
        //ft_rep.addToBackStack(null);
        ft_add.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft_add.commit();
    }

}
