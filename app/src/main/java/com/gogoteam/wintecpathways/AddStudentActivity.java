package com.gogoteam.wintecpathways;



import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gogoteam.wintecpathways.database.DBHandler;
import com.gogoteam.wintecpathways.database.Student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {


    private TextInputEditText nameText, studentidText, emailText, dateText,programmeText,moduleSelected;
    private Button cancelBtn, saveBtn;
    private Spinner pathwaySpinner;
    DBHandler dbHandler;
    Student student;
    Bundle studentInfo;
    String studentID;
    String pathwayText;
    private List<Student> studentList;
    int test = 0;

    ImageView studentImageView;


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

        studentImageView = findViewById(R.id.studentImageView);
        nameText = findViewById(R.id.nameText);
        studentidText = findViewById(R.id.studentidText);
        emailText = findViewById(R.id.emailText);
        programmeText = findViewById(R.id.programmeText);
        dateText = findViewById(R.id.dateText);
        cancelBtn = findViewById(R.id.cancelBtn);
        saveBtn = findViewById(R.id.saveBtn);




        //prepopulate pathways
        pathwaySpinner = findViewById(R.id.pathwayID);
        pathwaySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pathways));
        pathwaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                test = position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        studentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler = new DBHandler(AddStudentActivity.this,null,null,1);
/*               Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Contact Image"), 1);*/


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, 0);
                }


            }
        });

        //student database
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

        // check if it's student info
        studentInfo = getIntent().getExtras();
        if (studentInfo == null){
            return;
        }

        // show student details
        studentID = studentInfo.getString("studentInfo");
        showStudentInfo();
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        super.onActivityResult(reqCode, resCode, data);

  /*      if(reqCode == IMAGE_CAPTURE){
            if(reqCode == RESULT_OK){
                try{
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    //setProgressBar();
                    studentImageView.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }*/
        if(reqCode == 0) {
            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                studentImageView.setImageBitmap(thumbnail);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*if(reqCode == 0){
            if(reqCode == RESULT_OK){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                studentImageView.setMaxWidth(200);
                studentImageView.setImageBitmap(thumbnail);
            }
        }*/
    }
    public void showStudentInfo()
    {

        student.setSID(studentID);

        // get module deatils from DB by sending module code
        studentList = dbHandler.searchStudent(student);
        pathwayText = studentList.get(0).getSpecialisation();
        int index = 0;

        for(int i = 0; i<pathways.length; i++)
        {
            if(pathwayText.equals(pathways[i]))
            {
                index = i;
            }
        }

        //private String name, studentid, email, date,programme,pathwayText,moduleSelected;
        nameText.setText(studentList.get(0).getSName());
        studentidText.setText(studentList.get(0).getSID());
        emailText.setText(studentList.get(0).getEmail());
        dateText.setText(studentList.get(0).getDate_Enrolled());
        programmeText.setText(studentList.get(0).getProgramme());
        pathwaySpinner.setSelection(index,true);
        if(studentList.get(0).getSBitmap() != null){
            studentImageView.setImageBitmap(studentList.get(0).getSBitmap());
        }

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
                        editStudentinfo();
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

    public void editStudentinfo() {

        boolean saveSuccess = true;



/*        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] data = baos.toByteArray();*/

        student.setSName(nameText.getText().toString());
        student.setSID(studentidText.getText().toString());
        student.setEmail(emailText.getText().toString());
        student.setDate_Enrolled(dateText.getText().toString());
        student.setProgramme(programmeText.getText().toString());
        student.setSpecialisation(pathways[test]);
        Bitmap bitmap = ((BitmapDrawable)studentImageView.getDrawable()).getBitmap();
        student.setSBitmap(bitmap);
        Log.i("chrisita", "editStudentinfo  " + pathways[test]);


        //saveSuccess = dbHandler.addStudent(student);
        if (studentID == null) {
            Log.i("chrisita", "studentID  null");
            saveSuccess = dbHandler.addStudent(student);
            if (saveSuccess == false) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("The student info already exist, please use edit function instead of add function.")
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
            } else {
                Toast.makeText(this, "Student Info saved!", Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            Log.i("chrisita", "studentID not  null ");
            dbHandler.updateStudent(student);
            finish();
        }
    }

}
