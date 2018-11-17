package com.gogoteam.wintecpathways;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StaffStudent extends AppCompatActivity {
    SearchView searchView;
    DBHandler dbHandler;
    private List<Student> studentList = new LinkedList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_student);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Students");
        dbHandler = new DBHandler(this, null, null, 1);

        // the floating button
        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addStudent(v);
                    }
                }
        );

        initModuleList();
        initRecyclerView();
    }
    private void initModuleList()
    {
        Student student = new Student();
        studentList = dbHandler.searchStudent(student);
    }

    private void initRecyclerView(){
        Log.i("Nancy", "initRecyclerView  ");
        RecyclerView recyclerView = findViewById(R.id.studentView);
        StudentRecyclerViewAdapter adapter = new StudentRecyclerViewAdapter(this, studentList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addStudent(View v)
    {
        Log.i("Nancy", "addmodule  ");
        // go to add module activity
        startActivity(new Intent(StaffStudent.this, AddStudentActivity.class));
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
