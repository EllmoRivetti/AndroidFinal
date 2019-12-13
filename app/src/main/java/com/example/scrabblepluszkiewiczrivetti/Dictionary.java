package com.example.scrabblepluszkiewiczrivetti;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Dictionary {

    private String[] wordList;

    public Dictionary(final String filename) throws FileNotFoundException
    {
        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                try
                {
                    File file = new File(filename);

                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        /*if(line.matches("-?\\d+(\\.\\d+)?"))
                        {
                            this.wordList
                        }
                        else
                        {

                        }
                        wordList*/
                    }
                    br.close();
                }
                catch(IOException e)
                {
                    //Something wrong happened
                }


            }
        });

        t.start();
    }

    public boolean isValidWord(String word)
    {

        return false;
    }

    public static boolean mayBeComposed(String word, char[] letters)
    {
        boolean[] isUsed = new boolean[letters.length];
        Arrays.fill(isUsed, false);

        for(char c : word.toCharArray()) {
            for (int i = 0; i < letters.length; i++) {
                if (letters[i] == c && !isUsed[i]) {
                    isUsed[i] = true;
                    break;
                } else if(i == letters.length){
                    return false;
                }

            }

        }
        return true;
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
                s.replace() = 'a';
            }
        }

        return s;
    }
}
