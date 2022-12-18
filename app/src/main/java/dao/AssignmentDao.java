package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Assignment;
import entities.Course;

@Dao
public interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assignment assignment);
    @Update
    void update(Assignment assignment);

    // Standard delete was causing a crash. ROOM's DAO_impl would try to call
    // information from the object is just deleted... (causing null reference).
    @Query("DELETE FROM assignments WHERE assignmentId = :assignmentId")
    void deleteAssignment(int assignmentId);

    @Query("SELECT * FROM assignments WHERE courseId= :courseId ORDER BY courseId ASC")
    List<Assignment> getAllAssociatedAssignments(int courseId);
}
