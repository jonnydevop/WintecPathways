package com.gogoteam.wintecpathways;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;

import com.gogoteam.wintecpathways.adapter.StudentRecyclerViewAdapter;
import com.gogoteam.wintecpathways.database.DBHandler;
import com.gogoteam.wintecpathways.database.Module;
import com.gogoteam.wintecpathways.database.Student;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StaffStudent extends AppCompatActivity implements SearchView.OnQueryTextListener {

    DBHandler dbHandler;
    StudentRecyclerViewAdapter adapter;
    private List<Student> studentList;

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
        adapter = new StudentRecyclerViewAdapter(this, studentList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this,DividerItemDecoration.VERTICAL, 36));

        //Hide floating button on Scroll
        final FloatingActionButton addBtn = findViewById(R.id.addBtn);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if(dy < 0){
                    addBtn.show();
                } else if (dy > 0){
                    addBtn.hide();
                }
            }
        });
    }

    private void addStudent(View v)
    {
        Log.i("Nancy", "addStudent  ");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getting the search bar
        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Student");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        List<Student> newList = new ArrayList<>();

        for(Student student: studentList )
        {
            if(student.getSName().toLowerCase().contains(userInput) || student.getSID().toLowerCase().contains(userInput))
            {
                newList.add(student);
            }
        }
        adapter.updateList(newList);
        return true;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        initModuleList();
        initRecyclerView();
    }
}
