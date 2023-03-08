package com.example.schedulerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.schedulerapp.Database.Repository;
import com.example.schedulerapp.R;
import com.example.schedulerapp.entities.Assessment;
import com.example.schedulerapp.entities.Course;
import com.example.schedulerapp.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    EditText editCourseName;
    EditText editStartDate;
    EditText editEndDate;
    EditText editStatus;
    EditText editCourseInstruct;
    EditText editInstructPhone;
    EditText editInstructEmail;
    EditText editNote;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String name;
    //String startDate;
   // String endDate;
    String status;
    String courseInstruct;
    String instructPhone;
    String instructEmail;
    String note;
    int id;
    int termId;
    Term term;
    Course course;
    Repository repository;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editCourseName=findViewById(R.id.coursename);
        editStartDate=findViewById(R.id.startdate);
        editEndDate=findViewById(R.id.enddate);
        editStatus=findViewById(R.id.coursestatus);
        editCourseInstruct=findViewById(R.id.courseinstructor);
        editInstructPhone=findViewById(R.id.instructphone);
        editInstructEmail=findViewById(R.id.instructemail);
        editNote=findViewById(R.id.coursenote);
        name = getIntent().getStringExtra("name");
        //startDate = getIntent().getStringExtra("start date");
        //endDate = getIntent().getStringExtra("end date");
        status = getIntent().getStringExtra("status");
        termId = getIntent().getIntExtra("termID", -1);
        note = getIntent().getStringExtra("note");
        editCourseName.setText(name);
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editStartDate.setText(sdf.format(new Date()));
        editEndDate.setText(sdf.format(new Date()));
        editStatus.setText(status);
        editCourseInstruct.setText(courseInstruct);
        editInstructEmail.setText(instructEmail);
        editInstructPhone.setText(instructPhone);
        editNote.setText(note);
        id=getIntent().getIntExtra("id", -1);
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment a: repository.getAllAssessments()){
            if (a.getAssessmentID()== id)
                filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessment(filteredAssessments);
        assessmentAdapter.setAssessment(repository.getAllAssessments());
        Button button=findViewById(R.id.courseSaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id==-1){
                   course = new Course(0, termId, editCourseName.getText().toString(),editStartDate.toString(), editEndDate.toString(), editCourseInstruct.getText().toString(), editInstructPhone.getText().toString(), editInstructPhone.getText().toString(),editNote.getText().toString() );
                    repository.insert(course);

                }
                else{
                    course=new Course(course.getCourseID(),termId, editCourseName.getText().toString(),editStartDate.toString(), editEndDate.toString(), editCourseInstruct.getText().toString(), editInstructPhone.getText().toString(), editInstructPhone.getText().toString(),editNote.getText().toString());
                    repository.update(course);

                }


            }
        });
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = "02/10/22";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        editEndDate.setOnClickListener(new View.OnClickListener() {
            //change everything to myCalendarEnd
            @Override
            public void onClick(View v) {
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "02/10/22";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }
        };
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                //change to MyCalendarEnd

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                startActivity(intent);
            }
        });
    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }
}