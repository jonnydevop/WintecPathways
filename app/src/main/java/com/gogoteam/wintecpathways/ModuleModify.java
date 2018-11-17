package com.gogoteam.wintecpathways;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ModuleModify extends AppCompatActivity {

    Module module;
    DBHandler dbHandler;
    Bundle moduleInfo;
    String moduleID;
    private List<Module> moduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_modify);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("MODULES");

        module = new Module();
        dbHandler = new DBHandler(this, null, null, 1);

        moduleInfo = getIntent().getExtras();
        if (moduleInfo == null){
            return;
        }
        moduleID = moduleInfo.getString("moduleInfo");

        showModuleInfo();
    }

    public void showModuleInfo()
    {
        String pathway = "None";
        String preModID = "None";
        TextView moduleCode = findViewById(R.id.moduleCodeTxt);
        TextView nameTxt = findViewById(R.id.nameTxt);
        TextView levelTxt = findViewById(R.id.levelTxt);
        TextView creditTxt = findViewById(R.id.creditTxt);
        TextView semesterTxt = findViewById(R.id.semesterTxt);
        TextView yearTxt = findViewById(R.id.yearTxt);
        TextView pathwayTxt = findViewById(R.id.pathwayTxt);
        TextView preReqTxt = findViewById(R.id.preReqTxt);
        TextView descriTxt = findViewById(R.id.descriTxt);

        // get the module code from module view ( module list)
        module.setMID(moduleID);

        Log.i("chris", "showModuleInfo  " + moduleID);
        // get module deatils from DB by sending module code
        moduleList = dbHandler.searchModule(module);
        if(!moduleList.get(0).getPathway_1().equals(""))
        {
            pathway = moduleList.get(0).getPathway_1();
            if (!moduleList.get(0).getPathway_2().equals(""))
                pathway += ", " + moduleList.get(0).getPathway_2();
            if (!moduleList.get(0).getPathway_3().equals(""))
                pathway += ", " + moduleList.get(0).getPathway_3();
        }
        if(!moduleList.get(0).getPreMID_1().equals(""))
        {
            preModID = moduleList.get(0).getPreMID_1();
            if (!moduleList.get(0).getPreMID_2().equals(""))
                preModID += ", " + moduleList.get(0).getPreMID_2();
            if (!moduleList.get(0).getPreMID_3().equals(""))
                preModID += ", " + moduleList.get(0).getPreMID_3();
        }


        moduleCode.setText(moduleList.get(0).getMID() + "  (" + moduleList.get(0).getClassification() + ")");
        nameTxt.setText(moduleList.get(0).getMName());
        levelTxt.setText("Level: " + moduleList.get(0).getLevel());
        creditTxt.setText("Credit: " + moduleList.get(0).getCredits());
        semesterTxt.setText("Semester: " + moduleList.get(0).getSemester());
        yearTxt.setText("Year: " + moduleList.get(0).getYear());
        pathwayTxt.setText("Pathway: " + pathway);
        preReqTxt.setText("Pre-require: " + preModID);
        descriTxt.setText("Description:" + moduleList.get(0).getDescription());
        descriTxt.setMovementMethod(ScrollingMovementMethod.getInstance());

        // log for testing DB, these details should be shown on layout, GOGO Juan!!
        Log.i("chris", "showModuleInfo  " + moduleList.get(0).getMID() + " " +
                moduleList.get(0).getClassification() + " " +
                moduleList.get(0).getSemester() + " " +
                moduleList.get(0).getMName() + " " +
                moduleList.get(0).getPathway_1()+ " " +
                moduleList.get(0).getPreMID_1() + " " +
                moduleList.get(0).getYear() + " " +
                moduleList.get(0).getDescription());
    }

    // when click delete button
    public void deleteBtn(View v)
    {
        // confirmation of deleting a module by senging module code
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteModule(moduleID);
                        startActivity(new Intent(ModuleModify.this, StaffModule.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick (DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog disc = builder.create();
        disc.show();
        disc.getWindow().setBackgroundDrawableResource(R.color.orangecard);

        TextView messageText = (TextView)disc.findViewById( android.R.id.message );
        messageText.setGravity( Gravity.CENTER_HORIZONTAL );
    }

    public void editBtn(View v)
    {
        Intent intent = new Intent (ModuleModify.this, AddModule.class);
        intent.putExtra("moduleInfo", moduleID);
        startActivity(intent);
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
        return false;    }


}
