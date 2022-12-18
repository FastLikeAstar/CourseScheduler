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

import entities.Assignment;

public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.AssignmentViewHolder> {

    private List<Assignment> mAssignments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssignmentListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public AssignmentListAdapter.AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assignment_list_item, parent, false);

        return new AssignmentViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AssignmentListAdapter.AssignmentViewHolder holder, int position) {
        if (mAssignments != null){
            Assignment current = mAssignments.get(position);
            String name = current.getAssignmentName();
            holder.assignmentItemView.setText(name);
        }
        else {
            holder.assignmentItemView.setText("No Assessment Name.");
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mAssignments.size();
    }

    public void setAssignments(List<Assignment> assignments){
        mAssignments = assignments;
        notifyDataSetChanged();
    }

    class AssignmentViewHolder extends RecyclerView.ViewHolder{

        private final TextView assignmentItemView;
        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentItemView = itemView.findViewById(R.id.listItemText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assignment current = mAssignments.get(position);
                    Intent changeActivity = new Intent(context, AssignmentDetails.class);
                    changeActivity.putExtra("id",current.getAssignmentId());
                    changeActivity.putExtra("course id",current.getCourseId());
                    changeActivity.putExtra("type", current.getType());
                    changeActivity.putExtra("name", current.getAssignmentName());
                    changeActivity.putExtra("start date", current.getStartDate());
                    changeActivity.putExtra("end date", current.getEndDate());
                    context.startActivity(changeActivity);


                }
            });
        }
    }
}
