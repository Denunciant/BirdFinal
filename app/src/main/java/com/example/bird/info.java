package com.example.bird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }
    public void toMenu(View view) {
        // backgroundSong.stop();
        Intent intent = new Intent(this, StartingActivity.class);
        startActivity(intent);
        finish();

    }
}