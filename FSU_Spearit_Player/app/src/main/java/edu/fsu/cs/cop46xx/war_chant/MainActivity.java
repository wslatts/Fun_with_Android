package edu.fsu.cs.cop46xx.war_chant;

/*
 * Created by Wendy Slattery 7/19/20
 * COP 46xx
 * Assignment 5 - Create an Ad for FSU CS on assignment 4 project
 */

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.util.concurrent.TimeUnit;



public class MainActivity extends AppCompatActivity {

    Button btn_song1, btn_song2, btn_song3, btn_song4, btn_song5, btn_song6, btn_song7;
    Button btn_stop, btn_pause, btn_resume;
    TextView txt_start, txt_finish;
    private MediaPlayer mediaPlayer;

    private double currentTime = 0;
    private double endTime = 0;

    private SeekBar seekBar;
    public static int durationOneTime = 0;
    private Handler myHandler = new Handler();

    AdView mAdView;


    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_song1 = (Button) findViewById(R.id.btn_war_chant);
        btn_song2 = (Button) findViewById(R.id.btn_fight_song);
        btn_song3 = (Button) findViewById(R.id.btn_victory_song);
        btn_song4 = (Button) findViewById(R.id.btn_garnet_gold);
        btn_song5 = (Button) findViewById(R.id.btn_cheer);
        btn_song6 = (Button) findViewById(R.id.btn_fourth_fanfare);
        btn_song7 = (Button) findViewById(R.id.btn_uprising);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_resume = (Button) findViewById(R.id.btn_play);

        txt_start = (TextView) findViewById(R.id.txt_cur_time);
        txt_finish = (TextView) findViewById(R.id.txt_end_time);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setClickable(false);


        //on-click listeners
        btn_song1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySong(R.raw.war_chant);
            }
        });

        btn_song2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySong(R.raw.fight_song);
            }
        });

        btn_song3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySong(R.raw.victory_song);
            }
        });

        btn_song4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySong(R.raw.gold_garnet);
            }
        });

        btn_song5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySong(R.raw.cheer);
            }
        });

        btn_song6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySong(R.raw.fanfare);
            }
        });

        btn_song7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySong(R.raw.seminole_uprising);
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Pausing Song", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                btn_stop.setEnabled(true);
                btn_pause.setEnabled(false);
                btn_resume.setEnabled(true);
            }
        });



        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer == null){
                    try{
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.war_chant);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //Toast.makeText(getApplicationContext(), "Resume Song", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
                btn_stop.setEnabled(true);
                btn_pause.setEnabled(true);
                btn_resume.setEnabled(false);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Stopping Song", Toast.LENGTH_SHORT).show();
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentTime = 0;
                endTime = 0;
                mediaPlayer.seekTo( 0);
                durationOneTime = 0;

                btn_stop.setEnabled(false);
                btn_pause.setEnabled(true);
                btn_resume.setEnabled(true);

            }
        });


    }

    private Runnable UpdateSeekBar = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            currentTime = mediaPlayer.getCurrentPosition();
            txt_start.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) currentTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) currentTime) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) currentTime)))
            );
            seekBar.setProgress((int)currentTime);
            myHandler.postDelayed(this, 100);
        }
    };

    @SuppressLint("DefaultLocale")
    private void PlaySong (int song) {
        if(mediaPlayer != null){
            try{
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //currentTime = 0;
        //endTime = 0;
        //mediaPlayer.seekTo( 0);
        durationOneTime = 0;

        mediaPlayer = MediaPlayer.create(MainActivity.this, song);
        //Toast.makeText(getApplicationContext(), "Playing Song", Toast.LENGTH_SHORT).show();

        endTime = mediaPlayer.getDuration();
        currentTime = mediaPlayer.getCurrentPosition();

        if(durationOneTime == 0) {
            seekBar.setMax((int) endTime);
            durationOneTime = 1;
        }

        txt_start.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) currentTime),
                TimeUnit.MILLISECONDS.toSeconds((long) currentTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) currentTime)))
        );

        txt_finish.setText(String.format("%d min, %d sec",
            TimeUnit.MILLISECONDS.toMinutes((long) endTime),
            TimeUnit.MILLISECONDS.toSeconds((long) endTime) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) endTime)))
        );

        seekBar.setProgress((int)currentTime);
        myHandler.postDelayed(UpdateSeekBar, 100);


        btn_pause.setEnabled(true);
        btn_stop.setEnabled(true);
        btn_resume.setEnabled((false));

        mediaPlayer.start();
    }

    public void open(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cs.fsu.edu/"));
        startActivity(browserIntent);
    }
}