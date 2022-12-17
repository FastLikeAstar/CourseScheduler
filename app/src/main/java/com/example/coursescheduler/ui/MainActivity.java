package com.example.coursescheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursescheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import database.Repository;

public class MainActivity extends AppCompatActivity {

    public static int alertNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Repository repository = new Repository(getApplication());
    }

    public void BeginClicked(View view){
        Intent navToNewActivity = new Intent(MainActivity.this, TermList.class );
        startActivity(navToNewActivity);
    }
    public static Date stringToDate (String dateAsString) throws ParseException {
        Date dateAsDate;
        String dateFormat ="MM/dd/yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.US);

        dateAsDate = dateFormatter.parse(dateAsString);

        return dateAsDate;
    }
    public static String dateToString(Date dateAsDate) throws ParseException {
        String stringAsDate;
        String dateFormat ="MM/dd/yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.US);

        stringAsDate = dateFormatter.format(dateAsDate);

        return stringAsDate;
    }
}