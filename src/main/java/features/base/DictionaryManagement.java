package features.base;

import java.io.*;
import java.util.Collections;
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
      addWord(new Word(word_target, word_explain));
    }
  }
  
  public static void insertFromFile() {
    try {
      FileReader fileReader = new FileReader(new File("src/main/java/app/fileDictionary/dictionary.txt"));
      BufferedReader reader = new BufferedReader(fileReader);
      
      String line = null;
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
  
  public static void dictionaryLookup() {
    Scanner scanner_ = new Scanner(System.in);
    System.out.println("What is the word that you want to look up?");
    String wordLookUp = scanner_.nextLine();
    wordLookUp.trim().toLowerCase();
    Word hasWord = Dictionary.binarySearch(wordLookUp, 0, Dictionary.getSize() - 1);
    if (hasWord == null) {
      System.out.println("This word is not in the dictionary");
    } else {
      System.out.println(hasWord.getWord_target() + " means: " + hasWord.getWord_explain());
    }
  }
  
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
  
  public static void addWord(Word word) {
    int indexToAdd = Dictionary.searchIndexToInsert(0, Dictionary.getDictionary().size() - 1, word);
    if (indexToAdd >= 0 && indexToAdd <= Dictionary.getDictionary().size()) {
      Dictionary.getDictionary().add(indexToAdd, word);
    }
  }
  
  public static void removeWord(int position) {
    if (Dictionary.getDictionary().size() > 0) {
      Dictionary.getDictionary().remove(position);
    } else {
      System.out.println("The Dictionary is empty!");
    }
  }
  
  public static void fixWord(Word word,String new_target,String new_explain){
    int pos = Collections.binarySearch(Dictionary.getDictionary(),word);
    Dictionary.getDictionary().get(pos).setWord_target(new_target);
    Dictionary.getDictionary().get(pos).setWord_explain(new_explain);
  }
}