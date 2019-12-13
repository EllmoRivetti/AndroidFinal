package com.example.scrabblepluszkiewiczrivetti;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    public static boolean mayBeComposed(String word, char[] letters)
    {
        word = replaceFrenchCharacter(word);
        boolean[] isUsed = new boolean[letters.length];
        Arrays.fill(isUsed, false);

        for(char c : word.toCharArray()) {
            for (int i = 0; i < letters.length; i++) {
                if (letters[i] == c && !isUsed[i]) {
                    isUsed[i] = true;
                    break;
                } else if(i == letters.length - 1){
                    return false;
                }
            }

        }
        return true;
    }

    public List<String> getWordsThatCanBeComposed(char[] letters)
    {
        List<String> l = new LinkedList<String>();
        for(String s : wordList)
        {
            if (mayBeComposed(s, letters))
            {
                l.add(s);
            }
        }

        return l;
    }
    public static String replaceFrenchCharacter(String s)
    {
        StringBuilder newString = new StringBuilder(s);
        for(int i = 0 ; i < s.length() ; i++)
        {
            if(s.charAt(i) == 'à'
                    || s.charAt(i) == 'â'
                    || s.charAt(i) == 'ä')
            {
                newString.setCharAt(i, 'a');
            }
            else if(s.charAt(i) == 'ç')
            {
                newString.setCharAt(i, 'c');
            }
            else if(s.charAt(i) == 'é'
                    || s.charAt(i) == 'è'
                    || s.charAt(i) == 'ê'
                    || s.charAt(i) == 'ë')
            {
                newString.setCharAt(i, 'e');
            }
            else if(s.charAt(i) == 'ô'
                    || s.charAt(i) == 'ö')
            {
                newString.setCharAt(i, 'o');
            }
            else if(s.charAt(i) == 'ù'
                    || s.charAt(i) == 'ü'
                    || s.charAt(i) == 'û')
            {
                newString.setCharAt(i, 'u');
            }
            else if(s.charAt(i) == 'œ')
            {
                //String start = newString.substring(0,i);
                //String end   = newString.substring(i);
                newString.insert(i, "oe");

            }
            else if(s.charAt(i) == 'æ')
            {
                newString.insert(i, "ae");
            }
        }

        return newString.toString();
    }

}
