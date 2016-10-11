package com.example.hailin.zhbeijing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.hailin.zhbeijing.R;
import com.example.hailin.zhbeijing.utils.PreferenceUtil;

public class SplashActivity extends Activity {

    private RelativeLayout rl_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        rl_splash = (RelativeLayout) findViewById(R.id.rl_splash);
        startAnimation();
    }

    private void startAnimation() {
        AnimationSet set = new AnimationSet(false);

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);

        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isUserGuideShowed = PreferenceUtil.getBoolean(SplashActivity.this,
                        "isUserGuideShowed", false);
                if(isUserGuideShowed){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rl_splash.startAnimation(set);
    }

}
