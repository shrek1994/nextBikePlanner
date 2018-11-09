package com.maciejwozny.nextbikeplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

public class ChooseStationDialog {
    private CalculatePathListener pathListener;
    private TextView start;
    private TextView end;

    public ChooseStationDialog(CalculatePathListener pathListener, TextView start, TextView end) {
        this.pathListener = pathListener;
        this.start = start;
        this.end = end;
    }

    public void show(Context context, String stationName) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    pathListener.setEnd(stationName);
                    end.setText(stationName);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    pathListener.setStart(stationName);
                    start.setText(stationName);
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(stationName).setPositiveButton("End Point", dialogClickListener)
                .setNegativeButton("Start point", dialogClickListener).show();
    }
}
