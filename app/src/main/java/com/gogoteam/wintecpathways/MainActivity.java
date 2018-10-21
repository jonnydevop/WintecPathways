package com.gogoteam.wintecpathways;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            disc.getWindow().setBackgroundDrawableResource(R.color.endOrangeGradient);
        }
    }

}
