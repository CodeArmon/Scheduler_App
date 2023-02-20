package com.example.schedulerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schedulerapp.Database.Repository;
import com.example.schedulerapp.R;
import com.example.schedulerapp.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Repository repo= new Repository((getApplication()));
        List<Term> terms= repo.getAllTerms();
        TermAdapter adapter =new TermAdapter(getApplicationContext());
        adapter.setTerms(terms);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });
    }
}