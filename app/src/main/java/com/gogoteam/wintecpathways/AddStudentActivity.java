package com.gogoteam.wintecpathways;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class AddStudentActivity extends AppCompatActivity {

    private ImageView studentImageView;
    private EditText nameText, studentidText, emailText, phoneText, dateText;
    private String name, studentid, email, date;
    private Button cancelBtn, saveBtn;
    //private DatePickerDialog.OnDateSetListener mDateSetListener;

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
        phoneText = (EditText) findViewById(R.id.phoneText);
        dateText = (EditText)findViewById(R.id.dateText);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        saveBtn = (Button)findViewById(R.id.saveBtn);


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
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode == RESULT_OK){
            if(reqCode == 1){
                studentImageView.setImageURI(data.getData());
            }
        }
    }


}
