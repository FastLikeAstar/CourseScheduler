package com.example.coursescheduler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.coursescheduler.R;

import java.util.List;

import database.Repository;
import entities.Term;

public class TermList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        RecyclerView recyclerView = findViewById(R.id.terms_recyclerview);

        final TermListAdapter adapter = new TermListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();

        adapter.setTerms(allTerms);

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }


    public void addNewTerm(View view){
        Intent navToNewActivity = new Intent(TermList.this, TermDetails.class );
        startActivity(navToNewActivity);
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Term> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.terms_recyclerview);
        final TermListAdapter listAdapter = new TermListAdapter(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter.setTerms(allTerms);
    }
}