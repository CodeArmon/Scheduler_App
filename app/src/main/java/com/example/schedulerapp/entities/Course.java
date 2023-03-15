package com.example.schedulerapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private int termID;
    private String courseName;
    private String startDate;
    private String endDate;
    private String status;
    private String courseInstruct;
    private String coursePhone;
    private String email;
    private String note;

    public Course(int courseID, int termID, String courseName, String startDate, String endDate, String status, String courseInstruct, String coursePhone, String email, String note) {
        this.courseID = courseID;
        this.termID = termID;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status=status;
        this.courseInstruct = courseInstruct;
        this.coursePhone = coursePhone;
        this.email = email;
        this.note=note;
    }

    public Course() {
    }

    public void setCoursePhone(String coursePhone) {
        this.coursePhone = coursePhone;
    }

    public String getCoursePhone() {
        return coursePhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", termID=" + termID +
                ", courseName='" + courseName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", teacherName='" + courseInstruct + '\'' +
                ", phoneNumber='" + coursePhone + '\'' +
                ", email='" + email + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCourseInstruct() {
        return courseInstruct;
    }

    public void setCourseInstruct(String courseInstruct) {
        this.courseInstruct = courseInstruct;
    }

    public String getPhoneNumber() {
        return coursePhone;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.coursePhone = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
