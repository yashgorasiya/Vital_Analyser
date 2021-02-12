package com.yjisolutions.vitalanalyser;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

    //    Button signOut;
    TextView userName,userMail;
    ImageView profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        int clr = Color.parseColor("#FF6D00");
//        toolbar.setBackgroundColor(clr); for changing toolbar color
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View header = navigationView.getHeaderView(0);
        userName = (TextView) header.findViewById(R.id.user_name);
        userMail = (TextView) header.findViewById(R.id.user_mail);
        profile = (ImageView) header.findViewById(R.id.profilePic);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Support()).commit();
//            navigationView.setCheckedItem(R.id.nav_home);
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

    //navigation Override Methods
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                navigationView.setCheckedItem(R.id.nav_home);
                toolbar.setTitle("Home");
                temp = new Support();
                break;
            case R.id.nav_info:
                navigationView.setCheckedItem(R.id.nav_info);
                temp = new AboutFragment();
                toolbar.setTitle("About Us");
                break;
            case R.id.nav_Help:
                navigationView.setCheckedItem(R.id.nav_Help);
                temp = new Setting();
                toolbar.setTitle("Setting");
                break;
            case R.id.nav_login:
                navigationView.setCheckedItem(R.id.nav_login);
                temp = new login();
                toolbar.setTitle("Log In");
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