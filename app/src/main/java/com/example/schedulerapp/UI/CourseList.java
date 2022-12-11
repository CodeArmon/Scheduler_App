package com.example.schedulerapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schedulerapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CourseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseList.this, CourseDetails.class);
                startActivity(intent);
            }
        });
    }
}