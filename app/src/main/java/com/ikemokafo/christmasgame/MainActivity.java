package com.ikemokafo.christmasgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity { //main menu of app

    private Button launchFriendBtn; //button to play against your friend
    private Button launchGrinchBtn; //button to play against the grinch
    public static MediaPlayer jingleBellsMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchFriendBtn = (Button) findViewById(R.id.playAgainstFriendBtn);
        launchGrinchBtn = (Button) findViewById(R.id.playAgainstGrinchBtn);

        launchFriendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchFriendActivity();
            }

        });

        launchGrinchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchGrinchActivity();
            }
        });

        jingleBellsMP = MediaPlayer.create(this,R.raw.jingle_bells_song);
        jingleBellsMP.start();
        jingleBellsMP.setLooping(true);
    }

    private void launchFriendActivity(){
        Intent intent = new Intent(this, FriendActivity.class);
        //jingleBellsMP.stop();
        startActivity(intent);
    }

    private void launchGrinchActivity(){
        Intent intent = new Intent(this, GrinchActivity.class);
        //jingleBellsMP.stop();
        startActivity(intent);
    }
}
