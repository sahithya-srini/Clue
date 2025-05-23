import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

        VBox rootPane = new VBox(10);
        rootPane.getChildren().addAll(infoLabel, board.getGridPane(), guessField);
        rootPane.setPrefSize(Board.BOARD_SIZE * Board.CELL_SIZE + 100, Board.BOARD_SIZE * Board.CELL_SIZE + 150);

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
