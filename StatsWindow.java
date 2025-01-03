import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Creates a StatsWindow and stores all of the data for each word.
 * @author Katie Engel
 * @version 1.0
 * */
public class StatsWindow {
    private List<Stat> stats;
    private final List<String> words;

    /**
     * Constructor for StatsWindow that stores a list of stats and a list of the corresponding words.
     * */
    public StatsWindow() {
        stats = getStats();
        words = getWords();
    }
    /**
     * Displays the statistics for this word.
     * @param target the target word
     * @param primaryStage the primary stage for the Jordle application
     * */
    public void displayStats(String target, Stage primaryStage) {
        Stat stat = stats.get(words.indexOf(target.toLowerCase()));

        BorderPane statsPane = new BorderPane();
        statsPane.setPrefSize(750, 500);

        Label statsTitle = new Label("Statistics");
        statsTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 40));

        Label targetWordLbl = new Label(String.format("Target Word: %s", words.get(stat.getIndex())));
        targetWordLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        VBox topBox = new VBox(statsTitle, targetWordLbl);
        topBox.setSpacing(10);
        topBox.setAlignment(Pos.CENTER);


        Label percentOfNumCorrect = new Label(String.format("This word was guessed correctly %.1f%% of the time",
                                                            stat.percentCorrect()));
        percentOfNumCorrect.setFont(Font.font("Verdana", 15));
        Label avgNumGuessesLbl = new Label(String.format("Average number of guesses for this word: %.1f",
                                                            stat.avgGuesses()));
        avgNumGuessesLbl.setFont(Font.font("Verdana", 15));

        VBox vbox = new VBox(percentOfNumCorrect, avgNumGuessesLbl);
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.CENTER);

        statsPane.setTop(topBox);
        statsPane.setCenter(vbox);

        Scene statsScreen = new Scene(statsPane, 750, 500);
        primaryStage.setScene(statsScreen);
        primaryStage.show();
    }
    /**
     * Updates the statistics for the target word.
     * @param targetWord the target word
     * @param correct if the word was guessed correctly
     * @param guesses the numbers of guesses it took to guess the word
     * */
    public void setStat(String targetWord, boolean correct, int guesses) {
        int index = words.indexOf(targetWord.toLowerCase());
        Stat stat = stats.get(index);
        if (correct) {
            stat.incNumCorrect();
        }
        stat.addNumGuessesToCorrect(guesses);
        stat.incNumTimes();

        stats.set(index, stat);

        storeStats();
    }
    /**
     * Stores the new statistics.
     * */
    private void storeStats() {
        try {
            FileWriter writer = new FileWriter("stats.txt");
            PrintWriter printWriter = new PrintWriter(writer);
            for (int i = 0; i < stats.size() - 1; i++) {
                printWriter.println(stats.get(i));
            }
            printWriter.print(stats.get(stats.size() - 1));
            printWriter.close();
        } catch (IOException ioe) {
            System.out.println("Error in writing to stats.txt: " + ioe.getMessage());
        }
    }
    /**
     * Gets the stats from the file stats.txt.
     * @return a list of statistics from stats.txt
     * */
    private List<Stat> getStats() {
        List<Stat> lines = new ArrayList<>();
        File file = new File("stats.txt");
        Scanner scan = null;
        int index = 0;
        try {
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                lines.add(new Stat(scan.nextInt(), scan.nextInt(), scan.nextInt(), index));
                index++;
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error in reading stats.txt: " + fnfe.getMessage());
        } finally {
            if (scan != null) {
                scan.close();
            }
        }
        return lines;
    }
    /**
     * Copied from Backend.java such that this class could have access to the list of words.
     * Reads in the words from the words.txt word bank.
     *
     * @return a list of five letter words as strings, or a list only containing "adieu" if
     *         words.txt could not be found.
     *
     * */
    private List<String> getWords() {
        List<String> lines = new ArrayList<>();
        File file = new File("words.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.length() == 5) {
                    lines.add(line.toLowerCase());
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error in reading words.txt: " + fnfe.getMessage());
            lines.add("adieu");
        } finally {
            if (scan != null) {
                scan.close();
            }
        }
        return lines;
    }
}