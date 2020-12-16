package com.example.bird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Score extends AppCompatActivity {

    ListView lv;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        lv = (ListView) findViewById(R.id.listViewScore);
        button = findViewById(R.id.resetBtn);

        final DBhelper dBhelper = new DBhelper(this);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList = dBhelper.getItemList();
        Toast.makeText(getApplicationContext(), "arrayList " + arrayList.size(), Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nRowDeleted = dBhelper.removeAll();
                Toast.makeText(getApplicationContext(), "Deleted records: " + nRowDeleted, Toast.LENGTH_SHORT).show();
                recreate();
            }
        });


    }


}