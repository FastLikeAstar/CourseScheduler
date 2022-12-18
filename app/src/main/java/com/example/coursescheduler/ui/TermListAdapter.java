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

import entities.Term;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public TermListAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);

        return new TermViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull TermListAdapter.TermViewHolder holder, int position) {
        if (mTerms != null){
            Term current = mTerms.get(position);
            String name = current.getTermName();
            holder.termItemView.setText(name);
        }
        else {
            holder.termItemView.setText("No Term name.");
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Term> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

    class TermViewHolder extends RecyclerView.ViewHolder{

        private final TextView termItemView;
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.listItemText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent changeActivity = new Intent(context, TermDetails.class);
                    changeActivity.putExtra("id",current.getTermId());
                    changeActivity.putExtra("name", current.getTermName());
                    changeActivity.putExtra("start date", current.getStartDate());
                    changeActivity.putExtra("end date", current.getEndDate());
                    context.startActivity(changeActivity);
                }
            });
        }
    }
}
