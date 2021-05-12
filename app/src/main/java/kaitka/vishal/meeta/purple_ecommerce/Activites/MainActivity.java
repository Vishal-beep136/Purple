package kaitka.vishal.meeta.purple_ecommerce.Activites;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyAccountFragment;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyCartFragment;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyOrdersFragment;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyRewardsFragment;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.MyWishlistFragment;
import kaitka.vishal.meeta.purple_ecommerce.R;

import static kaitka.vishal.meeta.purple_ecommerce.Activites.RegisterActivity.setSignUpFragment;
import static kaitka.vishal.meeta.purple_ecommerce.Fragments.SignInFragment.disableCloseBtn;
import static kaitka.vishal.meeta.purple_ecommerce.Fragments.SignUpFragment.disableCloseBtnSignUp;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int REWARDS_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;
    public static Boolean showCart = false;

    private FrameLayout frameLayout;
    private int currentFragment = -1;
    private NavigationView navigationView;
    private ImageView actionBarLogo;
    private Window window;
    private Toolbar toolbar;
    private Dialog signInDialog;
    private FirebaseUser currentUser;

    public static DrawerLayout drawer;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        actionBarLogo = findViewById(R.id.actionBar_logo);
        setSupportActionBar(toolbar);

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout = findViewById(R.id.main_framelayout);

        if (showCart) {
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart", new MyCartFragment(), -2);
            hideAppLogo();
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragmentPurple(), HOME_FRAGMENT);
        }

        //sign in dialog is start from here
        signInDialog = new Dialog(MainActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.sign_up_btn);
        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);

        //dialog sign In btn setOnClickListener
        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCloseBtn = true;
                disableCloseBtnSignUp = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
                finish();
            }
        });

        //dialog sign UP btn setOnClickListener
        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCloseBtn = true;
                disableCloseBtnSignUp = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //todo here you can get error so the video timestamp is 15:00 remember must check here and do research.the if else statement.
        //if else statement for unEnabled and Enabled the sign out btn in navigation drawer
        if (currentUser == null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 2).setEnabled(false);
        } else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 2).setEnabled(true);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            } else {
                if (showCart) {
                    showCart = false;
                    finish();

                } else {
                    actionBarLogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragmentPurple(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
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

        if (id == R.id.main_search_icon) {
            //todo search
            return true;

        } else if (id == R.id.main_notification) {
            //todo notification system
            return true;

        } else if (id == R.id.main_cart_icon) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            }
            return true;
        } else if (id == android.R.id.home) {
            showCart = false;
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT) {
            navigationView.getMenu().getItem(3).setChecked(true);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        int id = item.getItemId();

        if (currentUser != null) {

            if (id == R.id.nav_my_mall) {
                actionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new HomeFragmentPurple(), HOME_FRAGMENT);

            } else if (id == R.id.nav_my_orders) {
                hideAppLogo();
                gotoFragment("My Orders", new MyOrdersFragment(), ORDERS_FRAGMENT);

            } else if (id == R.id.nav_my_rewards) {
                hideAppLogo();
                gotoFragment("My Rewards", new MyRewardsFragment(), REWARDS_FRAGMENT);

            } else if (id == R.id.nav_my_cart) {
                hideAppLogo();
                gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);

            } else if (id == R.id.nav_my_wishlist) {
                hideAppLogo();
                gotoFragment("My Wishlist", new MyWishlistFragment(), WISHLIST_FRAGMENT);

            } else if (id == R.id.nav_my_account) {
                hideAppLogo();
                gotoFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGMENT);

            } else if (id == R.id.log_out) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();
            }
            //todo: in future you have to do some changes in here ok so, for now I am going to place the return here and close drawer method.
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }//if statement

        else {
            drawer.closeDrawer(GravityCompat.START);
            signInDialog.show();
            return false;
        }//else


    }

    private void hideAppLogo() {
        actionBarLogo.setVisibility(View.GONE);
    }

    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            if (fragmentNo == REWARDS_FRAGMENT) {
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
            } else {
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