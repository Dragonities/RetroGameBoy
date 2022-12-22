package com.example.uas_winston_c11210018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser user;
    private TextView nama;
    private Button logout;
    private ImageButton snake,tictac,suit,gyro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nama = findViewById(R.id.nama);
        user = FirebaseAuth.getInstance().getCurrentUser();
        logout = findViewById(R.id.logoutbtn);
        snake = findViewById(R.id.snake12);
        tictac = findViewById(R.id.tictactoe12);
        suit = findViewById(R.id.suit12);
        gyro = findViewById(R.id.gyrogame12);


        gyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), splashgyro.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright,R.anim.slideleft);
            }
        });

        snake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SplashScreenSnake.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright,R.anim.slideleft);
            }
        });
        tictac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), splashscreentic.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright,R.anim.slideleft);
            }
        });
        suit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), splashscreenrock.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright,R.anim.slideleft);
            }
        });


        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            overridePendingTransition(R.anim.slideright,R.anim.slideleft);
            finish();
        });
        if (user!=null){
            nama.setText("Hello, "+ user.getDisplayName());
            }else {
            nama.setText("login gagal");
        }

    }

}