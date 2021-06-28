package game.model;


public class Move {
    private int oldRow;
    private int oldCol;
    private int newRow;
    private int newCol;

    public Move(int oldRow, int oldCol, int newRow, int newCol) {
        this.oldRow = oldRow;
        this.oldCol = oldCol;
        this.newRow = newRow;
        this.newCol = newCol;
    }

    public Move(String description) {
        String[] s = description.split("_");
        this.oldRow = Integer.parseInt(s[0]);
        this.oldCol = Integer.parseInt(s[1]);
        this.newRow = Integer.parseInt(s[2]);
        this.newCol = Integer.parseInt(s[3]);
    }

    public int getOldRow() {
        return oldRow;
    }

    public int getOldCol() {
        return oldCol;
    }

    public int getNewRow() {
        return newRow;
    }

    public int getNewCol() {
        return newCol;
    }

    

  
}
