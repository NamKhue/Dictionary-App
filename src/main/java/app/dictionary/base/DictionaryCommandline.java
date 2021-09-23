package app.dictionary.base;

public class DictionaryCommandline {
    public static void showAllWords() {
        String listWords = "No" + "\t" + "|English" + "\t\t" + "|Vietnamese" + "\n";
        for (int i = 0; i < Dictionary.getDictionary().size(); i++) {
            listWords += (i + 1) + "\t| " + Dictionary.getDictionary().get(i).getWord_target()
                    + "\t\t\t" + "| " + Dictionary.getDictionary().get(i).getWord_explain() + "\n";
        }
        System.out.println(listWords);
    }

    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandLine();
        showAllWords();
    }
}
