package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dao.AssignmentDao;
import dao.CourseDao;
import dao.TermDao;
import entities.Assignment;
import entities.Course;
import entities.Term;

@Database(entities = {Term.class, Course.class, Assignment.class}, version=1, exportSchema = false)
public abstract class SchedulerDatabaseBuilder extends RoomDatabase {
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssignmentDao assignmentDao();

    private static volatile SchedulerDatabaseBuilder INSTANCE;

    static SchedulerDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (SchedulerDatabaseBuilder.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchedulerDatabaseBuilder.class, "SchedulerDatabase.db").fallbackToDestructiveMigration().build();
                }
            }

        }
        return INSTANCE;
    }
}
