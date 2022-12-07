package com.learnoset.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button [] buttons = new Button[9];
    private Button playAgainButton;
    private ImageView playerStatus;
    private ImageView line;
    private int rountCount;
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0, 1, 2, R.drawable.mark6},
            {3, 4, 5, R.drawable.mark8},
            {6, 7, 8, R.drawable.mark7},
            {0, 3, 6, R.drawable.mark3},
            {1, 4, 7, R.drawable.mark4},
            {2, 5, 8, R.drawable.mark5},
            {0, 4, 8, R.drawable.mark1},
            {2, 4, 6, R.drawable.mark2}
    };

    private Drawable x;
    private Drawable o;
    private Drawable owin;
    private Drawable xwin;
    private Drawable nowin;

    private View.OnClickListener resetClick;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerStatus = (ImageView) findViewById(R.id.playerStatus);
        playerStatus.setBackgroundResource(R.drawable.xplay);

        line = (ImageView) findViewById(R.id.line_1);

        playAgainButton = (Button) findViewById(R.id.playAgain);
        playAgainButton.setVisibility(View.GONE);
        playAgainButton.setEnabled(false);
        playAgainButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });

        for(int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        activePlayer = true;
        rountCount =0;
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        String buttonID = view.getResources().getResourceEntryName(view.getId());

        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length()));
        if(gameState[gameStatePointer] != 2 || checkWinner()){
            return;
        }
        if (activePlayer) {
            ((Button) view).setBackgroundResource(R.drawable.x);
            playerStatus.setBackgroundResource(R.drawable.oplay);
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) view).setBackgroundResource(R.drawable.o);
            playerStatus.setBackgroundResource(R.drawable.xplay);
            gameState[gameStatePointer] = 1;
        }
        rountCount++;
        if(checkWinner()){
            if(activePlayer){
                playerStatus.setBackgroundResource(R.drawable.xwin);
            }
            else{
                playerStatus.setBackgroundResource(R.drawable.owin);
            }

        }
        else if(rountCount == 9){
            playerStatus.setBackgroundResource(R.drawable.nowin);
            playAgainButton.setVisibility(View.VISIBLE);
            playAgainButton.setEnabled(true);
        }
        else {
            activePlayer = !activePlayer;
        }
        }

        public boolean checkWinner(){
            boolean winnerResult = false;

            for(int[] winningPosition : winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    line.setBackgroundResource(winningPosition[3]);
                    winnerResult = true;
                    playAgainButton.setVisibility(View.VISIBLE);
                    playAgainButton.setEnabled(true);
                }
            }
            return winnerResult;
        }

        public void playAgain() {
            playerStatus.setBackgroundResource(R.drawable.xplay);
            line.setBackgroundResource(0);
            activePlayer = true;
            rountCount = 0;
            playAgainButton.setEnabled(false);
            playAgainButton.setVisibility(View.GONE);

        for (int i=0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setBackgroundResource(0);
        }
        }
}