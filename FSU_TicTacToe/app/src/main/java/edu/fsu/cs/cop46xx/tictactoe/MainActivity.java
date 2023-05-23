package edu.fsu.cs.cop46xx.tictactoe;

/*********************************************************************************
 * COP 46xx
 * Wendy Slattery
 * 6/30/20
 *
 * Project 1: Tic Tac Toe
 * Based on Tutorial by Coding in Flow: https://www.youtube.com/watch?v=apDL78MFR3o
 **********************************************************************************/


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    // create array of cells to handle onclick listeners for board
    private Button[][] cells = new Button[3][3];

    // keep up with player turn toggling from player X on and off
    private boolean playerX_turn = true;

    // keep up with how many cells are occupied by turns taken
    private int turnCount;

    private TextView tv_playerX;
    private TextView tv_playerO;

    private int scoreX;
    private int scoreO;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_playerX = findViewById(R.id.tv_playerX);
        tv_playerO = findViewById(R.id.tv_playerO);

        // dynamic alternative to associate findViewByID for each grid cell button
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String buttonID = "button_" + i + j; // concatenate the string to collect each cell
                int resourceID = getResources().getIdentifier(buttonID,
                        "id", getPackageName());
                cells[i][j] = findViewById(resourceID); //get references for all our buttons
                cells[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // if the button is not empty and has already been designated with a token, then return
        if (! ((Button) v).getText().toString().equals("")) {
            return;
        }

        // if it's player X's turn, then set onclicklistener to mark button as X
        if(playerX_turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        // update turn is over, next player's turn
        turnCount++;

        if(isAWinner()){
            if(playerX_turn) {
                playerXWins();
            } else {
                playerOWins();
            }
        } else if (turnCount == 9){
            callDraw();
        }
        // if there is no winner and no draw, then switch player turn
        else {
            playerX_turn = !playerX_turn;
        }
    }

    private boolean isAWinner() {
        String[][] board = new String[3][3];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = cells[i][j].getText().toString();
            }
        }

        // check if any row has three matches that are not empty
        for(int i = 0; i < 3; i++){
            if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])
                && !board[i][0].equals("")){
                return true;
            }
        }

        // check if any column has three matches that are not empty
        for(int i = 0; i < 3; i++){
            if (board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])
                    && !board[0][i].equals("")){
                return true;
            }
        }

        // check diagonal top left to bottom right for non empty matches
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])
                && !board[0][0].equals("")){
            return true;
        }

        // check diagonal top right to bottom left for non empty matches
        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])
                && !board[0][2].equals("")){
            return true;
        }

        return false;
    }

    private void playerXWins(){
        scoreX++;
        Toast toast = Toast.makeText(this, "X Wins!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        updateScoreText();
        resetBoard();
    }

    private void playerOWins() {
        scoreO++;
        Toast toast = Toast.makeText(this, "O Wins!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        updateScoreText();
        resetBoard();
    }

    private void callDraw() {
        Toast toast = Toast.makeText(this, "Game was a Draw!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        resetBoard();
    }

    @SuppressLint("SetTextI18n")
    private void updateScoreText() {
        tv_playerX.setText("X: " + scoreX);
        tv_playerO.setText("O: " + scoreO);
    }

    private void resetBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                cells[i][j].setText("");
            }
        }

        turnCount = 0;
        playerX_turn = true;
    }

    private void resetGame(){
        scoreX = 0;
        scoreO = 0;
        updateScoreText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("turnCount", turnCount);
        outState.putInt("playerX_score", scoreX);
        outState.putInt("playerO_score", scoreO);
        outState.putBoolean("playerX_turn", playerX_turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        turnCount = savedInstanceState.getInt("turnCount");
        scoreX = savedInstanceState.getInt("playerX_score");
        scoreO = savedInstanceState.getInt("playerO_score");
        playerX_turn = savedInstanceState.getBoolean("playerX_turn");
    }
}