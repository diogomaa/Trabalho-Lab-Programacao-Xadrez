package game.model;

import game.factory.PieceFactory;

import javafx.scene.layout.Pane;


public class Boardpiece  {
    public static final String DARK = "dark";
    public static final String LIGHT = "light";

    private ChessBoard board;
    private Pane pane;
    private String color;
    private Piece piece;
    private int row, col;

    public Boardpiece(ChessBoard board, int row, int col) {
        this.board = board;
        this.row = row;
        this.col = col;
        this.pane = new Pane();
        if ((row % 2 == 0 && col % 2 == 1) || (row % 2 == 1 && col % 2 == 0)) {
            this.color = DARK;
            this.pane.setStyle("-fx-background-color: #1389C8");
        } else {
            this.color = LIGHT;
            this.pane.setStyle("-fx-background-color: #F37723");
        }

    
    }




    public boolean hasPiece() {
        return (this.piece != null);
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
     
        this.pane.getChildren().add(piece.getImage());

    }

    public void removePiece() {
        this.pane.getChildren().remove(this.piece.getImage());
        this.piece = null;
    }

    public Pane getPane() {
        return this.pane;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
