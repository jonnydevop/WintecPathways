package com.gogoteam.wintecpathways.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogoteam.wintecpathways.R;
import com.gogoteam.wintecpathways.database.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.ViewHolder>{
    private List<Student> studentList;
    private Context mContext;
   // ImageView imageView;

    public StudentRecyclerViewAdapter(Context context, List<Student> studentList) {
        Log.i("Nancy", "RecyclerViewAdapter  ");
        mContext = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item ,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        // i is the number of the list that the recyclerView need to show
        viewHolder.studentCode.setText(studentList.get(i).getSID());
        viewHolder.studentName.setText(studentList.get(i).getSName());
        //viewHolder.imageView.setImageAlpha(studentList.get(i).getImage());

        viewHolder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), moduleList.get(i).getMID(), Toast.LENGTH_SHORT).show();
                String moduleID = studentList.get(i).getSID();

                // go to module modify activity and pass the module code to the activity
                //Intent intent = new Intent (view.getContext(), ModuleModify.class);
                //intent.putExtra("moduleInfo", moduleID);
                //mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //Log.i("Nancy", "getItemCount  size =  " + moduleList.size());
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView studentCode;
        TextView studentName;
        //ImageView imageView;

        RelativeLayout parentView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentCode = itemView.findViewById(R.id.moduleText);
            studentName = itemView.findViewById(R.id.moduleDesc);
            //imageView = itemView.findViewById(R.id.imageView);
            parentView = itemView.findViewById(R.id.parentView);
        }
    }

    public void updateList(List<Student> newList)
    {
        studentList = new ArrayList<>();
        studentList.addAll(newList);
        notifyDataSetChanged();
    }
}
