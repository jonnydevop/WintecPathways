package com.gogoteam.wintecpathways;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

   /** private int SPLASH_TIME_OUT=3000;
    private ImageView logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=(ImageView)findViewById(R.id.logo);

        //Activate animation
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mysplashanimation);
        logo.startAnimation(myanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
} */
   @Override
   public void onAttachedToWindow() {
       super.onAttachedToWindow();
       Window window = getWindow();
       window.setFormat(PixelFormat.RGBA_8888);
   }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = (ImageView)findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mysplashanimation);
        imageView.setAnimation(animation);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    int waited = 0;
                    while(waited < 1000){
                        sleep(400);
                        waited +=100;
                    }
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashActivity.this.finish();

                }catch (InterruptedException e){

                }finally {
                    SplashActivity.this.finish();
                }
            }
        };

        thread.start();
    }
}


