package com.gogoteam.wintecpathways;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.gogoteam.wintecpathways.adapter.RecyclerViewAdapter;
import com.gogoteam.wintecpathways.database.DBHandler;
import com.gogoteam.wintecpathways.database.Module;

import java.util.LinkedList;
import java.util.List;

public class StaffModule extends AppCompatActivity {


    DBHandler dbHandler;
    private List<Module> moduleList = new LinkedList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_module);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Modules");
        dbHandler = new DBHandler(this, null, null, 1);

        // the floating button
        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addModule(v);
                    }
                }
        );

        initModuleList();
        initRecyclerView();
    }

    private void initModuleList()
    {
        Module module = new Module();
        moduleList = dbHandler.searchModule(module);
    }

    private void initRecyclerView(){
        Log.i("chris", "initRecyclerView  ");
        RecyclerView recyclerView = findViewById(R.id.moduleView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, moduleList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addModule(View v)
    {
        Log.i("chris", "addmodule  ");
        // go to add module activity
        startActivity(new Intent(StaffModule.this, AddModule.class));
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
