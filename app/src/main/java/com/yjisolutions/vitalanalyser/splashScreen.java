package com.yjisolutions.vitalanalyser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashScreen extends Activity {
    ImageView logo, back;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splesh_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation tob = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_zoom);

        logo = findViewById(R.id.logo);
        back = findViewById(R.id.splash_background);
        back.startAnimation(tob);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        /** Duration of wait **/
        int SPLASH_DISPLAY_LENGTH = 900;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(splashScreen.this, MainActivity.class);
                splashScreen.this.startActivity(mainIntent);
                splashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
