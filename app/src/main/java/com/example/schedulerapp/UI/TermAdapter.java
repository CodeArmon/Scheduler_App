package com.example.schedulerapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerapp.R;
import com.example.schedulerapp.entities.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {


    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView;
        private TermViewHolder(View itemView){
            super(itemView);
            termItemView=itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context,CourseList.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("start date", current.getStartDate());
                    intent.putExtra("end date", current.getEndDate());
                }
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;
    public TermAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=mInflater.inflate(R.layout.term_list_item,viewGroup,false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder termViewHolder, int position) {
        if(mTerms!=null){
            Term current =mTerms.get(position);
            String name=current.getTermName();
            termViewHolder.termItemView.setText(name);
        }
        else{
            termViewHolder.termItemView.setText("No Term Name");
        }

    }
    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mTerms!= null) {
        return mTerms.size();
        }
        else return 0;
    }
}
