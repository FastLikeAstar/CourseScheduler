package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName="assignments")
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private int assignmentId;
    private int courseId;
    private String assignmentName;
    private Date dueDate;

    public Assignment(int assignmentId, int courseId, String assignmentName, Date dueDate) {
        this.assignmentId = assignmentId;
        this.courseId = courseId;
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
    }

    public Assignment(){

    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
