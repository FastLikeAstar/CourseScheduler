package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName="courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int termId;
    private String courseName;
    private String status;
    private String startDate;
    private String endDate;
    private String courseInstructorName;
    private String courseInstructorNumber;
    private String courseInstructorEmail;
    private String notes;

    public Course(int courseId, int termId, String courseName, String status, String startDate, String endDate, String courseInstructorName, String courseInstructorNumber, String courseInstructorEmail, String notes) {
        this.courseId = courseId;
        this.termId = termId;
        this.courseName = courseName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorNumber = courseInstructorNumber;
        this.courseInstructorEmail = courseInstructorEmail;
        this.notes = notes;
    }

    public Course(){

    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCourseInstructorName() {
        return courseInstructorName;
    }

    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public String getCourseInstructorNumber() {
        return courseInstructorNumber;
    }

    public void setCourseInstructorNumber(String courseInstructorNumber) {
        this.courseInstructorNumber = courseInstructorNumber;
    }

    public String getCourseInstructorEmail() {
        return courseInstructorEmail;
    }

    public void setCourseInstructorEmail(String courseInstructorEmail) {
        this.courseInstructorEmail = courseInstructorEmail;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
