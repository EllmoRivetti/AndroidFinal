package com.example.scrabblepluszkiewiczrivetti;

import androidx.annotation.NonNull;

public class WordComposition {

    private int score;
    private String word;
    private char[] letters;
    private boolean[] used;

    public WordComposition(String word, char[] letters, boolean[] used)
    {
        this.letters = letters;
        this.word = word;
        this.score = 0;
        this.used = used;

        for (int i = 0; i < letters.length; ++i)
        {
            if (used[i])
            {
                int letterScore = Data.get().getLetterScore(letters[i]);
                score += letterScore;
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        String s = " - " + word + " ([";
        for(int i = 0; i < letters.length; ++i)
        {
            if (used[i]) {
                if (i < 0) s += " ";
                s += letters[i];
                if (i < letters.length - 1) s += ",";
            }
        }
        s+= "] " + score + ")\n";
        return s;
    }

    public int getScore()
    {
        return this.score;
    }

    public String getWord()
    {
        return this.word;
    }
}
