package features.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class DictionaryCommandline {
  
  // Function is used to show all words in dictionary.
  public static void showAllWords() {
    StringBuilder result = new StringBuilder();
    result.append("NO   | English        | Vietnamese").append("\n");
    
    int maxSpaceBetween = 20;
    
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
  
  // Function is used to find a word includes this sub word.
  public static void dictionarySearcher() {
    Scanner scan = new Scanner(System.in);
    ArrayList<Word> hintArray = new ArrayList<Word>();
    System.out.println("Which is subWord u wanna search in dictionary?");
    String subWord = scan.nextLine();
    if (Dictionary.binarySearchSubword(subWord, 0, Dictionary.getDictionary().size() - 1) == null) {
      System.out.println("NOTHING you wanna search!");
    } else {
      int posHint = Dictionary.getIndexOfWord(Dictionary.binarySearchSubword(subWord, 0, Dictionary.getDictionary().size()));
      System.out.println("--- List related Word ---");
      System.out.println(Dictionary.getDictionary().get(posHint).getWord_target());
      int limitedRelatedWords = 6;
      int left =1;
      int right = 1;
      while((posHint-left)>0&&Dictionary.getDictionary().get(posHint-left).getWord_target().length()>=subWord.length()&&limitedRelatedWords>0&&(Dictionary.getDictionary().get(posHint-left).getWord_target()).substring(0,subWord.length()).compareTo(subWord)==0){
        System.out.println(Dictionary.getDictionary().get(posHint-left).getWord_target());
        limitedRelatedWords--;
        left++;
      }
      while((posHint+right)<Dictionary.getDictionary().size()&&Dictionary.getDictionary().get(posHint+right).getWord_target().length()>=subWord.length()&&limitedRelatedWords>0&&(Dictionary.getDictionary().get(posHint+right).getWord_target()).substring(0,subWord.length()).compareTo(subWord)==0){
        System.out.println(Dictionary.getDictionary().get(posHint+right).getWord_target());
        limitedRelatedWords--;
        right++;
      }
    }
  }
  
  public static void dictionaryBasic() {
    DictionaryManagement.insertFromFile();
    showAllWords();
  }
  
  private static void showMenu() {
    DictionaryManagement.insertFromFile();
    DictionaryManagement.dictionaryExportToFile();
  
    Scanner input = new Scanner(System.in);
    int choice;
    
    do {
      System.out.println("=============== Menu ===============");
      System.out.println("1. Show list of words.");
      System.out.println("2. Search by a part of word.");
      System.out.println("3. Search a whole word.");
      System.out.println("4. Add a new word.");
      System.out.println("5. Modify a word.");
      System.out.println("6. Delete a word.");
      System.out.println("7. Exit.");
      System.out.print("Please choose from 1 to 7:  ");
      
      choice = Integer.parseInt(input.nextLine());
      System.out.print("\n");
      System.out.println("=============== Executing ===============");
      System.out.print("\n");
  
      String word_target = "", word_explain = "";
      Word hasWord;
  
      switch (choice) {
        case 1:
          DictionaryCommandline.showAllWords();
          break;
        case 2:
          dictionarySearcher();
          break;
        case 3:
          DictionaryManagement.dictionaryLookup();
          break;
        case 4:
          System.out.println("Which word do you wanna add?");
          System.out.print("Word:  ");
          word_target = input.nextLine();
          word_target = word_target.trim().toLowerCase();
          System.out.print("\n");
  
          hasWord = Dictionary.binarySearch(word_target, 0, Dictionary.getSize() - 1);
          if (hasWord == null) {
            System.out.print("Meaning:  ");
            word_explain = input.nextLine();
            word_explain = word_explain.trim().toLowerCase();
            System.out.print("\n");
            
            DictionaryManagement.addWord(new Word(word_target, word_explain));
            System.out.println("Added word successfully!");
          } else {
            System.out.println("This word is existed before!");
          }
  
          break;
        case 5:
          System.out.println("Which word do you wanna edit?");
          System.out.print("Word:  ");
          word_target = input.nextLine();
          word_target = word_target.trim().toLowerCase();
          System.out.print("\n");
  
          hasWord = Dictionary.binarySearch(word_target, 0, Dictionary.getSize() - 1);
          if (hasWord == null) {
            System.out.println("This word isn't existed!");
          } else {
            System.out.print("New word:  ");
            String new_word_target = input.nextLine();
            new_word_target = new_word_target.trim().toLowerCase();
            System.out.print("\n");
            System.out.print("Meaning:  ");
            String new_word_explain = input.nextLine();
            new_word_explain = new_word_explain.trim().toLowerCase();
            System.out.print("\n");
            
            DictionaryManagement.fixWord(hasWord, new_word_target, new_word_explain);
            System.out.println("Modified word successfully!");
          }
          
          break;
        case 6:
          System.out.println("Which word do you wanna delete?");
          System.out.print("Word:  ");
          word_target = input.nextLine().trim().toLowerCase();
          System.out.print("\n");
  
          hasWord = Dictionary.binarySearch(word_target, 0, Dictionary.getSize() - 1);
          if (hasWord == null) {
            System.out.println("This word isn't existed!");
          } else {
            Word word = Dictionary.binarySearch(word_target, 0, Dictionary.getSize()-1);

//            Dictionary.getDictionary().remove(pos);
//            System.out.println("Deleted word successfully!");
  
            System.out.println(word);
          }
    
          break;
        default:
          System.out.println("Bye bye !");
          break;
      }
      System.out.print("\n");
    } while (choice < 7);
  }
  
  public static void dictionaryAdvanced() {
    showMenu();
  }
}