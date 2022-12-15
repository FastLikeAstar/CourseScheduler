package com.example.coursescheduler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.coursescheduler.R;
import com.google.android.material.snackbar.Snackbar;

import database.Repository;
import entities.Term;

public class TermDetails extends AppCompatActivity {

    EditText editNamePrompt;
    EditText startDatePrompt;
    EditText endDatePrompt;
    EditText statusPrompt;

    int id;
    String name;
    String startDate;
    String endDate;
    String status;

    Term term;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editNamePrompt = findViewById(R.id.TermNamePrompt);
        startDatePrompt = findViewById(R.id.TermStartPrompt);
        endDatePrompt = findViewById(R.id.TermEndPrompt);
        statusPrompt = findViewById(R.id.TermStatusPrompt);

        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");
        status = getIntent().getStringExtra("status");

        repository = new Repository(getApplication());

        ImageButton imageButton = findViewById(R.id.saveTermButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (id == -1){
                    term = new Term(0, editNamePrompt.getText().toString(), startDatePrompt.getText().toString(),
                            endDatePrompt.getText().toString(), statusPrompt.getText().toString());
                    repository.insert(term);
                    Snackbar.make(view, "Term is Saved", Snackbar.LENGTH_LONG).show();
                }
                else{
                    term = new Term(id, editNamePrompt.getText().toString(), startDatePrompt.getText().toString(),
                            endDatePrompt.getText().toString(), statusPrompt.getText().toString());
                    repository.update(term);
                    Snackbar.make(view, "Term is Updated", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        editNamePrompt.setText(name);
        startDatePrompt.setText(startDate);
        endDatePrompt.setText(endDate);
        statusPrompt.setText(status);
    }
}