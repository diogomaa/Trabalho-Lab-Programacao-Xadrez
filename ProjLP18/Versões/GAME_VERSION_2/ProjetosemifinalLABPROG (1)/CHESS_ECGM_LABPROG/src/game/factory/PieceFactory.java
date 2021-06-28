package game.factory;

import game.model.*;


public class PieceFactory {
    public static Piece getPiece(String name, String color) {
        switch (name) {
            case "bishop":
                return new Bishop(color);
            case "king":
                return new King(color);
            case "knight":
                return new Knight(color);
            case "pawn":
                return new Pawn(color);
            case "queen":
                return new Queen(color);
            case "rook":
                return new Rook(color);
        }
        return null;
    }
}
