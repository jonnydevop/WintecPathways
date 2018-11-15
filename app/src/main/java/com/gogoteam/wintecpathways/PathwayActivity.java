package com.gogoteam.wintecpathways;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PathwayActivity extends AppCompatActivity {

    CardView networking;
    CardView softwareEngineering;
    CardView dataBases;
    CardView multimedia;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathway);

        getWindow().setExitTransition(new Explode());
        ActionBar actionBar = getSupportActionBar();

        final Intent studentModActivityPage = new Intent(PathwayActivity.this, StudentModuleViewActivity.class);

        //network pathway
        networking = (CardView)findViewById(R.id.card_view1);
        networking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("pathway","Networking");
                studentModActivityPage.putExtras(bundle);
                startActivity(studentModActivityPage);
            }
        });

        //Software Engineering pathway
        softwareEngineering = (CardView)findViewById(R.id.card_view2);
        softwareEngineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("pathway","softwareEngineering");
                studentModActivityPage.putExtras(bundle);
                startActivity(studentModActivityPage);
            }
        });

        //Databases pathway
        dataBases = (CardView)findViewById(R.id.card_view3);
        dataBases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("pathway","databases");
                studentModActivityPage.putExtras(bundle);
                startActivity(studentModActivityPage);
            }
        });

        //Multimedia pathway
        multimedia = (CardView)findViewById(R.id.card_view4);
        multimedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("pathway","multimedia");
                studentModActivityPage.putExtras(bundle);
                startActivity(studentModActivityPage);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                setContentView(R.layout.search_activity);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                setContentView(R.layout.activity_pathway);
                return true;
            }
        };

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(onActionExpandListener);
        return true;
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
