package com.example.schedulerapp.UI;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.Database.Repository;
import com.example.schedulerapp.R;
import com.example.schedulerapp.entities.Assessment;
import com.example.schedulerapp.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {
    EditText editName;
    EditText editEndDate;
    EditText editStartDate;
    EditText editType;
    DatePickerDialog.OnDateSetListener endDatePick;
    DatePickerDialog.OnDateSetListener startDatePick;
    final Calendar myCalendarEnd = Calendar.getInstance();
    final Calendar myCalendarStart = Calendar.getInstance();
    String name;
    String startDate;
    String endDate;
    String type;
    int id;
    int assessmentID;
    int courseID;
    Assessment assessment;
    Repository repository;
    RadioGroup rg;
    RadioButton radioButton;
    Spinner typeSpinner;
    RadioButton obj;
    RadioButton perf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        editName=findViewById(R.id.assessmentname);
        editEndDate=findViewById(R.id.enddate);
        editStartDate=findViewById(R.id.startdate);
        typeSpinner = findViewById(R.id.typeSpinner);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("start date");
        endDate = getIntent().getStringExtra("end date");
        courseID = getIntent().getIntExtra("id",-1 );
        id = getIntent().getIntExtra("assessment id", -1);
        type = getIntent().getStringExtra("type");
        editName.setText(name);
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editEndDate.setText(endDate);
        editStartDate.setText(startDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat(format, Locale.US);
        editEndDate.setText(sdf1.format(new Date()));
       // sdf1 = new SimpleDateFormat("dd MMM yyyy");
        editStartDate.setText(sdf1.format(new Date()));
        //Spinner spinner = (Spinner) findViewById(R.id.statusSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeChoices, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        typeSpinner.setAdapter(adapter);
        int position = adapter.getPosition(type);
        typeSpinner.setSelection(position);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=typeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment a: repository.getAllAssociatedAssessments(courseID)){
            if (a.getCourseID()== courseID)
                filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessment(filteredAssessments);
        assessmentAdapter.setAssessment(repository.getAllAssociatedAssessments(courseID));
        // AssessmentAdapter.setAssessment(filteredAssessments);
        //AssessmentAdapter.setAssessment(repository.getAllAssessments());
        Button button=findViewById(R.id.assessmentSaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(courseID==-1){
                    Toast.makeText(AssessmentDetails.this,  " User must select a course first", Toast.LENGTH_LONG).show();
                }
               else if(id==-1){
                    assessment= new Assessment(0,editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), type, courseID);
                    repository.insert(assessment);
                    startActivity(new Intent(AssessmentDetails.this, CourseDetails.class));
                    finish();

                }
                else{
                    assessment=new Assessment(id, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), type, courseID);
                    repository.update(assessment);
                    startActivity(new Intent(AssessmentDetails.this, AssessmentList.class));
                    finish();
                }


            }
        });
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "03/18/23";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, endDatePick, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDatePick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelEnd();
            }
        };
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = "03/18/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, startDatePick, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
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

        FloatingActionButton fab = findViewById(R.id.floatingActionButton4);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentDetails.this, CourseDetails.class);
                intent.putExtra("id",courseID);
                startActivity(intent);
            }
        });
    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
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
        for(Assessment a: repository.getAllAssociatedAssessments(courseID)){
            if (a.getCourseID()== courseID)
                filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessment(filteredAssessments);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(AssessmentDetails.this, HomeScreen.class));
                this.finish();
                return true;

            case R.id.startdateassess:
                String startDateFromScreen = editStartDate.getText().toString();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", startDateFromScreen + "  "+ name  + " assessment start date");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++HomeScreen.alertNum, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.enddateassess:
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
                Intent endIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
                endIntent.putExtra("key", endDateFromScreen + "  "+ name + " assessment end date");
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetails.this, ++HomeScreen.alertNum, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

