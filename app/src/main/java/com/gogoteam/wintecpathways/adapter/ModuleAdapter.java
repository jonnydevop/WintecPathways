package com.gogoteam.wintecpathways.adapter;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TabWidget;
import android.widget.TextView;

import com.gogoteam.wintecpathways.ItemClickListener;
import com.gogoteam.wintecpathways.R;
import com.gogoteam.wintecpathways.StudentModuleActivity;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {


    private Context context;
    private List<StudentModuleActivity> moduleList;

    public ModuleAdapter(Context context, List<StudentModuleActivity> moduleList) {
        this.context = context;
        this.moduleList = moduleList;
    }

    @Override
    public ModuleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_module_row, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ModuleViewHolder holder, int position) {

        final StudentModuleActivity module = moduleList.get(position);
        //context = holder.itemView.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        boolean pref = sharedPreferences.getBoolean("pref" + position,false);
        //Log.i("ModuleAdapter", "Pathway_2()  " + product.getPathway_2().length() + "   " + "Pathway_3()  " + product.getPathway_3().length());

        if(module != null && ( module.getPathway_1() !=null  && module.getPathway_1().length() ==0)) {
            holder.moduleNameView.setText(module.getMName());
            holder.moduleIDView.setText(module.getMID());
            /*if(pref) {
                holder.moduleButton.setChecked(true);

            }else{
                holder.moduleButton.setChecked(false);
            }
           // holder.moduleButton.setText("Not Completed");*/
            holder.moduleSemesterView.setText(module.getSemester());

        }else{
            holder.moduleNameView.setText(module.getMName());
            holder.moduleIDView.setText(module.getMID());
           /* if(pref) {
                holder.moduleButton.setChecked(true);
            }else{
                holder.moduleButton.setChecked(false);
            }*/
            //holder.moduleButton.setText("Not Completed");
            holder.moduleSemesterView.setText(module.getSemester());
            holder.moduleIDView.setBackgroundResource(R.color.colorAccent);
        }

        /*holder.moduleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.moduleButton.isChecked()) {
                    holder.moduleButton.setText("Completed");

                }
                if(!holder.moduleButton.isChecked()){
                    holder.moduleButton.setText("");

                }
            }
        });*/

        // open the dialog window to display the module information
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean click) {

                final Dialog dialog  = new Dialog(context);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modules_dialog);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //Set to display module information in dialogue window
                TextView closeDialog = (TextView) dialog.findViewById(R.id.closeDialog);
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                TextView moduleID = (TextView) dialog.findViewById(R.id.moduleID);
                moduleID.setText(module.getMID());

                TextView moduleName = (TextView) dialog.findViewById(R.id.moduleNameID);
                moduleName.setText(module.getMName());

                TextView creditsID = (TextView) dialog.findViewById(R.id.creditsID);
                creditsID.setText(module.getCredits());

                TextView levelID = (TextView) dialog.findViewById(R.id.levelID);
                levelID.setText("Level " + module.getLevel());

                String preReq = "";
                TextView preReqTxt = (TextView) dialog.findViewById(R.id.preReqTxt);
                if (module.getPreMID_1()!=null && !module.getPreMID_1().equals("")) {
                    preReq = preReq + module.getPreMID_1();
                }
                if (module.getPreMID_2()!=null && !module.getPreMID_2().equals("")) {
                    if (!preReq.equals("")) {
                        preReq = preReq + "/";
                    }
                    preReq = preReq + module.getPreMID_2();
                }
                if (module.getPreMID_3()!=null && !module.getPreMID_3().equals("")) {
                    if (!preReq.equals("")) {
                        preReq = preReq + "/";
                    }
                    preReq = preReq + module.getPreMID_3();
                }
                if (preReq.equals("")) {
                    preReq = "None";
                }
                preReqTxt.setText(preReq);

                String coreReq = "None";
                TextView coReqTxt = (TextView) dialog.findViewById(R.id.coReqTxt);
                if (module.getMID().equals("INFO604")) {
                    coreReq = "INFO601";
                }
                coReqTxt.setText(coreReq);

                String pathWay = "Mandatory";
                TextView streamTxt = (TextView) dialog.findViewById(R.id.streamTxt);
                if (module.getClassification().equals("Optional")) {
                    pathWay = "";
                }
                if (module.getPathway_1()!=null && !module.getPathway_1().equals("")) {
                    pathWay = pathWay + module.getPathway_1();
                }
                if (module.getPathway_2()!=null && !module.getPathway_2().equals("")) {
                    if (!pathWay.equals("")) {
                        pathWay = pathWay + "/";
                    }
                    pathWay = pathWay + module.getPathway_2();
                }
                if (module.getPathway_3()!=null && !module.getPathway_3().equals("")) {
                    if (!pathWay.equals("")) {
                        pathWay = pathWay + "/";
                    }
                    pathWay = pathWay + module.getPathway_3();
                }
                streamTxt.setText(pathWay);

               /* TextView classificationID = (TextView) dialog.findViewById(R.id.classificationID);
                semesterID.setText(module.getClassification());*/

                TextView descriptionId = (TextView) dialog.findViewById(R.id.descriptionId);
                descriptionId.setText(module.getDescription());

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    class ModuleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView moduleNameView, moduleIDView, moduleSemesterView;
        SwitchCompat moduleButton;


        private ItemClickListener itemClickListener;

        public ModuleViewHolder(final View itemView) {
            super(itemView);
            moduleNameView = itemView.findViewById(R.id.moduleNameView);
            moduleIDView = itemView.findViewById(R.id.moduleIDView);
            moduleButton = itemView.findViewById(R.id.moduleButton);
            moduleSemesterView = itemView.findViewById(R.id.moduleSemesterView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            /*moduleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (moduleButton.isChecked()) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("pref" +getAdapterPosition(), true);
                        editor.commit();

                    }
                    if(!moduleButton.isChecked()){
                        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("pref" + getAdapterPosition(), false);
                        editor.commit();

                    }
                }
            });*/

        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }


        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {

            itemClickListener.onClick(v, getAdapterPosition(),true);
            return true;
        }
    }
}