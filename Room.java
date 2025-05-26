import javafx.scene.paint.Color;

public class Room {
    private String name;
    private int rowStart, colStart, rowEnd, colEnd;
    private String imageSrc;

    public Room(String name, int rowStart, int colStart, int rowEnd, int colEnd, String imageSrc) {
        this.name = name;
        this.rowStart = rowStart;
        this.colStart = colStart;
        this.rowEnd = rowEnd;
        this.colEnd = colEnd;
        this.imageSrc = imageSrc;
    }

    public String getName() { return name; }

    public String getImageSrc() { return imageSrc; }

    public boolean isRoomStart(int row, int col) {
        return row == rowStart && col == colStart;
    }

    public boolean isInRoom(int row, int col) {
        return row >= rowStart && row <= rowEnd && col >= colStart && col <= colEnd;
    }
}
