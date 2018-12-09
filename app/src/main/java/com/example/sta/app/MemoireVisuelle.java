package com.example.sta.app;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MemoireVisuelle extends AppCompatActivity {
    final String TAG ="States";
    boolean first=true;
    TextView text1;
    Random rand = new Random();
    public int n = rand.nextInt(16);
    public int m = (rand.nextInt(7))+18;
    public int scor=00;
    Button [] tab = new Button[25]; //tableau qui contiens les carés(button)
    ArrayList<Integer> play = new ArrayList<Integer>(); //liste de clique de joueur
    int dif = 1; //level
    int blockplayed = 0; // le nombre de fois que le joueur a joué dans une etape
    int ddf = 0; // nombre d'etape dans le niveau
    int nbloose=0; // combien de fois il a echoué(il a le droit de 3 fois)
    boolean loose = false;
    boolean lvlwin = false; //il n'a pas gagné un niveau
    int debut_sec;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_memoire_visuelle2);
        init();

        //desactiver tous les carés
        for(int i=0;i<25;i++)
            tab[i].setEnabled(false);



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //get height width screen
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen =displayMetrics.widthPixels;

        LinearLayout layout1 = findViewById(R.id.layout1);
        drawLinearLayout(widthScreen,heightScreen,layout1,0,1);

        LinearLayout layout2 = findViewById(R.id.L4);
        drawLinearLayout(widthScreen,heightScreen,layout2,heightScreen/5,3);

        LinearLayout layout3 = findViewById(R.id.buttonsLayout);
        drawLinearLayout(widthScreen,heightScreen,layout3,(heightScreen/5) * 4,1);

    }

    void drawLinearLayout(int widthScreen, int heightScreen, LinearLayout firstLayout, int margintop, int steps)
    {


        int widthFL=widthScreen,heightFL=(heightScreen/5)*steps;
        int marginFL_TB=heightFL/8,marginFL_RL=widthScreen/15;

        firstLayout.getLayoutParams().height=heightFL;
        firstLayout.getLayoutParams().width=widthFL-(2*(marginFL_RL));
        setMargins(firstLayout,marginFL_RL,margintop,marginFL_RL,0);



    }


    private void setMargins (View view, int left, int top, int right, int bottom)
    {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
    // on appelle la fonction check dans tous les boutons
    public void b1(View view) {
        check(0);
    }
    public void b2(View view) {
        check(1);
    }
    public void b3(View view) {
        check(2);
    }
    public void b4(View view) {
        check(3);
    }
    public void b5(View view) {
        check(4);
    }
    public void b6(View view) {
        check(5);
    }
    public void b7(View view) {
        check(6);
    }
    public void b8(View view) {
        check(7);
    }
    public void b9(View view) {
        check(8);
    }
    public void b10(View view) {
        check(9);
    }
    public void b11(View view) {
        check(10);
    }
    public void b12(View view) {
        check(11);
    }
    public void b13(View view) {
        check(12);
    }
    public void b14(View view) {
        check(13);
    }
    public void b15(View view) {
        check(14);
    }
    public void b16(View view) {
        check(15);
    }
    public void b17(View view) {
        check(16);
    }
    public void b18(View view) {
        check(17);
    }
    public void b19(View view) {
        check(18);
    }
    public void b20(View view) {
        check(19);
    }
    public void b21(View view) {
        check(20);
    }
    public void b22(View view) {
        check(21);
    }
    public void b23(View view) {
        check(22);
    }
    public void b24(View view) {
        check(23);
    }
    public void b25(View view) {
        check(24);
    }


    public void refresh()
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        TextView scoreresult =(TextView)findViewById(R.id.scoreresult);
        Button next = (Button) findViewById(R.id.next);
        //si il a échoué
        if(loose)
        {
            //nombre d'étapes se diminue et le score
            // ddf--;

            scor--;
            // ddf=1;
            // dif =1;
        }
        else
        {   //si nombre d'etape inferieur de celui de niveau
            if(ddf<dif){
                ddf++;
                if(!first){
                    CharSequence text = "Il rest " +(dif-ddf+1)+" etapes";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();}
            }

            else
            {   // si il a joué tous les étape du niveau
                //nombre d'etape revient à 1
                ddf=1;
                nbloose=0;
                blockplayed=0;
                //on incremente le niveau
                dif++;

                //on affiche le niveau
                CharSequence text3 = "Niveau "+dif;
                Toast toast3 = Toast.makeText(context, text3, duration);
                toast3.show();


            }
        }
        //on vide la liste des clicks
        play.clear();
        //on remet les carés a leurs couleurs initiale
        for(int i=0;i<25;i++)
        {
            tab[i].setBackgroundColor(Color.parseColor("#984465"));
            tab[i].setEnabled(false);
        }

        loose = false;
        //mis a jour d'affichage de niveau
        TextView levelresult=(TextView)findViewById(R.id.levelresult);
        levelresult.setText(" "+dif);



    }
    //chek vérifie si il a clicker sur le bon caré ou non
   /* public void check(int k)
    {
        Button retry = (Button) findViewById(R.id.retry);
        Button next = (Button) findViewById(R.id.next);
        Button start = (Button) findViewById(R.id.start);
        TextView scoreresult =(TextView)findViewById(R.id.scoreresult);
        TextView levelresult =(TextView)findViewById(R.id.levelresult);
        start.setVisibility(View.GONE);
        retry.setVisibility(View.GONE);

        //   if(!lvlwin) {
        // si on a clické sur le bn carré
        if (play.contains(k)) {
            //on incremente le nombre d'etape
            blockplayed++;
            //le caré sera coloré en vert
            tab[k].setBackgroundColor(Color.GREEN);
            //si on a terminé les etape du niveau
            if(blockplayed==dif)
            {    scor+=10;

                //mis a jour du score
                scoreresult.setText(" "+scor);
                if(ddf==5 && dif==5){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "WIn!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    next.setVisibility(View.GONE);
                    retry.setVisibility(View.GONE);
                    start.setVisibility(View.VISIBLE);
                    start.setText("Rejouer");
                    scor=00;
                    dif = 1;
                    blockplayed = 0;
                    ddf = 0;
                    nbloose=0;
                    loose = false;
                    scoreresult.setText(" "+scor);
                }else{




                    //boutton next devient visible
                    next.setVisibility(View.VISIBLE);
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "bravo!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    //etape revient a 0
                    blockplayed=0;
                    lvlwin=true;}
            }
        } else {//si on a echoué
            nbloose++;
            blockplayed=0;
            //on lui donne la possibilité de ressayer 3 fois dans un niveau
            if(nbloose<3)
                retry.setVisibility(View.VISIBLE);
            else
                start.setVisibility(View.VISIBLE);

            tab[k].setBackgroundColor(Color.RED);
            loose = true;

            for (int i = 0; i < play.size(); i++) {
                tab[play.get(i)].setBackgroundColor(Color.GREEN);
                for (int l = 0; l < 25; l++) {
                    tab[l].setEnabled(false);
                }

            }
        }
    }*/
    //  }

    //chek vérifie si il a clicker sur le bon caré ou non
    public void check(int k)
    {
        Button retry = (Button) findViewById(R.id.retry);
        Button next = (Button) findViewById(R.id.next);
        Button start = (Button) findViewById(R.id.start);
        TextView scoreresult =(TextView)findViewById(R.id.scoreresult);
        TextView levelresult =(TextView)findViewById(R.id.levelresult);
        start.setVisibility(View.GONE);
        retry.setVisibility(View.GONE);

        //   if(!lvlwin) {
        // si on a clické sur le bn carré
        if (play.contains(k)) {
            //on incremente le nombre d'etape
            blockplayed++;
            //le caré sera coloré en vert
            tab[k].setBackgroundColor(Color.GREEN);
            //si on a terminé les etape du niveau
            if(blockplayed==dif)
            {    scor+=10;

                //mis a jour du score
                scoreresult.setText(" "+scor);
                if(ddf==5 && dif==5){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "WIn!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    next.setVisibility(View.GONE);
                    retry.setVisibility(View.GONE);
                    start.setVisibility(View.VISIBLE);
                    start.setText("Rejouer");
                    scor=00;
                    dif = 1;
                    blockplayed = 0;
                    ddf = 0;
                    nbloose=0;
                    loose = false;
                    scoreresult.setText(" "+scor);
                }else{




                    //boutton next devient visible
                    // next.setVisibility(View.VISIBLE);

                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "bravo!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    blockplayed=0;
                    lvlwin=true;
                    Handler hand=new Handler();
                    hand.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startrecur();
                        }
                    },2000);


                    //etape revient a 0
                }
            }
        } else {//si on a echoué
            // nbloose++;
            blockplayed=0;
            //on lui donne la possibilité de ressayer 3 fois dans un niveau
            // if(nbloose<3)
            retry.setVisibility(View.VISIBLE);
            // else
            //    start.setVisibility(View.VISIBLE);

            tab[k].setBackgroundColor(Color.RED);
            loose = true;

            for (int i = 0; i < play.size(); i++) {
                tab[play.get(i)].setBackgroundColor(Color.GREEN);
                for (int l = 0; l < 25; l++) {
                    tab[l].setEnabled(false);
                }

            }
        }
    }


    public void start(View view)
    {
        Button start =(Button)findViewById(R.id.start);
        start.setVisibility(View.GONE);
        refresh();
        debut_sec = (7-ddf)*1000;
        text1=(TextView)findViewById(R.id.textView1);
        play.clear();
        for(int i=0;i<dif;i++)
        {

            Random r = new Random();
            int k =r.nextInt(25);
            if(play.contains(k))
                i--;
            else
                play.add(k);
        }

        new CountDownTimer(debut_sec, 1000)
        {
            public void onTick(long millisUntilFinished) {
                text1.setText(" "+TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)) ;

                for(int i=0;i<play.size();i++)
                {
                    tab[play.get(i)].setBackgroundColor(Color.GREEN);
                }
                Button start =(Button) findViewById(R.id.start);
                Button retry =(Button) findViewById(R.id.retry);
                Button next =(Button) findViewById(R.id.next);
                start.setVisibility(View.GONE);
                retry.setVisibility(View.GONE);
                next.setVisibility(View.GONE);

            }

            public void onFinish() {
                text1.setText(" 00");
                if(first){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "Choisit les carés à coloré ";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    first=false;}
                for (int i = 0; i < play.size(); i++) {
                    tab[play.get(i)].setBackgroundColor(Color.parseColor("#984465"));

                }
                for(int i=0;i<25;i++)
                {
                    tab[i].setEnabled(true);
                }



            }
        }.start();
    }

    public void startrecur()
    {
        Button start =(Button)findViewById(R.id.start);
        start.setVisibility(View.GONE);
        refresh();
        debut_sec = (7-ddf)*1000;
        text1=(TextView)findViewById(R.id.textView1);
        play.clear();
        for(int i=0;i<dif;i++)
        {

            Random r = new Random();
            int k =r.nextInt(25);
            if(play.contains(k))
                i--;
            else
                play.add(k);
        }

        new CountDownTimer(10000, 1000)
        {
            public void onTick(long millisUntilFinished) {
                text1.setText(" "+TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)) ;

                for(int i=0;i<play.size();i++)
                {
                    tab[play.get(i)].setBackgroundColor(Color.GREEN);
                }
                Button start =(Button) findViewById(R.id.start);
                Button retry =(Button) findViewById(R.id.retry);
                Button next =(Button) findViewById(R.id.next);
                start.setVisibility(View.GONE);
                retry.setVisibility(View.GONE);
                next.setVisibility(View.GONE);

            }

            public void onFinish() {
                text1.setText(" 00");
                if(first){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "Choisit les carés à coloré ";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    first=false;}
                for (int i = 0; i < play.size(); i++) {
                    tab[play.get(i)].setBackgroundColor(Color.parseColor("#984465"));

                }
                for(int i=0;i<25;i++)
                {
                    tab[i].setEnabled(true);
                }



            }
        }.start();
    }



    public void Count(View view){


        text1=(TextView)findViewById(R.id.textView1);
        new CountDownTimer(10000, 1000) {
            // Random rand = new Random();
            // int n = rand.nextInt(16);
            //  int m = (rand.nextInt(7))+18;

            // adjust the milli seconds here
            // le timer qui calcule les 15 seconds
            public void onTick(long millisUntilFinished) {

                text1.setText(" "+TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)) ;

                Button start =(Button) findViewById(R.id.start);

                tab[n].setBackgroundColor(Color.parseColor("#984465"));
                tab[m].setBackgroundColor(Color.GREEN);

                //le bouton qui lance le timer va etre desactivé
                start.setEnabled(false);
            }
            //quand les 10 second finissent
            public void onFinish() {
                text1.setText(" 00!");
                Button start =(Button) findViewById(R.id.start);


                start.setVisibility(View.GONE);
                //le bouton de jouer sera visible


                TextView time=(TextView)findViewById(R.id.Time);
                TextView time1=(TextView)findViewById(R.id.textView1);

                //la couleur de caré revient a sa couleur initiale
                // b1.setBackgroundColor(Color.parseColor("#D3D3D3"));



                tab[n].setBackgroundColor(Color.parseColor("#D3D3D3"));
                tab[m].setBackgroundColor(Color.parseColor("#D3D3D3"));

                //le bouton qui lance le timer sera invisible


                //les bouton de timer seront invisibles
                time.setVisibility(View.GONE);
                time1.setVisibility(View.GONE);
                //reactiver les bouton
                //desactiver le click sur tous les boutons
                for(int i =0;i<25;i++)
                {
                    tab[i].setEnabled(true);
                }


            }
        }.start();




    }



    public void init()
    {
        tab[0] =(Button) findViewById(R.id.b1);
        tab[1] =(Button) findViewById(R.id.b2);
        tab[2] =(Button) findViewById(R.id.b3);
        tab[3 ]=(Button) findViewById(R.id.b4);
        tab[4 ]=(Button) findViewById(R.id.b5);
        tab[5 ]=(Button) findViewById(R.id.b6);
        tab[6 ]=(Button) findViewById(R.id.b7);
        tab[7] =(Button) findViewById(R.id.b8);
        tab[8 ]=(Button) findViewById(R.id.b9);
        tab[9] =(Button) findViewById(R.id.b10);
        tab[10] =(Button) findViewById(R.id.b11);
        tab[11] =(Button) findViewById(R.id.b12);
        tab[12] =(Button) findViewById(R.id.b13);
        tab[13] =(Button) findViewById(R.id.b14);
        tab[14] =(Button) findViewById(R.id.b15);
        tab[15] =(Button) findViewById(R.id.b16);
        tab[16] =(Button) findViewById(R.id.b17);
        tab[17] =(Button) findViewById(R.id.b18);
        tab[18] =(Button) findViewById(R.id.b19);
        tab[19] =(Button) findViewById(R.id.b20);
        tab[20] =(Button) findViewById(R.id.b21);
        tab[21] =(Button) findViewById(R.id.b22);
        tab[22] =(Button) findViewById(R.id.b23);
        tab[23] =(Button) findViewById(R.id.b24);
        tab[24] =(Button) findViewById(R.id.b25);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"MainActivity: OnStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"MainActivity: OnResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"MainActivity: OnPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"MainActivity: OnRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"MainActivity: OnDestroy()");
    }

     public void quitter(View view){
         Intent intent = new Intent(MemoireVisuelle.this, Main2Activity.class);
         startActivity(intent);
     }
}
