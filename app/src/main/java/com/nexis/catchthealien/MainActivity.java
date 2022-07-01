package com.nexis.catchthealien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    TextView txtTime;
    TextView txtbest;
    TextView txtScore;
    int score=0;
    Button btn_baslat;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;
    int bestscore;
    SharedPreferences sharedPreferences;

    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTime = findViewById(R.id.txtTime);
        txtbest=findViewById(R.id.txtbest);
        txtScore = findViewById(R.id.txtScore);
        btn_baslat=findViewById(R.id.btn_baslat);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        sharedPreferences=this.getSharedPreferences("com.nexis.catchthealien", Context.MODE_PRIVATE);

        imageArray = new ImageView[] {imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};








    }

    public void baslat(View v){

        int bestscoree=sharedPreferences.getInt("bestscore",0);

        txtbest.setText("BestScore: "+bestscoree);

        hide();
        btn_baslat.setVisibility(View.INVISIBLE);


        new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTime.setText(""+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                txtTime.setText("Time Off");

                if (score>bestscore){

                    bestscore=score;

                    txtbest.setText("BestScore:"+bestscore);

                    sharedPreferences.edit().putInt("bestscore",bestscore).apply();



                }

                handler.removeCallbacks(runnable);

                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }


                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Restart?");
                alert.setMessage("Are you sure to restart game?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //restart

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();

            }
        }.start();


    }
    public void hide(){
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {

                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,500);




            }
        };


        handler.post(runnable);



    }



    public void increaseScore (View view) {

        score++;
        txtScore.setText("Score: " + score);

    }


}