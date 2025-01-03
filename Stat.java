/**
 * Creates a Stat object.
 * @author Katie Engel
 * @version 1.0
 * */
public class Stat {
    private int numCorrect;
    private int numGuessesToCorrect;
    private int numTimes;
    private int index;

    /**
     * Constructor for a Stat object that takes in the number of times it was guessed correctly, the number
     * of guesses it took, the number of times it has appeared, and the index of the word associated with this data.
     * @param numCorrect the number of times the word was guessed correctly
     * @param numGuessesToCorrect the number of guesses it took to guess to the word
     * @param numTimes the number of times the word has appeared
     * @param index the index of the word that is associated with this data
     * */
    public Stat(int numCorrect, int numGuessesToCorrect, int numTimes, int index) {
        this.numCorrect = numCorrect;
        this.numGuessesToCorrect = numGuessesToCorrect;
        this.numTimes = numTimes;
        this.index = index;
    }
    /**
     * Gets the number of times the word was guessed correctly.
     * @return the number of times the word was guessed correctly
     * */
    public int getNumCorrect() {
        return numCorrect;
    }
    /**
     * Gets the number of guesses it took to guess the word.
     * @return the number of guesses it took to guess the word
     * */
    public int getNumGuessesToCorrect() {
        return numGuessesToCorrect;
    }
    /**
     * Gets the number of times this word has appeared.
     * @return the number of times this word has appeared
     * */
    public int getNumTimes() {
        return numTimes;
    }
    /**
     * Gets the index of the word associated with this data.
     * @return the index of the word associated with this data
     * */
    public int getIndex() {
        return index;
    }
    /**
     * Increments the number of times the word was guessed correctly.
     * */
    public void incNumCorrect() {
        numCorrect++;
    }
    /**
     * Adds the number of guesses it took to guess this word to the total number of guesses for this word.
     * @param guesses the number of guesses it took to guess this word
     * */
    public void addNumGuessesToCorrect(int guesses) {
        numGuessesToCorrect += guesses;
    }
    /**
     * Increments the number of times the word has appeared.
     * */
    public void incNumTimes() {
        numTimes++;
    }
    /**
     * Calculates the average number of guesses it has taken to guess this word.
     * @return the average number of guesses it has taken to guess this word
     * */
    public double avgGuesses() {
        if (numTimes == 0) {
            return 0;
        }
        return ((double) numGuessesToCorrect) / numTimes;
    }
    /**
     * Calculates the percent of the time this word has been guessed correctly.
     * @return the percent of the time this word has been guessed correctly
     * */
    public double percentCorrect() {
        if (numTimes == 0) {
            return 0;
        }
        return ((double) numCorrect) / numTimes * 100;
    }
    /**
     * Returns a string representing the data in this Stat object.
     * @return a string in the following format:<pre>
     * "{numCorrect} {numGuessesToCorrect} {numTimes}"</pre>
     * */
    public String toString() {
        return String.format("%d %d %d", numCorrect, numGuessesToCorrect, numTimes);
    }
}