package com.example.coursescheduler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.coursescheduler.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import database.Repository;
import entities.Assignment;
import entities.Course;
import entities.Term;

public class CourseDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText editNamePrompt;
    EditText startDatePrompt;
    EditText endDatePrompt;
    Spinner statusPrompt;
    EditText instructorNamePrompt;
    EditText instructorNumberPrompt;
    EditText instructorEmailPrompt;
    EditText courseNotesPrompt;

    int id;
    int termId;
    String name;
    String startDate;
    String endDate;
    String status;

    String termName;
    String termStart;
    String termEnd;

    String instructorName;
    String instructorNumber;
    String instructorEmail;
    String courseNotes;

    Course course;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        RecyclerView recyclerView = findViewById(R.id.assessment_recyclerview);

        final CourseListAdapter adapter = new CourseListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        editNamePrompt = findViewById(R.id.CourseNamePrompt);
        startDatePrompt = findViewById(R.id.CourseStartPrompt);
        endDatePrompt = findViewById(R.id.CourseEndPrompt);
        statusPrompt = findViewById(R.id.CourseStatusPrompt);
        instructorNamePrompt = findViewById(R.id.InstructorPrompt);
        instructorNumberPrompt = findViewById(R.id.NumberPrompt);
        instructorEmailPrompt = findViewById(R.id.EmailPrompt);
        courseNotesPrompt = findViewById(R.id.CourseNotesField);

        id = getIntent().getIntExtra("course id", -1);
        termId = getIntent().getIntExtra("term id", -1);
        termName = getIntent().getStringExtra("term name");
        termStart = getIntent().getStringExtra("term start");
        termEnd = getIntent().getStringExtra("term end");
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");
        instructorName = getIntent().getStringExtra("instructor");
        instructorNumber = getIntent().getStringExtra("phone number");
        instructorEmail = getIntent().getStringExtra("email");
        courseNotes = getIntent().getStringExtra("notes");

        // Status options (in progress, completed, dropped, plan to take)
        List<String> statusOptions = new ArrayList<String>();
        statusOptions.add("In Progress");
        statusOptions.add("Completed");
        statusOptions.add("Dropped");
        statusOptions.add("Plan to Take");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusOptions);


        repository = new Repository(getApplication());

        ImageButton imageButton = findViewById(R.id.saveCourseButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (id == -1){
                    course = new Course(0,termId, editNamePrompt.getText().toString(), status, startDatePrompt.getText().toString(),
                            endDatePrompt.getText().toString(),instructorNamePrompt.getText().toString(), instructorNumberPrompt.getText().toString(), instructorEmailPrompt.getText().toString(),
                            courseNotesPrompt.getText().toString());
                    repository.insert(course);
                    Snackbar.make(view, "Course is Saved", Snackbar.LENGTH_LONG).show();
                }
                else{
                    course = new Course(id,termId, editNamePrompt.getText().toString(), status, startDatePrompt.getText().toString(),
                            endDatePrompt.getText().toString(),instructorNamePrompt.getText().toString(), instructorNumberPrompt.getText().toString(), instructorEmailPrompt.getText().toString(),
                            courseNotesPrompt.getText().toString());
                    repository.update(course);
                    Snackbar.make(view, "Course is Updated", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        statusPrompt.setOnItemSelectedListener(this);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusPrompt.setAdapter(spinnerAdapter);

        editNamePrompt.setText(name);
        startDatePrompt.setText(startDate);
        endDatePrompt.setText(endDate);
        instructorNamePrompt.setText(instructorName);
        instructorNumberPrompt.setText(instructorNumber);
        instructorEmailPrompt.setText(instructorEmail);
        courseNotesPrompt.setText(courseNotes);


    }


    @Override
    protected void onResume(){
//        super.onResume();
//        List<Course> allCourse = repository.getAllCourse(this.id);
//        RecyclerView recyclerView = findViewById(R.id.course_recyclerview);
//        final CourseListAdapter listAdapter = new CourseListAdapter(this);
//        recyclerView.setAdapter(listAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        listAdapter.setCourses(allCourse);
        super.onResume();
        List<Course> allCourse = repository.getAllCourse(this.id);
        RecyclerView recyclerView = findViewById(R.id.assessment_recyclerview);
        final CourseListAdapter listAdapter = new CourseListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter.setCourses(allCourse);
    }

    public void addNewAssessment(View view){
        Intent navToNewActivity = new Intent(CourseDetails.this, AssignmentDetails.class );
        startActivity(navToNewActivity);
    }

    public void shareNotes(View view){
        Intent sendNotes = new Intent();
        sendNotes.setAction(Intent.ACTION_SEND);
        sendNotes.putExtra(Intent.EXTRA_TEXT, courseNotesPrompt.getText() );
        sendNotes.putExtra(Intent.EXTRA_TITLE, "Message title");
        sendNotes.setType("text/plain");

        Intent shareNotes = Intent.createChooser(sendNotes, null);
        startActivity(shareNotes);
    }

    public void deleteCourse(View view){
        int numberOfAssignments = 0;

        for (Assignment assignment: repository.getAllAssignments(this.id)){
            numberOfAssignments++;
        }

        if (numberOfAssignments == 0){
            repository.delete(course);
        } else{
            Snackbar.make(view, "Unable to delete this course. Delete the assessments first.", Snackbar.LENGTH_LONG).show();

        }
    }

    public boolean opOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent changeActivity = new Intent(this, TermDetails.class);
                changeActivity.putExtra("id",termId);
                changeActivity.putExtra("name", termName);
                changeActivity.putExtra("start date", termStart);
                changeActivity.putExtra("end date", termEnd);
                startActivity(changeActivity);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Intent changeActivity = new Intent(this, TermDetails.class);
        changeActivity.putExtra("id",termId);
        changeActivity.putExtra("name", termName);
        changeActivity.putExtra("start date", termStart);
        changeActivity.putExtra("end date", termEnd);
        startActivity(changeActivity);
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String statusSelected = adapterView.getItemAtPosition(i).toString();
        status = statusSelected;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        status = "";
    };
}