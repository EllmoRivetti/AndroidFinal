package com.example.scrabblepluszkiewiczrivetti;

import java.util.Comparator;

public class WordCompositionComparator implements Comparator<WordComposition> {
    @Override
    public int compare(WordComposition wordComposition, WordComposition t1) {
        int s = Integer.compare(t1.getScore(), wordComposition.getScore());
        if (s != 0) return s;
        else return wordComposition.getWord().compareTo(t1.getWord());
    }
}
