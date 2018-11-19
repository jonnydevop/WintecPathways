package com.gogoteam.wintecpathways;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//import com.gogoteam.wintecpathways.adapter.SearchAdapter;
import com.gogoteam.wintecpathways.database.DBHandler;
import com.gogoteam.wintecpathways.database.Module;

import java.util.ArrayList;
import java.util.List;

public class PathwayActivity extends AppCompatActivity {

    CardView networking;
    CardView softwareEngineering;
    CardView dataBases;
    CardView multimedia;
    Bundle bundle = new Bundle();
   // private SearchAdapter adapter;
    List<Module> suggestList = new ArrayList<>();
    DBHandler database;
    Module module;
    SearchView searchView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathway);

        getWindow().setExitTransition(new Explode());
        ActionBar actionBar = getSupportActionBar();

        database = new DBHandler(this, null, null, 1);
        module = new Module();

        final Intent studentModActivityPage = new Intent(PathwayActivity.this, StudentModuleViewActivity.class);

        //network pathway
        networking = findViewById(R.id.card_view1);
        networking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("pathway","Network Engineering");
                studentModActivityPage.putExtras(bundle);
                startActivity(studentModActivityPage);
            }
        });

        //Software Engineering pathway
        softwareEngineering = findViewById(R.id.card_view2);
        softwareEngineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("pathway","Software Engineering");
                studentModActivityPage.putExtras(bundle);
                startActivity(studentModActivityPage);
            }
        });

        //Databases pathway
        dataBases = findViewById(R.id.card_view3);
        dataBases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("pathway","Database Architecture");
                studentModActivityPage.putExtras(bundle);
                startActivity(studentModActivityPage);
            }
        });

        //Multimedia pathway
        multimedia = findViewById(R.id.card_view4);
        multimedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("pathway","Multimedia and Web Development");
                studentModActivityPage.putExtras(bundle);
                startActivity(studentModActivityPage);
            }
        });
    }
    //Toolbar
 //   @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//
//        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem menuItem) {
//                setContentView(R.layout.search_activity);
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
//                setContentView(R.layout.activity_pathway);
//                return true;
//            }
//        };
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        searchItem.setOnActionExpandListener(onActionExpandListener);
//
//        return true;
//    }

    private void viewData(){

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
