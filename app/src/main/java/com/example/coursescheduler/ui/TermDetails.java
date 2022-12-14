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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.coursescheduler.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        editNamePrompt = findViewById(R.id.TermNamePrompt);
        startDatePrompt = findViewById(R.id.TermStartPrompt);
        endDatePrompt = findViewById(R.id.TermEndPrompt);

        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");

        repository = new Repository(getApplication());

        List<Course> allCourse = repository.getAllCourse(this.id);
        RecyclerView recyclerView = findViewById(R.id.course_recyclerview);
        final CourseListAdapter listAdapter = new CourseListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter.setCourses(allCourse);

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
                                endDatePrompt.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
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

        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");

        editNamePrompt.setText(name);
        startDatePrompt.setText(startDate);
        endDatePrompt.setText(endDate);
    }

    public void addNewCourse(View view){
        Intent navToNewActivity = new Intent(TermDetails.this, CourseDetails.class );
        navToNewActivity.putExtra("term id",id);
        startActivity(navToNewActivity);
    }

    public void createTermAlarm(View view){
        try {
            Date dateAsDate = MainActivity.stringToDate(startDate);
            Long trigger = dateAsDate.getTime();

            Intent notification = new Intent(TermDetails.this, MyReceiver.class);
            notification.putExtra("key", "Your term, "+ name + ", starts today!");
            PendingIntent sender = PendingIntent.getBroadcast(TermDetails.this, MainActivity.alertNumber++,notification, FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            dateAsDate = MainActivity.stringToDate(endDate);
            trigger = dateAsDate.getTime();

            Intent notification2 = new Intent(TermDetails.this, MyReceiver.class);
            notification2.putExtra("key", "Your term, "+ name + ", ends today!");
            sender = PendingIntent.getBroadcast(TermDetails.this, MainActivity.alertNumber++,notification2, FLAG_IMMUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            Snackbar.make(view, "Reminder set for " + name +".", Snackbar.LENGTH_LONG).show();

        } catch (ParseException e) {
            Snackbar.make(view, "Reminder failed to create", Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public boolean opOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    public void deleteTerm(View view){
        int numberOfCourses = 0;

        for (Course course: repository.getAllCourse(this.id)){
            numberOfCourses++;
        }

        if (numberOfCourses == 0){
            repository.delete(term, id);
            Snackbar.make(view, "Term deleted. Re-save if this was a mistake.", Snackbar.LENGTH_LONG).show();

        } else{
            Snackbar.make(view, "Unable to delete this term. Delete the courses first.", Snackbar.LENGTH_LONG).show();
        }
    }

}