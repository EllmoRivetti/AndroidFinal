package com.example.scrabblepluszkiewiczrivetti;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;

public class PermissionReadSmsDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Permission needed").setMessage("Cette permission est nécessaire pour l'éxecution du programme")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("TAG", "Acceptation");
                        dialogInterface.dismiss();
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_SMS},
                                MainActivity.REQUEST_CODE_ASK_PERMISSIONS);

                    }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("TAG", "Annulation");
                        dialogInterface.dismiss();
                    }
                }) ;
        return builder.create();
    }
}
