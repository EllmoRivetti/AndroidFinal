package com.example.scrabblepluszkiewiczrivetti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    public final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    public Dictionary dict;
    ArrayList<HashMap<String, String>> mliste;

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

        mliste = new ArrayList<>();

        String key[] = {"word"};
        int values[] = {R.id.item_entry};

        ListView list = findViewById(R.id.listViewResult);
        SimpleAdapter contact_adapter = new SimpleAdapter(this, mliste, R.layout.item_entry, key, values);
        list.setAdapter(contact_adapter);


        for(int i = 0 ; i < 10 ; i ++)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("word", "Thing");
            mliste.add(map);
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
        Boolean b2 = dict.mayBeComposed("bonjour", new char[]{'b', 'o', 'o', 'j', 'n', 'u', 'r'});
        Log.i("Dict", Boolean.toString(b2));
        Boolean b3 = dict.mayBeComposed("bonjour", new char[]{'b', 'o', 'j', 'n', 'u', 'r'});
        Log.i("Dict", Boolean.toString(b3));
        Boolean b4 = dict.mayBeComposed("bônjöur", new char[]{'b', 'o', 'j', 'n', 'u', 'r'});
        Log.i("Dict", Boolean.toString(b4));
        Boolean b5 = dict.mayBeComposed("Bœnjöur", new char[]{'b', 'o', 'j', 'n', 'u', 'r'});
        Log.i("Dict", Boolean.toString(b5));
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
