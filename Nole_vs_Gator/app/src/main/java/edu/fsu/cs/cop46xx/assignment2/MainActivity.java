package edu.fsu.cs.cop46xx.assignment2;

/*
 * Created by Wendy Slattery 5/29/20
 * COP 46xx
 * Nole vs Gator: working with buttons
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtvw;
    private Button btn;
    private ImageView imgview;
    private int this_txt;
    private int this_btnTxt;
    private int this_img;
    int[] txt_collection ={R.string.go_seminoles, R.string.later_gator, R.string.seminole_pride,
                            R.string.meh, R.string.FSU_name, R.string.just_say_no};
    int[] btn_collection ={R.string.show_me_a_gator, R.string.show_me_a_nole};
    int[] img_collection ={R.drawable.nole1, R.drawable.gator, R.drawable.seal, R.drawable.gator2,
                            R.drawable.letters, R.drawable.gator3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonClick();
    }
        public void buttonClick()
        {
            txtvw=(TextView)findViewById(R.id.txtView);
            imgview=(ImageView)findViewById(R.id.imageView);
            btn=(Button)findViewById(R.id.button1);
            btn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            this_txt++;
                            this_btnTxt++;
                            this_img++;
                            this_txt = this_txt % txt_collection.length;
                            txtvw.setText(txt_collection[this_txt]);
                            this_btnTxt = this_btnTxt % btn_collection.length;
                            btn.setText(btn_collection[this_btnTxt]);
                            this_img = this_img % img_collection.length;
                            imgview.setImageResource(img_collection[this_img]);

                        }
                    }
            );
        }

}
