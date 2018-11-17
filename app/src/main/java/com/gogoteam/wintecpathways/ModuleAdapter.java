package com.gogoteam.wintecpathways;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {


    private Context mCtx;
    private List<StudentProductsActivity> moduleList;




    public ModuleAdapter(Context mCtx, List<StudentProductsActivity> moduleList) {
        this.mCtx = mCtx;
        this.moduleList = moduleList;


    }

    @Override
    public ModuleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ModuleViewHolder holder, int position) {

        StudentProductsActivity module = moduleList.get(position);
        //Log.i("ModuleAdapter", "Pathway_2()  " + product.getPathway_2().length() + "   " + "Pathway_3()  " + product.getPathway_3().length());

        if(module != null && ( module.getPathway_1() !=null  && module.getPathway_1().length() ==0)) {
            holder.moduleNameView.setText(module.getMName());
            holder.moduleIDView.setText(module.getMID());
            holder.moduleButton.setText(String.valueOf(module.getButton()));
            holder.moduleSemesterView.setText(module.getSemester());
            //holder.imageView.setImageAlpha(product.getImage());
        }else
        {
            holder.moduleNameView.setText(module.getMName());
            holder.moduleIDView.setText(module.getMID());
            holder.moduleButton.setText(String.valueOf(module.getButton()));
            holder.moduleSemesterView.setText(module.getSemester());
            holder.moduleIDView.setBackgroundResource(R.color.colorAccent);
        }
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    class ModuleViewHolder extends RecyclerView.ViewHolder {

        TextView moduleNameView, moduleIDView, moduleSemesterView, moduleButton;
        TextView textView;
        //LinearLayout stdPathwayModID;

        public ModuleViewHolder(View itemView) {
            super(itemView);
            moduleNameView = itemView.findViewById(R.id.moduleNameView);
            moduleIDView = itemView.findViewById(R.id.moduleIDView);
            moduleButton = itemView.findViewById(R.id.moduleButton);
            moduleSemesterView = itemView.findViewById(R.id.moduleSemesterView);
            //stdPathwayModID = itemView.findViewById(R.id.stdPathwayModID);
        }
    }
}