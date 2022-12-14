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
    @Delete
    void delete(Assignment assignment);
    @Query("SELECT * FROM assignments WHERE courseId= :courseId ORDER BY courseId ASC")
    List<Course> getAllAssociatedAssignments(int courseId);
}
