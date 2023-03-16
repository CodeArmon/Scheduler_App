package com.example.schedulerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class CourseDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editCourseName;
    EditText editStartDate;
    EditText editEndDate;
    Spinner editStatus;
    EditText editCourseInstruct;
    EditText editInstructPhone;
    EditText editInstructEmail;
    EditText editNote;
    Spinner courseSpinner;
    DatePickerDialog.OnDateSetListener startDatePick;
    DatePickerDialog.OnDateSetListener endDatePick;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String name;
    String startDate;
    String endDate;
    String status;
    String courseInstruct;
    String instructPhone;
    String instructEmail;
    String note;
    int id;
    int termId;
    int courseID;
    int numCourses;
    Term term;
    Course course;
    Course currentCourse;
    Repository repository;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editCourseName=findViewById(R.id.coursename);
        editStartDate=findViewById(R.id.startdate);
        editEndDate=findViewById(R.id.enddate);
        editStatus=findViewById(R.id.statusSpinner);
        editCourseInstruct=findViewById(R.id.courseinstructor);
        editInstructPhone=findViewById(R.id.instructphone);
        editInstructEmail=findViewById(R.id.instructemail);
        editNote=findViewById(R.id.coursenote);
        courseSpinner=findViewById(R.id.statusSpinner);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");
        status = getIntent().getStringExtra("status");
        courseInstruct = getIntent().getStringExtra("course instructor");
        instructPhone = getIntent().getStringExtra("course phone");
        courseID = getIntent().getIntExtra("courseID", -1);
        id = getIntent().getIntExtra("id", -1);
        termId = getIntent().getIntExtra("termID", -1);
        instructEmail = getIntent().getStringExtra("email");
        note = getIntent().getStringExtra("note");
        editCourseName.setText(name);
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        //Spinner spinner = (Spinner) findViewById(R.id.statusSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.statusChoices, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        courseSpinner.setAdapter(adapter);
        //status=courseSpinner.getSelectedItem().toString();
        int position = adapter.getPosition(status);
        courseSpinner.setSelection(position);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status=courseSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //editStatus.setText(status);
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
        for(Assessment a: repository.getAllAssociatedAssessments(courseID)){
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
                   course = new Course(0, termId, editCourseName.getText().toString(),editStartDate.getText().toString(), editEndDate.getText().toString(),status, editCourseInstruct.getText().toString(), editInstructPhone.getText().toString(), editInstructEmail.getText().toString(),editNote.getText().toString() );
                    repository.insert(course);

                }
                else{
                    System.out.println(".......................course ID="+ courseID);
                    course=new Course(id,termId, editCourseName.getText().toString(),editStartDate.getText().toString(), editEndDate.getText().toString(),status, editCourseInstruct.getText().toString(), editInstructPhone.getText().toString(),editInstructEmail.getText().toString(),editNote.getText().toString());
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
                new DatePickerDialog(CourseDetails.this, startDatePick, myCalendarStart
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
                new DatePickerDialog(CourseDetails.this, endDatePick, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDatePick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }
        };
        endDatePick = new DatePickerDialog.OnDateSetListener() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifystart:
                String startDateFromScreen = editStartDate.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = myDate.getTime();
                Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
                intent.putExtra("key", startDateFromScreen + " should trigger start");
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++HomeScreen.alertNum, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender);
                return true;
            case R.id.notifyend:
                String endDateFromScreen = editEndDate.getText().toString();
                String endMyFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat endsdf = new SimpleDateFormat(endMyFormat, Locale.US);
                Date endDate = null;
                try {
                    endDate = endsdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = endDate.getTime();
                Intent endIntent = new Intent(CourseDetails.this, MyReceiver.class);
                endIntent.putExtra("key", endDateFromScreen + " should trigger end");
                PendingIntent endSender = PendingIntent.getBroadcast(CourseDetails.this, ++HomeScreen.alertNum, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
                return true;
            case R.id.deletecourse:
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseID() == id) currentCourse = course;
                }

                numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseID() == id) ++numCourses;
                }

                if (numCourses == 0) {
                    repository.delete(currentCourse);
                    Toast.makeText(CourseDetails.this, currentCourse.getCourseName() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CourseDetails.this, "Can't delete Course", Toast.LENGTH_LONG).show();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
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

        //Toast.makeText(CourseDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}