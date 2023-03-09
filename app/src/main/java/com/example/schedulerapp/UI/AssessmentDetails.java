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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    DatePickerDialog.OnDateSetListener endDate;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarEnd = Calendar.getInstance();
    final Calendar myCalendarStart = Calendar.getInstance();
    String name;
    //String endDate;
    String type;
    int id;
    Assessment assessment;
    Repository repository;
    RadioGroup rg;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        editName=findViewById(R.id.assessmentname);
        editEndDate=findViewById(R.id.enddate);
        editStartDate=findViewById(R.id.startdate);
        name = getIntent().getStringExtra("name");
       // endDate = getIntent().getStringExtra("end date");
        editName.setText(name);
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editEndDate.setText(sdf.format(new Date()));
        editStartDate.setText(sdf.format(new Date()));
        id=getIntent().getIntExtra("id", -1);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

// get selected radioButton from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

// find the radioButton by returned id
        radioButton = (RadioButton) findViewById(selectedId);
// radioButton text
         type = radioButton.getText().toString();
        //rg = (RadioGroup) findViewById(R.id.radioGroup);
        //type = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment a: repository.getAllAssessments()){
            if (a.getCourseID()== id)
                filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessment(filteredAssessments);
        // AssessmentAdapter.setAssessment(filteredAssessments);
        //AssessmentAdapter.setAssessment(repository.getAllAssessments());
        Button button=findViewById(R.id.assessmentSaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id==-1){
                    assessment= new Assessment(0,editName.getText().toString(),editEndDate.toString(), type, id);
                    repository.insert(assessment);

                }
                else{
                    assessment=new Assessment(id, editName.getText().toString(),editEndDate.toString(), type, id);
                    repository.update(assessment);

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
                if (info.equals("")) info = "02/10/22";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {
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
                if (info.equals("")) info = "02/10/22";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
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

        FloatingActionButton fab = findViewById(R.id.floatingActionButton4);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentDetails.this, CourseDetails.class);
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
        RecyclerView recyclerView = findViewById(R.id.assessmentrecyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment a: repository.getAllAssessments()){
            if (a.getCourseID()== id)
                filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessment(filteredAssessments);

        //Toast.makeText(AssessmentDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.notifystart:
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
                intent.putExtra("key", startDateFromScreen + " should trigger start");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++HomeScreen.alertNum, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.notifyend:
                String endDateFromScreen = editEndDate.getText().toString();
                String endMyFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat endsdf = new SimpleDateFormat(endMyFormat, Locale.US);
                Date endDate = null;
                try {
                    myDate = endsdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = endDate.getTime();
                Intent endIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
                endIntent.putExtra("key1", endDateFromScreen + " should trigger end");
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetails.this, ++HomeScreen.alertNum, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

