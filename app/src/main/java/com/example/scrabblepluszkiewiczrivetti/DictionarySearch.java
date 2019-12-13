package com.example.scrabblepluszkiewiczrivetti;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class DictionarySearch extends AsyncTask<Void, List<String>, Void> {

    private AsyncResponse<List<String>> delegate = null;
    private Dictionary dict;
    private char[] letters;
    private List<String> result;

    public DictionarySearch(AsyncResponse<List<String>> delegate, Dictionary dict, char[] letters){
        this.dict = dict;
        this.letters = letters;
        this.delegate = delegate;
    }
    public DictionarySearch(AsyncResponse<List<String>> delegate, Dictionary dict, String s)
    {
        this(delegate, dict, s.toCharArray());
    }
    @Override
    protected Void doInBackground(Void ... unused) {
        result =  this.dict.getWordsThatCanBeComposed(letters);
        return null;
    }
    public List<String> getResult()
    {
        return this.result;
    }

    @Override
    protected void onPostExecute(final Void unused) {
        //Process the result here
        super.onPostExecute(unused);
        Log.i("Dict", "Done processing");
        delegate.processFinish(result);
    }

}
