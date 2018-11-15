package com.gogoteam.wintecpathways;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TabHost;
import java.util.ArrayList;
import java.util.List;

public class StudentModuleViewActivity extends AppCompatActivity {

    //a list to store all the products
    List<StudentProductsActivity> moduleListYear1;
    List<StudentProductsActivity> moduleListYear2;
    List<StudentProductsActivity> moduleListYear3;

    //access db
    Module module;
    DBHandler dbHandler;
    Bundle moduleInfo;
    String moduleID;
    private List<Module> moduleList;
    String pathway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentmoduleviewactivity);

        //tabHost
        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        //Read path variable
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null)
             pathway = bundle.getString("pathway");
        Log.i("StudentModuleViewAivity", "Pathway:" + pathway);

        //Retrieve modules information
        dbHandler = new DBHandler(this, null, null, 1);
        moduleList = dbHandler.retrievePathway(pathway);
        Log.i("StudentModuleViewAivity", "Modules list size:  " + String.valueOf(moduleList.size()));

        for(int j=0;j<24;j++)
            Log.i("StudentModuleViewAivity", "Modules Name:  " + moduleList.get(j).getMName());

        //TAB1
        TabHost.TabSpec spec = tabHost.newTabSpec("Year one ");
        spec.setContent(R.id.year1);
        spec.setIndicator("Year one");
        tabHost.addTab(spec);

        //initializing the productlist
        moduleListYear1 = new ArrayList<>();

        // First year modules
        for(int i=0;i<8;i++){
         moduleListYear1.add(new StudentProductsActivity(
                 "1",
                 moduleList.get(i).getMName(),
                 moduleList.get(i).getMID(),
                 "Completed"
                // R.drawable.applecrumbleimage
         ));
         }
        //getting the recyclerview from xml
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        ModuleAdapter adapter = new ModuleAdapter(this, moduleListYear1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //TAB2
        spec = tabHost.newTabSpec("Year two ");
        spec.setContent(R.id.year2);
        spec.setIndicator("Year two");
        tabHost.addTab(spec);
        moduleListYear2 = new ArrayList<>();

        //adding some items to our list
       for(int i=8;i<16;i++){
            moduleListYear2.add(new StudentProductsActivity(
                    "1",
                    moduleList.get(i).getMName(),
                    moduleList.get(i).getMID(),
                    "Completed"
                    //R.drawable.applecrumbleimage
            ));
        }


        //getting the recyclerview from xml
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        ModuleAdapter adapter2 = new ModuleAdapter(this, moduleListYear2);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        //TAB 3
        spec = tabHost.newTabSpec("Year three ");
        spec.setContent(R.id.year3);
        spec.setIndicator("Year three");
        tabHost.addTab(spec);
        moduleListYear3 = new ArrayList<>();

        //adding some items to our list
       for(int i=16;i<24;i++){
            moduleListYear3.add(new StudentProductsActivity(
                    "1",
                    moduleList.get(i).getMName(),
                    moduleList.get(i).getMID(),
                    "Completed"
                   // R.drawable.applecrumbleimage
            ));
        }

        //creating recyclerview adapter
        //getting the recyclerview from xml
        RecyclerView recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        ModuleAdapter adapter3 = new ModuleAdapter(this, moduleListYear3);
        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
    }


    public void showModuleInfo()
    {

        module = new Module();
        dbHandler = new DBHandler(this, null, null, 1);

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

        Log.i("StudentModuleViewAcvity", "showModuleInfo  " + moduleID);
        // get module deatils from DB by sending module code
        moduleList = dbHandler.searchModule(module);

        // log for testing DB, these details should be shown on layout
        Log.i("StudentModuleViewAivity", "showModuleInfo  " + moduleList.get(0).getMID() + " " +
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
