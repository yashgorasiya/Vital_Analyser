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
    ImageView logo,vital,analyser;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splesh_screen);
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Animation rtl = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.ltr);
        Animation ltr = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rtl);
        Animation tob = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.tob);

        logo=findViewById(R.id.logo);
        vital=findViewById(R.id.vital);
        analyser=findViewById(R.id.ana);
        logo.startAnimation(tob);
        vital.startAnimation(rtl);
        analyser.startAnimation(ltr);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        /** Duration of wait **/
        int SPLASH_DISPLAY_LENGTH = 1500;
        new Handler().postDelayed(new Runnable(){
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
