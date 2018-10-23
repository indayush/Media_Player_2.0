package com.example.ayushadarsh.media_player;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    boolean select = false;
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    SeekBar ss;
    Runnable runnable;
    Handler handler;

    Uri uri; // TO Select/map the songs/media files in the Device Memory
             // Uri = Uniform resource index


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5);
        b6=(Button)findViewById(R.id.button6);
        b7=(Button)findViewById(R.id.button7);
        b8=(Button)findViewById(R.id.button8);

        ss=(SeekBar)findViewById(R.id.seekBar);

        handler = new Handler();

        // For adding a Splash , Paste     compile 'pl.droidsonroids.gif:android-gif-drawable:1.2.10' in build.gradle Module :app

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Splash.class);
                startActivity(intent);
                finish();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.stop();
                finish();
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Splash.class);
                startActivity(i);

            }
        });


        ss.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void play(View p){

        if (select){        // By default boolean is true for if condition if nothing is given
            mp.start();

            changeseekbar();
        }
    }

    public void pause(View p){

        if (select){
            mp.pause();
        }

    }


    public void stop(View p){

        if (select){
            mp.seekTo(0);
            mp.pause();

            changeseekbar();
        }

    }

    public void prev(View p){

        mp.seekTo(mp.getCurrentPosition()-3500);

    }

    public void  next(View p){

        mp.seekTo(mp.getCurrentPosition()+3500);

    }

    public void select(View p){


        if (select){
            mp.pause();
        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT); // New Intent to select some content from Phone Memory
        i.setType("audio/*");                               // This decides the content type to be audio
        startActivityForResult(i,1);    // Result Oriented Intent. If true (1), it will go to onActivityResult Method


    }


    private void changeseekbar(){

        ss.setProgress(mp.getCurrentPosition());
        if (mp.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeseekbar();
                }
            };
            handler.postDelayed(runnable,1000);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Request code = 1 as mentioned above in startActivityResult
        // Result code = Result OK if audio file if found
        // Data = Song or Audio Files

        if (resultCode==RESULT_OK){
            uri = data.getData();
            mp=MediaPlayer.create(this,uri);
            select = true;

            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    ss.setMax(mp.getDuration());
                    mp.start();

                    changeseekbar();
                }
            });

        }

    }

}
