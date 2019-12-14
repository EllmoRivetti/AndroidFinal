package com.example.scrabblepluszkiewiczrivetti;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Dictionary{

    private String[] wordList;

    public Dictionary(final AsyncResponse<Void> response, Context context)
    {
        DictionaryLoad load = new DictionaryLoad(new AsyncResponse<String[]>() {
            @Override
            public void processFinish(String[] result) {
                wordList = result;
                response.processFinish(null);
            }
        }, context);
        load.execute();
    }

    public boolean isValidWord(String word)
    {
        Arrays.sort(wordList);
        return Arrays.binarySearch(wordList, word) >= 0;
    }

    private static int getJokersCountInLetters(char [] letters)
    {
        int jokerCount = 0;//Collections.frequency(Arrays.asList(letters), '*');
        for (char c : letters) {
            if (c == '*') {
                jokerCount ++;
            }
        }
        return jokerCount;
    }

    public static WordComposition mayBeComposed(String word, char[] letters)
    {
        word = word.toLowerCase();
        word = replaceFrenchCharacter(word);
        boolean[] isUsed = new boolean[letters.length];
        Arrays.fill(isUsed, false);

        int jokerCount = getJokersCountInLetters(letters);


        for(char c : word.toCharArray()) {
            for (int i = 0; i < letters.length; i++) {
                if (letters[i] == c && !isUsed[i]) {
                    isUsed[i] = true;
                    break;
                } else if(i == letters.length - 1) {
                    if (jokerCount > 0) {
                        jokerCount --;
                    } else {
                        return null;
                    }
                }
            }

        }
        return new WordComposition(word, letters, isUsed);
    }

    public List<WordComposition> getWordsThatCanBeComposed(char[] letters)
    {
        List<WordComposition> l = new LinkedList<WordComposition>();
        for(String s : wordList)
        {
            WordComposition wc = null;
            if ( (wc = mayBeComposed(s, letters)) != null)
            {
                l.add(wc);
            }
        }

        return l;
    }

    public static String replaceFrenchCharacter(String s)
    {
        StringBuilder newString = new StringBuilder(s);
        for(int i = 0 ; i < newString.length() ; i++)
        {
            if(newString.charAt(i) == 'à'
                    || newString.charAt(i) == 'â'
                    || newString.charAt(i) == 'ä')
            {
                newString.setCharAt(i, 'a');
            }
            else if(newString.charAt(i) == 'ç')
            {
                newString.setCharAt(i, 'c');
            }
            else if(newString.charAt(i) == 'é'
                    || newString.charAt(i) == 'è'
                    || newString.charAt(i) == 'ê'
                    || newString.charAt(i) == 'ë')
            {
                newString.setCharAt(i, 'e');
            }
            else if(newString.charAt(i) == 'ô'
                    || newString.charAt(i) == 'ö')
            {
                newString.setCharAt(i, 'o');
            }
            else if(newString.charAt(i) == 'ù'
                    || newString.charAt(i) == 'ü'
                    || newString.charAt(i) == 'û')
            {
                newString.setCharAt(i, 'u');
            }
            else if(newString.charAt(i) == 'œ')
            {
                newString.replace(i, i+1, "oe");
                i++;
            }
            else if(newString.charAt(i) == 'æ')
            {
                newString.replace(i, i+1, "ae");
                i++;
            }
        }

        return newString.toString();
    }

}
