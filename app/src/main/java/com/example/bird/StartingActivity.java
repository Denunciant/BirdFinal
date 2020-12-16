package com.example.bird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartingActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    MediaPlayer backgroundSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        Button btnPlay = findViewById(R.id.btnPlay);
       // btnPlay.setOnClickListener(new onClickListener);
        //commit test
        mediaPlayer = MediaPlayer.create(this, R.raw.welcome);
        backgroundSong = MediaPlayer.create(this, R.raw.bgsound);
        //backgroundSong.start();
        mediaPlayer.start();


    }
    public void playGame(View view) {
       // backgroundSong.stop();
        String name = findViewById(R.id.editTextTextPersonName).toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();

    }
    public void aboutInfo(View view) {
        // backgroundSong.stop();
        Intent intent = new Intent(this, info.class);
        startActivity(intent);
        finish();

    }
    public void toScore(View view) {
        // backgroundSong.stop();
        Intent intent = new Intent(this, Score.class);
        startActivity(intent);
        //finish();

    }

}