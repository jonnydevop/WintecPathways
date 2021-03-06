package com.gogoteam.wintecpathways.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogoteam.wintecpathways.database.Module;
import com.gogoteam.wintecpathways.ModuleModify;
import com.gogoteam.wintecpathways.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<Module> moduleList;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Module> moduleList) {
        Log.i("chris", "RecyclerViewAdapter  ");
        mContext = context;
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item,
                viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        // i is the number of the list that the recyclerView need to show
        viewHolder.moduleCode.setText(moduleList.get(i).getMID());
        viewHolder.moduleDesc.setText(moduleList.get(i).getMName());

        viewHolder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), moduleList.get(i).getMID(), Toast.LENGTH_SHORT).show();
                String moduleID = moduleList.get(i).getMID();

                // go to module modify activity and pass the module code to the activity
                Intent intent = new Intent (view.getContext(), ModuleModify.class);
                intent.putExtra("moduleInfo", moduleID);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //Log.i("chris", "getItemCount  size =  " + moduleList.size());
        return moduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView moduleCode;
        TextView moduleDesc;

        RelativeLayout parentView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleCode = itemView.findViewById(R.id.moduleText);
            moduleDesc = itemView.findViewById(R.id.moduleDesc);
            parentView = itemView.findViewById(R.id.parentView);
        }
    }

    public void updateList(List<Module> newList)
    {
        moduleList = new ArrayList<>();
        moduleList.addAll(newList);
        notifyDataSetChanged();
    }

}
