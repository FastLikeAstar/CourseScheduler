package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName="assignments")
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private int assignmentId;
    private int courseId;
    private String type;
    private String assignmentName;
    private String startDate;
    private String endDate;

    public Assignment(int assignmentId, int courseId, String type, String assignmentName, String startDate, String endDate) {
        this.assignmentId = assignmentId;
        this.courseId = courseId;
        this.type = type;
        this.assignmentName = assignmentName;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
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


}
