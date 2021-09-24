package app.dictionary.base;

import java.util.ArrayList;

public class Dictionary {
    private static ArrayList<Word> dictionary = new ArrayList<>();

    /**
     * constructor.
     */
    public Dictionary(ArrayList<Word> dictionary_) {
        dictionary = dictionary_;
    }

    /**
     * public push.
     */
    public static void push(Word word) {
        dictionary.add(word);
    }

    /**
     * getDictionary().
     * @return dictionary
     */
    public static ArrayList<Word> getDictionary() {
        return dictionary;
    }
}
