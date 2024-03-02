package com.yjisolutions.vitalanalyser;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Fragment temp;
    FirebaseAuth mAuth;
    //Navigation Header;
    TextView userName,userMail;
    ImageView profile;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        toolbar.setBackground(getDrawable(R.drawable.square));


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View header = navigationView.getHeaderView(0);
        userName = header.findViewById(R.id.user_name);
        userMail = header.findViewById(R.id.user_mail);
        profile = header.findViewById(R.id.profilePic);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());

            userName.setText(name);
            userMail.setText(email);
            Glide.with(this).load(photo).into(profile);
        } else {
            Glide.with(this).load(R.drawable.logo).into(profile);
        }
    }


    //nav_dot_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.three_dot, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_dot_home:
                toolbar.setTitle("Home");
                temp = new Home();
                break;
            case R.id.nav_dot_info:
                temp = new AboutFragment();
                toolbar.setTitle("About Us");
                break;
            case R.id.nav_dot_Help:
                temp = new Setting();
                toolbar.setTitle("Setting");
                break;
            case R.id.nav_dot_login:
                temp = new login();
                toolbar.setTitle("Log In");
                break;
            case R.id.nav_dot_exit:
                super.onBackPressed();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, temp).commit();
        return true;
    }



    //navigation Override Methods
    @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
            case R.id.nav_home:
                navigationView.setCheckedItem(R.id.nav_home);
                toolbar.setTitle("Home");
                temp = new Home();
                break;
            case R.id.nav_info:
                navigationView.setCheckedItem(R.id.nav_info);
                temp = new AboutFragment();
                toolbar.setTitle("About Us");
                break;
            case R.id.nav_Help:
                navigationView.setCheckedItem(R.id.nav_Help);
                temp = new Setting();
                toolbar.setTitle("Tutorial");
                break;
            case R.id.nav_login:
                navigationView.setCheckedItem(R.id.nav_login);
                temp = new login();
                toolbar.setTitle("Log In");
                break;
            case R.id.nav_exit:
                super.onBackPressed();
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, temp).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}