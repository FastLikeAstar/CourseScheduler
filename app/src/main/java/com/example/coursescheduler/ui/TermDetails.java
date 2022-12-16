package com.example.coursescheduler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.coursescheduler.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

import database.Repository;
import entities.Course;
import entities.Term;

public class TermDetails extends AppCompatActivity {

    EditText editNamePrompt;
    EditText startDatePrompt;
    EditText endDatePrompt;

    int id;
    String name;
    String startDate;
    String endDate;

    Term term;
    Repository repository;

    DatePickerDialog startDatePicker;
    DatePickerDialog endDatePicker;

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

                Long trigger = startDate.getTime();
                Intent notification = new Intent(TermDetails.this, MyReceiver.class);
                notification.putExtra("key", "Your term, "+ name + ", starts today!");
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                // Time Stamp 22:38 left - 1:03:00
            }
        });


        startDatePrompt.setInputType(InputType.TYPE_NULL);
        startDatePrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                startDatePicker = new DatePickerDialog(TermDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int monthOfYear, int dayOfMonth) {
                                startDatePrompt.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                startDatePicker.show();
            }
        });

        endDatePrompt.setInputType(InputType.TYPE_NULL);
        endDatePrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                endDatePicker = new DatePickerDialog(TermDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int monthOfYear, int dayOfMonth) {
                                startDatePrompt.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                endDatePicker.show();
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

    public void addNewCourse(View view){
        Intent navToNewActivity = new Intent(TermDetails.this, CourseDetails.class );
        startActivity(navToNewActivity);
    }
}