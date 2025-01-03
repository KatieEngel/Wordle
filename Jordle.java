import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Creates a Jordle game.
 * @author Katie Engel
 * @version 1.0
 * */
public class Jordle extends Application { 
    private Scene welcomeScene, gameScene;
    private int guesses;
    private boolean gameOver;
    private boolean darkMode;

    /**
     * Main method that launches Jordle.
     * @param args the arguments of the main method
     * */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Starts the application.
     * @param primaryStage the primary stage of this application
     * */
    @Override
    public void start(Stage primaryStage) {
        welcomeWindow(primaryStage);
        darkMode = false;
        startGame(primaryStage);
        primaryStage.setTitle("Jordle");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    /**
     * Creates the welcome window.
     * @param primaryStage the primary stage of this application
     * */
    private void welcomeWindow(Stage primaryStage) {
        //CREATE BORDERPANE
        BorderPane welcomeScreen = new BorderPane();
        welcomeScreen.setPrefSize(750.0, 450.0);
        Group root = new Group(welcomeScreen);

        //CREATE LABEL AND BUTTON
        Label titleLbl = new Label("Jordle");
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 100));
        titleLbl.setTextFill(Color.valueOf("#333333"));
        Button playBtn = new Button("Play");
        playBtn.setFont(Font.font("Verdana", 20));
        playBtn.setOnAction(e -> primaryStage.setScene(gameScene));
        //ADD LABEL AND BUTTON TO VBOX
        VBox centerBox = new VBox(titleLbl, playBtn);
        centerBox.setSpacing(20);
        centerBox.setAlignment(Pos.CENTER); //center the elements in the vbox

        //ADD VBOX TO THE WELCOME SCREEN IN THE CENTER
        welcomeScreen.setCenter(centerBox);

        //BACKGROUND IMAGE
        Image image = new Image("wordle_big_animation2.gif");
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

        welcomeScreen.setBackground(new Background(new BackgroundImage(image,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            bSize)));

        welcomeScene = new Scene(root, 750, 450);
    }

    /**
     * Creates the game window.
     * @param primaryStage the primary stage of this application
     * */
    private void startGame(Stage primaryStage) {
        BorderPane gameScreen = new BorderPane();
        gameScreen.setStyle("-fx-background-color: " + (darkMode ? "#404040;" : " #e6e6e6;"));
        gameScreen.setPrefSize(750, 500);
        Label jordleLbl = new Label("Jordle");
        jordleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        jordleLbl.setStyle("-fx-text-fill: " + (darkMode ? "#ffffff;" : "#404040;"));
        GridPane grid = new GridPane();
        createGrid(grid);
        ObservableList<Node> txtBoxes = grid.getChildren();
        Button restartBtn = new Button("Restart");
        restartBtn.setFont(Font.font("Verdana", 12));
        restartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startGame(primaryStage);
                primaryStage.setScene(gameScene);
                primaryStage.show();
            }
        });
        Button instructionsBtn = new Button("Instructions");
        instructionsBtn.setFont(Font.font("Verdana", 12));
        instructionsBtn.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Displays the instructions window when the button is clicked.
             * @param e the event occuring at this buttong (a button press)
             * */
            @Override
            public void handle(ActionEvent e) {
                StackPane instructionsScreen = new StackPane();
                instructionsScreen.setPrefSize(600, 300);
                Label heading = new Label("How to play Jordle");
                heading.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
                TextArea instructions = new TextArea(getInstructionsText());
                instructions.setFont(Font.font("Verdana", 12));
                instructions.setWrapText(true);
                instructions.setMouseTransparent(true);
                instructions.setFocusTraversable(false);
                VBox vBox = new VBox(heading, instructions);
                vBox.setSpacing(10.0);
                instructionsScreen.getChildren().addAll(vBox);
                Scene instructionsScene = new Scene(instructionsScreen, 600, 300);
                Stage instructionsStage = new Stage();
                instructionsStage.setTitle("Instructions");
                instructionsStage.setScene(instructionsScene);
                instructionsStage.show();
                grid.requestFocus();
            }
        });
        Label outputLbl = new Label("Try guessing a word!");
        outputLbl.setFont(Font.font("Verdana", 12));
        outputLbl.setStyle("-fx-text-fill: " + (darkMode ? "#ffffff;" : "#404040;"));
        HBox hbox = new HBox(outputLbl, restartBtn, instructionsBtn);
        hbox.setSpacing(20);
        hbox.setAlignment(Pos.CENTER);
        VBox centerVBox = new VBox(jordleLbl, grid, hbox);
        centerVBox.setAlignment(Pos.CENTER);
        gameScreen.setCenter(centerVBox);
        Button sceneMode = new Button(darkMode ? "Light Mode" : "Dark Mode");
        sceneMode.setFont(Font.font("Verdana", 12));
        sceneMode.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Changes the scene fro light to dark or dark to light mode when the button is clicked.
             * @param e the event occuring at this button (a button press)
             * */
            @Override
            public void handle(ActionEvent e) {
                sceneMode(gameScreen, txtBoxes, sceneMode, outputLbl, jordleLbl);
                grid.requestFocus();
            }
        });
        gameScreen.setTop(sceneMode);
        gameScene = new Scene(gameScreen, 750, 500);
        primaryStage.setTitle("Jordle");
        Backend backend = new Backend();
        guesses = 0;
        gameOver = false;
        Cursor cursor = new Cursor();
        gameScene.setOnKeyPressed(event -> {
            grid.requestFocus();
            if (!gameOver) {
                if (event.getCode() == KeyCode.ENTER) {
                    String guess = getGuess(cursor, txtBoxes);
                    try {
                        String checkedGuess = backend.check(guess);
                        changeTxtBoxes(cursor, checkedGuess, txtBoxes);
                        String target = backend.getTarget();
                        ++guesses;
                        gameOver = checkGameOver(guess, target, outputLbl);
                        if (gameOver) {
                            StatsWindow stats = new StatsWindow();
                            stats.setStat(target, guess.equalsIgnoreCase(target), guesses);
                            VBox endGame = new VBox();
                            endGame.setPrefSize(500, 200);
                            Label endGameTitle = new Label("The word was: " + backend.getTarget());
                            endGameTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                            Button goToStatsBtn = new Button("See Statistics");
                            goToStatsBtn.setFont(Font.font("Verdana", 12));
                            goToStatsBtn.setOnAction(new EventHandler<ActionEvent>() {
                                /**
                                 * Displays the statistics for the target word.
                                 * @param e the event occuring at the button (a button press)
                                 * */
                                @Override
                                public void handle(ActionEvent e) {
                                    stats.displayStats(target, primaryStage);
                                }
                            });
                            endGame.getChildren().addAll(endGameTitle, goToStatsBtn);
                            endGame.setAlignment(Pos.CENTER);
                            endGame.setSpacing(20);
                            Scene endGameScene = new Scene(endGame, 500, 200);
                            Stage endGameStage = new Stage();
                            endGameStage.setTitle("Game Complete");
                            endGameStage.setScene(endGameScene);
                            endGameStage.show();
                        }
                        cursor.nextRow();
                    } catch (InvalidGuessException iGE) {
                        Alert error = new Alert(AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText("Error");
                        error.setContentText("Invalid guess! Please enter a 5-letter word.");
                        error.show();
                    }
                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    if (((TextField) txtBoxes.get(cursor.getIndex())).getText().isBlank()) {
                        cursor.moveCursor(-1);
                    }
                    ((TextField) txtBoxes.get(cursor.getIndex())).setText("");
                    cursor.moveCursor(-1);
                }
                String keyText = event.getText();
                String lowerCase = keyText.toLowerCase();
                if (lowerCase.compareTo("a") >= 0 && lowerCase.compareTo("z") <= 0) {
                    if (!((TextField) txtBoxes.get(cursor.getIndex())).getText().isBlank()) {
                        cursor.moveCursor(1);
                    }
                    ((TextField) txtBoxes.get(cursor.getIndex())).setText(keyText);
                    cursor.moveCursor(1);
                }
            }
        });
        grid.requestFocus();
    }
    private String getInstructionsText() {
        return "Welcome to Jordle! Your goal is to guess the correct five-letter word. Type a five-letter"
            + " word on your keyboard and press ENTER to guess the word. The boxes containing the letters"
            + " of your guess will then change color to indicate whether that letter is in the"
            + " target word. Gray indicates that the letter is not in the word. Yellow indicates"
            + " that the letter is in the target word, but in a different position. Green"
            + " indicates that the letter is in the target word and in the correct position."
            + " You can only guess five-letter words and you only have six guesses. Pressing the"
            + " \"Restart\" button will restart the entire game and generate a new target word."
            + " Happy solving and good luck!";
    }
    /**
     * Changes the scene from dark to light mode or light to dark mode.
     * @param gameScreen the BorderPane containing all of our nodes
     * @param txtBoxes the list of text fields
     * @param sceneMode the button that changes the screen from dark to light or light to dark mode
     * @param outputLbl the label that will change display depending on the result of the game
     * @param jordleLbl the label at the top of the screen that displays "Jordle"
     * */
    private void sceneMode(BorderPane gameScreen, ObservableList<Node> txtBoxes, Button sceneMode, Label outputLbl,
                            Label jordleLbl) {
        darkMode = !darkMode;
        gameScreen.setStyle("-fx-background-color: " + (darkMode ? "#404040;" : " #e6e6e6;"));
        changeBoxColor(txtBoxes);
        sceneMode.setText(darkMode ? "Light Mode" : "Dark Mode");
        outputLbl.setStyle("-fx-text-fill: " + (darkMode ? "#ffffff;" : "#404040;"));
        jordleLbl.setStyle("-fx-text-fill: " + (darkMode ? "#ffffff;" : "#404040;"));
    }
    /**
     * Changes the color of the boxes when the user changes the screen to dark or light mode.
     * @param txtBoxes the list of text fields
     * */
    private void changeBoxColor(ObservableList<Node> txtBoxes) {
        for (int i = 0; i < txtBoxes.size(); i++) {
            if (isDefault(((TextField) txtBoxes.get(i)).getStyle())) {
                txtBoxes.get(i).setStyle("-fx-background-color: " + (darkMode ? "#808080;" : "#ffffff;"));
            }
            txtBoxes.get(i).setStyle(txtBoxes.get(i).getStyle() + "-fx-text-fill: "
                                    + (darkMode ? "#ffffff;" : "#808080;"));
        }
    }
    /**
     * Checks if the background of a text field is the default.
     * @param style the style of a text field
     * @return whether the text field has a default background
     * */
    private boolean isDefault(String style) {
        return !(style.contains("-fx-background-color: #cccccc;") || style.contains("-fx-background-color: #ffe680;")
            || style.contains("-fx-background-color: #bbff99;"));
    }
    /**
     * Creates 6 x 5 GridPane to be displayed in the center of the screen.
     * @param grid the GridPane being manipulated to contain 6 rows and 5 columns
     * */
    private void createGrid(GridPane grid) {
        int numCols = 5;
        int numRows = 6;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(30.0 / numCols);
            grid.getColumnConstraints().add(colConstraints);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(75.0 / numRows);
            grid.getRowConstraints().add(rowConstraints);
        }
        grid.setAlignment(Pos.CENTER);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                TextField txt = new TextField();
                txt.setMaxWidth(50);
                txt.setMinHeight(50);
                txt.setAlignment(Pos.CENTER);
                txt.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
                txt.setStyle("-fx-background-color: " + (darkMode ? "#808080;" : "#ffffff;")
                            + "-fx-text-fill: " + (darkMode ? "#ffffff;" : "#808080;"));
                txt.setMouseTransparent(true);
                txt.setFocusTraversable(false);
                grid.add(txt, j, i);
            }
        }
    }
    /**
     * Checks if the game is over by seeing if the user has won the game by guessing the word correctly,
     * or ran out of guesses.
     * @param guess the last guess the user made
     * @param target the target word
     * @param outputLbl the label that will change display depending on the result of the game
     * @return a boolean of if the game is over
     * */
    private boolean checkGameOver(String guess, String target, Label outputLbl) {
        if (guess.equalsIgnoreCase(target)) {
            outputLbl.setText("Congratulations! You've guessed the word!");
            return true;
        } else if (guesses >= 6) {
            outputLbl.setText(String.format("Game over. The word was %s.", target));
            return true;
        }
        return false;
    }
    /**
     * Gets the guess from the boxes.
     * @param cursor the cursor keeping track of which text field we are currently on
     * @param txtBoxes the list of text fields
     * @return the guess from the user input
     * */
    private String getGuess(Cursor cursor, ObservableList<Node> txtBoxes) {
        String guess = "";
        for (int i = cursor.getRow() * 5; i < cursor.getRow() * 5 + 5; i++) {
            guess += ((TextField) txtBoxes.get(i)).getText();
        }
        return guess;
    }
    /**
     * Changes the color of the text boxes depending on if the letter is contained in the target word
     * and the position of the letter.
     * @param cursor the cursor keeping track of which text field we are currently on
     * @param checkedGuess a string that indicates which letters were correct and if they were in the correct position
     * @param txtBoxes the list of text fields
     * */
    private void changeTxtBoxes(Cursor cursor, String checkedGuess, ObservableList<Node> txtBoxes) {
        for (int i = 0; i < 5; i++) {
            switch (checkedGuess.charAt(i)) {
            case 'y':
                ((TextField) txtBoxes.get(cursor.getRow() * 5 + i)).setStyle(txtBoxes.get(
                    cursor.getRow() * 5 + i).getStyle()
                    + "-fx-background-color: #ffe680;");
                break;
            case 'g':
                ((TextField) txtBoxes.get(cursor.getRow() * 5 + i)).setStyle(txtBoxes.get(
                    cursor.getRow() * 5 + i).getStyle()
                    + "-fx-background-color: #bbff99;");
                break;
            default:
                ((TextField) txtBoxes.get(cursor.getRow() * 5 + i)).setStyle(txtBoxes.get(
                    cursor.getRow() * 5 + i).getStyle()
                    + "-fx-background-color: #cccccc;");
                break;
            }
        }
    }
    /**
     * Creates a Cursor to track which box the letter should be typed in or deleted from.
     * @author Katie Engel
     * @version 1.0
     * */
    private class Cursor {
        private int index;
        private int row;
        /**
         * Constructor for a cursor object that keeps track of the index and the row it is on.
         * Both the index and row are initialized to 0.
         * */
        Cursor() {
            index = 0;
            row = 0;
        }
        /**
         * Moves the cursor by the value of the paramater move, but the cursor cannot go farther
         * than the current row in either direction.
         * @param move how much to move the cursor by
         * */
        public void moveCursor(int move) {
            if (index < 5) {
                index += move;
                if (index < 0) {
                    index = 0;
                } else if (index > 4) {
                    index = 4;
                }
            }
        }
        /**
         * Moves the cursor to the next row.
         * */
        public void nextRow() {
            row++;
            index = 0;
        }
        /**
         * Gets the index corresponding to the text field that the cursor is on.
         * @return the index of the text field the cursor ison
         * */
        public int getIndex() {
            return row * 5 + index;
        }
        /**
         * Gets the row that the cursor is on.
         * @return the row the cursor is on
         * */
        public int getRow() {
            return row;
        }
    }
}
