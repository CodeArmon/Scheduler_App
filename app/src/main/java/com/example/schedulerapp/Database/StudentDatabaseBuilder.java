package com.example.schedulerapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.schedulerapp.dao.AssessmentDAO;
import com.example.schedulerapp.dao.CourseDAO;
import com.example.schedulerapp.dao.TermDAO;
import com.example.schedulerapp.entities.Assessment;
import com.example.schedulerapp.entities.Course;
import com.example.schedulerapp.entities.Term;

@Database(entities = {Assessment.class, Course.class, Term.class}, version = 6, exportSchema = false)
public abstract class StudentDatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();

    public abstract CourseDAO courseDAO();

    public abstract TermDAO termDAO();

    private static volatile StudentDatabaseBuilder INSTANCE;

    static StudentDatabaseBuilder getDatabase(final Context context) {
       if(INSTANCE==null){
           synchronized (StudentDatabaseBuilder.class){
               if(INSTANCE==null){
                   INSTANCE= Room.databaseBuilder(context.getApplicationContext(),StudentDatabaseBuilder.class,"StudentSchedulerDatabase.db")
                           .fallbackToDestructiveMigration()
                           .build();
               }
           }

       }
       return INSTANCE;

        }
    }
