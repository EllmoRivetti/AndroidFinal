package com.example.scrabblepluszkiewiczrivetti;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDialogFragment;

public class PermissionReadSmsDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Permission needed").setMessage("Cette permission est nécessaire pour l'éxecution du programme").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("TAG", "Hello There");
                requestPermissions(new String[] {Manifest.permission.READ_SMS}, MainActivity.REQUEST_CODE_ASK_PERMISSIONS);
            }
        });
        return builder.create();
    }
}
