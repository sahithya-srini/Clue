import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    public static final int BOARD_SIZE = 13;
    public static final int CELL_SIZE = 30;
    public static final int ROOM_GRID_SIZE = 3;

    private GridPane grid;
    private ArrayList<Room> rooms = new ArrayList<>();

    public Board() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        setupRooms();
        drawBoard();
    }

    private void setupRooms() {
        rooms.add(new Room("Despicable Me", 0, 0, 2, 2, "file:Resources/DespicableMe.jpeg"));
        rooms.add(new Room("Tangled", 10, 0, 12, 2, "file:Resources/Tangled.jpeg"));
        rooms.add(new Room("Mission Impossible", 5, 0, 7, 2, "file:Resources/Mission_Impossible.jpeg"));
        rooms.add(new Room("Mean Girls", 5, 10, 7, 12, "file:Resources/Mean_Girls.jpeg"));
        rooms.add(new Room("Avengers", 0, 10, 2, 12, "file:Resources/Avengers.jpeg"));
        rooms.add(new Room("Cars", 10, 10, 12, 12, "file:Resources/Cars.jpeg"));
    }

    private void drawBoard() {
        grid.getChildren().clear();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Room room = getRoomAt(row, col);
                if (room != null && room.isRoomStart(row, col)) {
                    // Load the JPEG image from file path or resource
                    System.out.println("Printing room " + room.getName() + " at " + row + " " + col);
                    Image image = new Image(room.getImageSrc());
                    javafx.scene.image.ImageView imgView = new javafx.scene.image.ImageView(image);
                    imgView.setFitWidth(CELL_SIZE * ROOM_GRID_SIZE);
                    imgView.setFitHeight(CELL_SIZE * ROOM_GRID_SIZE);
                    grid.add(imgView, col, row, ROOM_GRID_SIZE, ROOM_GRID_SIZE);
                } else if (room == null) {
                    Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                    rect.setFill(Color.WHITE);
                    rect.setStroke(Color.GRAY);      
                    rect.setStrokeWidth(1);          
                    grid.add(rect, col, row, 1, 1);
                }
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
