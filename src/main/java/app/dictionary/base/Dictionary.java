package app.dictionary.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Dictionary {
    private static ArrayList<Word> dictionary = new ArrayList<>();



    public Dictionary(ArrayList<Word> dictionary_) {
        dictionary = dictionary_;
    }

    public Dictionary() {

    }

    public static int getIndexOfWord(Word word){
        return dictionary.indexOf(word);
    }

    public static int getSize() {
        return dictionary.size();
    }

    public static ArrayList<Word> getDictionary() {
        return dictionary;
    }

    public static void sortDir() {
        Collections.sort(dictionary, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return o1.getWord_target().compareTo(o2.getWord_target());
            }
        });
    }

    //The function is used to compare and
    // find the appropriate position to insert words in sorted order
    public static int searchIndexToInsert(int st, int end, Word word_add) {
        if (end < st) return st;
        int mid = st + (end - st) / 2;
        if (mid == dictionary.size()) return mid;
        Word word = dictionary.get(mid);
        int compare = word.compareTo(word_add);
        if (compare == 0) return -1;//If -1, this word already exists
        if (compare > 0) return searchIndexToInsert(st, mid - 1, word_add);
        return searchIndexToInsert(mid + 1, end, word_add);
    }

    public static Word binarySearch(String wordToSearch, int l, int r) {
        if (r < l) return null;
        int mid = l + (r - l) / 2;
        Word word = dictionary.get(mid);
        String current = word.getWord_target();

        int compare = current.compareTo(wordToSearch);
        if (compare == 0) return word;
        if (compare > 0) return binarySearch(wordToSearch, l, mid - 1);
        return binarySearch(wordToSearch, mid + 1, r);
    }

    public static Word binarySearchSubword(String subWord, int l, int r) {
        if (r < l) return null;
        int mid = l + (r - l) / 2;
        Word word = dictionary.get(mid);
        String current = word.getWord_target();
        if(current.length()>=subWord.length()){
            current=word.getWord_target().substring(0,subWord.length());
        }
        int compare = current.compareTo(subWord);
        if (compare == 0) return word;
        if (compare > 0) return binarySearchSubword(subWord, l, mid - 1);
        return binarySearchSubword(subWord, mid + 1, r);
    }
}
