package com.gogoteam.wintecpathways;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.gogoteam.wintecpathways.database.DBHandler;
import com.gogoteam.wintecpathways.database.Module;

import java.util.List;

public class AddModule extends AppCompatActivity {

    DBHandler dbHandler;
    Module module;
    Bundle moduleInfo;
    String moduleID;
    private List<Module> moduleList;
    TextInputEditText moduleCode;
    TextInputEditText nameTxt;
    TextInputEditText levelTxt;
    TextInputEditText creditTxt;
    TextInputEditText semesterTxt;
    TextInputEditText yearTxt;
    TextInputEditText preReqTxt1;
    TextInputEditText preReqTxt2;
    TextInputEditText coReqTxt;
    TextInputEditText descriTxt;
    CheckBox manCheBox;
    CheckBox softCheBox;
    CheckBox dbCheBox;
    CheckBox netCheBox;
    CheckBox webCheBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button saveBtn = findViewById(R.id.saveBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);
        moduleCode = findViewById(R.id.moduleCodeTxt);
        nameTxt = findViewById(R.id.nameTxt);
        levelTxt = findViewById(R.id.levelTxt);
        creditTxt = findViewById(R.id.creditTxt);
        semesterTxt = findViewById(R.id.semesterTxt);
        yearTxt = findViewById(R.id.yearTxt);
        preReqTxt1 = findViewById(R.id.preReqTxt1);
        preReqTxt2 = findViewById(R.id.preReqTxt2);
        coReqTxt = findViewById(R.id.coReqTxt);
        descriTxt = findViewById(R.id.descriTxt);
        manCheBox =  findViewById(R.id.manCheBox);
        softCheBox =  findViewById(R.id.softCheBox);
        dbCheBox =  findViewById(R.id.dbCheBox);
        netCheBox =  findViewById(R.id.netCheBox);
        webCheBox =  findViewById(R.id.webCheBox);

        module = new Module();
        dbHandler = new DBHandler(this, null, null, 1);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveModule(v);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelModule(v);
            }
        });

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
        String[] pathway = {"Network Engineering", "Software Engineering", "Database Architecture", "Multimedia and Web Development"};
        module.setMID(moduleID);

        Log.i("chris", "showModuleInfo  " + moduleID);
        // get module deatils from DB by sending module code
        moduleList = dbHandler.searchModule(module);

        moduleCode.setText(moduleList.get(0).getMID());
        nameTxt.setText(moduleList.get(0).getMName());
        levelTxt.setText(moduleList.get(0).getLevel());
        creditTxt.setText(moduleList.get(0).getCredits());
        semesterTxt.setText(moduleList.get(0).getSemester());
        yearTxt.setText(moduleList.get(0).getYear());
        preReqTxt1.setText(moduleList.get(0).getPreMID_1());
        preReqTxt2.setText(moduleList.get(0).getPreMID_2());
        descriTxt.setText(moduleList.get(0).getDescription());
        coReqTxt.setText("None");

        if(moduleList.get(0).getClassification().equals("Mandatory"))
            manCheBox.setChecked(true);
        else
        {
            if(moduleList.get(0).getPathway_1().equals(pathway[0]) | moduleList.get(0).getPathway_2().equals(pathway[0]) |
               moduleList.get(0).getPathway_3().equals(pathway[0]))
            {
                netCheBox.setChecked(true);
            }
            if(moduleList.get(0).getPathway_1().equals(pathway[1]) | moduleList.get(0).getPathway_2().equals(pathway[1]) |
                    moduleList.get(0).getPathway_3().equals(pathway[1]))
            {
                softCheBox.setChecked(true);
            }
            if(moduleList.get(0).getPathway_1().equals(pathway[2]) | moduleList.get(0).getPathway_2().equals(pathway[2]) |
                    moduleList.get(0).getPathway_3().equals(pathway[2]))
            {
                dbCheBox.setChecked(true);
            }
            if(moduleList.get(0).getPathway_1().equals(pathway[3]) | moduleList.get(0).getPathway_2().equals(pathway[3]) |
                    moduleList.get(0).getPathway_3().equals(pathway[3]))
            {
                webCheBox.setChecked(true);
            }

        }


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
    public void saveModule(View v)
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
        String[] pathway = {"","",""};

        module.setPreMID_1("");
        module.setPreMID_2("");
        module.setPreMID_3("");


        if(manCheBox.isChecked()) {
            module.setClassification("Mandatory");
        }
        else
        {
            int index =0;
            module.setClassification("Optional");

            if(softCheBox.isChecked()) {
                pathway[index] = "Software Engineer";
                index++;
            }
            if(dbCheBox.isChecked()) {
                pathway[index] = "Database Archtecture";
                index++;
            }
            if(netCheBox.isChecked()) {
                pathway[index] = "Network Engineer";
                index++;
            }
            if(webCheBox.isChecked()) {
                pathway[index] = "Multimedia and Web Development";
            }
            module.setPathway_1(pathway[0]);
            module.setPathway_2(pathway[1]);
            module.setPathway_3(pathway[2]);
        }

        module.setMID(moduleCode.getText().toString());
        module.setMName(nameTxt.getText().toString());
        module.setLevel(levelTxt.getText().toString());
        module.setCredits(creditTxt.getText().toString());
        module.setSemester(semesterTxt.getText().toString());
        module.setYear(yearTxt.getText().toString());
        module.setPreMID_1(preReqTxt1.getText().toString());
        module.setPreMID_2(preReqTxt2.getText().toString());
        module.setDescription(descriTxt.getText().toString());


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
        else {
            Toast.makeText(this, "Module saved!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // when cancel button is clicked
    public void cancelModule(View v)
    {
        // go back to Module View (List)
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
        }
        return false;
    }

}
