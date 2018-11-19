package com.gogoteam.wintecpathways;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gogoteam.wintecpathways.database.DBHandler;
import com.gogoteam.wintecpathways.database.Module;
import com.gogoteam.wintecpathways.database.Student;

import java.util.Calendar;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {

    private ImageView studentImageView;
    private EditText nameText, studentidText, emailText, phoneText, dateText;
    private TextInputEditText name, studentid, email, date;
    private Button cancelBtn, saveBtn;
    private Spinner pathway;

    DBHandler dbHandler;
    Student student;
    Bundle studentInfo;
    String studentID;

    private List<Student> studentList;




    //private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String [] pathways =
            {" SELECT A PATHWAY ",
            "Network Engineering",
            "Software Engineering",
            "Database Architecture",
            "Multimedia and Web Development"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        studentImageView = (ImageView)findViewById(R.id.studentImageView);
        nameText = (EditText)findViewById(R.id.nameText);
        studentidText = (EditText)findViewById(R.id.studentidText);
        emailText = (EditText)findViewById(R.id.emailText);
        //phoneText = (EditText) findViewById(R.id.phoneText);
        dateText = (EditText)findViewById(R.id.dateText);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        saveBtn = (Button)findViewById(R.id.saveBtn);

        //prepopulate pathways
        pathway = findViewById(R.id.pathwayID);
        pathway.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pathways));


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        studentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Contact Image"), 1);
            }
        });

        /*dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddStudentActivity.this,
                        R.style.Theme_AppCompat_Dialog_MinWidth,
                        mDateSetListener,day,month,year);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int day,  int month, int year ) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                dateText.setText(date);

            }
        };*/

        student = new Student();
        dbHandler = new DBHandler(this, null, null, 1);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent(v);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelStudent(v);
            }
        });

        // check if it's modify module
        studentInfo = getIntent().getExtras();
        if (studentInfo == null){
            return;
        }

        // show module details
        studentID = studentInfo.getString("moduleInfo");
        showStudentInfo();
    }
    public void showStudentInfo()
    {
        // get the module code from module view ( module list)
        String[] pathway = {"Network Engineering", "Software Engineering", "Database Architecture", "Multimedia and Web Development"};
        student.setSID(studentID);

        Log.i("chris", "showModuleInfo  " + studentID);
        // get module deatils from DB by sending module code
        studentList = dbHandler.searchStudent(student);

        //private String name, studentid, email, date;
        name.setText(studentList.get(0).getSName());
        studentid.setText(studentList.get(0).getSID());
        email.setText(studentList.get(0).getEmail());
        date.setText(studentList.get(0).getDate_Enrolled());



        // log for testing DB, these details should be shown on layout, GOGO Juan!!
        Log.i("chris", "showStudentInfo  " + studentList.get(0).getSName() + " " +
                studentList.get(0).getSID() + " " +
                studentList.get(0).getEmail() + " " +
                studentList.get(0).getDate_Enrolled() + " ");
    }

    // when save button is clicked
    public void saveStudent(View v)
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

    // when cancel button is clicked
    public void cancelStudent(View v) {
        // go back to Module View (List)
        finish();

    }

    public void addEditModule(){

        boolean saveSuccess = true;

//    private EditText nameText, studentidText, emailText, phoneText, dateText;
        student.setSName(nameText.getText().toString());
        student.setSID(studentidText.getText().toString());
        student.setEmail(emailText.getText().toString());
        //student.setPhone(phoneText.getText().toString());
        student.setDate_Enrolled(dateText.getText().toString());

        saveSuccess = dbHandler.addStudent(student);

        if(saveSuccess == false)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The module is already exist, please use edit function instead of add function.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            startActivity(new Intent(AddStudentActivity.this, StaffStudent.class));
                        }
                    });
            AlertDialog disc = builder.create();
            disc.show();
        }
        else {
            Toast.makeText(this, "Student Info saved!", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode == RESULT_OK){
            if(reqCode == 1){
                studentImageView.setImageURI(data.getData());
            }
        }
    }


}
