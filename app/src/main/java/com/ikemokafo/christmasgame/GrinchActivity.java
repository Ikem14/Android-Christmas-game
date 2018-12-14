package com.ikemokafo.christmasgame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Random;

public class GrinchActivity extends AppCompatActivity {

    ImageView exitImage;
    private enum TURN {CHRISTMAS, GRINCH}
    private TURN turn;
    private int exitCounter = 0; //tracks when to close the app
    private int numBlocks = 0; //tracks number of blocks used
    //keeps track of blocks that are not empty
    private boolean grinch[] = new boolean[10];// = {false, false, false, false, false, false, false, false, false};
    int grinchPlayer; //int value for grinch
    Random rand = new Random();
    boolean won = false; //determines if game is over or not before playing the grinch
    Transition transition;

    private ImageView[] blocks = new ImageView[9];
    private TextView display;
    private ImageView restart;

    Animation blinkImage;
    ToggleButton musicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setUp();
        initialize();
        MainActivity.jingleBellsMP.start();
    }

    @Override
    protected void onResume(){
        super.onResume();
        musicButton = (ToggleButton) findViewById(R.id.toggleButton);
        musicButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MainActivity.jingleBellsMP.start();
                }else{
                    MainActivity.jingleBellsMP.pause();
                }
            }
        });
    }

    //sets up the game
    private void setUp() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    //initialize values
    private void initialize() {

        //music
        musicButton = (ToggleButton) findViewById(R.id.toggleButton);
        musicButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MainActivity.jingleBellsMP.start();
                }else{
                    MainActivity.jingleBellsMP.pause();
                }
            }
        });

        //animation
        blinkImage = AnimationUtils.loadAnimation(this, R.anim.blink);

        exitImage = (ImageView) findViewById(R.id.exitImage);
        exitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exitCounter == 1) {
                    finish();
                } else {
                    exitCounter++;
                    Toast.makeText(getApplicationContext(), "PRESS AGAIN TO EXIT", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exitImage = (ImageView) findViewById(R.id.exitImage);
        exitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exitCounter == 1) {
                    finish();
                    //System.exit(0);
                } else {
                    exitCounter++;
                    Toast.makeText(getApplicationContext(), "PRESS AGAIN TO EXIT", Toast.LENGTH_SHORT).show();
                }
            }
        });

        display = (TextView) findViewById(R.id.show_board);
        restart = (ImageView) findViewById(R.id.restartImage);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starter = getIntent();
                finish();
                //starter.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //TransitionManager.go((Scene)R.layout.activity_main, transition);
                startActivity(starter);
            }
        });


        //initialize the imageviews
        for (int block = 0; block < 9; block++) {
            int resId = getResources().getIdentifier("block" + (block + 1), "id", getPackageName());
            blocks[block] = (ImageView) findViewById(resId);
            final int finalBlock = block;
                blocks[block].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        turn = TURN.CHRISTMAS;
                        changeTurn(finalBlock); // when you click on block, it changes turn and checks for win
                    }
                });
        }

        //initialize grinch array to false
        for(int i = 0; i < 10; i++){
            grinch[i] = false;
        }

    }

    //change turn function
    private void changeTurn(int block) {
        //auto play for the grinch
        if(turn == TURN.CHRISTMAS){
            blocks[block].setImageResource(R.mipmap.tree);
            blocks[block].setId(GrinchGame.CHRISTMAS);
            turn = TURN.GRINCH;
            display.setText(R.string.grinchTurn);
            grinch[block] = true; //set spot to true
            blocks[block].setEnabled(false);

            numBlocks++;
            if (GrinchGame.isCompleted(block + 1, blocks)) {
                display.setText(GrinchGame.winner);
                displayLine(GrinchGame.set);
                disableBoard();
                won = true;
            }else if (numBlocks == 9){
                display.setText(R.string.drawText);
            }
            //play the grinch
            if (won == false && numBlocks < 9) {
                grinchPlay(); //to generate random block for grinch
                blocks[grinchPlayer].setImageResource(R.mipmap.grinch);
                blocks[grinchPlayer].setId(GrinchGame.GRINCH);
                turn = TURN.CHRISTMAS;
                display.setText(R.string.christmasTurn);
                grinch[grinchPlayer] = true; //set spot to true
                blocks[grinchPlayer].setEnabled(false);

                numBlocks++;
                if (GrinchGame.isCompleted(grinchPlayer + 1, blocks)) {
                    display.setText(GrinchGame.winner);
                    displayLine(GrinchGame.set);
                    disableBoard();
                } else if (numBlocks == 9) {
                    display.setText(R.string.drawText);
                }
            }
        }
        /*if (turn == TURN.GRINCH) {
            grinchPlay(); //to generate random block for grinch
            blocks[grinchPlayer].setImageResource(R.mipmap.grinch);
            blocks[grinchPlayer].setId(GrinchGame.GRINCH);
            turn = TURN.CHRISTMAS;
            display.setText(R.string.christmasTurn);
            grinch[grinchPlayer] = true; //set spot to true
            blocks[grinchPlayer].setEnabled(false);

            numBlocks++;
            if (GrinchGame.isCompleted(grinchPlayer + 1, blocks)) {
                display.setText(GrinchGame.winner);
                displayLine(GrinchGame.set);
                disableBoard();
            }else if (numBlocks == 9){
                display.setText(R.string.drawText);
            }
        } else {
            blocks[block].setImageResource(R.mipmap.tree);
            blocks[block].setId(GrinchGame.CHRISTMAS);
            turn = TURN.GRINCH;
            display.setText(R.string.grinchTurn);
            grinch[block] = true; //set spot to true
            blocks[block].setEnabled(false);

            numBlocks++;
            if (GrinchGame.isCompleted(block + 1, blocks)) {
                display.setText(GrinchGame.winner);
                displayLine(GrinchGame.set);
                disableBoard();
            }else if (numBlocks == 9){
                display.setText(R.string.drawText);
            }
        }*/

    }

    //generates random spot to place grinch
    private void grinchPlay(){
        grinchPlayer = rand.nextInt(9);
        while(grinch[grinchPlayer]) {
            grinchPlayer = rand.nextInt(9);
        }
    }

    //display line function
    private void displayLine(int setLine) {
        View view;
        switch (setLine) {
            case 1:
                view = findViewById(R.id.topHorizontalLine);
                break;
            case 2:
                view = findViewById(R.id.centerHorizontalLine);
                break;
            case 3:
                view = findViewById(R.id.bottomHorizontalLine);
                break;
            case 4:
                view = findViewById(R.id.leftVerticalLine);
                break;
            case 5:
                view = findViewById(R.id.centerVerticalLine);
                break;
            case 6:
                view = findViewById(R.id.rightVerticalLine);
                break;
            case 7:
                view = findViewById(R.id.leftRightDiagonal);
                break;
            case 8:
                view = findViewById(R.id.rightLeftDiagonal);
                break;
            default://which will never happen
                view = findViewById(R.id.topHorizontalLine);
        }
        view.setVisibility(View.VISIBLE);
        view.startAnimation(blinkImage);
    }

    //disable lines after line appears
    private void disableBoard() {
        for (int i = 0; i < 9; i++)
            blocks[i].setEnabled(false);
    }
}
