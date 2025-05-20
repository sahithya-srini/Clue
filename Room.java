import javafx.scene.paint.Color;

public class Room {
    private String name;
    private Color color;
    private int rowStart, colStart, rowEnd, colEnd;

    public Room(String name, Color color, int rowStart, int colStart, int rowEnd, int colEnd) {
        this.name = name;
        this.color = color;
        this.rowStart = rowStart;
        this.colStart = colStart;
        this.rowEnd = rowEnd;
        this.colEnd = colEnd;
    }

    public String getName() { return name; }
    public Color getColor() { return color; }

    public boolean isInRoom(int row, int col) {
        return row >= rowStart && row <= rowEnd && col >= colStart && col <= colEnd;
    }
}
