package com.example.scrabblepluszkiewiczrivetti;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class DictionarySearch extends AsyncTask<Void, List<WordComposition>, Void> {

    private AsyncResponse<List<WordComposition>> delegate = null;
    private Dictionary dict;
    private char[] letters;
    private List<WordComposition> result;

    public DictionarySearch(AsyncResponse<List<WordComposition>> delegate, Dictionary dict, char[] letters){
        this.dict = dict;
        this.letters = letters;
        this.delegate = delegate;
    }
    public DictionarySearch(AsyncResponse<List<WordComposition>> delegate, Dictionary dict, String s)
    {
        this(delegate, dict, s.toCharArray());
    }
    @Override
    protected Void doInBackground(Void ... unused) {
        Log.i("Dict", "Letters: "+ letters.length);
        result =  this.dict.getWordsThatCanBeComposed(letters);
        return null;
    }
    public List<WordComposition> getResult()
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
