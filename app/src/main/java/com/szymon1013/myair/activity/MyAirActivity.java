package com.szymon1013.myair.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.szymon1013.myair.R;
import com.szymon1013.myair.databinding.ActivityMyAirBinding;
import com.szymon1013.myair.fragment.AirStationMap;
import com.szymon1013.myair.fragment.StationList;

public class MyAirActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, StationList.IStationListInteraction {
    private ActivityMyAirBinding mActivityMyAirBinding;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMyAirBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_air);
        if (savedInstanceState == null) {
            addListFragment();
        }
        setUI();

    }

    private void addListFragment() {
        Fragment fragment = new StationList();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().
                addToBackStack(null).
                add(mActivityMyAirBinding.appBarMyAir.contentMyAir.fragmentContainer.getId(),
                        fragment, StationList.TAG).
                commit();
    }

    private void setUI() {
        mDrawerLayout = mActivityMyAirBinding.drawerLayout;
        mToolbar = mActivityMyAirBinding.appBarMyAir.toolbar;
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = mActivityMyAirBinding.navView;
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragmentInContainer = getFragmentManager().findFragmentById(mActivityMyAirBinding.appBarMyAir.contentMyAir.fragmentContainer.getId());
            if (fragmentInContainer != null && fragmentInContainer instanceof StationList) {
                finish();
            } else
                super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            fragment = new AirStationMap();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (fragment != null) {
            goToFragment(fragment);
        }

        return true;
    }

    private void goToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().
                addToBackStack(null).
                replace(mActivityMyAirBinding.appBarMyAir.contentMyAir.fragmentContainer.getId(), fragment).
                commit();
    }


    @Override
    public void goToMap() {
        AirStationMap fragment = new AirStationMap();
        goToFragment(fragment);

    }
}
