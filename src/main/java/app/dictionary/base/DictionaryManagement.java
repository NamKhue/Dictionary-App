package app.dictionary.base;

import java.util.Scanner;

public class DictionaryManagement {
    public static Scanner scanner = new Scanner(System.in);

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
                    t = false;
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
}

