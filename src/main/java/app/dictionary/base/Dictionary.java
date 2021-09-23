package app.dictionary.base;

import java.util.ArrayList;

public class Dictionary {
    private static ArrayList<Word> dictionary = new ArrayList<>();

    public Dictionary(ArrayList<Word> dictionary_) {
        dictionary = dictionary_;
    }

    public static void push(Word word) {
        dictionary.add(word);
    }

    public static ArrayList<Word> getDictionary() {
        return dictionary;
    }
}
