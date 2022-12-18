package com.example.coursescheduler.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.R;

import java.util.List;

import entities.Course;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public CourseListAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);

        return new CourseViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull CourseListAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null){
            Course current = mCourses.get(position);
            String name = current.getCourseName();
            holder.courseItemView.setText(name);
        }
        else {
            holder.courseItemView.setText("No Course name.");
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCourses(List<Course> Courses){
        mCourses = Courses;
        notifyDataSetChanged();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{

        private final TextView courseItemView;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.listItemText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent changeActivity = new Intent(context, CourseDetails.class);
                    changeActivity.putExtra("id",current.getCourseId());
                    changeActivity.putExtra("term id",current.getTermId());
                    changeActivity.putExtra("name", current.getCourseName());
                    changeActivity.putExtra("status", current.getStatus());
                    changeActivity.putExtra("start date", current.getStartDate());
                    changeActivity.putExtra("end date", current.getEndDate());
                    changeActivity.putExtra("instructor", current.getCourseInstructorName());
                    changeActivity.putExtra("number", current.getCourseInstructorNumber());
                    changeActivity.putExtra("email", current.getCourseInstructorEmail());
                    changeActivity.putExtra("notes", current.getNotes());
                    context.startActivity(changeActivity);


                }
            });
        }
    }
}
