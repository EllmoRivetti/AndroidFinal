package com.example.scrabblepluszkiewiczrivetti;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    public final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    public Dictionary dict;
    public DictionarySearch search;

    ArrayList<HashMap<String, String>> mliste;

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.savedInstanceState = savedInstanceState;

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


        /*for(int i = 0 ; i < 10 ; i ++)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("word", "Thing");
            mliste.add(map);
        }*/

        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchButtonAction();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuStyle1:
                changeStyle(1);
                break;
            case R.id.menuStyle2:
                changeStyle(2);
                break;
            case R.id.menuStyle3:
                changeStyle(3);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeStyle(int id)
    {
        switch(id)
        {
            case 1:
                setTheme(R.style.Theme1);
                break;
            case 2:
                setTheme(R.style.Theme2);
                break;
            case 3:
                setTheme(R.style.Theme3);
                break;

            default:
                break;
        }
        setContentView(R.layout.activity_main);
    }

    private void searchButtonAction(){
        String s = "tet";
        MainActivity a = this; // In order to reference this

        search = new DictionarySearch(new AsyncResponse<List<String>>() {
            @Override
            public void processFinish(List<String> result) {
                List<String> listString = search.getResult();
                Log.i("Dict", "List: "+listString);
            }
        }, this.dict, s);
        search.execute();
    }

    private void init()
    {
        dict = new Dictionary(new AsyncResponse<Void>() {
            @Override
            public void processFinish(Void result) {
                resume();
            }
        }, this.getApplicationContext());
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
