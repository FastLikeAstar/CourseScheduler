package com.example.coursescheduler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.coursescheduler.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
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

    DatePickerDialog startDatePicker;
    DatePickerDialog endDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        repository = new Repository(getApplication());



//        List<Assignment> assignments = repository.getAllAssignments(this.id);
//        RecyclerView recyclerView = findViewById(R.id.assessment_recyclerview);
//        final AssignmentListAdapter adapter = new AssignmentListAdapter(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter.setAssignments(assignments);
        List<Assignment> allAssignments = repository.getAllAssignments(this.id);
        RecyclerView recyclerView = findViewById(R.id.assessment_recyclerview);
        final AssignmentListAdapter listAdapter = new AssignmentListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter.setAssignments(allAssignments);



        editNamePrompt = findViewById(R.id.CourseNamePrompt);
        startDatePrompt = findViewById(R.id.CourseStartPrompt);
        endDatePrompt = findViewById(R.id.CourseEndPrompt);
        statusPrompt = findViewById(R.id.CourseStatusPrompt);
        instructorNamePrompt = findViewById(R.id.InstructorPrompt);
        instructorNumberPrompt = findViewById(R.id.NumberPrompt);
        instructorEmailPrompt = findViewById(R.id.EmailPrompt);
        courseNotesPrompt = findViewById(R.id.CourseNotesField);

        id = getIntent().getIntExtra("id", -1);
        termId = getIntent().getIntExtra("term id", -1);
        termName = getIntent().getStringExtra("term name");
        termStart = getIntent().getStringExtra("term start");
        termEnd = getIntent().getStringExtra("term end");
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");
        instructorName = getIntent().getStringExtra("instructor");
        instructorNumber = getIntent().getStringExtra("number");
        instructorEmail = getIntent().getStringExtra("email");
        courseNotes = getIntent().getStringExtra("notes");

        // Status options (in progress, completed, dropped, plan to take)
        List<String> statusOptions = new ArrayList<String>();
        statusOptions.add("In Progress");
        statusOptions.add("Completed");
        statusOptions.add("Dropped");
        statusOptions.add("Plan to Take");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusOptions);




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

        startDatePrompt.setInputType(InputType.TYPE_NULL);
        startDatePrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                startDatePicker = new DatePickerDialog(CourseDetails.this,
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
                endDatePicker = new DatePickerDialog(CourseDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int monthOfYear, int dayOfMonth) {
                                endDatePrompt.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                endDatePicker.show();
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
        super.onResume();
        List<Assignment> allAssignments = repository.getAllAssignments(this.id);
        RecyclerView recyclerView = findViewById(R.id.assessment_recyclerview);
        final AssignmentListAdapter listAdapter = new AssignmentListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter.setAssignments(allAssignments);
    }

    public void addNewAssessment(View view){
        Intent navToNewActivity = new Intent(CourseDetails.this, AssignmentDetails.class );
        navToNewActivity.putExtra("course id",id);
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
            Snackbar.make(view, "Course deleted. Re-save if this was a mistake.", Snackbar.LENGTH_LONG).show();

        }

        if (numberOfAssignments == 0){
            repository.delete(course, id);
        } else{
            Snackbar.make(view, "Unable to delete this course. Delete the assessments first.", Snackbar.LENGTH_LONG).show();

        }
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_assignment_list, menu);
        return true;
    }


//    public boolean opOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case android.R.id.home:
//                Intent changeActivity = new Intent(this, TermDetails.class);
//                changeActivity.putExtra("id",termId);
//                changeActivity.putExtra("name", termName);
//                changeActivity.putExtra("start date", termStart);
//                changeActivity.putExtra("end date", termEnd);
//                startActivity(changeActivity);
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public void onBackPressed()
//    {
//        Intent changeActivity = new Intent(this, TermDetails.class);
//        changeActivity.putExtra("id",termId);
//        changeActivity.putExtra("name", termName);
//        changeActivity.putExtra("start date", termStart);
//        changeActivity.putExtra("end date", termEnd);
//        startActivity(changeActivity);
//    }



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