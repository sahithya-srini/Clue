import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private GameController gameController;
    private Label infoLabel;
    private TextField guessField;


    public void start(Stage primaryStage) {
        infoLabel = new Label("Welcome to Clue Game.");
        guessField = new TextField();
        guessField.setPromptText("Enter suspect guess");

        Board board = new Board();

        HBox rootPane = new HBox(20); // or 10??
//        rootPane.setPadding(new Insets(10)); // needed??

        VBox gameParts = new VBox(10);
        gameParts.getChildren().addAll(infoLabel, board.getGridPane(), guessField);
        gameParts.setPrefSize(Board.BOARD_SIZE * Board.CELL_SIZE + 100, Board.BOARD_SIZE * Board.CELL_SIZE + 150);
        //change sizing??
        rootPane.getChildren().add(gameParts);

        gameController = new GameController(board, infoLabel, guessField, rootPane);
        gameController.setupUI();

        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Clue Game");
        primaryStage.show();

        gameController.startGame(scene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
