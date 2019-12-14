package com.example.scrabblepluszkiewiczrivetti;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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

    DictionarySearch search;

    private void searchButtonAction(){
        String s = "be*";
        MainActivity a = this; // In order to reference this

        search = new DictionarySearch(new AsyncResponse<List<WordComposition>>() {
            @Override
            public void processFinish(List<WordComposition> result) {
                String s = "";
                if (result == null) {
                    s = "no result";
                } else {
                    // s = Integer.toString(this.result.size()) + "word(s) found : \n";
                    for (WordComposition wc : result)
                    {
                        s += wc.toString();
                    }
                }
                Log.i("Dict", s);
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
        List<WordComposition> words = dict.getWordsThatCanBeComposed(new char[]{'b', '*', 'e' });
        for(WordComposition s : words)
        {
            Log.i("Dict", s.toString());
        }
    }

    private void testMayBeComposed(String word, char[] letters)
    {
        WordComposition wc = dict.mayBeComposed(word, letters);
        if (wc != null)
            Log.i("Dict", "Can build word \"" + word + "\" from chars:  " + String.valueOf(letters) + " worth " + wc.getScore() + " points.");
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
