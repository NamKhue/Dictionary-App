package features.advance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class DictionaryV2 {
    private static DictionaryV2 instance = new DictionaryV2();
    private static String filename = "src/main/resources/data/ListWord.txt";

    private static ObservableList<Word> dictionary;

    public ObservableList<Word> getWordsList() {
        return dictionary;
    }

    public static DictionaryV2 getInstance() {
        return instance;
    }

    public static int getSize() {
        return dictionary.size();
    }

    /**
     *
     * @param st is index of first element.
     * @param end is index of last element.
     * @param word_add is the word user want to insert into Dictionary.
     * @return best position.
     */
    private static int searchIndexToInsert(int st, int end, Word word_add) {
        if (end < st) return st;
        int mid = st + (end - st) / 2;
        if (mid == dictionary.size()) return mid;
        Word word = dictionary.get(mid);
        int compare = word.compareTo(word_add);
        if (compare == 0) return -1; // If -1, this word already exists
        if (compare > 0) return searchIndexToInsert(st, mid - 1, word_add);
        return searchIndexToInsert(mid + 1, end, word_add);
    }

    public static void sortDir() {
        Collections.sort(
                dictionary,
                new Comparator<Word>() {
                    @Override
                    public int compare(Word o1, Word o2) {
                        return o1.getWord_target().compareTo(o2.getWord_target());
                    }
                });
    }

    /**
     *
     * @param wordToSearch .
     * @param l is index of first element.
     * @param r is index of last element.
     * @return the word user want ot search.
     */
    public Word binarySearch(String wordToSearch, int l, int r) {
        if (r < l) return null;
        int mid = l + (r - l) / 2;
        Word word = dictionary.get(mid);
        String current = word.getWord_target();
        int compare = current.compareTo(wordToSearch);
        if (compare == 0) return word;
        if (compare > 0) return binarySearch(wordToSearch, l, mid - 1);
        return binarySearch(wordToSearch, mid + 1, r);
    }

    /**
     * @param wordToSearch .
     * @param l is index of first element.
     * @param r is index of last element.
     * @return the position of word user want to look up.
     */
    public int binaryLookUp(String wordToSearch, int l, int r) {
        if (r < l) return -1;
        int mid = l + (r - l) / 2;
        Word word = dictionary.get(mid);
        String current = word.getWord_target();
        int compare = current.compareTo(wordToSearch);
        if (compare == 0) return mid;
        if (compare > 0) return binaryLookUp(wordToSearch, l, mid - 1);
        return binaryLookUp(wordToSearch, mid + 1, r);
    }

    public ArrayList<String> getWordTarget() {
        ArrayList<String> listWordTarget = new ArrayList<>();
        for (int i = 0; i < Dictionary.getSize(); i++) {
            listWordTarget.add(dictionary.get(i).getWord_target());
        }
        return listWordTarget;
    }

    //While program is running, Diction will be loaded at the same time.
    public void LoadDictionary() throws IOException {
        dictionary = FXCollections.observableArrayList();
        File inFile = new File("src/main/resources/data/anhviet109K.txt");
        FileReader fileReader = new FileReader(inFile);
        FileInputStream fileInputStream = new FileInputStream(inFile);
        Scanner scanner = new Scanner(fileInputStream);

        int t = 0;
        StringBuffer b = new StringBuffer();
        String wordTarget = new String();
        String wordExplain = new String();
        while (scanner.hasNextLine()) {
            String a = scanner.nextLine();
            if (t == 0) {
                String[] list = a.split(" ");
                wordTarget = list[0];
                t++;
            } else if (!a.isEmpty()) {
                b.append(a);
                b.append("\n");

            } else if (a.isEmpty()) {
                wordExplain = b.toString();
                b = new StringBuffer();
                t = 0;
                dictionary.add(new Word(wordTarget, wordExplain));
                wordExplain = new String();
                wordTarget = new String();
            }
        }
    }

    public static ObservableList<Word> getDictionary() {
        return dictionary;
    }

    // Data storage from Dictionary to file text.
    public void storeTodoItems() throws IOException {

        //    Path path = Paths.get(filename);
        //    BufferedWriter bw = Files.newBufferedWriter(path);
        //    try {
        //      Iterator<Word> iter = dictionary.iterator();
        //      while (iter.hasNext()) {
        //        Word word = iter.next();
        //        bw.write(String.format("%s\t%s", word.getWord_target(), word.getWord_explain()));
        //        bw.newLine();
        //      }
        //    } finally {
        //      if (bw != null) {
        //        bw.close();
        //      }
        //    }
        Path path = Paths.get("src/main/resources/data/anhviet109K.txt");
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            for (int i = 0; i < dictionary.size(); i++) {
                bw.write(dictionary.get(i).getWord_target());
                bw.newLine();
                bw.write(dictionary.get(i).getWord_explain());
                bw.newLine();
                bw.newLine();
            }

        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    /**
     * @param word is the word user want to insert.
     */
    public static void push(Word word) {
        int indexToAdd = searchIndexToInsert(0, dictionary.size() - 1, word);
        if (indexToAdd >= 0 && indexToAdd <= dictionary.size()) {
            dictionary.add(indexToAdd, word);
        }
    }
}