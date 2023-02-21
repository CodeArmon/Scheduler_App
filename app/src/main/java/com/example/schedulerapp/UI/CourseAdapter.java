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
import com.example.schedulerapp.entities.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private final TextView courseItemView2;
        private final TextView courseItemView3;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView=itemView.findViewById(R.id.textViewcoursename);
            courseItemView2=itemView.findViewById(R.id.textViewcoursestartdate);
            courseItemView3=itemView.findViewById(R.id.textViewcourseenddate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context,CourseDetails.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("start date", current.getStartDate());
                    intent.putExtra("end date", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=mInflater.inflate(R.layout.course_list_item,viewGroup,false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder courseViewHolder, int position) {
        if(mCourses!=null){
            Course current =mCourses.get(position);
            int courseid = current.getCourseID();
            String name=current.getCourseName();
            String startdate= current.getStartDate();
            String enddate = current.getEndDate();
            courseViewHolder.courseItemView.setText(name);
            courseViewHolder.courseItemView2.setText(startdate);
            courseViewHolder.courseItemView3.setText(enddate);
        }
        else{
            courseViewHolder.courseItemView.setText("No Course Name");
        }

    }
    public void setCourses(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourses!= null) {
            return mCourses.size();
        }
        else return 0;
    }
}
