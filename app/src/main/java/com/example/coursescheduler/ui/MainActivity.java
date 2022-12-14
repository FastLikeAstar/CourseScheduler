package com.example.coursescheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursescheduler.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BeginClicked(View view){
        Intent navToNewActivity = new Intent(MainActivity.this, TermList.class );
        startActivity(navToNewActivity);
    }
}