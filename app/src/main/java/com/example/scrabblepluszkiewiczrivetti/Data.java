package com.example.scrabblepluszkiewiczrivetti;

import java.util.HashMap;

public final class Data {

    private Data instance = null;
    private HashMap<Character, Integer> scorePerCharacter;

    private Data()
    {
        scorePerCharacter = new HashMap<>();
        scorePerCharacter.put(Character.valueOf('*'), 0);
        scorePerCharacter.put(Character.valueOf('a'), 1);
        scorePerCharacter.put(Character.valueOf('b'), 3);
        scorePerCharacter.put(Character.valueOf('c'), 3);
        scorePerCharacter.put(Character.valueOf('d'), 2);
        scorePerCharacter.put(Character.valueOf('e'), 1);
        scorePerCharacter.put(Character.valueOf('f'), 4);
        scorePerCharacter.put(Character.valueOf('g'), 2);
        scorePerCharacter.put(Character.valueOf('h'), 4);
        scorePerCharacter.put(Character.valueOf('i'), 1);
        scorePerCharacter.put(Character.valueOf('j'), 8);
        scorePerCharacter.put(Character.valueOf('k'), 5);
        scorePerCharacter.put(Character.valueOf('l'), 1);
        scorePerCharacter.put(Character.valueOf('m'), 3);
        scorePerCharacter.put(Character.valueOf('n'), 1);
        scorePerCharacter.put(Character.valueOf('o'), 1);
        scorePerCharacter.put(Character.valueOf('p'), 3);
        scorePerCharacter.put(Character.valueOf('q'), 10);
        scorePerCharacter.put(Character.valueOf('r'), 1);
        scorePerCharacter.put(Character.valueOf('s'), 1);
        scorePerCharacter.put(Character.valueOf('t'), 1);
        scorePerCharacter.put(Character.valueOf('u'), 1);
        scorePerCharacter.put(Character.valueOf('v'), 4);
        scorePerCharacter.put(Character.valueOf('w'), 4);
        scorePerCharacter.put(Character.valueOf('x'), 8);
        scorePerCharacter.put(Character.valueOf('y'), 4);
        scorePerCharacter.put(Character.valueOf('z'), 10);
    }

    public Data get()
    {
        if  (instance == null)
        {
            instance = new Data();
        }
        return instance;
    }

}
