package com.example.scrabblepluszkiewiczrivetti;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Dictionary extends AsyncTask<String, String, String> {

    private String[] wordList;
    private MainActivity main;


    // Runs in UI before background thread is called
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Do something like display a progress bar
    }

    @Override
    protected String doInBackground(String... voids) {

        try {
            Log.i("Dict", "Starting file parse");
            InputStream ips = main.getApplicationContext().getResources().openRawResource(R.raw.fr);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    int size = Integer.parseInt(line);
                    wordList = new String[size];
                } else {
                    wordList[i - 1] = line;
                    Log.i("Dict", wordList[i - 1]);
                }
                i++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // This is called from background thread but runs in UI
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(final String unused) {
        //Process the result here
        super.onPostExecute(unused);
        Log.i("Dict", "Done processing");

        main.resume();
    }

    public Dictionary(MainActivity main)
    {
        this.main = main;
    }

    public boolean isValidWord(String word)
    {
        Arrays.sort(wordList);
        return Arrays.binarySearch(wordList, word) >= 0;
    }

}
