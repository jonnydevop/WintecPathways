package com.gogoteam.wintecpathways;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class AddModule extends AppCompatActivity {

    DBHandler dbHandler;
    Module module;
    Bundle moduleInfo;
    String moduleID;
    private List<Module> moduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Modules");

        module = new Module();
        dbHandler = new DBHandler(this, null, null, 1);

        // check if it's modify module
        moduleInfo = getIntent().getExtras();
        if (moduleInfo == null){
            return;
        }

        // show module details
        moduleID = moduleInfo.getString("moduleInfo");
        showModuleInfo();
    }

    public void showModuleInfo()
    {
        // get the module code from module view ( module list)
        module.setMID(moduleID);

        Log.i("chris", "showModuleInfo  " + moduleID);
        // get module deatils from DB by sending module code
        moduleList = dbHandler.searchModule(module);

        // log for testing DB, these details should be shown on layout, GOGO Juan!!
        Log.i("chris", "showModuleInfo  " + moduleList.get(0).getMID() + " " +
                moduleList.get(0).getClassification() + " " +
                moduleList.get(0).getSemester() + " " +
                moduleList.get(0).getMName() + " " +
                moduleList.get(0).getPathway_1()+ " " +
                moduleList.get(0).getPreMID_1() + " " +
                moduleList.get(0).getYear());
    }

    // when save button is clicked
    public void saveBtn(View v)
    {
        // confirmation for saving the module details
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to save?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       addEditModule();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick (DialogInterface dialog, int id) {
                        // delete dialog is user click No
                        dialog.cancel();
                    }
                });
        AlertDialog disc = builder.create();
        disc.show();
        disc.getWindow().setBackgroundDrawableResource(R.color.orangecard);

        TextView messageText = (TextView)disc.findViewById( android.R.id.message );
        messageText.setGravity( Gravity.CENTER_HORIZONTAL );

    }

    public void addEditModule(){
        boolean saveSuccess = true;

        saveSuccess = dbHandler.addModule(module);

        if(saveSuccess == false)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The module is already exist, please use edit function instead of add function.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            startActivity(new Intent(AddModule.this, StaffModule.class));
                        }
                    });
            AlertDialog disc = builder.create();
            disc.show();
        }
    }

    // when cancel button is clicked
    public void cancelClick(View v)
    {
        // go back to Module View (List)
        finish();

    }

}
