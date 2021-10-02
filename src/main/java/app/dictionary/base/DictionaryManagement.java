package app.dictionary.base;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DictionaryManagement {
    public static Scanner scanner = new Scanner(System.in);

    /**
     * dictionaryExportToFile().
     */
    public static void dictionaryExportToFile() {
        try {
            BufferedWriter writerFile = new BufferedWriter(new FileWriter("src/main/java/app/fileDictionary/dictionary.txt"));
            for (int i = 0; i < Dictionary.getDictionary().size(); i++) {
                writerFile.write(Dictionary.getDictionary().get(i).getWord_target().trim()+"\t"+Dictionary.getDictionary().get(i).getWord_explain().trim()+"\n");
            }
            writerFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * searchingWord().
     */
    public static void dictionarySearcher() {
        String searchMore;
        StringBuilder wordSearch = new StringBuilder();
        do {
            System.out.print("\nPlease fill in with the word you want to search: " + wordSearch);
            String partOfWordSearch = scanner.nextLine().toLowerCase();
            wordSearch.append(partOfWordSearch);

            List<Word> resultSearch = Dictionary.getDictionary().stream()
                    .filter(word -> word.getWord_target().contains(partOfWordSearch))
                    .collect(Collectors.toList());
            for (Word word : resultSearch) {
                System.out.println(word.getWord_target() + " " + word.getWord_explain());
            }

            System.out.print("\nWant to clear search(y/n) ? ");
            String clearSearch = scanner.nextLine();
            if (Objects.equals(clearSearch, "y")) {
                wordSearch = new StringBuilder();
            }

            System.out.print("\nWanna search more(y/n) ? ");
            searchMore = scanner.nextLine();
        } while(Objects.equals(searchMore, "y"));
    }

    /**
     * addNewWord().
     */
    public static void addNewWord() {
        String wantAddMore;
        do {
            System.out.println("Please fill in with your word you want to add:");
            String word_target = scanner.nextLine().toLowerCase();
            String word_explain = scanner.nextLine().toLowerCase();
            Word newWord = new Word(word_target, word_explain);
            int indexToAdd = Dictionary.searchIndexToInsert(0, Dictionary.getSize() - 1, newWord);
            if (indexToAdd >= 0 && indexToAdd <= Dictionary.getSize()) {
                Dictionary.getDictionary().add(indexToAdd, newWord);
            }
            System.out.print("\nDo you want to add more(y/n) ? ");
            wantAddMore = scanner.nextLine();
            System.out.print("\n");
        } while(Objects.equals(wantAddMore, "y"));
        DictionaryManagement.dictionaryExportToFile();
        System.out.println("Successfully added your word!");
    }

    /**
     * returnPosOfWord().
     */
    // cach 1
    public static int returnPosOfWord(String word_target) {
        return Collections.binarySearch(Dictionary.getDictionary(), new Word(word_target, ""));
    }

    /**
     * removeWord().
     */
    public static void removeWord() {
        System.out.println("Please fill in with the word you want to remove:");
        String word_need_to_remove = scanner.nextLine().toLowerCase();
        int pos = returnPosOfWord(word_need_to_remove);
        if (pos >= 0) {
            Dictionary.getDictionary().remove((Dictionary.getDictionary().get(pos)));
            System.out.println("Successfully removed word!");
            DictionaryManagement.dictionaryExportToFile();
        } else {
            System.out.println("Can't find your word!");
        }
    }

    /**
     * modifyWord().
     */
    public static void modifyWord() {
        System.out.println("Please fill in with the word you want to modify:");
        String word_need_to_edit = scanner.nextLine().toLowerCase();
        int pos = returnPosOfWord(word_need_to_edit);
        if (pos >= 0) {
            System.out.println("Please fill in with the meaning of word: ");
            String modify_word_meaning = scanner.nextLine().toLowerCase();
            Dictionary.getDictionary().get(pos).setWord_explain(modify_word_meaning);

            System.out.println("Do you wanna change English word(y/n) ? ");
            String wannaChange = scanner.nextLine().toLowerCase();
            if (Objects.equals(wannaChange, "y")) {
                System.out.println("Please fill in with your English word: ");
                String modify_word_target = scanner.nextLine().toLowerCase();
                Dictionary.getDictionary().get(pos).setWord_target(modify_word_target);
            }
            System.out.println("Successfully modified word!");
            DictionaryManagement.dictionaryExportToFile();
        } else {
            System.out.println("Can't find your word!");
        }
    }

    /**
     * insertFromCommandLine().
     */
    public static void insertFromCommandLine() {
        boolean t = false;
        int numberOfElem = 0;
        //To enter the number of elements
        do {
            System.out.println("Enter the number of words you want to add: ");
            if (scanner.hasNextInt()) {
                numberOfElem = scanner.nextInt();
                if (numberOfElem > 0) {
                    t = true;
                } else {
                    System.out.println("Input error, re-enter: ");
                }
            } else {
                System.out.println("Input error, re-enter: ");
                scanner.nextLine();
            }
        } while (!t);
        scanner.nextLine();
        for (int i = 0; i < numberOfElem; i++) {
            System.out.println((i + 1) + ": ");
            System.out.println("In English: ");
            String word_target = scanner.nextLine();
            System.out.println("In Vietnamese: ");
            String word_explain = scanner.nextLine();
            Dictionary.push(new Word(word_target, word_explain));
        }
    }

    /**
     * insertFromFile().
     */
    public static void insertFromFile() {
        try {
            FileReader fileReader = new FileReader("src/main/java/app/fileDictionary/dictionary.txt");
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase();
                String[] separated_line = line.split("\t");
                Dictionary.getDictionary().add(new Word(separated_line[0], separated_line[1]));
            }
            Dictionary.sortDir();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * dictionaryLookup().
     */
    public static void dictionaryLookup() {
        Scanner scanner_ = new Scanner(System.in);
        System.out.println("What is the word that you want to look up?");
        String wordLookUp = scanner_.nextLine();
        wordLookUp.trim().toLowerCase();
        Dictionary usingForBinarySearch = new Dictionary();
        Word hasWord = usingForBinarySearch.binarySearch(wordLookUp, 0, Dictionary.getSize() - 1);
        if (hasWord == null) {
            System.out.println("This word is not in the dictionary");
        } else {
            System.out.println(hasWord.getWord_target() + " means: " + hasWord.getWord_explain());
        }
    }
}