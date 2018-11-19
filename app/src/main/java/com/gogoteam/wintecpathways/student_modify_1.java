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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gogoteam.wintecpathways.database.DBHandler;
import com.gogoteam.wintecpathways.database.Module;
import com.gogoteam.wintecpathways.database.Student;
import com.gogoteam.wintecpathways.database.StudentModule;

import java.util.List;

public class student_modify_1 extends AppCompatActivity {
    Student student;
    DBHandler dbHandler;
    Bundle studentInfo;
    String studentID;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_modify_1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("STUDENTS");

        student = new Student();
        dbHandler = new DBHandler(this, null, null, 1);

        studentInfo = getIntent().getExtras();
        if (studentInfo == null){
            return;
        }
        studentID = studentInfo.getString("studentInfo");

        showStudentInfo();
    }

    public void showStudentInfo()
    {
        String moduleStr = "";
        StudentModule sm;

        TextView Name = findViewById(R.id.nameText);
        TextView ID = findViewById(R.id.studentidText);
        TextView email = findViewById(R.id.emailText);
        TextView date = findViewById(R.id.dateText);
        TextView programme = findViewById(R.id.programmeText);
        TextView pathway = findViewById(R.id.pathwayID);
        TextView modules = findViewById(R.id.textModule);

        student.setSID(studentID);
        studentList = dbHandler.searchStudent(student);

        Name.setText(studentList.get(0).getSName());
        ID.setText(studentList.get(0).getSID());
        email.setText(studentList.get(0).getEmail());
        date.setText(studentList.get(0).getDate_Enrolled());
        programme.setText(studentList.get(0).getProgramme());
        pathway.setText(studentList.get(0).getSpecialisation());
        if (studentList.get(0).getModules()!=null) {
            for (int i=0; i<studentList.get(0).getModules().size();i++) {
                sm = studentList.get(0).getModules().get(i);
                if (sm.getCompletion().equals("No")) {
                    if (!moduleStr.equals("")) {
                        moduleStr = moduleStr + "/";
                    }
                    moduleStr = moduleStr + sm.getMID();
                }
            }
        }
        if (moduleStr.equals("")) {
            moduleStr = "None";
        }
        modules.setText(moduleStr);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.action_delete:
                deleteBtn();
                return true;
        }
        return false;    }

    // when click delete button
    public void deleteBtn()
    {Log.i("nancy", "del student");

        // confirmation of deleting a module by senging module code
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("nancy", "del student yes:" + studentID);
                        if (dbHandler.deleteStudent(studentID)==false) {
                            Toast.makeText(student_modify_1.this,"Delete failed as the student is enrolled!",Toast.LENGTH_LONG).show();
                        }
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick (DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog disc = builder.create();
        disc.setContentView(R.layout.delete_dialog);
        disc.show();
        //disc.getWindow().setBackgroundDrawableResource(R.color.orangecard);

        TextView messageText = (TextView)disc.findViewById( android.R.id.message );
        messageText.setGravity( Gravity.CENTER_HORIZONTAL );
    }

}