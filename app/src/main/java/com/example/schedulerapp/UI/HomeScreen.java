package com.example.schedulerapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.schedulerapp.Database.Repository;
import com.example.schedulerapp.R;
import com.example.schedulerapp.entities.Assessment;
import com.example.schedulerapp.entities.Course;
import com.example.schedulerapp.entities.Term;


public class HomeScreen extends AppCompatActivity {

    public static int alertNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Button button=findViewById(R.id.button);
       /* Term term= new Term(0,"Spring", "12-14-2002","12-15-2022");
        Repository repository = new Repository(getApplication());
        repository.insert(term); */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeScreen.this, TermList.class);
                startActivity(intent);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.addSampleData:
                Term term= new Term(0,"Spring", "12-14-2002","12-15-2022");
                Term term1= new Term(0,"Spring Next", "12-14-2023","12-15-2024");
                Repository repository = new Repository(getApplication());
                repository.insert(term);
                repository.insert(term1);
                Assessment assessment= new Assessment(0,"Performance","12-20-2022","Performance",1);
                repository.insert(assessment);
                Course course1=new Course(0, 2, "Mobile","03-09-2023","03-10-2023","Professor Xavier","404-555-0912","profx@school.edu","optional");
                repository.insert(course1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}