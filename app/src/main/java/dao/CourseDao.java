package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Course;


@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);
    @Update
    void update(Course course);

    // Standard delete was causing a crash. ROOM's DAO_impl would try to call
    // information from the object is just deleted... (causing null reference).
    @Query("DELETE FROM courses WHERE courseId = :courseId")
    void deleteCourse(int courseId);

    @Query("SELECT * FROM courses WHERE termId= :termId ORDER BY courseId ASC")
    List<Course> getAllAssociatedCourses(int termId);
}
