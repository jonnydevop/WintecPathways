package com.gogoteam.wintecpathways;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class StaffActivity extends AppCompatActivity {

    CardView studentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        ActionBar actionBar = getSupportActionBar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.staff_menu, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                setContentView(R.layout.activity_pathway);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                setContentView(R.layout.activity_pathway);
                return true;
            }
        };

        MenuItem aboutItem = menu.findItem(R.id.importitem);
        aboutItem.setOnActionExpandListener(onActionExpandListener);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int a;
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            case R.id.importitem:
                a = dbHandler.loadData();
                TextView buckysText1 = (TextView)findViewById(R.id.studentSearchText);
                buckysText1.setText(String.valueOf(a));
                return true;
            case R.id.exportitem:
                /*
                a = dbHandler.loadData();
                TextView buckysText1 = (TextView)findViewById(R.id.studentSearchText);
                buckysText1.setText(String.valueOf(a));
                */
                return true;
        }
        return false;
    }


}
