import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int BOARD_SIZE = 13;
    public static final int CELL_SIZE = 30;

    private GridPane grid;
    private List<Room> rooms = new ArrayList<>();

    public Board() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        setupRooms();
        drawBoard();
    }

    private void setupRooms() {
        rooms.add(new Room("Lavender Room", Color.LAVENDER, 0, 0, 2, 2));
        rooms.add(new Room("Blue Room", Color.LIGHTBLUE, 10, 0, 12, 2));
        rooms.add(new Room("Blush Room", Color.LAVENDERBLUSH, 5, 0, 7, 2));
        rooms.add(new Room("White Blue Room", Color.ALICEBLUE, 5, 10, 7, 12));
        rooms.add(new Room("Green Room", Color.LIGHTGREEN, 0, 10, 2, 12));
        rooms.add(new Room("Coral Room", Color.LIGHTCORAL, 10, 10, 12, 12));
    }

    private void drawBoard() {
        grid.getChildren().clear();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                Room room = getRoomAt(row, col);
                if (room != null) {
                    rect.setFill(room.getColor());
                } else if ((row + col) % 2 == 0) {
                    rect.setFill(Color.LIGHTGRAY);
                } else {
                    rect.setFill(Color.WHITE);
                }
                grid.add(rect, col, row);
            }
        }
    }

    public Room getRoomAt(int row, int col) {
        for (Room room : rooms) {
            if (room.isInRoom(row, col)) {
                return room;
            }
        }
        return null;
    }

    public GridPane getGridPane() {
        return grid;
    }

    public void addPlayerToken(Player player, Circle token) {
        grid.add(token, player.getCol(), player.getRow());
    }

    public void movePlayerToken(Player player, Circle token, int newRow, int newCol) {
        grid.getChildren().remove(token);
        grid.add(token, newCol, newRow);
    }
}
