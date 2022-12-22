package com.example.uas_winston_c11210018;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TictactoeActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView player1score,player2score, playerstatus;
    private Button[] buttons = new Button[9];
    private Button resetgame;

    private int player1scorecount, player2scorecount, rountcount;
    boolean activeplayer;

    int[] gamestate = {2,2,2,2,2,2,2,2,2};

    int[] [] winningPosition = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}, {6, 7, 8}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

            player1score = findViewById(R.id.player1score);
            player2score = findViewById(R.id.player2score);
            playerstatus = findViewById(R.id.playerstatus);

            resetgame = findViewById(R.id.restart);

            for (int i =0;i< buttons.length; i++){
                String buttonID = "btn_" + i;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i] = (Button) findViewById(resourceID);
                buttons[i].setOnClickListener(this);
            }

            rountcount =0;
            player1scorecount =0;
            player2scorecount =0;
            activeplayer = true;

        }

        @Override
        public void onClick(View v) {
            if ((!((Button)v).getText().toString().equals(""))){
                return;
            }
            String buttonID = v.getResources().getResourceEntryName(v.getId());
            int gamestatepointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

            if (activeplayer){
                ((Button)v).setText("X");
                ((Button)v).setTextColor(Color.parseColor("#FFC34A"));
                gamestate[gamestatepointer]= 0;

            }else{
                ((Button)v).setText("O");
                ((Button)v).setTextColor(Color.parseColor("#70FFEA"));
                gamestate[gamestatepointer]= 1;
            }
            rountcount++;

            if (checkWinner()){
                if (activeplayer){
                    player1scorecount++;
                    updatePlayerscore();
                    Toast.makeText(this, "Player 1 Won!", Toast.LENGTH_SHORT).show();
                    playAgain();
                }else{
                    player2scorecount++;
                    updatePlayerscore();
                    Toast.makeText(this, "Player 2 Won!", Toast.LENGTH_SHORT).show();
                    playAgain();
                }
            }else if(rountcount == 9){
                playAgain();
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();

            }else{
                activeplayer = !activeplayer;
            }
            if (player1scorecount > player2scorecount){
                playerstatus.setText("Player 1 is leading");
            }else if(player2scorecount>player1scorecount){
                playerstatus.setText("Player 2 is leading");
            }else{
                playerstatus.setText("");
            }
            resetgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playAgain();
                    player1scorecount = 0;
                    player2scorecount = 0;
                    playerstatus.setText("");
                    updatePlayerscore();
                }
            });
        }
        public boolean checkWinner(){
            boolean winnerResult = false;

            for (int [] winningPosition : winningPosition){
                if (gamestate[winningPosition[0]] == gamestate[winningPosition[1]] &&
                        gamestate[winningPosition[1]] == gamestate[winningPosition[2]] &&
                        gamestate[winningPosition[0]] !=2){
                    winnerResult = true;
                }
            }
            return winnerResult;
        }
        public void updatePlayerscore(){
            player1score.setText(Integer.toString(player1scorecount));
            player2score.setText(Integer.toString(player2scorecount));
        }
        public void playAgain(){
            rountcount = 0;
            activeplayer = true;

            for (int i = 0; i < buttons.length; i++){
                gamestate[i] = 2;
                buttons[i].setText("");
            }
        }
    }
