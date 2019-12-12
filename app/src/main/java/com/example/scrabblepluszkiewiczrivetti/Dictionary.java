package com.example.scrabblepluszkiewiczrivetti;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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


}
