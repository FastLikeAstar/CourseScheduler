package database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.AssignmentDao;
import dao.CourseDao;
import dao.TermDao;
import entities.Assignment;
import entities.Course;
import entities.Term;

public class Repository {
    private TermDao mTermDao;
    private CourseDao mCourseDao;
    private AssignmentDao mAssignmentDao;

    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assignment> mAllAssignments;

    private static int NUMBER_OF_THREADS= 4 ;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        SchedulerDatabaseBuilder database = SchedulerDatabaseBuilder.getDatabase(application);
        mTermDao = database.termDao();
        mAssignmentDao = database.assignmentDao();
        mCourseDao = database.courseDao();
    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms = mTermDao.getAllTerms();
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return mAllTerms;
    }

    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDao.insert(term);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDao.update(term);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(Term term, int id){
        databaseExecutor.execute(()->{
            mTermDao.deleteTerm(id);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    public List<Course> getAllCourse(int termId){
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDao.getAllAssociatedCourses(termId);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return mAllCourses;
    }
    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDao.insert(course);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDao.update(course);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(Course course, int id){
        databaseExecutor.execute(()->{
            mCourseDao.deleteCourse(id);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    public List<Assignment> getAllAssignments(int courseId){
        databaseExecutor.execute(()->{
            mAllAssignments = mAssignmentDao.getAllAssociatedAssignments(courseId);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return mAllAssignments;
    }
    public void insert(Assignment assignment){
        databaseExecutor.execute(()->{
            mAssignmentDao.insert(assignment);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(Assignment assignment){
        databaseExecutor.execute(()->{
            mAssignmentDao.update(assignment);
        });
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(Assignment assignment, int id){

        databaseExecutor.execute(()->{
            mAssignmentDao.deleteAssignment(id);
        });
        try{
            Thread.sleep(400);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
