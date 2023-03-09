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
import com.example.schedulerapp.entities.Assessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{

    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private final TextView assessmentItemView2;
        private final TextView assessmentItemView3;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView=itemView.findViewById(R.id.textViewassessmentname);
            assessmentItemView2=itemView.findViewById(R.id.textViewassessmentstartdate);
            assessmentItemView3=itemView.findViewById(R.id.textViewassessmentenddate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    final Assessment current = mAssessment.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("name", current.getAssessmentName());
                    intent.putExtra("end date", current.getEndDate());
                    intent.putExtra("assessment id", current.getAssessmentID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssessment;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=mInflater.inflate(R.layout.assessment_list_item,viewGroup,false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder assessmentViewHolder, int position) {
        if(mAssessment!=null){
            Assessment current =mAssessment.get(position);
            String name=current.getAssessmentName();
            String enddate = current.getEndDate();
            int assessmentID= current.getAssessmentID();
            assessmentViewHolder.assessmentItemView.setText(name);
            assessmentViewHolder.assessmentItemView3.setText(enddate);
           // assessmentViewHolder.assessmentItemView2.setText(assessmentID);
        }
        else{
            assessmentViewHolder.assessmentItemView.setText("No Assessment Name");
        }
    }


    public void setAssessment(List<Assessment> assessments){
        mAssessment=assessments;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if(mAssessment!= null) {
            return mAssessment.size();
        }
        else return 0;
    }
}
