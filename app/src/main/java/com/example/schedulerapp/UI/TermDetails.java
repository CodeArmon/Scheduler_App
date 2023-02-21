package com.example.schedulerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulerapp.Database.Repository;
import com.example.schedulerapp.R;
import com.example.schedulerapp.entities.Course;
import com.example.schedulerapp.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    String name;
    String startDate;
    String endDate;
    int id;
    Term term;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editName=findViewById(R.id.termname);
        editStartDate=findViewById(R.id.startdate);
        editEndDate=findViewById(R.id.enddate);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");
        editName.setText(name);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        id=getIntent().getIntExtra("id", -1);
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.coursesrecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for(Course c: repository.getAllCourses()){
            if (c.getTermID()== id)
                filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);
        courseAdapter.setCourses(repository.getAllCourses());
        Button button=findViewById(R.id.termSaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id==-1){
                    term= new Term(0,editName.getText().toString(),startDate, endDate);
                    repository.insert(term);

                }
                else{
                    term=new Term(id, editName.getText().toString(),startDate,endDate);
                    repository.update(term);

                }


            }
        });




        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermDetails.this, CourseList.class);
                startActivity(intent);
            }
        });
    }
}