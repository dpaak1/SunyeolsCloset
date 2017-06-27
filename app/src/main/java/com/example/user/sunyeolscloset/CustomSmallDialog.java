package com.example.user.sunyeolscloset;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class CustomSmallDialog extends Dialog {

    private ImageButton closeButton;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.custom_exit_dialog);
        closeButton = (ImageButton) findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });

    }


    public CustomSmallDialog(Context context) { //생성자
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }


}
