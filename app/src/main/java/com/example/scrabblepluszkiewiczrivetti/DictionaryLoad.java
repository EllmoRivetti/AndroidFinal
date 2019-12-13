package com.example.scrabblepluszkiewiczrivetti;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DictionaryLoad extends AsyncTask<Void, Void, Void> {

    private AsyncResponse<String[]> delegate = null;
    private Context context;
    private String[] wordList;

    // Runs in UI before background thread is called
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Do something like display a progress bar
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.i("Dict", "Starting file parse.");
            InputStream ips = context.getResources().openRawResource(R.raw.fr);
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
                }
                i++;
            }
            br.close();
            Log.i("Dict", "Done parsing file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // This is called from background thread but runs in UI
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(final Void unused) {
        //Process the result here
        super.onPostExecute(unused);
        Log.i("Dict", "Done processing");
        delegate.processFinish(wordList);
    }

    public DictionaryLoad(AsyncResponse<String[]> delegate, Context context)
    {
        this.delegate = delegate;
        this.context = context;
    }
}
