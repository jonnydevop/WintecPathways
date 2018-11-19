package com.gogoteam.wintecpathways;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gogoteam.wintecpathways.adapter.RecyclerViewAdapter;
import com.gogoteam.wintecpathways.database.DBHandler;
import com.gogoteam.wintecpathways.database.Module;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StaffModule extends AppCompatActivity implements SearchView.OnQueryTextListener {


    DBHandler dbHandler;
    private List<Module> moduleList = new LinkedList<>();;

    //adapter
    RecyclerViewAdapter adapter;

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
        adapter = new RecyclerViewAdapter(this, moduleList);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getting the search bar
        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
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
        List<Module> newList = new ArrayList<>();

        for(Module module: moduleList )
        {
            if(module.getMID().toLowerCase().contains(userInput) || module.getMName().toLowerCase().contains(userInput))
            {
                newList.add(module);
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
