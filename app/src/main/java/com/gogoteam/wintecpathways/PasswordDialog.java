package com.gogoteam.wintecpathways;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

public class PasswordDialog extends Dialog {

    MainActivity mainActivity;
    ImageView cancelButton, acceptButton;
    EditText textPassword;
    TextInputLayout errorInputLayout;

    public PasswordDialog(Context context){
        super(context);
        mainActivity = (MainActivity)context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_login);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        textPassword = (EditText)findViewById(R.id.textPassword);
        errorInputLayout = (TextInputLayout)findViewById(R.id.errorInputLayout);
        acceptButton = (ImageView)findViewById(R.id.accept_button);
        cancelButton = (ImageView)findViewById(R.id.cancel_button);

        //Set the onClickListener buttons
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textPassword.getText().toString().equals("")){// Compares if no input
                    errorInputLayout.setError("Please enter password");


                }else if(!textPassword.getText().toString().equals("1234")){// Here you can change password
                    errorInputLayout.setError("Incorrect password");

                }else{
                    Intent i = new Intent(mainActivity, StaffActivity.class);
                    mainActivity.startActivity(i);
                    dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
