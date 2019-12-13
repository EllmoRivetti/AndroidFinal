package com.example.scrabblepluszkiewiczrivetti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    public Dictionary dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionReadSms();
        }
        else {
            init();
        }


    }

    private void init()
    {
        dict = new Dictionary(this);
        dict.execute("");
    }

    public void resume()
    {
        Log.i("Dict", "Testing isValidWord");
        Boolean b1 = dict.isValidWord("évoquent");
        Log.i("Dict", Boolean.toString(b1));

        Log.i("Dict", "Testing mayBeComposed");
        testMayBeComposed("bonjour",    new char[]{'b', 'o', 'o', 'j', 'n', 'u', 'r'});
        testMayBeComposed("bonjour",    new char[]{'b', 'o', 'j', 'n', 'u', 'r'});
        testMayBeComposed("bônjöur",    new char[]{'b', 'o', 'o', 'j', 'n', 'u', 'r'});
        testMayBeComposed("Bœnjöur",    new char[]{'b', 'o', 'o', 'j', 'n', 'u', 'r'});
        testMayBeComposed("bec",        new char[]{'b', '*', 'e' });


        Log.i("Dict", "Testing getWordsThatCanBeComposed");
        List<String> words = dict.getWordsThatCanBeComposed(new char[]{'b', '*', 'e' });
        for(String s : words)
        {
            Log.i("Dict", s);
        }
    }

    private void testMayBeComposed(String word, char[] letters)
    {
        Boolean b2 = dict.mayBeComposed(word, letters);
        if (b2)
            Log.i("Dict", "Can build word \"" + word + "\" from chars:  " + String.valueOf(letters));
        else
            Log.i("Dict", "Can NOT build word \"" + word + "\" from chars: " + String.valueOf(letters));

    }

    public void requestPermissionReadSms() {
        PermissionReadSmsDialog dialog = new PermissionReadSmsDialog();
        dialog.show(getSupportFragmentManager(), "Dialog Permission Read SMS");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission acceptée !", Toast.LENGTH_LONG).show();
                    init();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
