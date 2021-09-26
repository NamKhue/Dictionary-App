package app.dictionary.base;

public class Word implements Comparable<Word> {

    private String word_target;
    private String word_explain;

    /**
     * constructor 1.
     */
    public Word(String word_target, String word_explain) {
        this.word_target = word_target.trim().toLowerCase();
        this.word_explain = word_explain.trim().toLowerCase();
    }

    /**
     * constructor 2.
     */
    public Word() {
        this.word_explain = "";
        this.word_target = "";
    }

    /**
     * getWord_target().
     */
    public String getWord_target() {
        return word_target;
    }

    /**
     * getWord_explain().
     */
    public String getWord_explain() {
        return word_explain;
    }

    /**
     * setWord_target().
     */
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    /**
     * setWord_explain().
     */
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    /**
     * toString().
     */
    @Override
    public String toString() {
        return " " + this.word_target + " |= " + this.word_explain;
    }

    @Override
    public int compareTo(Word o) {
        return this.word_target.compareTo(o.getWord_target());
    }
}

