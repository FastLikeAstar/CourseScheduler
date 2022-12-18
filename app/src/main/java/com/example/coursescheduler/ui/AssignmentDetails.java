package com.example.coursescheduler.ui;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import database.Repository;
import entities.Assignment;
import entities.Assignment;
import entities.Term;

public class AssignmentDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText editNamePrompt;
    EditText startDatePrompt;
    EditText endDatePrompt;
    Spinner typePrompt;
    EditText instructorNamePrompt;
    EditText instructorNumberPrompt;
    EditText instructorEmailPrompt;
    EditText courseNotesPrompt;

    int id;
    int courseId;
    int termId;
    String type;
    String name;
    String startDate;
    String endDate;

    String termName;
    String termStart;
    String termEnd;

    String courseName;
    String courseStart;
    String courseEnd;
    String status;
    String instructorName;
    String instructorNumber;
    String instructorEmail;
    String courseNotes;

    Assignment assignment;
    Repository repository;

    DatePickerDialog startDatePicker;
    DatePickerDialog endDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);




        editNamePrompt = findViewById(R.id.AssignmentNamePrompt);
        startDatePrompt = findViewById(R.id.AssignmentStartPrompt);
        endDatePrompt = findViewById(R.id.AssignmentEndPrompt);
        typePrompt = findViewById(R.id.AssignmentTypePrompt);


        id = getIntent().getIntExtra("id", -1);
//        termId = getIntent().getIntExtra("term id", -1);
        courseId = getIntent().getIntExtra("course id", -1);
//        termName = getIntent().getStringExtra("term name");
//        termStart = getIntent().getStringExtra("term start");
//        termEnd = getIntent().getStringExtra("term end");
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");
//        instructorName = getIntent().getStringExtra("instructor");
//        instructorNumber = getIntent().getStringExtra("phone number");
//        instructorEmail = getIntent().getStringExtra("email");
//        courseNotes = getIntent().getStringExtra("notes");


        // Type options (Performance or Objective)
        List<String> statusOptions = new ArrayList<String>();
        statusOptions.add("Performance");
        statusOptions.add("Objective");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusOptions);


        repository = new Repository(getApplication());

        ImageButton imageButton = findViewById(R.id.saveAssignmentButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (id == -1){
                     assignment = new Assignment(0, courseId, type,editNamePrompt.getText().toString(), startDatePrompt.getText().toString(),
                             endDatePrompt.getText().toString());
                    repository.insert(assignment);
                    Snackbar.make(view, "Assessment is Saved", Snackbar.LENGTH_LONG).show();
                }
                else{
                    assignment = new Assignment(id, courseId, type,editNamePrompt.getText().toString(), startDatePrompt.getText().toString(),
                            endDatePrompt.getText().toString());
                    repository.update(assignment);
                    Snackbar.make(view, "Assessment is Updated", Snackbar.LENGTH_LONG).show();
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
                startDatePicker = new DatePickerDialog(AssignmentDetails.this,
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
                endDatePicker = new DatePickerDialog(AssignmentDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int monthOfYear, int dayOfMonth) {
                                endDatePrompt.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                endDatePicker.show();
            }
        });

        typePrompt.setOnItemSelectedListener(this);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typePrompt.setAdapter(spinnerAdapter);

        editNamePrompt.setText(name);
        startDatePrompt.setText(startDate);
        endDatePrompt.setText(endDate);
    }


    public void deleteAssignment(View view){
        repository.delete(assignment, id);
        Snackbar.make(view, "Assessment deleted. Re-save if this was a mistake.", Snackbar.LENGTH_LONG).show();
    }

    public void createAssignmentAlarm(View view){
        try {
            Date dateAsDate = MainActivity.stringToDate(startDate);
            Long trigger = dateAsDate.getTime();

            Intent notification = new Intent(AssignmentDetails.this, MyReceiver.class);
            notification.putExtra("key", "Your assessment, "+ name + ", starts today!");
            PendingIntent sender = PendingIntent.getBroadcast(AssignmentDetails.this, MainActivity.alertNumber++,notification, FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            dateAsDate = MainActivity.stringToDate(endDate);
            trigger = dateAsDate.getTime();

            Intent notification2 = new Intent(AssignmentDetails.this, MyReceiver.class);
            notification2.putExtra("key", "Your assessment, "+ name + ", ends today!");
            sender = PendingIntent.getBroadcast(AssignmentDetails.this, MainActivity.alertNumber++,notification2, FLAG_IMMUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            Snackbar.make(view, "Reminder set for " + name +".", Snackbar.LENGTH_LONG).show();

        } catch (ParseException e) {
            Snackbar.make(view, "Reminder failed to create", Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String typeSelected = adapterView.getItemAtPosition(i).toString();
        type = typeSelected;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        status = "";
    };
}