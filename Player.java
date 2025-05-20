import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Player {
    private String name;
    private int row, col;
    private Circle token;
    private ArrayList<String> cards = new ArrayList<>();


    public Player(String name, Color color, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.token = new Circle(Board.CELL_SIZE / 2.5);
        this.token.setFill(color);
    }

    public ArrayList<String> getCards() {
        return cards;
    }
    public void addCard(String card) {
        cards.add(card);
    }
    public String getName() { return name; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public Circle getToken() { return token; }

}
