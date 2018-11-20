package com.gogoteam.wintecpathways;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gogoteam.wintecpathways.database.DBHandler;


public class StaffActivity extends AppCompatActivity {

    CardView studentCard;
    CardView studentSearchView;
    CardView moduleSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        ActionBar actionBar = getSupportActionBar();

        studentSearchView = (CardView)findViewById(R.id.studentSearchView);
        studentSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffActivity.this, StaffStudent.class));
            }
        });

        moduleSearchView = (CardView)findViewById(R.id.moduleSearchView);
        moduleSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffActivity.this, StaffModule.class));
            }
        });
    }
    //Toolbar menu
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

        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            case R.id.importitem:
                dbHandler.loadData();
                //TextView buckysText1 = (TextView)findViewById(R.id.studentSearchText);
                //buckysText1.setText(String.valueOf(a));
                Toast.makeText(this,"Data load completed!",Toast.LENGTH_LONG).show();
                return true;
            case R.id.exportitem:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("If you want this premium feature, please support us and buy the Addon")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                AlertDialog disc = builder.create();
                disc.show();
                /*
                String state = Environment.getExternalStorageState();
                //When External Storage is available
                if(state.equals(Environment.MEDIA_MOUNTED)) {
                    File sdCard = Environment.getExternalStorageDirectory();
                    File file = new File(sdCard,"export.txt");

                    try {
                        FileOutputStream outputStream = new FileOutputStream(file);//打开文件输出流
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));//写入到缓存流
                        writer.write("data here");//从从缓存流写入
                        writer.close();//关闭流
                        Toast.makeText(this, "Sucess!", Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception exception) {
                        Toast.makeText(this, "failed!"+exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }*/
                return true;
        }
        return false;
    }


}
