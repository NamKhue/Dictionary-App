package app.dictionary.base;

public class DictionaryCommandline {
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

    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandLine();
        showAllWords();
    }
}
