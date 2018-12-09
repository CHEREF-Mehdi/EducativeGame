package com.example.sta.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    long animationDuration = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        Typeface face = Typeface.createFromAsset(getAssets(), "CabinSketch-Bold.ttf");
        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setTypeface(face);


        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }

        });
        iv = (ImageView) findViewById(R.id.imageView);
        //this.handleAnimation();

        TextView textView1 = (TextView) findViewById(R.id.tv1);
        textView1.setTypeface(face);
        textView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }

        });
    }

    public void onStart()
    {
        super.onStart();
        this.handleAnimation();
    }


    public void handleAnimation()
    {
       /* ObjectAnimator animatorX= ObjectAnimator.ofFloat(iv,"x",900f);
        animatorX.setDuration(animationDuration);
        AnimatorSet as = new AnimatorSet();
        as.playTogether(animatorX);
        as.start();
        Log.i("tag", String.valueOf(animatorX.getDuration()));*/
        final Animation animtra= AnimationUtils.loadAnimation(this,R.anim.translate);
        iv.startAnimation(animtra);
    }
    public void handleAnimation1()
    {
       /* ObjectAnimator animatorX= ObjectAnimator.ofFloat(iv,"x",-900f);
        animatorX.setDuration(animationDuration);
        AnimatorSet as = new AnimatorSet();
        as.playTogether(animatorX);
        as.start();
        if(animatorX.getCurrentPlayTime()==0)
        {
            handleAnimation();
        }*/
    }
}
