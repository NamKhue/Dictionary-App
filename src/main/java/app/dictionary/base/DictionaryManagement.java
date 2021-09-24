package app.dictionary.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;
import java.util.Scanner;

public class DictionaryManagement {
    /**
     * insertFromFile().
     */
    public static void insertFromFile() {
        try {
            File inFile = new File("src/main/java/data/dictionaries.txt");
            FileReader fileReader = new FileReader(inFile);
            BufferedReader reader = new BufferedReader(fileReader);

            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] word = line.split("\\s\\s+");
                String word_target = word[0].trim();
                String word_explain = word[1].trim();
                Dictionary.push(new Word(word_target, word_explain));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * dictionaryLookup().
     */
    public static void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        String wantSearchMore = "y";

        while (Objects.equals(wantSearchMore, "y")) {
            System.out.println("Please fill in with a word: ");
            String wordSearch = scanner.nextLine();

            StringBuilder result = new StringBuilder();
            int check = 0;
            int countWordSearch = 0;

            for (int i = 0; i < Dictionary.getDictionary().size(); i++) {
                String wordTarget = Dictionary.getDictionary().get(i).getWord_target();
                if (wordTarget.contains(wordSearch)) {
                    if (check == 0) {
                        result.append("Found following word(s):\n\n");
                        result.append("NO   | English        | Vietnamese").append("\n");
                    }
                    result.append(countWordSearch + 1).append("    | ");
                    result.append(Dictionary.getDictionary().get(i).getWord_target());

                    int currentLength = Dictionary.getDictionary().get(i).getWord_target().length();
                    int maxSpaceBetween = 14;

                    for (int j = currentLength; j < maxSpaceBetween; j++) {
                        result.append(" ");
                    }
                    result.append(" | ");

                    result.append(Dictionary.getDictionary().get(i).getWord_explain());
                    result.append("\n");
                    ++check;
                    ++countWordSearch;
                }
            }
            if (check < 1) {
                System.out.println("Can't find your word!");
            } else {
                System.out.println(result);
            }

            System.out.print("Do you want to search another word (y/n) ?  ");
            wantSearchMore = scanner.nextLine();
        }
    }
}

