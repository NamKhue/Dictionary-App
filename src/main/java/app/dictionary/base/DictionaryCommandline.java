package app.dictionary.base;

import java.util.Scanner;

public class DictionaryCommandline {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * showAllWords().
     */
    public static void showAllWords() {
        StringBuilder result = new StringBuilder();
        result.append("NO   | English        | Vietnamese").append("\n");

        int maxSpaceBetween = 14;

        for (int i = 0; i < Dictionary.getDictionary().size(); i++) {
            result.append(i + 1).append("    | ");

            result.append(Dictionary.getDictionary().get(i).getWord_target());
            int currentLength = Dictionary.getDictionary().get(i).getWord_target().length();
            for (int j = currentLength; j < maxSpaceBetween; j++) {
                result.append(" ");
            }
            result.append(" | ");

            result.append(Dictionary.getDictionary().get(i).getWord_explain());
            result.append("\n");
        }
        System.out.println(result);
    }

    /**
     * dictionaryBasic().
     */
    public static void dictionaryBasic() {
        DictionaryManagement.insertFromFile();
        showAllWords();
    }

    /**
     * dictionaryAdvanced().
     */
    public static void dictionaryAdvanced() {
        DictionaryManagement.insertFromFile();
        showMenu();
    }

    /**
     * showMenu().
     */
    public static void showMenu() {
        int choose;
        do {
            System.out.println("1. Show list of word.");
            System.out.println("2. Search word.");
            System.out.println("3. Add a new word.");
            System.out.println("4. Modify word.");
            System.out.println("5. Remove word.");
            System.out.println("6. Export file.");
            System.out.println("7. Exit.");
            System.out.println("Choose:");
            choose = Integer.parseInt(scanner.nextLine());

            switch (choose) {
                case 1:
                    DictionaryCommandline.showAllWords();
                    break;
                case 2:
//                    DictionaryManagement.dictionaryLookup();
                    DictionaryManagement.dictionarySearcher();
                    break;
                case 3:
                    DictionaryManagement.addNewWord();
                    break;
                case 4:
                    DictionaryManagement.modifyWord();
                    break;
                case 5:
                    DictionaryManagement.removeWord();
                    break;
                case 6:
                    DictionaryManagement.dictionaryExportToFile();
                    break;
                case 7:
                    System.out.println("Bye bye!");
                    break;
                default:
                    System.out.println("Please fill again the number of choice.");
                    break;
            }
            System.out.print("\n");
        } while (choose != 7);
    }
}