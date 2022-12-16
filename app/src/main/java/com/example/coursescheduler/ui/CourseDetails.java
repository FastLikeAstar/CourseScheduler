package com.example.coursescheduler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.coursescheduler.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import database.Repository;
import entities.Course;
import entities.Term;

public class CourseDetails extends AppCompatActivity {

    EditText editNamePrompt;
    EditText startDatePrompt;
    EditText endDatePrompt;

    int id;
    String name;
    String startDate;
    String endDate;

    Term term;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        RecyclerView recyclerView = findViewById(R.id.course_recyclerview);

        final CourseListAdapter adapter = new CourseListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editNamePrompt = findViewById(R.id.TermNamePrompt);
        startDatePrompt = findViewById(R.id.TermStartPrompt);
        endDatePrompt = findViewById(R.id.TermEndPrompt);

        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");

        repository = new Repository(getApplication());

        ImageButton imageButton = findViewById(R.id.saveTermButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (id == -1){
                    term = new Term(0, editNamePrompt.getText().toString(), startDatePrompt.getText().toString(),
                            endDatePrompt.getText().toString());
                    repository.insert(term);
                    Snackbar.make(view, "Term is Saved", Snackbar.LENGTH_LONG).show();
                }
                else{
                    term = new Term(id, editNamePrompt.getText().toString(), startDatePrompt.getText().toString(),
                            endDatePrompt.getText().toString());
                    repository.update(term);
                    Snackbar.make(view, "Term is Updated", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        editNamePrompt.setText(name);
        startDatePrompt.setText(startDate);
        endDatePrompt.setText(endDate);
    }
    @Override
    protected void onResume(){
        super.onResume();
        List<Course> allCourse = repository.getAllCourse(this.id);
        RecyclerView recyclerView = findViewById(R.id.course_recyclerview);
        final CourseListAdapter listAdapter = new CourseListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter.setCourses(allCourse);
    }

    public void addNewAssessment(View view){
        Intent navToNewActivity = new Intent(CourseDetails.this, AssignmentDetails.class );
        startActivity(navToNewActivity);
    }
}