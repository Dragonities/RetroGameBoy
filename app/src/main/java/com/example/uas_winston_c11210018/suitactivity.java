package com.example.uas_winston_c11210018;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class suitactivity extends AppCompatActivity {

    private TextView score;
    private ImageView ikom,iplayer;
    private ImageButton gunting,batu,kertas;
    private int scoreplayer = 0, scorecomputer=0;
    private Button restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suitactivity);

        score = findViewById(R.id.score);
        ikom = findViewById(R.id.imagekomputer);
        iplayer = findViewById(R.id.imageplayer);
        gunting = findViewById(R.id.btngunting);
        batu = findViewById(R.id.btnbatu);
        kertas = findViewById(R.id.btnkertas);
        restart = findViewById(R.id.restart_btn);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_score();
            }
        });

        gunting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iplayer.setImageResource(R.drawable.ks);
                String message = gamestart("gunting");
                Toast.makeText(suitactivity.this, "Kamu memilih gunting, "+message, Toast.LENGTH_SHORT).show();
                score.setText("Score Player: "+ Integer.toString(scoreplayer) + " Komputer : "+Integer.toString(scorecomputer));
            }
        });
        batu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iplayer.setImageResource(R.drawable.bt);
                String message = gamestart("batu");
                Toast.makeText(suitactivity.this, "Kamu memilih batu, "+message, Toast.LENGTH_SHORT).show();
                score.setText("Score Player: "+ Integer.toString(scoreplayer) + " Komputer : "+Integer.toString(scorecomputer));
            }
        });

        kertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iplayer.setImageResource(R.drawable.pp);
                String message = gamestart("kertas");
                Toast.makeText(suitactivity.this, "Kamu memilih kertas, "+message, Toast.LENGTH_SHORT).show();
                score.setText("Score Player: "+ Integer.toString(scoreplayer) + " Komputer : "+Integer.toString(scorecomputer));
            }
        });
    }
    private String gamestart(String player){
        String Komputer = "";
        Random random = new Random();

        int angkakomp = random.nextInt(3)+1;

        if (angkakomp == 1) {
            Komputer = "batu";
        } else if (angkakomp == 2){
            Komputer = "gunting";
        }else if (angkakomp == 3){
            Komputer = "kertas";
        }
        if (Komputer == "batu"){
            ikom.setImageResource(R.drawable.bt);
        }else if (Komputer == "gunting"){
            ikom.setImageResource(R.drawable.ks);
        }else if (Komputer == "kertas"){
            ikom.setImageResource(R.drawable.pp);
        }

        if (Komputer == player){
            return "Seri";
        }else if (player == "batu" && Komputer == "kertas"){
            scorecomputer++;
            return "Player Kalah";
        }else if (player == "kertas" && Komputer == "batu"){
            scoreplayer++;
            return "Player Menang";
        }else if (player == "gunting" && Komputer == "kertas"){
            scoreplayer++;
            return "Player Menang";
        }else if (player == "kertas" && Komputer == "gunting"){
            scorecomputer++;
            return "Player Kalah";
        }else if (player == "batu" && Komputer == "gunting"){
            scoreplayer++;
            return "Player Menang";
        }else if (player == "gunting" && Komputer == "batu"){
            scorecomputer++;
            return "Player Kalah";

        }else{
            scorecomputer++;
            return "Player Kalah";
        }
    }
    public void clear_score() {

       scoreplayer = 0;
       scorecomputer = 0;

        score.setText("Score");



        ikom.setImageDrawable(getResources().getDrawable(R.drawable.que));
        iplayer.setImageDrawable(getResources().getDrawable(R.drawable.que));

    }

}