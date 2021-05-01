package kaitka.vishal.meeta.purple_ecommerce;


import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import kaitka.vishal.meeta.purple_ecommerce.Fragments.HomeFragmentPurple;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyCartFragment;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyOrdersFragment;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyRewardsFragment;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyWishlistFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int HOME_FRAGMENT = 0;
    private static final  int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final  int WISHLIST_FRAGMENT = 3;
    private static final  int REWARDS_FRAGMENT = 4;

    private FrameLayout frameLayout;
    private static int currentFragment = -1;
    private NavigationView navigationView;
    private ImageView actionBarLogo;
    private Window window;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        actionBarLogo = findViewById(R.id.actionBar_logo);
        setSupportActionBar(toolbar);

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout = findViewById(R.id.main_framelayout);
        setFragment(new HomeFragmentPurple(), HOME_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT){
                super.onBackPressed();
            }
            else {
                actionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new HomeFragmentPurple(), HOME_FRAGMENT);
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_search_icon){
            //todo search
            return true;

        }
        else if (id == R.id.main_notification){
            //todo notification system
            return true;

        }
        else if (id == R.id.main_cart_icon){
            hideAppLogo();
            gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT){
            navigationView.getMenu().getItem(3).setChecked(true);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_my_mall){

            actionBarLogo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            setFragment(new HomeFragmentPurple(), HOME_FRAGMENT);
        }
        else if (id == R.id.nav_my_orders){
            hideAppLogo();
            gotoFragment("My Orders", new MyOrdersFragment(), ORDERS_FRAGMENT);
        }
        else if (id == R.id.nav_my_rewards){
            hideAppLogo();
            gotoFragment("My Rewards", new MyRewardsFragment(),REWARDS_FRAGMENT);

        }
        else if (id == R.id.nav_my_cart){
            hideAppLogo();
            gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);

        }
        else if (id == R.id.nav_my_wishlist){
            hideAppLogo();
            gotoFragment("My Wishlist", new MyWishlistFragment(),WISHLIST_FRAGMENT);

        }
        else if (id == R.id.nav_my_account){

        }else if (id == R.id.log_out){

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void hideAppLogo() {
        actionBarLogo.setVisibility(View.GONE);
    }

    private void setFragment(Fragment fragment, int fragmentNo){
        if (fragmentNo != currentFragment){
            if (fragmentNo == REWARDS_FRAGMENT){
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
            }
            else {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryVariant));
            }

            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
        

    }
}