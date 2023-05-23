package edu.fsu.cs.cop46xx.bears_beets_battlestargalactica;
/*
 * Created by Wendy Slattery 6/7/20
 * Based on TV Sitcom The Office
 * for academic purposes only for COP 46xx
 */
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;



public class MainActivity extends AppCompatActivity {

    ImageView img_gamerPick, img_dwightsPick;
    TextView txt_score;
    Button btn_bear, btn_beets, btn_battlestar;
    private int gamerScore;
    private int dwightsScore;
    private SoundPool soundPool;
    private int[] sounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign ids to variables
        btn_bear = (Button) findViewById(R.id.btn_bear);
        btn_beets = (Button) findViewById(R.id.btn_beets);
        btn_battlestar = (Button) findViewById(R.id.btn_battlestar);

        img_gamerPick = (ImageView) findViewById(R.id.img_gamerPick);
        img_dwightsPick = (ImageView) findViewById(R.id.img_dwightsPick);

        txt_score = (TextView) findViewById(R.id.txt_score);

        gamerScore = 0;
        dwightsScore = 0;

        //SoundPool incorporates audio sounds
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            //SoundPool (int maxStreams, int streamType, int srcQuality)
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        // load up array of sounds for the pool
        sounds = new int[9];
        sounds[Sounds.BEAREATS] = soundPool.load(this, R.raw.bears_eat_beets, 1);
        sounds[Sounds.HURTME] = soundPool.load(this, R.raw.hurt_feelings, 1);
        sounds[Sounds.IGNORANT] = soundPool.load(this, R.raw.ignorant_slut, 1);
        sounds[Sounds.MICHAEL] = soundPool.load(this, R.raw.michael, 1);
        sounds[Sounds.NOTARY] = soundPool.load(this, R.raw.notary, 1);
        sounds[Sounds.NOSTRESS] = soundPool.load(this, R.raw.nothing_stresses_me, 1);
        sounds[Sounds.TWOSCHOOLS] = soundPool.load(this, R.raw.two_schools, 1);
        sounds[Sounds.WHATBEAR] = soundPool.load(this, R.raw.what_kind_of_bear, 1);
        sounds[Sounds.WELLWELL] = soundPool.load(this, R.raw.well_well_well, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if(sounds[Sounds.BEAREATS] == sampleId){
                    soundPool.play(sampleId, 1, 1, 1, 0, 1);
                }
            }
        });


        // on-click listeners
        btn_bear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                img_gamerPick.setImageResource(R.drawable.bear);
                String result = takeTurn("bear");
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                txt_score.setText(String.format("Score:   You: %d  |  Dwight: %d",
                        gamerScore, dwightsScore));
                //soundPool.play(bearsSound, 1, 1, 1, 0, 1);

            }
        });

        btn_beets.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                img_gamerPick.setImageResource(R.drawable.beet);
                String result = takeTurn("beets");
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                txt_score.setText(String.format("Score:   You: %d  |  Dwight: %d",
                        gamerScore, dwightsScore));
                //soundPool.play(beetsSound, 1, 1, 1, 0, 1);
            }
        });

        btn_battlestar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                img_gamerPick.setImageResource(R.drawable.battlestar);
                String result = takeTurn("battlestar");
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                txt_score.setText(String.format("Score:   You: %d  |  Dwight: %d",
                        gamerScore, dwightsScore));
                //soundPool.play(battlestarSound, 1, 1, 1, 0, 1);
            }
        });

    }

    public String takeTurn(String gamersPick){
        String dwightsPick = "";
        Random random = new Random();

        // Only three choices of one, two, or three randomly selected for Dwight's Pick
        int dwightsNumber = random.nextInt(3) + 1;

        switch(dwightsNumber){
            case 1:
                dwightsPick = "bear";
                img_dwightsPick.setImageResource(R.drawable.bear);
                break;
            case 2:
                dwightsPick = "beets";
                img_dwightsPick.setImageResource(R.drawable.beet);
                break;
            case 3:
                dwightsPick = "battlestar";
                img_dwightsPick.setImageResource(R.drawable.battlestar);
                break;
            default:
                dwightsPick = "notSure";
                //img_dwightsPick.setImageResource(R.drawable.bear);
        }

        if(dwightsPick.equals(gamersPick)) {
            soundPool.play(sounds[Sounds.MICHAEL], 1, 1, 1, 0, 1);
            return "DRAW - \nNobody wins.";
        }
        else if (dwightsPick.equals("bear") && gamersPick.equals("beets")){
            dwightsScore++;
            soundPool.play(sounds[Sounds.BEAREATS], 1, 1, 1, 0, 1);
            return "YOU LOSE - \nBears eat Beets";
        }
        else if (dwightsPick.equals("beets") && gamersPick.equals("bear")){
            gamerScore++;
            soundPool.play(sounds[Sounds.WHATBEAR], 1, 1, 1, 0, 1);
            return "YOU WIN - \nBears eat Beets";
        }
        else if (dwightsPick.equals("beets") && gamersPick.equals("battlestar")){
            dwightsScore++;
            soundPool.play(sounds[Sounds.HURTME], 1, 1, 1, 0, 1);
            return "YOU LOSE - \nBeets jams engines in Battlestar Galactica";
        }
        else if (dwightsPick.equals("battlestar") && gamersPick.equals("beets")){
            gamerScore++;
            soundPool.play(sounds[Sounds.IGNORANT], 1, 1, 1, 0, 1);
            return "YOU WIN - \nBeets jams engines in Battlestar Galactica";
        }
        else if (dwightsPick.equals("battlestar") && gamersPick.equals("bear")){
            dwightsScore++;
            soundPool.play(sounds[Sounds.WELLWELL], 1, 1, 1, 0, 1);
            return "YOU LOSE - \nBattlestar Galactica lands on Bears";
        }
        else if (dwightsPick.equals("bear") && gamersPick.equals("battlestar")){
            gamerScore++;
            soundPool.play(sounds[Sounds.NOSTRESS], 1, 1, 1, 0, 1);
            return "YOU WIN - \nBattlestar Galactica lands on Bears";
        }
        else{
            return "Error: \nDid not calculate choices or winners";
        }
    }


}
