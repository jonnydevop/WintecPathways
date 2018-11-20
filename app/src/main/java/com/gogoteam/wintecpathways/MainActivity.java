package com.gogoteam.wintecpathways;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


public class MainActivity extends AppCompatActivity {

    CardView studentCard, staffCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        final SharedPreferences pref = getSharedPreferences("Preferences", MODE_PRIVATE);

        String lver = pref.getString("Version", "");
        String ver = this.getString(R.string.version);
        if(!ver.equals(lver))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Disclaimer")
                    .setMessage(this.getString(R.string.disclaimer))
                    .setCancelable(false)
                    .setIcon(R.drawable.caution)
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {

                        public void onClick (DialogInterface dialog, int id) {
                            SharedPreferences pref = getSharedPreferences("Preferences", MODE_PRIVATE);
                            SharedPreferences.Editor edit = pref.edit();
                            edit.putString("Version", getString(R.string.version));
                            edit.commit();
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                     MainActivity.this.finish();
                             }
                    });
            AlertDialog disc = builder.create();
            disc.show();
            //disc.getWindow().setBackgroundDrawableResource(R.color.endOrangeGradient);
        }

        studentCard = (CardView)findViewById(R.id.studentView);
        staffCard = (CardView)findViewById(R.id.staffView);
        studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PathwayActivity.class));
            }
        });

        staffCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PasswordDialog myDialog = new PasswordDialog(MainActivity.this);
                myDialog.setCancelable(false);
                myDialog.show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About")
                .setMessage(this.getString(R.string.about))
                .setCancelable(false)
                .setIcon(R.mipmap.logo)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick (DialogInterface dialog, int id)   {
                        dialog.cancel();
                    }
                });
        AlertDialog disc = builder.create();
        disc.show();
        //disc.getWindow().setBackgroundDrawableResource(R.color.endOrangeGradient);
        return true;
    }
}
