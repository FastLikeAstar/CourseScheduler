package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Term;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);
    @Update
    void update(Term term);

    // Standard delete was causing a crash. ROOM's DAO_impl would try to call
    // information from the object is just deleted... (causing null reference).
    @Query("DELETE FROM terms WHERE termId = :termId")
    void deleteTerm(int termId);

    @Query("SELECT * FROM terms ORDER BY startDate ASC")
    List<Term> getAllTerms();

}
