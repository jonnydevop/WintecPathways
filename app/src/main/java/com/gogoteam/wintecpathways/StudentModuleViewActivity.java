
//public class StudentModuleViewActivity {
package com.gogoteam.wintecpathways;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TabHost;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentModuleViewActivity extends AppCompatActivity {

    //a list to store all the products
    List<StudentProductsActivity> productList;
    List<StudentProductsActivity> productList2;
    List<StudentProductsActivity> productList3;

    //access db

    Module module;
    DBHandler dbHandler;
    Bundle moduleInfo;
    String moduleID;
    private List<Module> moduleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentmoduleviewactivity);

        //set title
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("Modules");

        //tabHost
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        dbHandler = new DBHandler(this, null, null, 1);
        List<Module> moduleList = dbHandler.retrievePathway("Software Engineering");
        Log.i("chris", "showModuleInfo  " + String.valueOf(moduleList.size()));

        //tab1
        TabHost.TabSpec spec = tabHost.newTabSpec("Year one ");
        spec.setContent(R.id.year1);
        spec.setIndicator("Year one");
        tabHost.addTab(spec);
        //initializing the productlist
        productList = new ArrayList<>();

        //adding some items to our list
        productList.add(
                new StudentProductsActivity(
                        "1",
                        "Module 1",
                        "Module desc",
                        "Completed",
                        R.drawable.applecrumbleimage));

        productList.add(
                new StudentProductsActivity(
                        "2",
                        "Module 2",
                        "Module desc",
                        "Completed",
                        R.drawable.applecrumbleimage));

        productList.add(
                new StudentProductsActivity(
                        "3",
                        "Module 3",
                        "Module desc",
                        "NotCompleted",
                        R.drawable.applecrumbleimage));

        //showModuleInfo();

        //creating recyclerview adapter

        //getting the recyclerview from xml
        // ModuleAdapter adapter = new ModuleAdapter(this, productList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        ModuleAdapter adapter = new ModuleAdapter(this, productList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //tab2
        spec = tabHost.newTabSpec("Year two ");
        spec.setContent(R.id.year2);
        spec.setIndicator("Year two");
        tabHost.addTab(spec);
        productList2 = new ArrayList<>();
        //adding some items to our list
        productList2.add(
                new StudentProductsActivity(
                        "1",
                        "Module 1",
                        "Module desc",
                        "Completed",
                        R.drawable.applecrumbleimage));

        productList2.add(
                new StudentProductsActivity(
                        "2",
                        "Module 2",
                        "Module desc",
                        "Completed",
                        R.drawable.applecrumbleimage));

        productList2.add(
                new StudentProductsActivity(
                        "3",
                        "Module 3",
                        "Module desc",
                        "NotCompleted",
                        R.drawable.applecrumbleimage));

        //creating recyclerview adapter

        //getting the recyclerview from xml
        // ModuleAdapter adapter = new ModuleAdapter(this, productList);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        ModuleAdapter adapter2 = new ModuleAdapter(this, productList2);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        //tab3
        spec = tabHost.newTabSpec("Year three ");
        spec.setContent(R.id.year3);
        spec.setIndicator("Year three");
        tabHost.addTab(spec);
        productList3 = new ArrayList<>();
        //adding some items to our list
        productList3.add(
                new StudentProductsActivity(
                        "1",
                        "Module 1",
                        "Module desc",
                        "Completed",
                        R.drawable.applecrumbleimage));

        productList3.add(
                new StudentProductsActivity(
                        "2",
                        "Module 2",
                        "Module desc",
                        "Completed",
                        R.drawable.applecrumbleimage));

        productList3.add(
                new StudentProductsActivity(
                        "3",
                        "Module 3",
                        "Module desc",
                        "NotCompleted",
                        R.drawable.applecrumbleimage));

        //creating recyclerview adapter
        //getting the recyclerview from xml
        // ModuleAdapter adapter = new ModuleAdapter(this, productList);
        RecyclerView recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        ModuleAdapter adapter3 = new ModuleAdapter(this, productList3);
        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
    }


    public void showModuleInfo()
    {
        dbHandler = new DBHandler(this, null, null, 1);
        module = new Module();

        moduleInfo = getIntent().getExtras();
        if (moduleInfo == null){
            return;
        }
        moduleID = moduleInfo.getString("moduleInfo");

        //dbHandler = new DBHandler(this, null, null, 1);
        module.setPathway_1("Network Engineering");
        moduleList = dbHandler.searchModule(module);

        // get the module code from module view ( module list)
        module.setMID(moduleID);

        Log.i("chris", "showModuleInfo  " + moduleID);
        // get module deatils from DB by sending module code
        moduleList = dbHandler.searchModule(module);

        // log for testing DB, these details should be shown on layout, GOGO Juan!!
        Log.i("chris", "showModuleInfo  " + moduleList.get(0).getMID() + " " +
                moduleList.get(0).getClassification() + " " +
                moduleList.get(0).getSemester() + " " +
                moduleList.get(0).getMName() + " " +
                moduleList.get(0).getPathway_1()+ " " +
                moduleList.get(0).getPreMID_1() + " " +
                moduleList.get(0).getYear());

        moduleList.get(0).getMID();
        moduleList.get(0).getClassification();
        moduleList.get(0).getSemester();
        moduleList.get(0).getMName();
        moduleList.get(0).getPathway_1();
        moduleList.get(0).getPreMID_1();
        moduleList.get(0).getYear();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
        }
        return false;
    }
}


