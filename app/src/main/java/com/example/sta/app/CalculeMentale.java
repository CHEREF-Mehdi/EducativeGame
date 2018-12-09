package com.example.sta.app;

import android.animation.ObjectAnimator;
import android.content.res.AssetFileDescriptor;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class CalculeMentale extends AppCompatActivity {
    private ProgressBar mprogressBar;
    private int top,bottom,nbrOperation=0;
    private int score=0,operation,level=1;
    private int[] Numbers={0,10,20,35,55,75,95};
    private ObjectAnimator anim ;
    private CountDownTimer countDownTimer;
    private MediaPlayer player=null;

    private Runnable checkEndTime=new Runnable() {
        @Override
        public void run() {

            try {
                player = new MediaPlayer();
                AssetFileDescriptor afd = getAssets().openFd("gamester.mp3");
                player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            LoosingGame("Out of"," Time");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //enlever la partie supperieur ajouter par default (nom de l'application)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_calcule_mentale);

        //interface
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //get height width screen
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen =displayMetrics.widthPixels;

        buttonsLevel(heightScreen,widthScreen);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player!=null){
            player.stop();
            player=null;
        }

        Log.d("TAG",    "The service is destroyed");
    }


    void LoosingGame(String message1,String message2){

        //interface
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //get height width screen
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen =displayMetrics.widthPixels;

        final int widthFL=widthScreen/7,heightFL=heightScreen/3;
        final int marginFL_TB=heightFL/25,marginFL_RL=widthFL/3;



        Button BtnStart=findViewById(R.id.startPlay);
        BtnStart.getLayoutParams().height=heightFL-marginFL_TB;
        BtnStart.getLayoutParams().width=widthFL*6-marginFL_RL*3;
        setMargins(BtnStart,marginFL_RL,marginFL_TB,marginFL_RL,0);
        BtnStart.setVisibility(View.VISIBLE);

        Button Btn1=findViewById(R.id.respond1);
        Btn1.getLayoutParams().height=0;
        Btn1.getLayoutParams().width=0;
        setMargins(Btn1,0,0,0,0);

        Button Btn2=findViewById(R.id.respond2);
        Btn2.getLayoutParams().height=0;
        Btn2.getLayoutParams().width=0;
        setMargins(Btn2,0,0,0,0);

        Button Btn3=findViewById(R.id.respond3);
        Btn3.getLayoutParams().height=0;
        Btn3.getLayoutParams().width=0;
        setMargins(Btn3,0,0,0,0);

        //textview
        TextView firstTextView=findViewById(R.id.firstTextView);
        firstTextView.setText(message1);

        TextView secondTextView=findViewById(R.id.firstTextViewL2);
        secondTextView.setText(message2);

        ImageView operation=findViewById(R.id.thirdImageView);
        operation.setVisibility(View.INVISIBLE);
        mprogressBar.setVisibility(View.INVISIBLE);
    }

    void beginGame(){

        if(player!=null) {player.stop(); countDownTimer.cancel();}
        if(countDownTimer!=null) { countDownTimer.cancel();}

        operation =random(0,1);
        ImageView operationImage=findViewById(R.id.thirdImageView);

        if(operation==0){
            //minus
            operationImage.setImageResource(R.drawable.minus1);
            if(level<=2){
                top = random(0,Numbers[level]);
                bottom = random(0,top);
            }else{
                top = random(random(Numbers[level-2],Numbers[level]),Numbers[level]);
                bottom = random(0,top);
            }

            propositions(top-bottom);
        }else{
            operationImage.setImageResource(R.drawable.plus);
            if(level<=2){
                top = random(0,Numbers[level]);
                bottom = random(0,Numbers[level]);
            }else{
                top = random(random(Numbers[level-2],Numbers[level]-15),Numbers[level]);
                bottom = random(random(Numbers[level-2],Numbers[level]-15),Numbers[level]);
            }

            propositions(top+bottom);
        }
        //textview
        TextView firstTextView=findViewById(R.id.firstTextView);
        firstTextView.setText(String.valueOf(top));

        final TextView secondTextView=findViewById(R.id.firstTextViewL2);
        secondTextView.setText(String.valueOf(bottom));

        final int time=getTimeRequired();
        final TextView Time=findViewById(R.id.timeTextView);

        countDownTimer=new CountDownTimer(time,950){
            int Seconds=time/1000;
            @Override
            public void onTick(long l) {
                Time.setText(String.valueOf(Seconds));
                Seconds--;
            }

            @Override
            public void onFinish() {
                Time.setText("0");
            }
        };
        countDownTimer.start();


        anim.cancel();
        anim.setDuration(time);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
        mprogressBar.postDelayed(checkEndTime, time);
        nbrOperation++;
        TextView leveltext=findViewById(R.id.levelTextView);
        leveltext.setText(String.valueOf(level));
        TextView scoretext=findViewById(R.id.scoreTextView);
        scoretext.setText(String.valueOf(score));

    }

    int getTimeRequired(){
        if(nbrOperation<=3){
            level=1;
            return 10000;
        }

        if(nbrOperation<=7){
            level=2;
            return 15000;
        }
        if(nbrOperation<=11){
            level=3;
            return 17000;
        }

        if(nbrOperation<=15){
            level=4;
            return 20000;
        }
        if(nbrOperation<=20){
            level=5;
            return 25000;
        }
        level=6;
        return 30000;
    }


    void propositions(final int respend){
        final Button[] btns=new Button[3];
        btns[0]=findViewById(R.id.respond1);
        btns[1]=findViewById(R.id.respond2);
        btns[2]=findViewById(R.id.respond3);
        final int rand=random(0,2);
        int falseRespend1=-1;

        for(int i=0;i<3;i++){
            final int j=i;

            if(i==rand){
                //correct respend
                btns[i].setText(String.valueOf(respend));
                btns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        btns[j].getBackground().setColorFilter(getResources().getColor(R.color.correctR), PorterDuff.Mode.MULTIPLY);

                        setOrdreImage(rand,1);
                        anim.cancel();
                        clicked(1);
                    }
                });

            }else {
                //false Respend
                if (falseRespend1==-1) {
                    falseRespend1=i;
                }else{
                    int [] rendomVal=getpreVals(respend);
                    btns[falseRespend1].setText(String.valueOf(rendomVal[0]));
                    btns[i].setText(String.valueOf(rendomVal[1]));
                }

                btns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        btns[rand].getBackground().setColorFilter(getResources().getColor(R.color.correctR), PorterDuff.Mode.MULTIPLY);
                        for(int i=0;i<3;i++){
                            if(i!=rand){
                                btns[i].getBackground().setColorFilter(getResources().getColor(R.color.falseR), PorterDuff.Mode.MULTIPLY);
                            }

                        }
                        setOrdreImage(rand,1);
                        anim.cancel();
                        clicked(0);
                    }
                });

            }

        }


    }

    int[] getpreVals(int correctOne){
        int[] res=new int[2];
        if(operation==0){//minus
                if(level<=2){
                    do{
                        res[0] = random(0,correctOne+10);
                    }while ( res[0]==correctOne);

                    do{
                        res[1]=random(0,correctOne+5);
                    }while (res[1]==correctOne || res[1]==res[0]);

                }else{
                    do{
                        res[0] = random(0,correctOne+5);
                    }while ( res[0]==correctOne);

                    do{
                        res[1]=random(0,correctOne+3);
                    }while (res[1]==correctOne || res[1]==res[0]);
                }

        }else{
            //addition
            if(level<=2){
                do{
                    res[0] = random(0,correctOne+20);

                }while ( res[0]==correctOne);

                do{
                    res[1]=random(0,correctOne+10);
                }while (res[1]==correctOne || res[1]==res[0]);

            }else{
                do{
                    res[0] = random(correctOne-14,correctOne+14);

                }while ( res[0]==correctOne);

                do{
                    res[1]=random(correctOne-14,correctOne+14);
                }while (res[1]==correctOne || res[1]==res[0]);
            }
        }
        return res;
    }

    void clicked(final int i)  {

        Button Btn1=findViewById(R.id.respond1);
        Btn1.setOnClickListener(null);
        Button Btn2=findViewById(R.id.respond2);
        Btn2.setOnClickListener(null);
        Button Btn3=findViewById(R.id.respond3);
        Btn3.setOnClickListener(null);

        ImageView money = findViewById(R.id.money);

        if(i==1){
            score+=level*level;
            TextView scoreText=findViewById(R.id.scoreTextView);
            scoreText.setText(String.valueOf(score));
            getTimeRequired();
            TextView levelText=findViewById(R.id.levelTextView);
            levelText.setText(String.valueOf(level));
            try {
                player = new MediaPlayer();
                AssetFileDescriptor afd = getAssets().openFd("money.mp3");
                player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                player.prepare();
                player.start();

            } catch (IOException e) {

                e.printStackTrace();
            }

            money.setVisibility(View.VISIBLE);
        }else{
            try {
                player = new MediaPlayer();
                AssetFileDescriptor afd = getAssets().openFd("gamester.mp3");
                player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                player.prepare();
                player.start();

            } catch (IOException e) {

                e.printStackTrace();
            }
            money.setVisibility(View.INVISIBLE);
        }

        mprogressBar.removeCallbacks(checkEndTime);
        countDownTimer.cancel();
        anim.cancel();
        anim.setDuration(3000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Button b=findViewById(R.id.respond1);
                b.getBackground().clearColorFilter();
                b=findViewById(R.id.respond2);
                b.getBackground().clearColorFilter();
                b=findViewById(R.id.respond3);
                b.getBackground().clearColorFilter();
                setOrdreImage(0,0);
                ImageView money = findViewById(R.id.money);
                money.setVisibility(View.INVISIBLE);
                if(i==1){

                    beginGame();
                }else{
                    LoosingGame("wrong","answer");
                }

            }
        }, 3000);

    }

    void setOrdreImage(int i,int v)
    {
        ImageView [] imgs=new ImageView[3];
        imgs[0]=findViewById(R.id.fImageViewL3);
        imgs[1]=findViewById(R.id.sImageViewL3);
        imgs[2]=findViewById(R.id.tImageViewL3);

        for(int j=0;j<3;j++){
            if(i==j){
                imgs[j].setImageResource(R.drawable.correct);
            }else{
                imgs[j].setImageResource(R.drawable.error);
            }
            if(v==1)imgs[j].setVisibility(View.VISIBLE);
            else imgs[j].setVisibility(View.INVISIBLE);
        }

    }

    int random(int min ,int max)
    {
        if(min<0)min=0;
        return (int)Math.floor(Math.random() * (max - min+1) + min);
    }

    void drawSpaceGame(int heightScreen,int widthScreen){

        LinearLayout firstLayout =findViewById(R.id.choiceLevel);
        firstLayout.getLayoutParams().height=0;
        firstLayout.getLayoutParams().width=0;

        drawHeader(heightScreen/10,widthScreen);
        drawFirstLayout(widthScreen/10*9,heightScreen/10*9);
        drawSecondtLayout(widthScreen/10*9,heightScreen/10*9);
        drawThirdLayout(widthScreen,heightScreen/10*9);

        anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
    }

    void buttonsLevel(final int height, final int width){
        LinearLayout firstLayout =findViewById(R.id.choiceLevel);
        firstLayout.getLayoutParams().height=height;
        firstLayout.getLayoutParams().width=width;


        Button b1=findViewById(R.id.Level1);
        b1.getLayoutParams().height=height/6;
        b1.getLayoutParams().width=width;
        b1.getBackground().setColorFilter(getResources().getColor(R.color.L1), PorterDuff.Mode.MULTIPLY);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level=1;
                nbrOperation=0;
                drawSpaceGame(height,width);
            }
        });

        Button b2=findViewById(R.id.Level2);
        b2.getLayoutParams().height=height/6;
        b2.getLayoutParams().width=width;
        b2.getBackground().setColorFilter(getResources().getColor(R.color.L2), PorterDuff.Mode.MULTIPLY);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level=2;
                nbrOperation=4;
                drawSpaceGame(height,width);
            }
        });

        Button b3=findViewById(R.id.Level3);
        b3.getLayoutParams().height=height/6;
        b3.getLayoutParams().width=width;
        b3.getBackground().setColorFilter(getResources().getColor(R.color.L3), PorterDuff.Mode.MULTIPLY);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level=3;
                nbrOperation=8;
                drawSpaceGame(height,width);
            }
        });

        Button b4=findViewById(R.id.Level4);
        b4.getLayoutParams().height=height/6;
        b4.getLayoutParams().width=width;
        b4.getBackground().setColorFilter(getResources().getColor(R.color.L4), PorterDuff.Mode.MULTIPLY);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level=4;
                nbrOperation=12;
                drawSpaceGame(height,width);
            }
        });

        Button b5=findViewById(R.id.Level5);
        b5.getLayoutParams().height=height/6;
        b5.getLayoutParams().width=width;
        b5.getBackground().setColorFilter(getResources().getColor(R.color.L5), PorterDuff.Mode.MULTIPLY);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level=5;
                nbrOperation=16;
                drawSpaceGame(height,width);
            }
        });

        Button b6=findViewById(R.id.Level6);
        b6.getLayoutParams().height=height/6;
        b6.getLayoutParams().width=width;
        b6.getBackground().setColorFilter(getResources().getColor(R.color.L6), PorterDuff.Mode.MULTIPLY);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level=6;
                nbrOperation=21;
                drawSpaceGame(height,width);
            }
        });

    }

    void drawHeader(int height,int width){
        LinearLayout firstLayout =findViewById(R.id.HeaderLayout);
        firstLayout.getLayoutParams().height=height;
        firstLayout.getLayoutParams().width=width;

        LinearLayout TimetLayout =findViewById(R.id.Time);
        TimetLayout.getLayoutParams().height=height;
        TimetLayout.getLayoutParams().width=width/3;

        TextView TimeLabel=findViewById(R.id.timeTextViewLabel);
        TimeLabel.getLayoutParams().height=height/2;
        TimeLabel.getLayoutParams().width=width/3;

        TextView Time=findViewById(R.id.timeTextView);
        Time.getLayoutParams().height=height/2;
        Time.getLayoutParams().width=width/3;

        LinearLayout scoreLayout =findViewById(R.id.score);
        scoreLayout.getLayoutParams().height=height;
        scoreLayout.getLayoutParams().width=width/3;

        TextView scoreLabel=findViewById(R.id.scoreTextViewLabel);
        scoreLabel.getLayoutParams().height=height/2;
        scoreLabel.getLayoutParams().width=width/3;

        TextView score=findViewById(R.id.scoreTextView);
        score.getLayoutParams().height=height/2;
        score.getLayoutParams().width=width/3;

        LinearLayout levelLayout =findViewById(R.id.levelLayout);
        levelLayout.getLayoutParams().height=height;
        levelLayout.getLayoutParams().width=width/3;

        TextView levelLabel=findViewById(R.id.levelTextViewLabel);
        levelLabel.getLayoutParams().height=height/2;
        levelLabel.getLayoutParams().width=width/3;

        TextView leveltext=findViewById(R.id.levelTextView);
        leveltext.getLayoutParams().height=height/2;
        leveltext.getLayoutParams().width=width/3;
        leveltext.setText(String.valueOf(level));


    }

    void drawThirdLayout(int widthScreen,int heightScreen)
    {
        final int widthFL=widthScreen/7,heightFL=heightScreen/3;
        final int marginFL_TB=heightFL/25,marginFL_RL=widthFL/3;

        //third layout
        LinearLayout firstLayout =findViewById(R.id.thirdLayout);
        firstLayout.getLayoutParams().height=heightFL;
        firstLayout.getLayoutParams().width=widthFL*7;
        setMargins(firstLayout,0,heightFL*2+heightScreen/10,0,0);


        //left layout 2
        LinearLayout leftLayout2 =findViewById(R.id.leftLayout2);
        leftLayout2.getLayoutParams().height=heightFL;
        leftLayout2.getLayoutParams().width=widthFL+marginFL_RL;
        setMargins(leftLayout2,0,0,0,0);

        ImageView firstImageView=findViewById(R.id.fImageViewL3);
        firstImageView.getLayoutParams().height=heightFL/3-marginFL_TB;
        firstImageView.getLayoutParams().width=widthFL;
        firstImageView.setVisibility(View.INVISIBLE);
        setMargins(firstImageView,marginFL_RL,marginFL_TB,0,0);

        ImageView secondImageView=findViewById(R.id.sImageViewL3);
        secondImageView.getLayoutParams().height=heightFL/3-marginFL_TB;
        secondImageView.getLayoutParams().width=widthFL;
        secondImageView.setVisibility(View.INVISIBLE);
        setMargins(secondImageView,marginFL_RL,0,0,0);

        ImageView thirdImageView=findViewById(R.id.tImageViewL3);
        thirdImageView.getLayoutParams().height=heightFL/3-marginFL_TB;
        thirdImageView.getLayoutParams().width=widthFL;
        thirdImageView.setVisibility(View.INVISIBLE);
        setMargins(thirdImageView,marginFL_RL,0,0,0);

        //right layout
        LinearLayout rightLayout =findViewById(R.id.rightLayout);
        rightLayout.getLayoutParams().height=heightFL;
        rightLayout.getLayoutParams().width=widthFL*6-marginFL_RL;
        setMargins(rightLayout,0,0,0,0);

        final Button BtnStart=findViewById(R.id.startPlay);
        BtnStart.getLayoutParams().height=heightFL-marginFL_TB;
        BtnStart.getLayoutParams().width=widthFL*6-marginFL_RL*3;
        setMargins(BtnStart,marginFL_RL,marginFL_TB,marginFL_RL,0);

        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score=0;
                BtnStart.getLayoutParams().height=0;
                BtnStart.getLayoutParams().width=0;
                setMargins(BtnStart,0,0,0,0);

                Button Btn1=findViewById(R.id.respond1);
                Btn1.getLayoutParams().height=heightFL/3-marginFL_TB;
                Btn1.getLayoutParams().width=widthFL*6-marginFL_RL*3;
                setMargins(Btn1,marginFL_RL,marginFL_TB,marginFL_RL,0);


                Button Btn2=findViewById(R.id.respond2);
                Btn2.getLayoutParams().height=heightFL/3-marginFL_TB;
                Btn2.getLayoutParams().width=widthFL*6-marginFL_RL*3;
                setMargins(Btn2,marginFL_RL,0,marginFL_RL,0);

                Button Btn3=findViewById(R.id.respond3);
                Btn3.getLayoutParams().height=heightFL/3-marginFL_TB;
                Btn3.getLayoutParams().width=widthFL*6-marginFL_RL*3;
                setMargins(Btn3,marginFL_RL,0,marginFL_RL,0);

                Btn1.setTextSize(25);
                Btn2.setTextSize(25);
                Btn3.setTextSize(25);

                ImageView operation=findViewById(R.id.thirdImageView);
                operation.setVisibility(View.VISIBLE);
                mprogressBar.setVisibility(View.VISIBLE);

                beginGame();

            }
        });


    }

    void drawFirstLayout(int widthScreen,int heightScreen)
    {
        //first layout
        LinearLayout firstLayout =findViewById(R.id.firstLayout);

        //images
        ImageView [] img=new ImageView[3];
        //image right
        ImageView firstImageView=findViewById(R.id.firstImageView);
        img[0]=firstImageView;
        //image left
        ImageView secondImageView=findViewById(R.id.secondImageView);
        img[1]=secondImageView;
        //image bottom
        ImageView thirdImageView=findViewById(R.id.thirdImageView);
        img[2]=thirdImageView;

        //textview
        TextView firstTextView=findViewById(R.id.firstTextView);

        drawLinearLayout(widthScreen,heightScreen,firstLayout,heightScreen/10,img,firstTextView,0);
    }

    void drawSecondtLayout(int widthScreen,int heightScreen)
    {
        //second layout
        LinearLayout firstLayout =findViewById(R.id.secondLayout);

        //images
        ImageView [] img=new ImageView[3];
        //image right
        ImageView firstImageView=findViewById(R.id.firstImageViewL2);
        img[0]=firstImageView;
        //image left
        ImageView secondImageView=findViewById(R.id.secondImageViewL2);
        img[1]=secondImageView;

        //textview
        TextView firstTextView=findViewById(R.id.firstTextViewL2);

        drawLinearLayout(widthScreen,heightScreen,firstLayout,heightScreen/3+heightScreen/10,img,firstTextView,1);
    }

    void drawLinearLayout(int widthScreen, int heightScreen, LinearLayout firstLayout, int margintop, ImageView[] ImageView, final TextView firstTextView, int progBar)
    {
        int widthFL=widthScreen,heightFL=heightScreen/3;
        int marginFL_TB=heightFL/8,marginFL_RL=widthScreen/20;

        firstLayout.getLayoutParams().height=heightFL;
        firstLayout.getLayoutParams().width=widthFL;
        setMargins(firstLayout,0,margintop,0,0);

        //image right
        ImageView[0].getLayoutParams().height=heightFL/3*2-marginFL_TB;
        ImageView[0].getLayoutParams().width=widthFL/5-marginFL_RL;
        setMargins(ImageView[0],marginFL_RL,marginFL_TB+marginFL_TB/2,0,0);

        //textview
        firstTextView.getLayoutParams().height=heightFL/3*2-marginFL_TB*2;
        firstTextView.getLayoutParams().width=widthFL/5*3;
        setMargins(firstTextView,0,marginFL_TB,0,0);



        ImageView[1].getLayoutParams().height=heightFL/3*2-marginFL_TB;
        ImageView[1].getLayoutParams().width=widthFL/5-marginFL_RL;
        setMargins(ImageView[1],0,marginFL_TB/2,0,0);

        if(progBar!=1){
            ImageView[2].getLayoutParams().height=heightFL/3;
            ImageView[2].getLayoutParams().width=widthFL/5;
            setMargins(ImageView[2],-3*(widthFL/5)+marginFL_RL,(heightFL/3)*2-marginFL_TB/2,0,0);
            ImageView[2].setVisibility(View.INVISIBLE);
        }else{

            mprogressBar = findViewById(R.id.progressBar);
            mprogressBar.getLayoutParams().height=heightFL/3;
            mprogressBar.getLayoutParams().width=heightFL/3;
            setMargins(mprogressBar,-3*(widthFL/5)+marginFL_RL+(widthFL/10)-heightFL/6,(heightFL/3)*2-marginFL_TB/2,0,0);
            mprogressBar.setVisibility(View.INVISIBLE);

            ImageView money = findViewById(R.id.money);
            money.getLayoutParams().height=heightFL/3;
            money.getLayoutParams().width=heightFL/3;
            setMargins(money,marginFL_RL+(widthFL/10)-heightFL/6,(heightFL/3)*2-marginFL_TB/2,0,0);
            money.setVisibility(View.INVISIBLE);
        }
    }


    private void setMargins (View view, int left, int top, int right, int bottom)
    {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


}