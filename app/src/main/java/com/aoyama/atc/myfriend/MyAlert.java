package com.aoyama.atc.myfriend;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 19/11/2559.
 */

public class MyAlert {

    //exclipit การต่อท่อภายใน
    private Context context;
    private int anInt;
    private String titleString,messageString;

    public MyAlert(Context context, int anInt, String titleString, String messageString) {
        this.context = context;
        this.anInt = anInt;
        this.titleString = titleString;
        this.messageString = messageString;
    }//constructor

    public void myDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(anInt);
        builder.setTitle(titleString);
        builder.setMessage(messageString);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    } //my dialog
}// Main class
